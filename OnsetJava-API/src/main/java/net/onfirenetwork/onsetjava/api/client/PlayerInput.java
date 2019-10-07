package net.onfirenetwork.onsetjava.api.client;

import net.onfirenetwork.onsetjava.api.entity.Player;
import net.onfirenetwork.onsetjava.api.enums.InputMode;
import net.onfirenetwork.onsetjava.api.util.Completable;
import net.onfirenetwork.onsetjava.api.util.Vector2d;
import net.onfirenetwork.onsetjava.api.util.Vector2i;

public interface PlayerInput {

    Player getPlayer();

    void showChat(boolean show);

    void setChatPosition(Vector2d position);

    Vector2d getChatPosition();

    Completable<Boolean> isChatFocused();

    void showCursor(boolean show);

    Completable<Boolean> isCursorEnabled();

    void setInputMode(InputMode mode);

    Completable<Vector2i> getMousePosition();

    void setMousePosition(int x, int z);

    default void setMousePosition(Vector2i position){
        setMousePosition(position.getX(), position.getY());
    }

    Completable<Boolean> isCtrlPressed();

    Completable<Boolean> isShiftPressed();

    Completable<Boolean> isCmdPressed();

    Completable<Boolean> isAltPressed();

    void setIgnoreMoveInput(boolean ignore);

    void setIgnoreLookInput(boolean ignore);

}
