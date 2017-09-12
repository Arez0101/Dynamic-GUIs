package net.arez0101.dynguis.inventory;

import lombok.Getter;
import net.arez0101.dynguis.tileentity.DynamicTileInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.SlotItemHandler;

public class DynamicContainer extends Container {
	
	private final static int SLOT_SIZE = 18;
	private final static int OFFSET_Y = 25;
	private final static int OFFSET_X = 9;
	private final static int HOTBAR_OFFSET_Y = 58;
	private final static int INVENTORY_SLOTS = 36;
	
	@Getter
	private final DynamicTileInventory tileentity;
	
	@Getter
	private int rows;
	
	@Getter
	private int columns;
	
	@Getter
	private InventoryPlayer playerInventory;
	
	private boolean hasPlayerInventory = true;
	
	public DynamicContainer(InventoryPlayer inventory, DynamicTileInventory tile, int rows, int columns, boolean hasPlayerInv) {
		this.tileentity = tile;
		this.rows = rows;
		this.columns = columns;
		this.hasPlayerInventory = hasPlayerInv;
		this.playerInventory = inventory;
		
		if (hasPlayerInv) {
			this.constructPlayerInventory(inventory);
		}
		
		this.constructAllSlots();
	}
	
	public void constructPlayerInventory(InventoryPlayer inventory) {		
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 9; y++) {
				this.addSlotToContainer(new Slot(inventory, y + (x * 9) + 9, this.getInventoryStartX() + (y * SLOT_SIZE), this.getInventoryStartY() + (x * SLOT_SIZE)));
			}
		}
		
		for (int h = 0; h < 9; h++) {
			this.addSlotToContainer(new Slot(inventory, h, this.getInventoryStartX() + (h * SLOT_SIZE), this.getInventoryStartY() + HOTBAR_OFFSET_Y));
		}
	}
	
	public void constructAllSlots() {
		for (int x = 0; x < this.rows; x++) {
			for (int y = 0; y < this.columns; y++) {
				this.addSlotToContainer(new SlotItemHandler(this.tileentity.getItemHandler(), INVENTORY_SLOTS + y + (x * this.columns), OFFSET_X + (y * SLOT_SIZE), OFFSET_Y + (x * SLOT_SIZE)));
			}
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return this.tileentity.canInteractWith(playerIn);
	}
	
	public boolean hasPlayerInventory() {
		return this.hasPlayerInventory;
	}
	
	public int getInventoryStartY() {
		return OFFSET_Y + (this.rows * SLOT_SIZE) + 4;
	}
	
	public int getInventoryStartX() {
		if (this.columns <= 9) {
			return OFFSET_X;
		}
		return (this.columns * SLOT_SIZE) + (OFFSET_X * 2) / 4;
	}
	
	public int getSlotSize() {
		return SLOT_SIZE;
	}
	
	public void fillInventory(NonNullList<ItemStack> stacks) {
		for (int i = 0; i < this.inventoryItemStacks.size(); i++) {
			this.inventoryItemStacks.set(i, stacks.get(i));
		}
	}
}
