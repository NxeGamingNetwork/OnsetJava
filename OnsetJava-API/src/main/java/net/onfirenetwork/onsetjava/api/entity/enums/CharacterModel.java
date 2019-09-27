package net.onfirenetwork.onsetjava.api.entity.enums;

import lombok.Getter;

public class CharacterModel {

    public static final CharacterModel ORANGE_SHIRT_1 = new CharacterModel(1, false);
    public static final CharacterModel ORANGE_SHIRT_2 = new CharacterModel(2, false);
    public static final CharacterModel ORANGE_SHIRT_3 = new CharacterModel(3, false);
    public static final CharacterModel ORANGE_SHIRT_4 = new CharacterModel(4, false);
    public static final CharacterModel GRAY_SHIRT = new CharacterModel(5, false);
    public static final CharacterModel BLUE_SHIRT_1 = new CharacterModel(6, true);
    public static final CharacterModel RED_SHIRT = new CharacterModel(7, false);
    public static final CharacterModel ORANGE_SHIRT_5 = new CharacterModel(8, true);
    public static final CharacterModel BLUE_SHIRT_2 = new CharacterModel(9, false);
    public static final CharacterModel PURPLE_SUIT_1 = new CharacterModel(10, true);
    public static final CharacterModel PURPLE_SUIT_2 = new CharacterModel(11, true);
    public static final CharacterModel PURPLE_SUIT_3 = new CharacterModel(12, false);
    public static final CharacterModel POLICE = new CharacterModel(13, false);
    public static final CharacterModel BLACK_SUIT_1 = new CharacterModel(14, false);
    public static final CharacterModel WHITE_SHIRT = new CharacterModel(15, false);
    public static final CharacterModel BLACK_SUIT_2 = new CharacterModel(16, false);
    public static final CharacterModel DOCTOR = new CharacterModel(17, false);
    public static final CharacterModel BLACK_SUIT_3 = new CharacterModel(18, false);
    public static final CharacterModel SHIRTLESS_1 = new CharacterModel(19, false);
    public static final CharacterModel SHIRTLESS_2 = new CharacterModel(20, true);
    public static final CharacterModel ZOMBIE_1 = new CharacterModel(21, false);
    public static final CharacterModel ZOMBIE_2 = new CharacterModel(22, false);
    public static final CharacterModel ALIEN_1 = new CharacterModel(23, false);
    public static final CharacterModel ALIEN_2 = new CharacterModel(24, false);
    public static final CharacterModel FBI = new CharacterModel(25, false);
    public static final CharacterModel SOLDIER_COMMS = new CharacterModel(26, false);
    public static final CharacterModel SOLDIER_NIGHT_VISION = new CharacterModel(27, false);
    public static final CharacterModel SOLDIER_GOOGLES = new CharacterModel(28, false);
    public static final CharacterModel SOLDIER = new CharacterModel(29, false);

    @Getter
    private int id;
    /*
    This property shouldn't be discriminating anybody! It should help rp servers to only show models matching to the rp characters skin color.
     */
    @Getter
    private boolean darkSkin;

    private CharacterModel(int id, boolean darkSkin){
        this.id = id; this.darkSkin = darkSkin;
    }

    public static CharacterModel[] values(){
        return new CharacterModel[]{
                ORANGE_SHIRT_1, ORANGE_SHIRT_2, ORANGE_SHIRT_3, ORANGE_SHIRT_4, GRAY_SHIRT, BLUE_SHIRT_1, RED_SHIRT, ORANGE_SHIRT_5, BLUE_SHIRT_2, PURPLE_SUIT_1, PURPLE_SUIT_2, PURPLE_SUIT_3, POLICE, BLACK_SUIT_1, WHITE_SHIRT, BLACK_SUIT_2, DOCTOR, BLACK_SUIT_3, SHIRTLESS_1, SHIRTLESS_2, ZOMBIE_1, ZOMBIE_2, ALIEN_1, ALIEN_2, FBI, SOLDIER_COMMS, SOLDIER_NIGHT_VISION, SOLDIER_GOOGLES, SOLDIER
        };
    }
    public static CharacterModel getModel(int id){
        return new CharacterModel(id, false);
    }
}
