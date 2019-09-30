package net.onfirenetwork.onsetjava.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PostEffect {
    VIGNETTE_INTENSITY("ImageEffects", "VignetteIntensity"),
    GRAIN_JITTER("ImageEffects", "GrainJitter"),
    GRAIN_INTENSITY("ImageEffects", "GrainIntensity"),
    WHITE_BALANCE_TEMPERATURE("WhiteBalance", "Temp"),
    WHITE_BALANCE_TINT("WhiteBalance", "Tint"),
    SATURATION("Global", "Saturation"),
    CONTRAST("Global", "Contrast"),
    GAMMA("Global", "Gamma"),
    GAIN("Global", "Gain"),
    CHROMATIC_INTENSITY("Chromatic", "Intensity"),
    CHROMATIC_OFFSET("Chromatic", "StartOffset"),
    BLOOM_INTENSITY("Bloom", "Intensity"),
    DEPTH_DISTANCE("DepthOfField", "Distance"),
    DEPTH_BLUR_SMOOTH_KM("DepthOfField", "DepthBlurSmoothKM"),
    DEPTH_BLUR_RADIUS("DepthOfField", "DepthBlurRadius"),
    MOTION_BLUR_AMOUNT("MotionBlur", "Amount"),
    MOTION_BLUR_MAX("MotionBlur", "Max");
    @Getter
    private String category;
    @Getter
    private String setting;
}
