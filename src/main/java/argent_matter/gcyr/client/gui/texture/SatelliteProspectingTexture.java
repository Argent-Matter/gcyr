package argent_matter.gcyr.client.gui.texture;

import argent_matter.gcyr.common.satellite.OreFinderSatellite;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.misc.PacketProspecting;
import com.lowdragmc.lowdraglib.gui.editor.ColorPattern;
import com.lowdragmc.lowdraglib.gui.util.DrawerHelper;
import com.lowdragmc.lowdraglib.utils.ColorUtils;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import lombok.Getter;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.reflect.Array;
import java.util.function.Function;

import static com.mojang.blaze3d.vertex.DefaultVertexFormat.POSITION_TEX_COLOR;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@OnlyIn(Dist.CLIENT)
public class SatelliteProspectingTexture extends AbstractTexture {
    public static final String SELECTED_ALL = "[all]";
    @Getter
    private String selected = SELECTED_ALL;
    private boolean darkMode;
    @Getter
    private final int imageWidth;
    @Getter
    private final int imageHeight;
    public final BlockState[][][] data;
    public final Function<BlockState, Integer> colorFunction;
    private final int playerXGui;
    private final int playerYGui;
    private final float direction;
    private final int playerChunkX;
    private final int playerChunkZ;
    private final int radius;

    public SatelliteProspectingTexture(int playerChunkX, int playerChunkZ, int posX, int posZ, float direction, int radius, boolean darkMode, Function<BlockState, Integer> colorFunction) {
        this.darkMode = darkMode;
        this.radius = radius;
        this.data = (BlockState[][][]) Array.newInstance(BlockState.class, (radius * 2 - 1) * OreFinderSatellite.CELL_SIZE, (radius * 2 - 1) * OreFinderSatellite.CELL_SIZE, 0);
        this.colorFunction = colorFunction;
        this.imageWidth = (radius * 2 - 1) * 16;
        this.imageHeight = (radius * 2 - 1) * 16;
        this.playerChunkX = playerChunkX;
        this.playerChunkZ = playerChunkZ;
        this.direction = (direction + 180) % 360;
        this.playerXGui = posX - (playerChunkX - this.radius + 1) * 16 + (posX > 0 ? 1 : 0);
        playerYGui = posZ - (playerChunkZ - this.radius + 1) * 16 + (posX > 0 ? 1 : 0);
    }

    public void updateTexture(BlockState[][][] data, ChunkPos chunk) {
        int ox;
        if ((chunk.x > 0 && playerChunkX > 0) || (chunk.x < 0 && playerChunkX < 0)) {
            ox = Math.abs(Math.abs(chunk.x) - Math.abs(playerChunkX));
        } else {
            ox = Math.abs(playerChunkX) + Math.abs(chunk.x);
        }
        if (playerChunkX > chunk.x) {
            ox = -ox;
        }

        int oy;
        if ((chunk.z > 0 && playerChunkZ > 0) || (chunk.z < 0 && playerChunkZ < 0)) {
            oy = Math.abs(Math.abs(chunk.z) - Math.abs(playerChunkZ));
        } else {
            oy = Math.abs(playerChunkZ) + Math.abs(chunk.z);
        }
        if (playerChunkZ > chunk.z) {
            oy = -oy;
        }

        int currentColumn = (this.radius - 1) + ox;
        int currentRow = (this.radius - 1) + oy;
        if (currentRow < 0) {
            return;
        }

        for (int x = 0; x < OreFinderSatellite.CELL_SIZE; x++) {
            System.arraycopy(data[x], 0, data[x + currentColumn * OreFinderSatellite.CELL_SIZE], currentRow * OreFinderSatellite.CELL_SIZE, OreFinderSatellite.CELL_SIZE);
        }
        load();
    }

    private NativeImage getImage() {
        int wh = (this.radius * 2 - 1) * 16;
        NativeImage image = new NativeImage(wh, wh, false);
        for (int i = 0; i < wh; i++) {
            for (int j = 0; j < wh; j++) {
                var items = this.data[i * OreFinderSatellite.CELL_SIZE / 16][j * OreFinderSatellite.CELL_SIZE / 16];
                // draw bg
                image.setPixelRGBA(i, j, (darkMode ? ColorPattern.GRAY.color : ColorPattern.WHITE.color));
                //draw items
                for (var item : items) {
                    if (!selected.equals(SELECTED_ALL)) continue;
                    var color = colorFunction.apply(item);
                    image.setPixelRGBA(i, j, combine(255, ColorUtils.blueI(color), ColorUtils.greenI(color), ColorUtils.redI(color)));
                    break;
                }
                // draw grid
                if ((i) % 16 == 0 || (j) % 16 == 0) {
                    image.setPixelRGBA(i, j, ColorUtils.averageColor(image.getPixelRGBA(i, j), 0xff000000));
                }
            }
        }
        return image;
    }

    /**
     * The resulting color of this operation is stored as least to most significant bits.
     */
    public static int combine(int alpha, int blue, int green, int red) {
        return (alpha & 0xFF) << 24 | (blue & 0xFF) << 16 | (green & 0xFF) << 8 | (red & 0xFF) << 0;
    }

    public void load() {
        doLoad(getImage());
    }

    private void doLoad(NativeImage image) {
        TextureUtil.prepareImage(this.getId(), 0, image.getWidth(), image.getHeight());
        image.upload(0, 0, 0, 0, 0, image.getWidth(), image.getHeight(), false, false, false, true);
    }

    public void draw(GuiGraphics graphics, int x, int y) {
        if (this.getId() == -1) return;
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuilder();
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, this.getId());
        var matrix4f = graphics.pose().last().pose();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, POSITION_TEX_COLOR);
        bufferbuilder.vertex(matrix4f, x, y + imageHeight, 0).uv(0, 1).color(-1).endVertex();
        bufferbuilder.vertex(matrix4f, x + imageWidth, y + imageHeight, 0).uv(1, 1).color(-1).endVertex();
        bufferbuilder.vertex(matrix4f, x + imageWidth, y, 0).uv(1, 0).color(-1).endVertex();
        bufferbuilder.vertex(matrix4f, x, y, 0).uv(0, 0).color(-1).endVertex();
        tessellator.end();

        GuiTextures.UP.copy().setColor(ColorPattern.RED.color).rotate(direction / 2).draw(graphics, 0, 0, x + playerXGui - 20, y + playerYGui - 20, 40, 40);

        //draw red vertical line
        if (playerXGui % 16 > 7 || playerXGui % 16 == 0) {
            DrawerHelper.drawSolidRect(graphics, x + playerXGui - 1, y, 1, imageHeight, ColorPattern.RED.color);
        } else {
            DrawerHelper.drawSolidRect(graphics, x + playerXGui, y, 1, imageHeight, ColorPattern.RED.color);
        }
        //draw red horizontal line
        if (playerYGui % 16 > 7 || playerYGui % 16 == 0) {
            DrawerHelper.drawSolidRect(graphics, x, y + playerYGui - 1, imageWidth, 1, ColorPattern.RED.color);
        } else {
            DrawerHelper.drawSolidRect(graphics, x, y + playerYGui, imageWidth, 1, ColorPattern.RED.color);
        }
    }

    @Override
    public void load(ResourceManager resourceManager) {

    }

    public void setDarkMode(boolean darkMode) {
        if (this.darkMode != darkMode) {
            this.darkMode = darkMode;
            load();
        }
    }

    public void setSelected(String uniqueID) {
        if (!this.selected.equals(uniqueID)) {
            this.selected = uniqueID;
            load();
        }
    }
}
