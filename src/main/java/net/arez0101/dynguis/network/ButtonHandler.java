package net.arez0101.dynguis.network;

import net.arez0101.dynguis.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ButtonHandler {
	
	public static SimpleNetworkWrapper network;
	
	public static void init() {
		network = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
	}
	
	@SideOnly(Side.CLIENT)
	public static void sendButtonPacket(TileEntity tile, int buttonID) {
		NBTTagCompound tag = new NBTTagCompound();
		BlockPos pos = tile.getPos();
		
		tag.setInteger("x", pos.getX());
		tag.setInteger("y", pos.getY());
		tag.setInteger("z", pos.getZ());
		
		tag.setInteger("PlayerID", Minecraft.getMinecraft().player.getEntityId());
		tag.setInteger("ButtonID", buttonID);
		
		network.sendToServer(new PacketToServer(tag, ButtonHandler.GUI_BUTTON_HANDLER));
	}
	
	public static final IData GUI_BUTTON_HANDLER = new IData() {

		@Override
		@SideOnly(Side.CLIENT)
		public void manageData(NBTTagCompound tag, MessageContext context) {
			World world = Minecraft.getMinecraft().world;

			if (world != null) {
				EntityPlayer player = (EntityPlayer) world.getEntityByID(tag.getInteger("PlayerID"));
				TileEntity tile = world.getTileEntity(new BlockPos(tag.getInteger("x"), tag.getInteger("y"), tag.getInteger("z")));
				Container container = player.openContainer;

//				if (tile instanceof PagedTileInventory) {
//					((PagedTileInventory) tile).pageChange(tag.getInteger("ButtonID"), player);
//				}
//
//				if (container instanceof PagedContainer) {
//					((PagedContainer) container).pageChange(tag.getInteger("ButtonID"), player);
//				}
			}
		}
	};
}
