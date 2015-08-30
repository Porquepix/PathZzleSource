package fr.porquepix.pathzzle.gui.components;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

import org.lwjgl.BufferUtils;

import fr.porquepix.pathzzle.gui.GuiComponent;
import fr.porquepix.pathzzle.render.Renderer;
import fr.porquepix.pathzzle.render.Shader;
import fr.porquepix.pathzzle.render.Texture;
import fr.porquepix.pathzzle.utils.Color4f;

public class Label extends GuiComponent {
	
	private static final Texture FONT = new Texture("/gui/font.png", GL_NEAREST);
	private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.;,:=+-*/\\()!?@ ";
	
	private int x, y;
	private int size;
	
	private String text;
	private Color4f color;

	public Label(String text, int x, int y, int size, boolean center) {
		this(text, x, y, size, new Color4f(1, 1, 1), center);
	}
	
	public Label(String text, int x, int y, int size, Color4f color, boolean center) {
		this.text = text.toUpperCase();
		
		this.x = x;
		if (center) this.x -= (text.length() * (int)(size * (7.0f / 8.0f))) /2;
		
		this.y = y;
		this.size = size;
		this.color = color;
		
		createVBO();
	}

	private void createVBO() {
		buffer = BufferUtils.createFloatBuffer(text.length() * 4 * (2 + 4 + 2));

		fillBuffer();

		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_DYNAMIC_DRAW);
		buffer.clear();
	}

	private void fillBuffer() {
		buffer.clear();
		bufferSize = 0;

		for (int i = 0; i < text.length(); i++) {
			buffer.put(getCharData(text.charAt(i), x + i * (int)(size * (7.0f / 8.0f)), y, size, color));
			bufferSize += 4;
		}

		buffer.flip();
	}

	private float[] getCharData(char character, int x, int y, int size, Color4f color) {
		int i = CHARS.indexOf(character);	
		return Renderer.quadData(x, y, size, size, 0, 0, color, i, 26.0f, 6.0f);
	}
	
	@Override
	public void update() {	
		if (updateVBO) {
			updateVBO = false;
			createVBO();
		}
	}

	@Override
	public void renderGUI() {
		FONT.bind();
		Shader.HUD.bind();
		Renderer.renderVBO(vbo, bufferSize);
	}
	
	public void setText(String text) {
		if (text.toUpperCase().equals(this.text)) return;
		
		buffer = BufferUtils.createFloatBuffer(text.length() * 4 * (2 + 4 + 2));
		
		this.text = text.toUpperCase();
		updateVBO = true;
	}

}
