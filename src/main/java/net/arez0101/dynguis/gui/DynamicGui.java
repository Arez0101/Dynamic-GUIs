package net.arez0101.dynguis.gui;

import java.awt.Color;

import lombok.Getter;
import lombok.Setter;
import net.arez0101.dynguis.inventory.DynamicContainer;
import net.arez0101.dynguis.util.Alignment;
import net.arez0101.dynguis.util.ButtonDirection;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;

public class DynamicGui extends GuiContainer {
	
	private static final int offsetX = 9;
	private static final int offsetY = 4;
	private static final int offsetYWithTitle = offsetY + 18 + offsetY;
	private static final int slotSize = 18;
	
	@Getter
	private DynamicContainer container;
	
	@Getter
	private final int width;
	
	@Getter
	private final int height;
	
	@Getter
	private final int rows;
	
	@Getter
	private final int columns;
	
	private static boolean hasPlayerInventory;
	public static boolean hasPlayerInventory() {return hasPlayerInventory;}
	
	@Getter
	@Setter
	private static Color borderHighlight = Color.WHITE;
	
	@Getter
	@Setter
	private static Color borderShadow = Color.decode("#373737");
	
	@Getter
	@Setter
	private static Color fillColor = Color.decode("#C6C6C6");
	
	@Getter
	@Setter
	private static Color textColor = Color.decode("#404040");
	
	@Getter
	@Setter
	private static Color slotBackground = Color.decode("8B8B8B");
	
//	@Getter
//	@Setter
//	private static boolean isPaged = false;
	
//	@Getter
//	@Setter
//	private static boolean isScrolling = false;
	
	public DynamicGui(DynamicContainer inventorySlotsIn, int rows, int columns, boolean hasPlayerInventory) {
		this(inventorySlotsIn, getWidthFromColumns(columns), getHeightFromRows(rows), rows, columns, hasPlayerInventory);
	}

	public DynamicGui(DynamicContainer container, int width, int height, int rows, int columns, boolean hasPlayerInventory) {
		super(container);
		this.container = container;
		this.width = width;
		this.height = height;
		this.rows = rows;
		this.columns = columns;
		this.hasPlayerInventory = hasPlayerInventory;
		this.xSize = width;
		this.ySize = height;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		this.fillFrame(this.getWidth(), this.getHeight());
		this.drawFrameBorders(this.getWidth(), this.getHeight());
		this.drawInventorySlots();
		this.drawPlayerInventorySlots();
	}
	
	public void drawFrameBorders(int width, int height) {
		// Draw top bar
		this.drawRect(this.getGuiLeft(), this.getGuiTop(), this.getGuiLeft() + this.getWidth(), this.getGuiTop(), this.getBorderHighlight().getRGB());
		
		// Draw left bar
		this.drawRect(this.getGuiLeft(), this.getGuiTop(), this.getGuiLeft(), this.getGuiTop() + this.getHeight() - 1, this.getBorderHighlight().getRGB());
		
		// Draw bottom bar
		this.drawRect(this.getGuiLeft(), this.getGuiTop() + this.getHeight(), this.getGuiLeft() + this.getWidth(), this.getGuiTop() + this.getHeight(), this.getBorderShadow().getRGB());
		
		// Draw right bar
		this.drawRect(this.getGuiLeft() + this.getWidth(), this.getGuiTop() + 1, this.getGuiLeft() + this.getWidth(), this.getGuiTop() + this.getHeight(), this.getBorderShadow().getRGB());
	}
	
	public void fillFrame(int width, int height) {
		this.drawRect(this.getGuiLeft(), this.getGuiTop(), this.getGuiLeft() + width, this.getGuiTop() + height, this.getFillColor().getRGB());
	}
	
	public void drawTitle(String title) {
		this.drawTitle(title, Alignment.LEFT_ALIGNED);
	}
	
	public void drawTitle(String title, Alignment alignment) {
		int x = this.offsetX;
		
		switch (alignment) {
		case LEFT_ALIGNED:
			x = this.offsetX;
		case CENTERED:
			x = (this.getWidth() / 2) - (this.fontRendererObj.getStringWidth(title) / 2);
		case RIGHT_ALIGNED:
			x = this.getWidth() - this.offsetX - this.fontRendererObj.getStringWidth(title);
		}
		
		this.fontRendererObj.drawString(title, x, this.offsetY, this.getTextColor().getRGB());
	}
	
