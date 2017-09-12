package net.arez0101.dynguis.tileentity;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public abstract class DynamicTileInventory extends TileEntity {
	
	@Getter
	@Setter
	private String name;
	
	@Getter
	private int inventorySize;
	
	@Getter
	private static IItemHandler itemHandler;
	
	public DynamicTileInventory(String name, int inventorySize) {
		this.setName(name);
		this.inventorySize = inventorySize;
		this.itemHandler = new ItemStackHandler() {
			
			@Override
			public int getSlotLimit(int slot) {
				return this.getStackInSlot(slot).getMaxStackSize();
			}
			
			@Override
			protected void onContentsChanged(int slot) {
				super.onContentsChanged(slot);
				DynamicTileInventory.this.markDirty();
			}
		};
	}
	
	public abstract int getMaxStackSize(int slot);
	
	public abstract boolean isValidStack(int slot, ItemStack stack);
	
	public abstract boolean canRemoveStack(int slot);
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return this.getCapability(capability, facing) != null;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			IItemHandler handler = this.getItemHandler();
			
			if (handler != null) {
				return (T) handler;
			}
		}
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return !oldState.getBlock().isAssociatedBlock(newState.getBlock());
	}
	
	public static void saveInventory(IItemHandler inventory, NBTTagCompound tag) {
		if (itemHandler != null && itemHandler.getSlots() > 0) {
			NBTTagList list = new NBTTagList();
			
			for (int i = 0; i < itemHandler.getSlots(); i++) {
				ItemStack stack = itemHandler.getStackInSlot(i);
				NBTTagCompound item = new NBTTagCompound();
				stack.writeToNBT(item);
				list.appendTag(item);
			}
			tag.setTag("Items", list);
		}
	}
	
	public static void loadInventory(IItemHandler inventory, NBTTagCompound tag) {
		if (itemHandler != null && itemHandler.getSlots() > 0) {
			NBTTagList list = tag.getTagList("Items", 10);
			
			for (int i = 0; i < itemHandler.getSlots(); i++) {
				NBTTagCompound item = list.getCompoundTagAt(i);
				itemHandler.insertItem(i, item != null && item.hasKey("id") ? new ItemStack(item) : ItemStack.EMPTY, false);
			}
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		this.saveInventory(this.getItemHandler(), compound);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.loadInventory(this.getItemHandler(), compound);
	}
	
	public boolean canInteractWith(EntityPlayer playerIn) {
		return playerIn.getDistanceSq(this.getPos().getX() + 0.5, this.getPos().getY() + 0.5, this.getPos().getZ() + 0.5) <= 64 && this.getWorld().getTileEntity(this.getPos()) == this;
	}
}
