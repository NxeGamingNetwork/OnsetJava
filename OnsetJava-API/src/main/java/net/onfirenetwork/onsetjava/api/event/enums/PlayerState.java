package net.onfirenetwork.onsetjava.api.event.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Getter
public enum PlayerState {
    NONE(0),
	ONFOOT(1),
	DRIVER(2),
	PASSENGER(3),
	ENTER_VEHICLE(4),
	EXIT_VEHICLE(5);
    int identifier;
    public static PlayerState get(int value){
        for(PlayerState state: values()){
            if(state.identifier == value){
                return state;
            }
        }
        return null;
    }
}
