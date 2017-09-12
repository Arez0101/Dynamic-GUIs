package net.arez0101.dynguis.network;

import io.netty.buffer.ByteBuf;
import net.arez0101.dynguis.DynamicGUIs;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketToServer implements IMessage {
	
	private NBTTagCompound tag;
	private IData handler;
	
	public PacketToServer(NBTTagCompound tag, IData handler) {
		this.tag = tag;
		this.handler = handler;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		PacketBuffer buffer = new PacketBuffer(buf);
		
		try {
			this.tag = buffer.readCompoundTag();
			this.handler = ButtonHandler.GUI_BUTTON_HANDLER;
		}
		catch (Exception e) {
			DynamicGUIs.LOGGER.error("An error occured trying to read a server packet.", e);
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		PacketBuffer buffer = new PacketBuffer(buf);
		buffer.writeCompoundTag(this.tag);
	}
	
	public static class Message implements IMessageHandler<PacketToServer, IMessage> {

		@Override
		public IMessage onMessage(final PacketToServer message, final MessageContext ctx) {
			FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(new Runnable() {
				
				@Override
				public void run() {
					if (message.tag != null && message.handler != null) {
						message.handler.manageData(message.tag, ctx);
					}
				}
			});
			return null;
		}
	}
}
