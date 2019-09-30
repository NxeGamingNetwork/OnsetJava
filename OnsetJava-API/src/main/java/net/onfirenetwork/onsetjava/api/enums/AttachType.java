package net.onfirenetwork.onsetjava.api.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
@AllArgsConstructor
public enum AttachType {
    ATTACH_NONE(0),
    ATTACH_PLAYER(1),
    ATTACH_VEHICLE(2),
    ATTACH_OBJECT(3),
    ATTACH_NPC(4);
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
