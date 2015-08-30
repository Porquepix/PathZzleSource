package fr.porquepix.pathzzle.gui.menus;

import org.lwjgl.opengl.Display;

import fr.porquepix.pathzzle.gui.GuiComponent;
import fr.porquepix.pathzzle.gui.IEventGui;
import fr.porquepix.pathzzle.gui.Menu;
import fr.porquepix.pathzzle.gui.MenuManager;
import fr.porquepix.pathzzle.gui.components.Button;
import fr.porquepix.pathzzle.gui.components.Label;

public class CreditMenu extends Menu implements IEventGui {

	public CreditMenu() {
		super();
		
		guis.add(new Button("Back", Display.getWidth() / 2, Display.getHeight() - 48, 150, 48).setEventClick(this));
		
		guis.add(new Label("Programmed by", Display.getWidth() / 2, 200, 22, true));
		guis.add(new Label("Porquepix (github.com/Porquepix)", Display.getWidth() / 2, 235, 18, true));
		guis.add(new Label("Special thanks", Display.getWidth() / 2, 350, 22, true));
		guis.add(new Label("Essa (soundcloud.com/essa-1) for the music", Display.getWidth() / 2, 385, 18, true));
	}

	@Override
	public void onClick(GuiComponent c) {
		if (c == guis.get(0)) {
			MenuManager.getInstance().setMenu(new LauncherMenu());
		}
	}

}
