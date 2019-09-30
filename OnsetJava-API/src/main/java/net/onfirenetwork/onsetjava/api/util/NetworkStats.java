package net.onfirenetwork.onsetjava.api.util;

import com.google.gson.JsonObject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class NetworkStats {
    private JsonObject element;

    public int getPacketLossTotal() {
        return getInt("packetlossTotal");
    }

    public int getPacketLossLastSecond() {
        return getInt("packetlossLastSecond");
    }

    public int getMessagesInResendBuffer() {
        return getInt("messagesInResendBuffer");
    }

    public int getBytesInResendBuffer() {
        return getInt("bytesInResendBuffer");
    }

    public int getBytesSend() {
        return getInt("bytesSend");
    }

    public int getBytesReceived() {
        return getInt("bytesReceived");
    }

    public int getBytesResent() {
        return getInt("bytesResent");
    }

    public int getBytesSendTotal() {
        return getInt("bytesSendTotal");
    }

    public int bytesReceivedTotal() {
        return getInt("bytesReceivedTotal");
    }

    public boolean isLimitedByCongestionControl() {
        return getBool("isLimitedByCongestionControl");
    }

    public boolean isLimitedByOutgoingBandwidthLimit() {
        return getBool("isLimitedByOutgoingBandwidthLimit");
    }

    private int getInt(String name) {
        return element.get(name).getAsInt();
    }

    private boolean getBool(String name) {
        return element.get(name).getAsBoolean();
    }
}
