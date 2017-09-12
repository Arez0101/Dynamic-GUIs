package net.arez0101.dynguis.proxy;

import net.arez0101.dynguis.gui.DynamicGuiHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event) {}
	
	public void init(FMLInitializationEvent event) {
		DynamicGuiHandler.init();
	}
	
	public void postInit(FMLPostInitializationEvent event) {}
}
