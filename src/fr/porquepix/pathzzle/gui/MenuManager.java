package fr.porquepix.pathzzle.gui;

import fr.porquepix.pathzzle.gui.menus.LauncherMenu;
import fr.porquepix.pathzzle.render.Shader;

public class MenuManager {
	
	private static MenuManager _instance = null;
	
	private Menu menu;
	
	private boolean updateMenu;
	private Menu newMenu;
	
	protected MenuManager() {
		menu = new LauncherMenu();
	}
	
	public static MenuManager getInstance() {
		if (_instance == null) {
			_instance = new MenuManager();
		}
		return _instance;
	}

	public void update() {
		menu.update();
		
		if (updateMenu) {
			menu = newMenu;
			updateMenu = false;
		}
	}
	
	public void render() {
		Shader.HUD.bind();	
		menu.render();
	}
	
	public void renderGUI() {
		Shader.HUD.bind();	
		menu.renderGUI();
	}
	
	public void setMenu(Menu menu) {
		this.newMenu = menu;
		this.updateMenu = true;
	}

	public void dispose() {
		menu.dispose();
		_instance = null;
	}

	
}
