package net.onfirenetwork.onsetjava.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum InputMode {
    GAME(0),
	GAME_AND_UI(1),
	UI(2);
    @Getter
    int identifier;
}
