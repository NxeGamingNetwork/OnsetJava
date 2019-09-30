package net.onfirenetwork.onsetjava.simple.entity;

import com.google.gson.JsonElement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.onfirenetwork.onsetjava.api.Dimension;
import net.onfirenetwork.onsetjava.api.client.PlayerVehicle;
import net.onfirenetwork.onsetjava.api.client.Sound;
import net.onfirenetwork.onsetjava.api.client.TextBox;
import net.onfirenetwork.onsetjava.api.client.WebUI;
import net.onfirenetwork.onsetjava.api.entity.NPC;
import net.onfirenetwork.onsetjava.api.entity.Player;
import net.onfirenetwork.onsetjava.api.entity.Vehicle;
import net.onfirenetwork.onsetjava.api.entity.WorldObject;
import net.onfirenetwork.onsetjava.api.enums.CharacterAnimation;
import net.onfirenetwork.onsetjava.api.enums.CharacterModel;
import net.onfirenetwork.onsetjava.api.enums.PlayerState;
import net.onfirenetwork.onsetjava.api.enums.WeaponModel;
import net.onfirenetwork.onsetjava.api.util.*;
import net.onfirenetwork.onsetjava.simple.SimpleDimension;
import net.onfirenetwork.onsetjava.simple.client.*;
import net.onfirenetwork.onsetjava.simple.util.JsonUtils;

