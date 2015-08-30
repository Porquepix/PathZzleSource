package fr.porquepix.pathzzle.gui;

import java.util.ArrayList;
import java.util.List;

public abstract class Menu {

	protected List<GuiComponent> guis;
	
	public Menu() {
		guis = new ArrayList<GuiComponent>();
	}
	
	public void update() {
		for (GuiComponent c : guis) {
			c.update();
		}
	}

	public void render() {
		
	}
	
	public void renderGUI() {
		for (GuiComponent c : guis) {
			c.renderGUI();
		}
	}
	
	public void dispose() {
		guis.clear();
	}
	
}
