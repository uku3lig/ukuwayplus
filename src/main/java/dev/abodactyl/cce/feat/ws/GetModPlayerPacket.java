package dev.abodactyl.cce.feat.ws;

public class GetModPlayerPacket implements IPacket {

    public String Type = "GET_MOD_PLAYER";
    public String Name;

    public GetModPlayerPacket(String n) {
        Name = n;
    }

}
