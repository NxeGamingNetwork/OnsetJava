package net.onfirenetwork.onsetjava.api.enums;

import lombok.Getter;

public class WeaponModel {

    public static final WeaponModel FIST = new WeaponModel(1);
    public static final WeaponModel PISTOL_1 = new WeaponModel(2);
    public static final WeaponModel PISTOL_2 = new WeaponModel(3);
    public static final WeaponModel GLOCK = new WeaponModel(4);
    public static final WeaponModel PISTOL_4 = new WeaponModel(5);
    public static final WeaponModel MODERN_SHOTGUN = new WeaponModel(6);
    public static final WeaponModel SHOTGUN = new WeaponModel(7);
    public static final WeaponModel MP5 = new WeaponModel(8);
    public static final WeaponModel MAC10 = new WeaponModel(9);
    public static final WeaponModel UMP45 = new WeaponModel(10);
    public static final WeaponModel M16 = new WeaponModel(11);
    public static final WeaponModel AK47 = new WeaponModel(12);
    public static final WeaponModel GOLDEN_AK47 = new WeaponModel(13);
    public static final WeaponModel G36 = new WeaponModel(14);
    public static final WeaponModel RIFLE_4 = new WeaponModel(15);
    public static final WeaponModel AKS = new WeaponModel(16);
    public static final WeaponModel RIFLE_6 = new WeaponModel(17);
    public static final WeaponModel MK16 = new WeaponModel(18);
    public static final WeaponModel RIFLE_8 = new WeaponModel(19);
    public static final WeaponModel AWP = new WeaponModel(20);

    @Getter
    private int id;

    private WeaponModel(int id){
        this.id = id;
    }

    public static WeaponModel[] values(){
        return new WeaponModel[]{
            FIST, PISTOL_1, PISTOL_2, GLOCK, PISTOL_4, MODERN_SHOTGUN, SHOTGUN, MP5, MAC10, UMP45, M16, AK47, GOLDEN_AK47, G36, RIFLE_4, AKS, RIFLE_6, MK16, RIFLE_8, AWP
        };
    }

    public boolean equals(Object other){
        if(other == null)
            return false;
        if(!(other instanceof WeaponModel))
            return false;
        return ((WeaponModel) other).id == id;
    }

    public static WeaponModel getModel(int id){
        return new WeaponModel(id);
    }

}
