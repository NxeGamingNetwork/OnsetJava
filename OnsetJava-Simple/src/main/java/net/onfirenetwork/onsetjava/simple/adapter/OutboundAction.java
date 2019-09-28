package net.onfirenetwork.onsetjava.simple.adapter;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OutboundAction {
    private static int nextIdentifier = 1;
    int identifier;
    String type;
    int nonce;
    Object[] params;
    public OutboundAction(String type, int nonce, Object... params){
        this.identifier = nextIdentifier;
        nextIdentifier++;
        this.type = type;
        this.nonce = nonce;
        this.params = params;
    }
}
