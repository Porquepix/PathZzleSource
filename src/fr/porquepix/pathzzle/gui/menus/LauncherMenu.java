package fr.porquepix.pathzzle.gui.menus;

import org.lwjgl.opengl.Display;

import fr.porquepix.pathzzle.Main;
import fr.porquepix.pathzzle.game.Game;
import fr.porquepix.pathzzle.game.GameState;
import fr.porquepix.pathzzle.game.GameState.State;
import fr.porquepix.pathzzle.game.Level;
import fr.porquepix.pathzzle.gui.GuiComponent;
import fr.porquepix.pathzzle.gui.IEventGui;
import fr.porquepix.pathzzle.gui.Menu;
import fr.porquepix.pathzzle.gui.MenuManager;
import fr.porquepix.pathzzle.gui.components.Button;
import fr.porquepix.pathzzle.gui.components.Image;
import fr.porquepix.pathzzle.gui.components.Label;
import fr.porquepix.pathzzle.render.Texture;

public class LauncherMenu extends Menu implements IEventGui {

	public LauncherMenu() {
		super();
		
		guis.add(new Button("Play", Display.getWidth() / 2, Display.getHeight() / 2, 400, 48).setEventClick(this));
		guis.add(new Button("Levels", Display.getWidth() / 2, Display.getHeight() / 2 + (48 + 15) * 1, 400, 48).setEventClick(this));
		guis.add(new Button("Editor", Display.getWidth() / 2, Display.getHeight() / 2 + (48 + 15) * 2, 400, 48).setEventClick(this));
		guis.add(new Button("Options", Display.getWidth() / 2, Display.getHeight() / 2 + (48 + 15) * 2, 400, 48).setEventClick(this));
		guis.add(new Button("Credit", 65, Display.getHeight() - 32, 100, 32, 16).setEventClick(this));
		guis.add(new Button("Quit", Display.getWidth() - 65, Display.getHeight() - 32, 100, 32, 16).setEventClick(this));
		
		guis.add(new Label(Main.TITLE + " Version " + Main.VERSION, 15, 15, 15, false));
		
		guis.add(new Image((Display.getWidth() - 634) / 2, 150, 634, 113, Texture.LOGO));
	}

	@Override
	public void onClick(GuiComponent c) {
		if (c == guis.get(0)) {
			Game.getInstance().setLevel(new Level(1));
			Game.getInstance().resetScore();
			GameState.getInstance().setState(State.IN_GAME);
		} else 	if (c == guis.get(1)) {
			MenuManager.getInstance().setMenu(new LevelsMenu());
		} else 	if (c == guis.get(3)) {
			MenuManager.getInstance().setMenu(new OptionsMenu());
		} else 	if (c == guis.get(4)) {
			MenuManager.getInstance().setMenu(new CreditMenu());
		} else 	if (c == guis.get(5)) {
			Main.getInstance().dispose();
			System.exit(0);
		}		
	}

}
