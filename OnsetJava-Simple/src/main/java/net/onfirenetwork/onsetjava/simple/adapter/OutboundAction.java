package net.onfirenetwork.onsetjava.simple.adapter;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OutboundAction {
    String type;
    int nonce;
    Object[] params;
    public OutboundAction(String type, int nonce, Object... params){
        this.type = type;
        this.nonce = nonce;
        this.params = params;
    }
}
