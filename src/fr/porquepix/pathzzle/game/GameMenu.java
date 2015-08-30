package fr.porquepix.pathzzle.game;

import org.lwjgl.opengl.Display;

import fr.porquepix.pathzzle.game.GameState.State;
import fr.porquepix.pathzzle.gui.GuiComponent;
import fr.porquepix.pathzzle.gui.IEventGui;
import fr.porquepix.pathzzle.gui.Menu;
import fr.porquepix.pathzzle.gui.MenuManager;
import fr.porquepix.pathzzle.gui.components.Button;
import fr.porquepix.pathzzle.gui.menus.LauncherMenu;

public class GameMenu extends Menu implements IEventGui {

	public GameMenu() {
		super();
		
		guis.add(new Button("Home", Display.getWidth() - 65, Display.getHeight() - 32, 100, 32, 16).setEventClick(this));
	}

	@Override
	public void onClick(GuiComponent c) {
		if (c == guis.get(0)) {
			GameState.getInstance().setState(State.IN_MENU);
			MenuManager.getInstance().setMenu(new LauncherMenu());
		}
	}

}
