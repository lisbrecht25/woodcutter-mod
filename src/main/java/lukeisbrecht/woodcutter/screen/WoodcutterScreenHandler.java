package lukeisbrecht.woodcutter.screen;

import lukeisbrecht.woodcutter.Woodcutter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;

public class WoodcutterScreenHandler extends ScreenHandler {
    private final ScreenHandlerContext context;
    private final Inventory inventory;

    public WoodcutterScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public WoodcutterScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(Woodcutter.WOODCUTTER_SCREEN_HANDLER, syncId);
        this.context = context;
        this.inventory = new SimpleInventory(2);

        this.addSlot(new Slot(inventory, 0, 20, 33));
        this.addSlot(new Slot(inventory, 1, 143, 33) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }
        });

        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, Woodcutter.WOODCUTTER_BLOCK);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack stack = ItemStack.EMPTY;
        Slot clickedSlot = this.slots.get(slot);

        if (clickedSlot != null && clickedSlot.hasStack()) {
            ItemStack clickedStack = clickedSlot.getStack();
            stack = clickedStack.copy();

            if (slot == 1) {
                if (!this.insertItem(clickedStack, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (slot != 0) {
                if (!this.insertItem(clickedStack, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(clickedStack, 2, 38, false)) {
                return ItemStack.EMPTY;
            }

            if (clickedStack.isEmpty()) {
                clickedSlot.setStack(ItemStack.EMPTY);
            } else {
                clickedSlot.markDirty();
            }
        }

        return stack;
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.context.run((world, pos) -> {
            this.dropInventory(player, this.inventory);
        });
    }
}
