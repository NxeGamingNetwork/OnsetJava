package net.onfirenetwork.onsetjava.simple.util;

import com.google.gson.JsonElement;
import lombok.experimental.UtilityClass;
import net.onfirenetwork.onsetjava.api.util.Vector3d;

import java.util.function.Supplier;

@UtilityClass
public class JsonUtils {
    public static <T> T preCheckAndRun(JsonElement e, Supplier<T> supplier) {
        if (e == null || e.isJsonNull() || e.getAsInt() == 0)
            return null;
        return supplier.get();
    }

    public static Vector3d toVector(JsonElement[] elements) {
        return new Vector3d(elements[0].getAsDouble(), elements[1].getAsDouble(), elements[2].getAsDouble());
    }
}
