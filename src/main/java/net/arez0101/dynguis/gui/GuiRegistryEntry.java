package net.arez0101.dynguis.gui;

import lombok.Getter;

public class GuiRegistryEntry {
	
	@Getter
	private String name;
	
	@Getter
	private DynamicGui gui;
	
	public GuiRegistryEntry(String name, DynamicGui gui) {
		this.name = name;
		this.gui = gui;
	}
}
