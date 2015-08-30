package fr.porquepix.pathzzle.gui.components;

import static org.lwjgl.opengl.GL15.*;

import org.lwjgl.BufferUtils;

import fr.porquepix.pathzzle.gui.GuiComponent;
import fr.porquepix.pathzzle.render.Renderer;
import fr.porquepix.pathzzle.render.Shader;
import fr.porquepix.pathzzle.render.Texture;
import fr.porquepix.pathzzle.utils.Color4f;

public class Image extends GuiComponent {

	private int x, y;
	private int width, height;
	private Texture texture;

	public Image(int x, int y, int width, int height, Texture t) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.texture = t;
		
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

	private void fillBuffer() {
		buffer.clear();
		bufferSize = 0;

		buffer.put(Renderer.quadData(x, y, width, height, 0, 0, new Color4f(1, 1, 1), 1, 1.0f, 1.0f));
		bufferSize += 4;

		buffer.flip();
	}
	
	@Override
	public void update() {
		
	}

	@Override
	public void renderGUI() {
		texture.bind();
		Shader.HUD.bind();
		Renderer.renderVBO(vbo, bufferSize);
	}
	
}
