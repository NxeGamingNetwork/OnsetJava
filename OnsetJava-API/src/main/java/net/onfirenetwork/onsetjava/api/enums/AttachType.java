package net.onfirenetwork.onsetjava.api.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
@AllArgsConstructor
public enum AttachType {
    NONE(0),
    PLAYER(1),
    VEHICLE(2),
    OBJECT(3),
    NPC(4);
    int identifier;

    public static AttachType get(int value) {
        for (AttachType attachType : values()) {
            if (attachType.identifier == value) {
                return attachType;
            }
        }
        return null;
    }
}
