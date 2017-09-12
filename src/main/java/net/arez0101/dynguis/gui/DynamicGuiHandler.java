package net.arez0101.dynguis.gui;

import java.util.ArrayList;
import java.util.List;

import net.arez0101.dynguis.DynamicGUIs;
import net.arez0101.dynguis.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class DynamicGuiHandler implements IGuiHandler {
	
	private static List<GuiRegistryEntry> GUI_REGISTRY = new ArrayList<GuiRegistryEntry>();
	
	public static void init() {
		DynamicGUIs.LOGGER.info("Intializing [" + Reference.MOD_NAME + "]'s GuiHandler...");
		NetworkRegistry.INSTANCE.registerGuiHandler(DynamicGUIs.INSTANCE, new DynamicGuiHandler());
	}
	
	public static void registerGui(String name, DynamicGui gui) {
		GuiRegistryEntry entry = new GuiRegistryEntry(name, gui);
		registerGui(entry);		
	}
	
	public static void registerGui(GuiRegistryEntry entry) {
		if (!GUI_REGISTRY.contains(entry)) {
			GUI_REGISTRY.add(entry);
			
		}
		else {
			DynamicGUIs.LOGGER.error("Unable to register GUI with name " + entry.getName() + ". It was either improperly registered or already exists.");
		}
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (GUI_REGISTRY.get(ID) != null || ID <= GUI_REGISTRY.size()) {
			return GUI_REGISTRY.get(ID).getGui().getContainer();
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (GUI_REGISTRY.get(ID) != null || ID <= GUI_REGISTRY.size()) {
			return GUI_REGISTRY.get(ID).getGui();
		}
		return null;
	}
	
	public static ResourceLocation getGuiComponentLocation(String fileName) {
		return new ResourceLocation(Reference.MOD_ID, "textures/gui/" + fileName + ".png");
	}
	
	public static int getGuiID(String name) {
		for (int i = 0; i < GUI_REGISTRY.size(); i++) {
			if (GUI_REGISTRY.get(i).getName() == name) {
				return i;
			}
		}
		return 0;
	}
}
