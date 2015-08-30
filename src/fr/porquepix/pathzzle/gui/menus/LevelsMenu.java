package fr.porquepix.pathzzle.gui.menus;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;

import fr.porquepix.pathzzle.game.Game;
import fr.porquepix.pathzzle.game.GameState;
import fr.porquepix.pathzzle.game.GameState.State;
import fr.porquepix.pathzzle.game.Level;
import fr.porquepix.pathzzle.gui.GuiComponent;
import fr.porquepix.pathzzle.gui.IEventGui;
import fr.porquepix.pathzzle.gui.Menu;
import fr.porquepix.pathzzle.gui.MenuManager;
import fr.porquepix.pathzzle.gui.components.Button;
import fr.porquepix.pathzzle.gui.components.Label;
import fr.porquepix.pathzzle.utils.Config;

public class LevelsMenu extends Menu implements IEventGui {

	private static final int nbResultPerPage = 10;
	
	private int baseLevelPage, baseLevelMaxPage, baseLevelNb;
	private List<Button> baseLevelsToAdd;
	private List<Button> baseLevelsToRemove;
	private boolean updateBaseLevels = false;
	
	public LevelsMenu() {
		super();
		
		guis.add(new Label("Base Levels", Display.getWidth() / 2, 75, 22, true));
		guis.add(new Label("Custom Levels", Display.getWidth() / 2, 400, 22, true));
		guis.add(new Label("Soon Available !", Display.getWidth() / 2, 450, 16, true));
		guis.add(new Label("Others", Display.getWidth() / 2, 550, 22, true));
		
		guis.add(new Button("Back", Display.getWidth() / 2, Display.getHeight() - 48, 150, 48).setEventClick(this));
		guis.add(new Button("Previous", Display.getWidth() / 2 - 110, 325, 200, 48).setEventClick(this));
		guis.add(new Button("Next", Display.getWidth() / 2 + 110, 325, 200, 48).setEventClick(this));
		guis.add(new Button("Generate random level", Display.getWidth() / 2, 625, 500, 48).setEventClick(this));
		
		baseLevelPage = 1;
		baseLevelNb = (int) Config.getInstance().get(Config.MAX_BASE_LEVEL);
		baseLevelMaxPage = (int) Math.ceil((float) baseLevelNb / nbResultPerPage);
		baseLevelsToAdd = new ArrayList<Button>();
		updateBaseLevelsPage();
	}
	
	private void updateBaseLevelsPage() {
		baseLevelsToRemove = new ArrayList<Button>(baseLevelsToAdd);
		baseLevelsToAdd.clear();
		
		int pageResult = (baseLevelPage - 1) * nbResultPerPage;
		for (int i = pageResult; i < Math.min(baseLevelNb - pageResult, nbResultPerPage) + pageResult; i++) {
			final int j = i;
			baseLevelsToAdd.add(new Button("Level " + (i + 1), Display.getWidth() / 6 + ((i - pageResult) % (nbResultPerPage / 2)) * (200 + 15), 150 + (int) ((i - pageResult) / (nbResultPerPage / 2)) * 75, 200, 48).setEventClick(new IEventGui() {
				@Override
				public void onClick(GuiComponent c) {
					GameState.getInstance().setState(State.IN_GAME);
					Game.getInstance().setLevel(new Level(j + 1));
				}
			}));
		}
		
		updateBaseLevels = true;
	}
	
	public void update() {
		for (GuiComponent b : guis) {
			if (b == guis.get(5) && baseLevelPage == 1) continue;
			if (b == guis.get(6) && baseLevelPage >= baseLevelMaxPage) continue;
			
			b.update();
		}
		
		if (updateBaseLevels) {
			guis.removeAll(baseLevelsToRemove);
			guis.addAll(baseLevelsToAdd);
			updateBaseLevels = false;
		}
	}
	
	@Override
	public void renderGUI() {
		for (GuiComponent b : guis) {
			if (b == guis.get(5) && baseLevelPage == 1) continue;
			if (b == guis.get(6) && baseLevelPage >= baseLevelMaxPage) continue;
			
			b.renderGUI();
		}
	}

	@Override
	public void onClick(GuiComponent c) {
		if (c == guis.get(4)) {
			MenuManager.getInstance().setMenu(new LauncherMenu());
		} else if (c == guis.get(5)) {
			baseLevelPage--;
			updateBaseLevelsPage();
		} else if (c == guis.get(6)) {
			baseLevelPage++;
			updateBaseLevelsPage();
		}	else if (c == guis.get(7)) {
			GameState.getInstance().setState(State.IN_GAME);
			Game.getInstance().setLevel(new Level());
		}
	}

}
