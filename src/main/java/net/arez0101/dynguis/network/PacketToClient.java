package net.arez0101.dynguis.network;

import io.netty.buffer.ByteBuf;
import net.arez0101.dynguis.DynamicGUIs;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketToClient implements IMessage {
	
	private NBTTagCompound tag;
	private IData handler;
	
	public PacketToClient(NBTTagCompound tag, IData handler) {
		this.tag = tag;
		this.handler = handler;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		PacketBuffer buffer = new PacketBuffer(buf);
		
		try {
			this.tag = buffer.readCompoundTag();
			int id = buffer.readInt();
			this.handler = ButtonHandler.GUI_BUTTON_HANDLER;
		}
		catch (Exception e) {
			DynamicGUIs.LOGGER.error("An error occured trying to read a client packet.", e);
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		PacketBuffer buffer = new PacketBuffer(buf);
		buffer.writeCompoundTag(this.tag);
	}
	
	public static class Message implements IMessageHandler<PacketToClient, IMessage> {

		@Override
		public IMessage onMessage(final PacketToClient message, final MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(new Runnable() {
				
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
