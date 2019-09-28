package net.onfirenetwork.onsetjava.api.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Getter
public enum WebVisibility {
    HIDDEN(0),
    VISIBLE(1),
    NO_INTERACTION(2);
    int identifier;
    public static WebVisibility get(int value){
        for(WebVisibility visibility: values()){
            if(visibility.identifier == value){
                return visibility;
            }
        }
        return null;
    }
}
