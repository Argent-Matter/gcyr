package argent_matter.gcyr.client.gui.screen;

import argent_matter.gcyr.GCyR;
import argent_matter.gcyr.GCyRClient;
import argent_matter.gcyr.api.space.planet.Galaxy;
import argent_matter.gcyr.api.space.planet.Planet;
import argent_matter.gcyr.api.space.planet.PlanetRing;
import argent_matter.gcyr.api.space.planet.SolarSystem;
import argent_matter.gcyr.client.gui.Category;
import argent_matter.gcyr.common.data.GCyRNetworking;
import argent_matter.gcyr.common.gui.PlanetSelectionMenu;
import argent_matter.gcyr.common.networking.c2s.PacketCreateSpaceStation;
import argent_matter.gcyr.common.networking.c2s.PacketSendSelectedDimension;
import argent_matter.gcyr.data.loader.PlanetData;
import argent_matter.gcyr.util.GCyRValues;
import com.gregtechceu.gtceu.GTCEu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import lombok.Getter;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class PlanetSelectionScreen extends Screen implements MenuAccess<PlanetSelectionMenu> {

    public static final ResourceLocation SMALL_MENU_LIST = GCyR.id("textures/gui/selection_menu.png");
    public static final ResourceLocation LARGE_MENU_TEXTURE = GCyR.id("textures/gui/selection_menu_large.png");
    public static final ResourceLocation SCROLL_BAR = GTCEu.id("textures/gui/widget/slider.png");
    private static final Component CATALOG_TEXT = Component.translatable("menu.gcyr.catalog");
    private static final Component BACK_TEXT = Component.translatable("menu.gcyr.back");
    public static final Component PLANET_TEXT = Component.translatable("menu.gcyr.planet");
    public static final Component MOON_TEXT = Component.translatable("menu.gcyr.moon");
    public static final Component ORBIT_TEXT = Component.translatable("menu.gcyr.orbit");
    public static final Component NO_GRAVITY_TEXT = Component.translatable("menu.gcyr.no_gravity");
    public static final Component SPACE_STATION_TEXT = Component.translatable("menu.gcyr.space_station");
    public static final Component SOLAR_SYSTEM_TEXT = Component.translatable("menu.gcyr.solar_system");
    public static final Component GALAXY_TEXT = Component.translatable("menu.gcyr.galaxy");
    public static final Component CATEGORY_TEXT = Component.translatable("menu.gcyr.category");
    public static final Component PROVIDED_TEXT = Component.translatable("menu.gcyr.provided");
    public static final Component TYPE_TEXT = Component.translatable("menu.gcyr.type");
    public static final Component GRAVITY_TEXT = Component.translatable("menu.gcyr.gravity");
    public static final Component OXYGEN_TEXT = Component.translatable("menu.gcyr.oxygen");
    public static final Component TEMPERATURE_TEXT = Component.translatable("menu.gcyr.temperature");
    public static final Component OXYGEN_TRUE_TEXT = Component.translatable("menu.gcyr.oxygen.true");
    public static final Component OXYGEN_FALSE_TEXT = Component.translatable("menu.gcyr.oxygen.false");

    public static final int SCROLL_BAR_X = 92;
    public static final int SCROLL_SENSITIVITY = 5;
    final Set<Category> solarSystemsCategories = new HashSet<>();
    final Set<Category> galaxyCategories = new HashSet<>();
    @Getter
    private final PlanetSelectionMenu menu;
    private final Map<Category, LinkedList<ExtendedButton>> categoryButtons = new HashMap<>();
    public int minScrollY = 177;
    public int maxScrollY = 274;
    private Category currentCategory = Category.GALAXY_CATEGORY;
    @Getter
    private float guiTime;
    private Button scrollBar;

    public PlanetSelectionScreen(PlanetSelectionMenu handler, Inventory inventory, Component title) {
        super(title);
        this.menu = handler;

        if (GCyRClient.galaxies.size() <= 1) {
            currentCategory = Category.MILKY_WAY_CATEGORY;
        }

        // Set the initial gui time to the level time. This creates a random start position for each rotating object.
        guiTime = handler.getPlayer().level().getRandom().nextFloat() * 100000.0f;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {

        // For rotations
        this.guiTime += delta;
        this.renderBackground(guiGraphics, mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);

        // Catalog text.
        guiGraphics.drawString(this.font, CATALOG_TEXT, 29, (int) ((this.height / 2.0f) - 143.0f / 2.0f), -1);
    }

    private void drawBackground(GuiGraphics guiGraphics) {
        guiGraphics.fill(0, 0, this.width, this.height, 0xff000419);
        RenderSystem.enableBlend();
    }

    private void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        super.renderBackground(guiGraphics);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        // Planet selection background
        drawBackground(guiGraphics);

        int currentPage = this.getPage();

        SolarSystem solarSystem = null;
        Set<PlanetRing> planetRings = new HashSet<>();
        for (SolarSystem system : GCyRClient.solarSystems) {
            if (this.currentCategory.id().equals(system.solarSystem()) || this.currentCategory.parent() != null && this.currentCategory.parent().id().equals(system.solarSystem())) {
                solarSystem = system;
                break;
            }
        }

        for (PlanetRing ring : GCyRClient.planetRings) {
            if (this.currentCategory.id().equals(ring.solarSystem()) || this.currentCategory.parent() != null && this.currentCategory.parent().id().equals(ring.solarSystem())) {
                planetRings.add(ring);
            }
        }

        if (currentPage == 1) {
            GCyRClient.galaxies.stream().filter(g -> g.galaxy().equals(this.currentCategory.id()))
                .findFirst()
                .ifPresent(galaxy -> addRotatingTexture(this, guiGraphics, -125, -125, galaxy.scale(), galaxy.scale(), galaxy.texture(), 0.6f));
        }
        // Render the Solar System when inside the Solar System category
        else {
            if (solarSystem != null) {

                // Sun
                addTexture(guiGraphics, (this.width - solarSystem.sunScale()) / 2, (this.height - solarSystem.sunScale()) / 2, solarSystem.sunScale(), solarSystem.sunScale(), solarSystem.sun());

                for (PlanetRing ring : planetRings) {
                    drawCircle(this.width / 2f, this.height / 2f, ring.radius() * 24, 75, solarSystem.ringColour());
                }

                for (PlanetRing ring : planetRings) {
                    int coordinates = (int) (ring.radius() * 17 - (ring.scale() / 1.9));
                    addRotatingTexture(this, guiGraphics, coordinates, coordinates, ring.scale(), ring.scale(), ring.texture(), 365 / (float) ring.speed());
                }
            }
        }

        // Display either the small or large menu when a planet category is opened.
        if (currentPage == 3) {
            addTexture(guiGraphics, 0, (this.height / 2) - 177 / 2, 215, 177, LARGE_MENU_TEXTURE);
            this.scrollBar.setX(210);
        } else {
            addTexture(guiGraphics, 0, (this.height / 2) - 177 / 2, 105, 177, SMALL_MENU_LIST);
            this.scrollBar.setX(SCROLL_BAR_X);
        }

        this.categoryButtons.forEach((category, buttons) -> buttons.forEach(button -> button.visible = this.currentCategory.equals(category)));

        // Disable the back button when there is nothing to go back to.
        Button backButton = this.categoryButtons.get(Category.BACK).get(0);
        backButton.visible = this.currentCategory.parent() != null;
        if (currentPage == 1 && GCyRClient.galaxies.size() <= 1) {
            backButton.visible = false;
        }
        if (this.categoryButtons.containsKey(this.currentCategory)) {
            this.scrollBar.visible = this.categoryButtons.get(this.currentCategory).size() > (currentPage == 3 ? 13 : 5);
        }

        minScrollY = (this.height / 2) - 33;
        maxScrollY = (this.height / 2) + 64;

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.disableBlend();
        RenderSystem.disableDepthTest();
        RenderSystem.disableScissor();
    }

    @Override
    protected void init() {
        super.init();

        // The back button. It is always element [0] in the buttons list.
        LinkedList<ExtendedButton> backButtonList = new LinkedList<>();
        ExtendedButton backButton = new ExtendedButton(10, this.height / 2 - 36, 71, 20, 1.0f, 1.0f, 1.0f, BACK_TEXT, pressed -> onNavigationButtonClick(currentCategory.parent()));
        this.addRenderableWidget(backButton);
        backButtonList.add(backButton);

        this.categoryButtons.put(Category.BACK, backButtonList);

        // All buttons are data-driven; they are created from files in the /planet_data/planets directory.
        List<Planet> planets = new ArrayList<>(PlanetData.planets().values());
        planets.sort(Comparator.comparing(g -> g.translation().substring(Math.abs(g.translation().indexOf(".text")))));
        planets.forEach(planet -> {
            Category galaxyCategory = new Category(planet.galaxy(), Category.GALAXY_CATEGORY);
            Category solarSystemCategory = new Category(planet.solarSystem(), galaxyCategory);
            Category planetCategory = new Category(planet.parentWorld() == null ? planet.level().location() : planet.parentWorld().location(), solarSystemCategory);

            Component label = Component.translatable(planet.translation());

            this.galaxyCategories.add(galaxyCategory);
            this.solarSystemsCategories.add(solarSystemCategory);

            if (planet.parentWorld() == null) {
                createNavigationButton(label, solarSystemCategory, planet.buttonColor(), 71, 20, TooltipType.CATEGORY, planet, planetCategory);
            }

            createTeleportButton(1, label, planetCategory, planet.buttonColor(), 71, 20, TooltipType.PLANET, planet);
            createSpaceStationTeleportButton(2, SPACE_STATION_TEXT, planetCategory, planet.buttonColor(), 71, 20, planet);
        });

        this.galaxyCategories.forEach((this::createGalaxyButton));
        this.solarSystemsCategories.forEach((this::createSolarSystemButton));

        // Scroll bar
        this.scrollBar = new Button(SCROLL_BAR_X, minScrollY, 4, 8, Component.nullToEmpty(""), pressed -> {
        }, Button.DEFAULT_NARRATION) {
            @Override
            public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
                if (this.visible) {
                    RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();
                    RenderSystem.enableDepthTest();
                    guiGraphics.blit(SCROLL_BAR, this.getX(), this.getY(), 0, 0, this.width, this.height, this.width, this.height);
                }
            }
        };
        this.addRenderableWidget(this.scrollBar);
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    public void onNavigationButtonClick(Category target) {
        this.resetButtonScroll();
        this.scrollBar.setY(minScrollY);
        this.currentCategory = target;
    }

    public void createGalaxyButton(Category galaxyCategory) {
        Component label = Component.translatable(galaxyCategory.id().toLanguageKey());
        Galaxy galaxy = GCyRClient.galaxies.stream().filter(g -> g.galaxy().equals(galaxyCategory.id())).findFirst().orElse(null);
        createNavigationButton(label, Category.GALAXY_CATEGORY, (galaxy != null ? galaxy.buttonColor() : 0xFFAA00AA), 75, 20, TooltipType.GALAXY, null, galaxyCategory);
    }

    public void createSolarSystemButton(Category solarSystemCategory) {
        Component label = Component.translatable(solarSystemCategory.id().toLanguageKey());
        SolarSystem solarSystem = GCyRClient.solarSystems.stream().filter(g -> g.solarSystem().equals(solarSystemCategory.id())).findFirst().orElse(null);
        createNavigationButton(label, solarSystemCategory.parent(), (solarSystem != null ? solarSystem.buttonColor() : 0xFF0000AA), 71, 20, TooltipType.SOLAR_SYSTEM, null, solarSystemCategory);
    }

    public void createNavigationButton(Component label, Category category, int colour, int sizeX, int sizeY, TooltipType tooltip, Planet planetInfo, Category target) {
        createButton(label, category, colour, sizeX, sizeY, tooltip, planetInfo, press -> onNavigationButtonClick(target));
    }

    public void createTeleportButton(int row, Component label, Category category, int colour, int sizeX, int sizeY, TooltipType tooltip, Planet planetInfo) {
        createTeleportButton(row, label, category, colour, sizeX, sizeY, tooltip, planetInfo, press -> selectPlanet(planetInfo));
    }

    public void createTeleportButton(int row, Component label, Category category, int colour, int sizeX, int sizeY, TooltipType tooltip, Planet planetInfo, Consumer<Button> onClick) {
        int newRow = 0;
        if (row == 2) {
            newRow = 76;
        } else if (row == 3) {
            newRow = 118;
        }

        LinkedList<ExtendedButton> buttons = this.categoryButtons.getOrDefault(category, new LinkedList<>());

        int column = getColumn(category) - (row - 1) * 22;
        column -= 22 * (buttons.size() / 2);
        createButton(newRow + 10, column, label, category, colour, sizeX, sizeY, tooltip, planetInfo, onClick);
    }

    public void createSpaceStationTeleportButton(int row, Component label, Category category, int colour, int sizeX, int sizeY, Planet planet) {
        createTeleportButton(row, label, category, colour, sizeX, sizeY, TooltipType.SPACE_STATION, planet, press -> {
            if (minecraft != null && minecraft.player != null) {
                selectPlanet(planet);
                GCyRNetworking.NETWORK.sendToServer(new PacketCreateSpaceStation());
            }
        });
    }

    public void selectPlanet(Planet planet) {
        this.minecraft.player.closeContainer();
        // Tell the server to teleport the player after the button has been pressed.
        GCyRNetworking.NETWORK.sendToServer(new PacketSendSelectedDimension(planet.level().location()));
    }

    public Button createButton(Component label, Category category, int colour, int sizeX, int sizeY, TooltipType tooltip, Planet planetInfo, Consumer<Button> onClick) {
        return createButton(10, label, category, colour, sizeX, sizeY, tooltip, planetInfo, onClick);
    }

    public Button createButton(int row, Component label, Category category, int colour, int sizeX, int sizeY, TooltipType tooltip, Planet planetInfo, Consumer<Button> onClick) {
        int column = getColumn(category);
        return createButton(row, column, label, category, colour, sizeX, sizeY, tooltip, planetInfo, onClick);
    }

    public Button createButton(int row, int column, Component label, Category category, int colour, int sizeX, int sizeY, TooltipType tooltip, Planet planetInfo, Consumer<Button> onClick) {
        LinkedList<ExtendedButton> buttons = this.categoryButtons.getOrDefault(category, new LinkedList<>());

        float colorR = (float) ((colour << 16) & 0xFF) / 255.0f;
        float colorG = (float) ((colour << 8) & 0xFF) / 255.0f;
        float colorB = (float) (colour & 0xFF) / 255.0f;
        ExtendedButton button = new ExtendedButton(row, column, sizeX, sizeY, colorR, colorG, colorB, label, onClick::accept, (button1) -> renderButtonTooltip(planetInfo, tooltip, button1));
        this.addRenderableWidget(button);

        buttons.add(button);
        categoryButtons.put(category, buttons);
        return button;
    }

    public List<FormattedCharSequence> renderButtonTooltip(Planet planetInfo, TooltipType tooltip, Button button) {
        List<Component> textEntries = new LinkedList<>();

        switch (tooltip) {
            case GALAXY -> {
                textEntries.add(CATEGORY_TEXT.copy().withStyle(ChatFormatting.BLUE).append(": ").append(button.getMessage().copy().withStyle(ChatFormatting.AQUA)));
                textEntries.add(TYPE_TEXT.copy().withStyle(ChatFormatting.BLUE).append(": ").append(GALAXY_TEXT.copy().withStyle(ChatFormatting.AQUA)));
            }
            case SOLAR_SYSTEM -> {
                textEntries.add(CATEGORY_TEXT.copy().withStyle(ChatFormatting.BLUE).append(": ").append(button.getMessage().copy().withStyle(ChatFormatting.AQUA)));
                textEntries.add(TYPE_TEXT.copy().withStyle(ChatFormatting.BLUE).append(": ").append(SOLAR_SYSTEM_TEXT.copy().withStyle(ChatFormatting.DARK_AQUA)));
            }
            case CATEGORY -> {
                textEntries.add(CATEGORY_TEXT.copy().withStyle(ChatFormatting.BLUE).append(": ").append(button.getMessage().copy().withStyle(ChatFormatting.GREEN)));
                textEntries.add(PROVIDED_TEXT.copy().withStyle(ChatFormatting.BLUE).append(": ").append(Component.translatable("item.gcyr.tier_" + planetInfo.rocketTier() + "_rocket").withStyle(ChatFormatting.AQUA)));
            }
            case PLANET -> {
                textEntries.add(TYPE_TEXT.copy().withStyle(ChatFormatting.BLUE).append(": ").append((planetInfo.parentWorld() == null ? PlanetSelectionScreen.PLANET_TEXT : PlanetSelectionScreen.MOON_TEXT).copy().withStyle(ChatFormatting.AQUA)));
                textEntries.add(GRAVITY_TEXT.copy().withStyle(ChatFormatting.BLUE).append(": ").append(Component.literal(planetInfo.gravity() + " m/s").withStyle(ChatFormatting.AQUA)));
                textEntries.add(OXYGEN_TEXT.copy().withStyle(ChatFormatting.BLUE).append(": ").append((planetInfo.hasOxygen() ? OXYGEN_TRUE_TEXT : OXYGEN_FALSE_TEXT).copy().withStyle(planetInfo.hasOxygen() ? ChatFormatting.GREEN : ChatFormatting.RED)));
                ChatFormatting temperatureColour = ChatFormatting.GREEN;

                // Make the temperature text look orange when the temperature is hot and blue when the temperature is cold.
                if (planetInfo.temperature() > 50) {
                    // Hot.
                    temperatureColour = ChatFormatting.GOLD;
                } else if (planetInfo.temperature() < -20) {
                    // Cold.
                    temperatureColour = ChatFormatting.DARK_BLUE;
                }

                textEntries.add(TEMPERATURE_TEXT.copy().withStyle(ChatFormatting.BLUE).append(": ").append(Component.literal(planetInfo.temperature() + " K").withStyle(temperatureColour)));
            }
            default -> {

            }
        }

        if (tooltip.equals(TooltipType.ORBIT) || tooltip.equals(TooltipType.SPACE_STATION)) {
            textEntries.add(TYPE_TEXT.copy().withStyle(ChatFormatting.BLUE).append(": ").append(ORBIT_TEXT.copy().withStyle(ChatFormatting.DARK_AQUA)));
            textEntries.add(GRAVITY_TEXT.copy().withStyle(ChatFormatting.BLUE).append(": ").append(NO_GRAVITY_TEXT.copy().withStyle(ChatFormatting.DARK_AQUA)));
            textEntries.add(OXYGEN_TEXT.copy().withStyle(ChatFormatting.BLUE).append(": ").append(OXYGEN_FALSE_TEXT.copy().withStyle(ChatFormatting.RED)));
            textEntries.add(TEMPERATURE_TEXT.copy().withStyle(ChatFormatting.BLUE).append(": ").append(Component.literal(GCyRValues.ORBIT_TEMPERATURE + " K").withStyle(ChatFormatting.DARK_BLUE)));
        }
        return textEntries.stream().map(Component::getVisualOrderText).toList();
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        // Simple scrollbar.
        for (Map.Entry<Category, LinkedList<ExtendedButton>> entry : this.categoryButtons.entrySet()) {
            if (this.currentCategory.equals(entry.getKey())) {

                List<Button> buttons = new LinkedList<>();
                Button backButton = this.categoryButtons.get(Category.BACK).get(0);
                // Create a new list with the back button included. We have to do this as the back button is a separate category, so it needs
                // to manually be added to this category so that it scrolls with every other button.
                buttons.add(backButton);
                buttons.addAll(entry.getValue());

                boolean isLargePage = this.getPage() == 3;
                // The amount of buttons that are offscreen.
                // The large page had 3 buttons per row so each row needs to be treated as one.
                final int overflowButtons = buttons.size() - (isLargePage ? 13 : 5);

                // Don't scroll if there are not enough buttons.
                if (overflowButtons > 0) {
                    final int referencePoint = backButton.getY();
                    int minThreshold = this.height / 2 - 35;
                    int maxThreshold = (this.height / 2 - 38) - (overflowButtons * (isLargePage ? 7 : 21));
                    int sensitivity = (int) (SCROLL_SENSITIVITY * amount);

                    // Lock the scroll to the min and max thresholds.
                    if (amount > 0) {
                        if (referencePoint >= minThreshold) {
                            sensitivity = 0;
                        }
                    } else if (amount < 0) {
                        if (referencePoint <= maxThreshold) {
                            sensitivity = 0;
                        }
                    }

                    // Move all buttons based on the scroll.
                    for (Button button2 : buttons) {
                        button2.setY(button2.getY() + sensitivity);

                        if (referencePoint >= minThreshold) {
                            button2.setY(button2.getY() - referencePoint - minThreshold);
                        } else if (referencePoint <= maxThreshold) {
                            button2.setY(button2.getY() - referencePoint - maxThreshold);
                        }
                    }

                    float min = maxThreshold / (float) minThreshold;
                    float ratio = backButton.getY() / (float) minThreshold;
                    ratio = Mth.inverseLerp(ratio, 1, min);

                    // Flip min and max for inverse operation.
                    this.scrollBar.setY((int) Mth.lerp(ratio, maxScrollY, minScrollY));
                    this.scrollBar.setY(Mth.clamp(this.scrollBar.getY(), minScrollY, maxScrollY));
                }

                break;
            }
        }

        return super.mouseScrolled(mouseX, mouseY, amount);
    }

    public void resetButtonScroll() {
        categoryButtons.values().forEach(list -> list.forEach(button -> {
            button.setY(button.getStartY());
        }));
    }

    private int getPage() {
        Category category = this.currentCategory;
        if (category.parent() == null) {
            // Galaxy menu
            return 0;
        } else if (category.parent().parent() == null) {
            // Solar system menu
            return 1;
        } else if (category.parent().parent().parent() == null) {
            // Planet menu
            return 2;
        } else if (category.parent().parent().parent().parent() == null) {
            // Planet menu
            return 3;
        }
        // Should never be called
        GCyR.LOGGER.warn("Invalid page!");
        return 0;
    }

    public int getColumn(Category category) {
        LinkedList<ExtendedButton> buttons = this.categoryButtons.getOrDefault(category, new LinkedList<>());
        int index = buttons.size() + 1;
        int startY = this.height / 2 - 58;
        // Disable the galaxy category if the Milky Way is the only galaxy
        if (Category.GALAXY_CATEGORY.equals(category.parent()) && GCyRClient.galaxies.size() <= 1) {
            return startY + 22 * index;
        }
        return startY + 22 * index + (category.parent() != null ? 22 : 0);
    }

    // Do not close unless in creative mode
    @Override
    public void onClose() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null && (minecraft.player.isCreative() || minecraft.player.isSpectator())) {
            super.onClose();
        }
    }

    // Reset the buttons when the window size is changed
    @Override
    public void resize(Minecraft minecraft, int width, int height) {
        this.categoryButtons.clear();
        this.resetButtonScroll();
        super.resize(minecraft, width, height);
    }

    public enum TooltipType {
        NONE, GALAXY, SOLAR_SYSTEM, CATEGORY, PLANET, ORBIT, SPACE_STATION
    }

    public static void addTexture(GuiGraphics guiGraphics, int x, int y, int width, int height, ResourceLocation texture) {
        guiGraphics.blit(texture, x, y, 0, 0, width, height, width, height);
    }

    public static void addRotatingTexture(PlanetSelectionScreen screen, GuiGraphics guiGraphics, int x, int y, int width, int height, ResourceLocation texture, float speed) {

        double scale = Minecraft.getInstance().getWindow().getGuiScaledHeight() / 400.0;

        x *= scale;
        y *= scale;
        x += 1;
        y += 1;

        width *= scale;
        height *= scale;

        guiGraphics.pose().pushPose();

        guiGraphics.pose().translate(screen.width / 2.0f, screen.height / 2.0f, 0);
        guiGraphics.pose().mulPose(Axis.ZP.rotationDegrees(screen.getGuiTime() * (speed / 3.0f)));

        addTexture(guiGraphics, x, y, width, height, texture);

        guiGraphics.pose().popPose();
    }

    public static void drawCircle(double x, double y, double radius, int sides, int ringColour) {
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuilder();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        double scale = Minecraft.getInstance().getWindow().getGuiScaledHeight() / 400.0;
        radius *= scale;

        double width = radius - 0.6;
        for (double i = width; i < radius - 0.5 + 1; i += 0.1) {
            bufferBuilder.begin(VertexFormat.Mode.DEBUG_LINE_STRIP, DefaultVertexFormat.POSITION_COLOR);
            for (int j = 0; j <= sides; j++) {
                double angle = (Math.PI * 2 * j / sides) + Math.toRadians(180);
                //noinspection PointlessBitwiseExpression
                bufferBuilder.vertex(x + Math.sin(angle) * i, y + Math.cos(angle) * i, 0)
                        .color((ringColour >> 16) & 0xFF, (ringColour >> 8) & 0xFF, (ringColour >> 0) & 0xFF, (ringColour >> 24) & 0xFF)
                        .endVertex();
            }
            tessellator.end();
        }
    }
}