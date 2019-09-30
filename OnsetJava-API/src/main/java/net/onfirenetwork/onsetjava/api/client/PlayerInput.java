package net.onfirenetwork.onsetjava.api.client;

import net.onfirenetwork.onsetjava.api.entity.Player;
import net.onfirenetwork.onsetjava.api.util.Completable;
import net.onfirenetwork.onsetjava.api.util.Vector2d;

public interface PlayerInput {

    Player getPlayer();

    void showChat(boolean show);

    void setChatPosition(Vector2d position);

    Vector2d getChatPosition();

    Completable<Boolean> isChatFocused();

    void showCursor(boolean show);

    Completable<Boolean> isCursorEnabled();

    void setInputMode(int mode);

    Completable<Vector2d> getMousePosition();

    Completable<Boolean> isCtrlPressed();

    Completable<Boolean> isShiftPressed();

    void setIgnoreMoveInput(boolean ignore);

    void setIgnoreLookInput(boolean ignore);

}
