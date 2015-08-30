package fr.porquepix.pathzzle.game;

import fr.porquepix.pathzzle.gui.MenuManager;

public class GameState {
	
	private static GameState _instance = null;
	
	private boolean newState;
	private State state;

	private MenuManager menu;
	private Game game;

	protected GameState() {
		newState = false;
		state = State.IN_MENU;
	}
	
	public static GameState getInstance() {
		if (_instance == null) {
			_instance = new GameState();
		}
		return _instance;
	}
	
	public void update() {
		if (newState) newState = false;
		
		if (state == State.IN_GAME) {
			if (game == null) {
				game = Game.getInstance();
			}
			game.update();
		}
		
		if (newState) return;
		
		if (state == State.IN_MENU) {
			if (menu == null) {
				menu = MenuManager.getInstance();
			}
			menu.update();
		}
	}
	
	public void render() {
		if (newState) newState = false;
		
		if (state == State.IN_GAME) {
			if (game == null) {
				game = Game.getInstance();
			}
			game.render();
		}
		
		if (newState) return;
		
		if (state == State.IN_MENU) {
			if (menu == null) {
				menu = MenuManager.getInstance();
			}
			menu.render();
		}
	}
	
	public void renderGUI() {
		if (newState) newState = false;
		
		if (state == State.IN_GAME) {
			if (game == null) {
				game = Game.getInstance();
			}
			game.renderGUI();
		}
		
		if (newState) return;
		
		if (state == State.IN_MENU) {
			if (menu == null) {
				menu = MenuManager.getInstance();
			}
			menu.renderGUI();
		}
	}
	
	public void setState(State state) {
		this.state = state;
		this.newState = true;
	}
	
	public void dispose() {		
		if (game != null)
			game.dispose();
		
		if (menu != null)
			menu.dispose();
	}
	
	public enum State {
		IN_GAME, IN_MENU;
	}

}
