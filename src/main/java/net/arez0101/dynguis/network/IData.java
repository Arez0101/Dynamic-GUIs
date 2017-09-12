package net.arez0101.dynguis.network;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public interface IData {
	
	void manageData(NBTTagCompound tag, MessageContext context);
}
