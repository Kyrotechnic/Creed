package net.kore.mixins.network;

import net.kore.Kore;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.handshake.INetHandlerHandshakeServer;
import net.minecraft.network.handshake.client.C00Handshake;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.io.IOException;

@Mixin(C00Handshake.class)
public class MixinC00Handshake {
    @Shadow private boolean hasFMLMarker;
    private int protocolVersion;
    private String ip;
    private int port;
    private EnumConnectionState requestedState;

    /**
     * Reads the raw packet data from the data stream.
     * @author Kyrotechnics
     * @reason Revert without forge
     */
    @Overwrite
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.protocolVersion = buf.readVarIntFromBuffer();
        this.ip = buf.readStringFromBuffer(255);
        this.port = buf.readUnsignedShort();
        this.requestedState = EnumConnectionState.getById(buf.readVarIntFromBuffer());
        this.hasFMLMarker = this.ip.contains("\0FML\0");

        if (Kore.customBrand.hideForge())
        {
            this.hasFMLMarker = false;
        }

        this.ip = this.ip.split("\0FML\0")[0];
    }

    /**
     * Writes the raw packet data to the data stream.
     * @author Kyrotechnics
     * @reason Revert without forge
     */
    @Overwrite
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeVarIntToBuffer(this.protocolVersion);

        if (Kore.customBrand.hideForge())
        {
            buf.writeString(this.ip);
        }
        else
        {
            buf.writeString(this.ip + "\0FML\0");
        }

        buf.writeShort(this.port);
        buf.writeVarIntToBuffer(this.requestedState.getId());
    }
}