import java.util.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimplePlayer implements Player {
    @Getter
    SimpleDimension dimension;
    @Getter
    SimplePlayerGraphics graphics = new SimplePlayerGraphics(this);
    @Getter
    SimplePlayerInput input = new SimplePlayerInput(this);
    @Getter
    int id;
    @Getter
    CharacterModel model = CharacterModel.ORANGE_SHIRT_1;
    String steamId;
    String name;
    @Getter
    boolean voiceEnabled = true;
    List<WebUI> webuis = new ArrayList<>();
    List<Sound> sounds = new ArrayList<>();
    @Getter
    List<TextBox> textBoxes = new ArrayList<>();
    Map<String, Object> attributes = new HashMap<>();
    Map<Vehicle, PlayerVehicle> playerVehicles = new HashMap<>();

    public SimplePlayer(Dimension dimension, int id) {
        this.dimension = (SimpleDimension) dimension;
        this.id = id;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = (SimpleDimension) dimension;
        this.dimension.getServer().call("SetPlayerDimension", id, dimension.getId()).get();
    }

    public String getSteamId() {
        if (steamId == null) {
            steamId = dimension.getServer().call("GetPlayerSteamId", id).get()[0].getAsString();
        }
        return steamId;
    }

    public String getName() {
        if (name == null) {
            name = dimension.getServer().call("GetPlayerName", id).get()[0].getAsString();
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
        dimension.getServer().call("SetPlayerName", id, name);
    }

    public NetworkStats getNetworkStats() {
        return new NetworkStats(dimension.getServer().call("GetPlayerNetworkStats", id).get()[0].getAsJsonObject());
    }

    public Location getLocation() {
        Vector3d loc = JsonUtils.toVector(dimension.getServer().call("GetPlayerLocation", id).get());
        double heading = dimension.getServer().call("GetPlayerHeading", id).get()[0].getAsDouble();
        return new Location(loc, heading);
    }

    public void setLocation(Location location) {
        dimension.getServer().call("SetPlayerLocation", id, location.getX(), location.getY(), location.getZ()).get();
        dimension.getServer().call("SetPlayerHeading", id, location.getHeading()).get();
    }

    public void setSpawnLocation(Location location) {
        dimension.getServer().call("SetPlayerSpawnLocation", id, location.getX(), location.getY(), location.getZ(), location.getHeading()).get();
    }

    public double getHealth() {
        return dimension.getServer().call("GetPlayerHealth", id).get()[0].getAsDouble();
    }

    public void setHealth(double health) {
        dimension.getServer().call("SetPlayerHealth", id, health);
    }

    public double getArmor() {
        return dimension.getServer().call("GetPlayerArmor", id).get()[0].getAsDouble();
    }

    public void setArmor(double armor) {
        dimension.getServer().call("SetPlayerArmor", id, armor);
    }

    public void exitVehicle() {
        dimension.getServer().call("RemovePlayerFromVehicle", id).get();
    }

    public void enterVehicle(Vehicle vehicle) {
        dimension.getServer().call("SetPlayerInVehicle", id, vehicle.getId()).get();
    }

    public void enterVehicle(Vehicle vehicle, int seat) {
        dimension.getServer().call("SetPlayerInVehicle", id, vehicle.getId(), seat).get();
    }

    public int getVehicleSeat() {
        return dimension.getServer().call("GetPlayerVehicleSeat", id).get()[0].getAsInt();
    }

    public void setVoiceEnabled(boolean enable) {
        voiceEnabled = enable;
        dimension.getServer().call("SetPlayerVoiceEnabled", id, enable);
    }

    public void sendMessage(String message) {
        dimension.getServer().call("AddPlayerChat", id, message);
    }

    public Vehicle getVehicle() {
        JsonElement vid = dimension.getServer().call("GetPlayerVehicle", id).get()[0];
        return JsonUtils.preCheckAndRun(vid, () -> dimension.getServer().getVehicle(vid.getAsInt()));
    }

    public void kick(String message) {
        dimension.getServer().call("KickPlayer", id, message);
    }

    public WebUI getWebUI(int id) {
        return webuis.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }

    public List<WebUI> getWebUIs() {
        return webuis;
    }

    public Completable<WebUI> createWebUI(Vector2d position, Vector2d size, int zOrder, int frameRate) {
        Completable<WebUI> completable = new Completable<>();
        dimension.getServer().callClient(id, "CreateWebUI", position.getX(), position.getY(), size.getX(), size.getY(), zOrder, frameRate).then(ret -> {
            WebUI ui = new SimpleWebUI(this, ret[0].getAsInt());
            webuis.add(ui);
            completable.complete(ui);
        });
        return completable;
    }

    public Completable<WebUI> create3DWebUI(Location location, Vector3d rotation, Vector2d size, int frameRate) {
        Completable<WebUI> completable = new Completable<>();
        dimension.getServer().callClient(id, "CreateWebUI3D", location.getX(), location.getY(), location.getY(), rotation.getX(), rotation.getY(), rotation.getZ(), size.getX(), size.getY(), frameRate).then(ret -> {
            WebUI ui = new SimpleWebUI(this, ret[0].getAsInt());
            webuis.add(ui);
            completable.complete(ui);
        });
        return completable;
    }

    public Sound getSound(int id) {
        return sounds.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }

    public List<Sound> getSounds() {
        return sounds;
    }

    public Completable<Sound> createSound(String soundFile, boolean loop) {
        Completable<Sound> completable = new Completable<>();
        dimension.getServer().callClient(id, "CreateSound", soundFile, loop).then(ret -> {
            Sound sound = new SimpleSound(this, ret[0].getAsInt());
            sounds.add(sound);
            completable.complete(sound);
        });
        return completable;
    }

    public Completable<Sound> create3DSound(Location location, String soundFile, double radius, boolean loop) {
        Completable<Sound> completable = new Completable<>();
        dimension.getServer().callClient(id, "CreateSound3D", soundFile, location.getX(), location.getY(), location.getZ(), radius, loop).then(ret -> {
            Sound sound = new SimpleSound(this, ret[0].getAsInt());
            sounds.add(sound);
            completable.complete(sound);
        });
        return completable;
    }

    public boolean isTalking() {
        return dimension.getServer().call("IsPlayerTalking", id).get()[0].getAsBoolean();
    }

    public PlayerState getState() {
        return PlayerState.get(dimension.getServer().call("GetPlayerState", id).get()[0].getAsInt());
    }

    public int getMovementMode() {
        return dimension.getServer().call("GetPlayerMovementMode", id).get()[0].getAsInt();
    }

    public double getSpeed() {
        return dimension.getServer().call("GetPlayerMovementSpeed", id).get()[0].getAsDouble();
    }

    public boolean isAiming() {
        return dimension.getServer().call("IsPlayerAiming", id).get()[0].getAsBoolean();
    }

    public boolean isReloading() {
        return dimension.getServer().call("IsPlayerReloading", id).get()[0].getAsBoolean();
    }

    public void setWeaponStat(WeaponModel weapon, int index, double value) {
        dimension.getServer().call("SetPlayerWeaponStat", id, weapon.getId(), index, value);
    }

    public void setWeapon(int slot, WeaponModel weapon, int ammo, boolean equip) {
        dimension.getServer().call("SetPlayerWeapon", id, weapon.getId(), ammo, equip, slot);
    }

    public WeaponModel getWeapon(int slot) {
        return WeaponModel.getModel(dimension.getServer().call("GetPlayerWeapon", id, slot).get()[0].getAsInt());
    }

    public WeaponModel getWeapon() {
        return WeaponModel.getModel(dimension.getServer().call("GetPlayerWeapon", id).get()[0].getAsInt());
    }

    public int getWeaponSlot() {
        return dimension.getServer().call("GetPlayerEquippedWeaponSlot", id).get()[0].getAsInt();
    }

    public void setWeaponSlot(int slot) {
        dimension.getServer().call("EquipPlayerWeaponSlot", id, slot);
    }

    public WeaponModel getEquippedWeapon() {
        return WeaponModel.getModel(dimension.getServer().call("GetPlayerEquippedWeapon", id).get()[0].getAsInt());
    }

    public void setSpectate(boolean spectate) {
        dimension.getServer().call("SetPlayerSpectate", id, spectate);
    }

    public void resetCamera() {
        dimension.getServer().call("ResetPlayerCamera", id);
    }

    public boolean isDead() {
        return dimension.getServer().call("IsPlayerDead", id).get()[0].getAsBoolean();
    }

    public void setRespawnTime(int time) {
        dimension.getServer().call("SetPlayerRespawnTime", id, time);
    }

    public int getRespawnTime() {
        return dimension.getServer().call("GetPlayerRespawnTime", id).get()[0].getAsInt();
    }

    public void setModel(CharacterModel model) {
        this.model = model;
        dimension.getServer().call("SetPlayerModel", id, model.getId());
    }

    public String getAddress() {
        return dimension.getServer().call("GetPlayerIP", id).get()[0].getAsString();
    }

    public int getPing() {
        return dimension.getServer().call("GetPlayerPing", id).get()[0].getAsInt();
    }

    public String getLocale() {
        return dimension.getServer().call("GetPlayerLocale", id).get()[0].getAsString();
    }

    public String getGUID() {
        return dimension.getServer().call("GetPlayerGUID", id).get()[0].getAsString();
    }

    public void startAnimation(CharacterAnimation animation) {
        dimension.getServer().call("SetPlayerAnimation", id, animation.name());
    }

    public void stopAnimation() {
        dimension.getServer().call("SetPlayerAnimation", id, "STOP");
    }

    public void setParachute(boolean parachute) {
        dimension.getServer().call("AttachPlayerParachute", id, parachute);
    }

    public void setHeadSize(double size) {
        dimension.getServer().call("SetPlayerHeadSize", id, size);
    }

    public double getHeadSize() {
        return dimension.getServer().call("GetPlayerHeadSize", id).get()[0].getAsDouble();
    }

    public Completable<TextBox> createTextBox(double x, double y, String text, TextBox.Justification justification){
        Completable<TextBox> completable = new Completable<>();
        dimension.getServer().callClient(id, "CreateTextBox", x, y, text, justification.name().toLowerCase(Locale.ENGLISH)).then(ret -> {
            TextBox textBox = new SimpleTextBox(this, ret[0].getAsInt());
            textBoxes.add(textBox);
            completable.complete(textBox);
        });
        return completable;
    }

    public boolean isPlayerStreamedIn(Player otherPlayer) {
        return dimension.getServer().call("IsPlayerStreamedIn", id, otherPlayer.getId()).get()[0].getAsBoolean();
    }

    public boolean isVehicleStreamedIn(Vehicle vehicle) {
        return dimension.getServer().call("IsVehicleStreamedIn", id, vehicle.getId()).get()[0].getAsBoolean();
    }

    public boolean isNPCStreamedIn(NPC npc) {
        return dimension.getServer().call("IsNPCStreamedIn", id, npc.getId()).get()[0].getAsBoolean();
    }

    public boolean isObjectStreamedIn(WorldObject object) {
        return dimension.getServer().call("IsObjectStreamedIn", id, object.getId()).get()[0].getAsBoolean();
    }

    public List<Player> getStreamedPlayers() {
        return JsonUtils.toList(dimension.getServer().call("GetStreamedPlayersForPlayer", id).get()[0].getAsJsonArray(), e -> dimension.getServer().getPlayer(e.getAsInt()));
    }

    public List<Vehicle> getStreamedVehicles() {
        return JsonUtils.toList(dimension.getServer().call("GetStreamedVehiclesForPlayer", id).get()[0].getAsJsonArray(), e -> dimension.getServer().getVehicle(e.getAsInt()));
    }

    public PlayerVehicle getClientVehicle(Vehicle vehicle) {
        if (playerVehicles.containsKey(vehicle)) {
            return playerVehicles.get(vehicle);
        }
        SimplePlayerVehicle playerVehicle = new SimplePlayerVehicle(this, vehicle);
        playerVehicles.put(vehicle, playerVehicle);
        return playerVehicle;
    }

    public void setWaypoint(int slot, Location location, String text){
        dimension.getServer().callClient(id, "SetWaypoint", slot, text, location.getX(), location.getY(), location.getZ());
    }

    public void removeWaypoint(int slot){
        dimension.getServer().callClient(id, "SetWaypoint", slot, "", 0, 0, 0);
    }

    public void showWeaponInfo(boolean show){
        dimension.getServer().callClient(id, "ShowWeaponHUD", show);
    }

    public void showHealthInfo(boolean show){
        dimension.getServer().callClient(id, "ShowHealthHUD", show);
    }

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    public <T> T getAttribute(String key) {
        return (T) attributes.get(key);
    }
}
