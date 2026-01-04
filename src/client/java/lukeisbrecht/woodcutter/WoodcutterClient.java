package lukeisbrecht.woodcutter;

import lukeisbrecht.woodcutter.screen.WoodcutterScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class WoodcutterClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		HandledScreens.register(Woodcutter.WOODCUTTER_SCREEN_HANDLER, WoodcutterScreen::new);
	}
}