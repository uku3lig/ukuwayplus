package plus.hideaway.mod.feat.menu;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.OptionListWidget;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TextContent;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import plus.hideaway.mod.HideawayPlus;
import plus.hideaway.mod.feat.discord.DiscordManager;
import plus.hideaway.mod.feat.ws.WebSocketManager;

@Environment(EnvType.CLIENT)
public class MenuScreen extends GameOptionsScreen {

    private final Screen parent;
    private OptionListWidget list;

    public MenuScreen(Screen parent) {
        super(
            parent,
            HideawayPlus.client().options,
            Text.literal("\uE002").setStyle(Style.EMPTY.withFont(new Identifier("hideawayplus:text")))
        );
        this.parent = parent;
    }

    private static SimpleOption<?>[] getOptions() {
        return new SimpleOption[]{
                SimpleOption.ofBoolean(
                    "option.hideawayplus.rpc",
                    SimpleOption.constantTooltip(
                            MutableText.of(TextContent.EMPTY)
                                    .append(Text.literal("Displays a live location and data about what you're playing on your Discord account."))
                                    .append(Text.literal("\n\n"))
                                    .append(Text.literal("This does not grant us access to your account other than knowing what your tag is.").setStyle(Style.EMPTY.withColor(Formatting.DARK_GRAY)))
                                    .append(Text.literal("\n\n"))
                                    .append(Text.literal("Impact: ").setStyle(Style.EMPTY.withColor(Formatting.DARK_GRAY)))
                                    .append(Text.literal("MEDIUM").setStyle(Style.EMPTY.withColor(Formatting.GOLD).withBold(true).withFont(new Identifier("hideawayplus:text"))))
                                    .append(Text.literal("\n\n"))
                                    .append(Text.literal("Requires an opened Discord desktop client.").setStyle(Style.EMPTY.withColor(Formatting.DARK_GRAY)))
                    ),
                        HideawayPlus.settings().rpc,
                    (value) -> {
                        HideawayPlus.settings().rpc = value;
                        if (value) HideawayPlus.DISCORD_MANAGER = new DiscordManager();
                        else HideawayPlus.DISCORD_MANAGER = null;
                    }
                ),
                SimpleOption.ofBoolean(
                        "option.hideawayplus.ws",
                        SimpleOption.constantTooltip(
                                MutableText.of(TextContent.EMPTY)
                                        .append(Text.literal("Coming soon!").setStyle(Style.EMPTY.withColor(Formatting.DARK_GRAY).withItalic(true)))
                                        .append(Text.literal("Connects you to our WebSocket network."))
                                        .append(Text.literal("\n\n"))
                                        .append(Text.literal("Our WebSocket network provides services like chat channels, live outage updates and more.").setStyle(Style.EMPTY.withColor(Formatting.DARK_GRAY)))
                                        .append(Text.literal("\n\n"))
                                                // .append(Text.literal("Impact: ").setStyle(Style.EMPTY.withColor(Formatting.DARK_GRAY)))
                                                // .append(Text.literal("HIGH").setStyle(Style.EMPTY.withColor(Formatting.RED).withBold(true).withFont(new Identifier("hideawayplus:text"))))
                                                // .append(Text.literal("\n\n"))
                                                // .append(Text.literal("Many core services may not function without this feature.").setStyle(Style.EMPTY.withColor(Formatting.DARK_GRAY)))
                        ),
                        HideawayPlus.settings().ws,
                        (value) -> {
                            HideawayPlus.settings().ws = value;
                            if (value) HideawayPlus.WS_MANAGER = new WebSocketManager();
                            else HideawayPlus.WS_MANAGER = null;
                        }
                ),
                SimpleOption.ofBoolean(
                        "option.hideawayplus.stats",
                        SimpleOption.constantTooltip(
                                MutableText.of(TextContent.EMPTY)
                                        .append(Text.literal("Keeps track of your accuracy and proficiency at minigames."))
                                        .append(Text.literal("\n\n"))
                                        .append(Text.literal("This data isn't shared with us by default and is stored locally.").setStyle(Style.EMPTY.withColor(Formatting.DARK_GRAY)))
                                        .append(Text.literal("\n\n"))
                                        .append(Text.literal("Impact: ").setStyle(Style.EMPTY.withColor(Formatting.DARK_GRAY)))
                                        .append(Text.literal("LOW").setStyle(Style.EMPTY.withColor(Formatting.GREEN).withBold(true).withFont(new Identifier("hideawayplus:text"))))
                                        .append(Text.literal("\n\n"))
                        ),
                        HideawayPlus.settings().stats,
                        (value) -> {
                            HideawayPlus.settings().stats = value;
                        }
                )
        };
    }

    @Override
    protected void init() {
        this.list = new OptionListWidget(this.client, this.width, this.height, 96, this.height - 32, 25);
        this.list.addAll(MenuScreen.getOptions());
        this.addSelectableChild(this.list);
        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, button -> {
            this.client.options.write();
            this.client.getWindow().applyVideoMode();
            this.client.setScreen(this.parent);
        }).dimensions(this.width / 2 - 100, this.height - 27, 200, 20).build());
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button2) {
        int i = HideawayPlus.client().options.getGuiScale().getValue();
        if (super.mouseClicked(mouseX, mouseY, button2)) {
            if (HideawayPlus.client().options.getGuiScale().getValue() != i) {
                this.client.onResolutionChanged();
            }
            return true;
        }
        return false;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        list.render(matrices, mouseX, mouseY, delta);
        DrawableHelper.drawCenteredTextWithShadow(matrices, this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
