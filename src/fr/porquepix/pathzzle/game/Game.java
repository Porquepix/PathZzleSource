package fr.porquepix.pathzzle.game;

import org.lwjgl.input.Keyboard;

import fr.porquepix.pathzzle.gui.HUD;
import fr.porquepix.pathzzle.input.InputManager;
import fr.porquepix.pathzzle.render.Shader;

public class Game {

	private static Game _instance = null;
	
	private int score;
	private int levelScore;
	private boolean hasNewLevel;
	
	private Level level;
	private GameMenu menu;
	private Level newLevel;
	private HUD hud;

	
	protected Game() {
		score = 0;
		levelScore = 0;
		
		menu = new GameMenu();
		
		newLevel = new Level(1);
		
		hud = new HUD();
	}
	
	public static Game getInstance() {
		if (_instance == null) {
			_instance = new Game();
		}
		return _instance;
	}
	
	public void update() {
		if (level != null) {			
			level.update();
		}
		
		if (InputManager.getKeyDown(Keyboard.KEY_SPACE)) {
			hasNewLevel = true;
			addPoint(-levelScore);
		}	
		
		if (hasNewLevel) {
			hasNewLevel = false;
			
			if (level != null) {
				level.dispose();
				System.gc();
			}
			
			levelScore = 0;
			level = new Level(newLevel);
		}
		
		hud.update();
		menu.update();
	}
	
	public void render() {
		if (level == null) return;
		
		level.render();
		menu.render();
	}
	
	public void renderGUI() {
		if (level == null) return;
		
		Shader.HUD.bind();
		hud.renderGUI();
		menu.renderGUI();
	}
	
	public void addPoint(int nbPoints) {
		score += nbPoints;
		levelScore += nbPoints;
		hud.setScore(score);
	}
	
	public void resetScore() {
		score = 0;
		levelScore = 0;
		hud.setScore(score);
	}
	
	public void dispose() {
		level.dispose();
		menu.dispose();
		_instance = null;
	}
	
	public void setLevel(Level l) {
		newLevel = l;
		hasNewLevel = true;
	}
	
	public Level getLevel() {
		return level;
	}

}
