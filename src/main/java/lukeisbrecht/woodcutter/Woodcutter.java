package lukeisbrecht.woodcutter;

import lukeisbrecht.woodcutter.block.WoodcutterBlock;
import lukeisbrecht.woodcutter.screen.WoodcutterScreenHandler;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class Woodcutter implements ModInitializer {
	public static final String MOD_ID = "woodcutter";

	public static Block WOODCUTTER_BLOCK;
	public static ScreenHandlerType<WoodcutterScreenHandler> WOODCUTTER_SCREEN_HANDLER;

	@Override
	public void onInitialize() {
		// Create identifiers
		Identifier blockId = Identifier.of(MOD_ID, "wood_cutter");

		// Create registry keys
		RegistryKey<Block> blockKey = RegistryKey.of(RegistryKeys.BLOCK, blockId);
		RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, blockId);

		// Register block with registry key
		WOODCUTTER_BLOCK = Registry.register(
				Registries.BLOCK,
				blockId,
				new WoodcutterBlock(AbstractBlock.Settings.create()
						.registryKey(blockKey)
						.strength(3.5f)
						.requiresTool()
						.sounds(BlockSoundGroup.WOOD))
		);

		// Register block item with registry key
		Registry.register(
				Registries.ITEM,
				blockId,
				new BlockItem(WOODCUTTER_BLOCK, new Item.Settings()
						.registryKey(itemKey))
		);

		// Register screen handler
		WOODCUTTER_SCREEN_HANDLER = Registry.register(
				Registries.SCREEN_HANDLER,
				blockId,
				new ScreenHandlerType<>(WoodcutterScreenHandler::new, FeatureFlags.VANILLA_FEATURES)
		);
	}
}