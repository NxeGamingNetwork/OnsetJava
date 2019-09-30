package net.onfirenetwork.onsetjava.simple.client;

import com.google.gson.JsonElement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.onfirenetwork.onsetjava.api.client.PlayerInput;
import net.onfirenetwork.onsetjava.api.util.Completable;
import net.onfirenetwork.onsetjava.api.util.Vector2d;
import net.onfirenetwork.onsetjava.simple.entity.SimplePlayer;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimplePlayerInput implements PlayerInput {

    @Getter
    SimplePlayer player;

    public SimplePlayerInput(SimplePlayer player){
        this.player = player;
    }

    Vector2d chatPosition = new Vector2d(0, 0);

    public void showChat(boolean show) {
        call("ShowChat", show);
    }

    public void setChatPosition(Vector2d position) {
        this.chatPosition = position;
        call("SetChatLocation", position.getX(), position.getY());
    }

    public Vector2d getChatPosition() {
        return chatPosition;
    }

    public Completable<Boolean> isChatFocused(){
        Completable<Boolean> completable = new Completable<>();
        call("IsChatFocused").then(ret -> completable.complete(ret[0].getAsBoolean()));
        return completable;
    }

    public void showCursor(boolean show){
        call("ShowMouseCursor", show);
    }

    public Completable<Boolean> isCursorEnabled(){
        Completable<Boolean> completable = new Completable<>();
        call("IsMouseCursorEnabled").then(ret -> completable.complete(ret[0].getAsBoolean()));
        return completable;
    }

    public void setInputMode(int mode){
        call("SetInputMode", mode);
    }

    public Completable<Vector2d> getMousePosition(){
        Completable<Vector2d> completable = new Completable<>();
        call("GetMouseLocation").then(ret -> completable.complete(new Vector2d(ret[0].getAsDouble(), ret[1].getAsDouble())));
        return completable;
    }

    public Completable<Boolean> isCtrlPressed(){
        Completable<Boolean> completable = new Completable<>();
        call("IsCtrlPressed").then(ret -> completable.complete(ret[0].getAsBoolean()));
        return completable;
    }

    public Completable<Boolean> isShiftPressed(){
        Completable<Boolean> completable = new Completable<>();
        call("IsShiftPressed").then(ret -> completable.complete(ret[0].getAsBoolean()));
        return completable;
    }

    public void setIgnoreMoveInput(boolean ignore){
        call("SetIgnoreMoveInput", ignore);
    }

    public void setIgnoreLookInput(boolean ignore){
        call("SetIgnoreLookInput", ignore);
    }

    private Completable<JsonElement[]> call(String name, Object... params) {
        return player.getDimension().getServer().callClient(player.getId(), name, params);
    }

}