	public void newNavigationButton(ButtonDirection direction, int startX, int startY, int buttonWidth, int buttonHeight) {
		this.buttonList.add(new GuiButton(direction.ordinal(), this.getGuiLeft() + startX, this.getGuiTop() + startY, buttonWidth, buttonHeight, direction.getText()));
	}
	
	public void newMultipurposeButton(int id, int startX, int startY, int buttonWidth, int buttonHeight) {
		this.buttonList.add(new GuiButton(id, this.getGuiLeft() + startX, this.getGuiTop() + startY, buttonWidth, buttonHeight, ""));
	}
	
	public void drawInventorySlots() {
		for (int y = 0; y < this.rows; y++) {
			for (int x = 0; x < this.columns; x++) {
				int startX = this.getGuiLeft() + this.offsetX + (x * this.slotSize);
				int startY = this.getGuiTop() + this.offsetYWithTitle + (y * this.slotSize);
				int endX = startX + this.slotSize;
				int endY = startY + this.slotSize;
				Color highlight = this.getSlotBackground().brighter().brighter();
				Color shadow = this.getSlotBackground().darker().darker();
				
				// Draw background
				this.drawRect(startX, startY, endX, endY, this.getSlotBackground().getRGB());
				
				// Draw top highlight
				this.drawRect(startX, startY, endX, startY, highlight.getRGB());
				
				// Draw left highlight
				this.drawRect(startX, startY, startX, endY, highlight.getRGB());
				
				// Draw bottom shadow
				this.drawRect(startX + 1, endY, endX, endY, shadow.getRGB());
				
				// Draw right shadow
				this.drawRect(endX, startY + 1, endX, endY, shadow.getRGB());
			}
		}
	}
	
	public void drawPlayerInventorySlots() {
		// Inventory slots
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				int startX = this.getGuiLeft() + this.offsetX + (x * this.slotSize);
				int startY = this.getGuiTop() + this.offsetYWithTitle + (this.slotSize * this.getRows()) + 4 + (y * this.slotSize);
				int endX = startX + this.slotSize;
				int endY = startY + this.slotSize;
				Color highlight = this.getSlotBackground().brighter().brighter();
				Color shadow = this.getSlotBackground().darker().darker();
				
				// Draw background
				this.drawRect(startX, startY, endX, endY, this.getSlotBackground().getRGB());
				
				// Draw top highlight
				this.drawRect(startX, startY, endX, startY, highlight.getRGB());
				
				// Draw left highlight
				this.drawRect(startX, startY, startX, endY, highlight.getRGB());
				
				// Draw bottom shadow
				this.drawRect(startX + 1, endY, endX, endY, shadow.getRGB());
				
				// Draw right shadow
				this.drawRect(endX, startY + 1, endX, endY, shadow.getRGB());
			}
		}
		
		// Hotbar slots
		for (int x = 0; x < 9; x++) {
			int startX = this.getGuiLeft() + this.offsetX + (x * this.slotSize);
			int startY = this.getGuiTop() + this.offsetYWithTitle + (this.slotSize * (this.getRows() + 3)) + 8;
			int endX = startX + this.slotSize;
			int endY = startY + this.slotSize;
			Color highlight = this.getSlotBackground().brighter().brighter();
			Color shadow = this.getSlotBackground().darker().darker();
			
			// Draw background
			this.drawRect(startX, startY, endX, endY, this.getSlotBackground().getRGB());
			
			// Draw top highlight
			this.drawRect(startX, startY, endX, startY, highlight.getRGB());
			
			// Draw left highlight
			this.drawRect(startX, startY, startX, endY, highlight.getRGB());
			
			// Draw bottom shadow
			this.drawRect(startX + 1, endY, endX, endY, shadow.getRGB());
			
			// Draw right shadow
			this.drawRect(endX, startY + 1, endX, endY, shadow.getRGB());
		}
	}
	
	protected static int getHeightFromRows(int rows) {
		int playerInvHeight = 0;
		
		if (hasPlayerInventory()) {
			playerInvHeight = (4 * slotSize) + 8;
		}
		
		return (rows * slotSize) + (offsetYWithTitle + offsetY) + playerInvHeight;
	}
	
	protected static int getWidthFromColumns(int columns) {
		return columns <= 9 ? (9 * slotSize) + (offsetX * 2) : (columns * slotSize) + (offsetX * 2);
	}
}
