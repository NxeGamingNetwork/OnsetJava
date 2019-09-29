package net.onfirenetwork.onsetjava.simple.util;

import com.google.gson.JsonElement;
import lombok.experimental.UtilityClass;

import java.util.function.Supplier;

@UtilityClass
public class JsonUtils {
    public static <T> T preCheckAndRun(JsonElement e, Supplier<T> supplier) {
        if (e == null || e.isJsonNull() || e.getAsInt() == 0)
            return null;
        return supplier.get();
    }
}
