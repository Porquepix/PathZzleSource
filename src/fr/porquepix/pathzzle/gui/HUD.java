package fr.porquepix.pathzzle.gui;

import static org.lwjgl.opengl.GL15.*;

import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;

import fr.porquepix.pathzzle.entities.Player;
import fr.porquepix.pathzzle.game.Game;
import fr.porquepix.pathzzle.gui.components.Label;
import fr.porquepix.pathzzle.render.Renderer;
import fr.porquepix.pathzzle.render.Texture;
import fr.porquepix.pathzzle.utils.Color4f;

public class HUD extends GuiComponent {

	private int selectedPlayerTextureLoc;
	
	private ArrayList<GuiComponent> guis; 

	public HUD() {
		selectedPlayerTextureLoc = 0;
		
		guis = new ArrayList<GuiComponent>();
		
		guis.add(new Label("Player", 87, 25, 16, true));
		guis.add(new Label("Score", 87, 150, 16, true));
		guis.add(new Label("0", 87, 175, 16, true));
		guis.add(new Label("*Space* to", 87, Display.getHeight() - 60, 14, true));
		guis.add(new Label("restart level", 87, Display.getHeight() - 35, 14, true));
		
		createVBO();
	}
	
	private void createVBO() {
		buffer = BufferUtils.createFloatBuffer(4 * (2 + 4 + 2));

		fillBuffer();

		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_DYNAMIC_DRAW);
		buffer.clear();
	}

	private void updateVBO() {
		fillBuffer();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferSubData(GL_ARRAY_BUFFER, 0, buffer);
		buffer.clear();
	}

	private void fillBuffer() {
		buffer.clear();
		bufferSize = 0;

		buffer.put(Renderer.quadData(55, 50, 64, 64, 0, 0, new Color4f(1, 1, 1 ,1), selectedPlayerTextureLoc, 8, 8));
		bufferSize += 4;

		buffer.flip();
	}
	
	public void update() {
		for (GuiComponent c : guis) {
			c.update();
		}
		
		Player p = Game.getInstance().getLevel().getBoard().getSelectedPlayer();
		int t;
		if (p != null) {
			t = p.getTexture() + 1;
		} else {
			t = 0;
		}
		
		if (t != selectedPlayerTextureLoc) {
			selectedPlayerTextureLoc = t;
			updateVBO = true;
		}
		
		if (updateVBO) {
			updateVBO = false;
			updateVBO();
		}
	}

	public void renderGUI() {
		for (GuiComponent c : guis) {
			c.renderGUI();
		}
		
		Texture.HUD.bind();
		Renderer.renderVBO(vbo, bufferSize);
	}
	
	public void setScore(int score) {
		((Label) (guis.get(2))).setText(Integer.toString(score));
	}	
	
}
