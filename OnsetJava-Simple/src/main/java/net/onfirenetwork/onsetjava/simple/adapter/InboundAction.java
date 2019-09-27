package net.onfirenetwork.onsetjava.simple.adapter;

import com.google.gson.JsonElement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class InboundAction {
    String type;
    int nonce;
    JsonElement[] params;
}
