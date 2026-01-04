package lukeisbrecht.woodcutter.screen;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.VertexFormat;
import lukeisbrecht.woodcutter.Woodcutter;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class WoodcutterScreen extends HandledScreen<WoodcutterScreenHandler> {
    private static final Identifier TEXTURE =
            Identifier.of(Woodcutter.MOD_ID, "textures/gui/container/wood_cutter.png");

    private static RenderPipeline pipeline;

    public WoodcutterScreen(WoodcutterScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundHeight = 166;
        this.playerInventoryTitleY = this.backgroundHeight - 94;

        // Initialize pipeline once
        if (pipeline == null) {
            pipeline = RenderPipeline.builder()
                    .withLocation(TEXTURE)
                    .withVertexShader(Identifier.ofVanilla("core/gui"))
                    .withFragmentShader(Identifier.ofVanilla("core/gui"))
                    .withVertexFormat(VertexFormats.POSITION_TEXTURE, VertexFormat.DrawMode.QUADS)
                    .build();
        }
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0.0F, 0.0F, backgroundWidth, backgroundHeight, 256, 256);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}