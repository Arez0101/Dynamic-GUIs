package net.arez0101.dynguis.util;

import lombok.Getter;

public enum ButtonDirection {
	
	LEFT("\u25c0"),
	RIGHT("\u25b6"),
	UP("\u25b2"),
	DOWN("\u25bc");
	
	@Getter
	private String text;
	
	private ButtonDirection(String text) {
		this.text = text;
	}
}
