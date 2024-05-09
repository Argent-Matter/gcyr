package argent_matter.gcyr.common.gui.widget;

import argent_matter.gcyr.common.satellite.OreFinderSatellite;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.lang.reflect.Array;

public class PacketSatelliteProspecting {
    public int chunkX;
    public int chunkZ;
    public BlockState[][][] data;

    public PacketSatelliteProspecting(int chunkX, int chunkZ) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.data = (BlockState[][][]) Array.newInstance(BlockState.class, OreFinderSatellite.CELL_SIZE, OreFinderSatellite.CELL_SIZE, 0);
    }

    public static PacketSatelliteProspecting readPacketData(FriendlyByteBuf buffer) {
        PacketSatelliteProspecting packet = new PacketSatelliteProspecting(buffer.readVarInt(), buffer.readVarInt());
        for (int x = 0; x < OreFinderSatellite.CELL_SIZE; x++) {
            for (int z = 0; z < OreFinderSatellite.CELL_SIZE; z++) {
                packet.data[x][z] = (BlockState[]) Array.newInstance(BlockState.class, buffer.readVarInt());
                for (int i = 0; i < packet.data[x][z].length; i++) {
                    packet.data[x][z][i] = Block.stateById(buffer.readVarInt());
                }
            }
        }
        return packet;
    }

    public void writePacketData(FriendlyByteBuf buffer) {
        buffer.writeVarInt(chunkX);
        buffer.writeVarInt(chunkZ);
        for (int x = 0; x < OreFinderSatellite.CELL_SIZE; x++) {
            for (int z = 0; z < OreFinderSatellite.CELL_SIZE; z++) {
                buffer.writeVarInt(data[x][z].length);
                for (var item : data[x][z]) {
                    buffer.writeVarInt(Block.getId(item));
                }
            }
        }
    }

}