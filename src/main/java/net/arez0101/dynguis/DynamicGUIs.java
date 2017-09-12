package net.arez0101.dynguis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.arez0101.dynguis.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
		modid = Reference.MOD_ID,
		name = Reference.MOD_NAME,
		version = Reference.VERSION
)
public class DynamicGUIs {
	
	@Mod.Instance(Reference.MOD_ID)
	public static DynamicGUIs INSTANCE;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.COMMON_PROXY)
	public static CommonProxy PROXY;
	
	public static Logger LOGGER = LogManager.getLogger(Reference.MOD_NAME);
	
	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		PROXY.preInit(event);
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event) {
		PROXY.init(event);
	}
	
	@EventHandler
	public static void postInit(FMLPostInitializationEvent event) {
		PROXY.postInit(event);
	}
}
