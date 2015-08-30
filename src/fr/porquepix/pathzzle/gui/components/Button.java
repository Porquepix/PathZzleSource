package fr.porquepix.pathzzle.gui.components;

import static org.lwjgl.opengl.GL15.*;

import org.lwjgl.BufferUtils;

import fr.porquepix.pathzzle.audio.AudioManager;
import fr.porquepix.pathzzle.gui.GuiComponent;
import fr.porquepix.pathzzle.gui.IEventGui;
import fr.porquepix.pathzzle.input.InputManager;
import fr.porquepix.pathzzle.render.Renderer;
import fr.porquepix.pathzzle.render.Shader;
import fr.porquepix.pathzzle.render.Texture;
import fr.porquepix.pathzzle.utils.Color4f;

public class Button extends GuiComponent {

	private int x, y;
	private int width, height;
	private boolean hover;

	private Label text;
	private IEventGui event;
	private Color4f c;	

	public Button(String text, int x, int y, int w, int h) {
		this(text, x, y, w, h, 24);
	}

	public Button(String text, int x, int y, int w, int h, int fontSize) {
		this.text = new Label(text, x - (text.length() * (int) (fontSize * (7.0f / 8.0f)) / 2), y - h / 4, fontSize, false);

		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		
		c = new Color4f(0.5f, 0.5f, 0.5f, 1);
		
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

		buffer.put(Renderer.quadData(x - width / 2, y - height / 2, width, height, 0, 0, c, 0, 1.0f, 2.0f));
		bufferSize += 4;

		buffer.flip();
	}

	public Button setEventClick(IEventGui e) {
		this.event = e;
		return this;
	}

	@Override
	public void update() {
		float mx = InputManager.getMousePosition().x();
		float my = InputManager.getMousePosition().y();

		updateVBO = hover;
		
		hover = false;
		c = new Color4f(0.5f, 0.5f, 0.5f, 1);
		
		if (mx > x - width / 2 && my > y - height / 2 && mx < x + width / 2 && my < y + height / 2) {
			hover = true;
			c = new Color4f(1, 0, 0, 1);
			if (InputManager.getMouseDown(0)) {
				if (event != null) {
					event.onClick(this);
					AudioManager.getInstance().play(1);
				}
			}
		}
		
		if (updateVBO != hover) {
			updateVBO();
		}
	}

	@Override
	public void renderGUI() {
		Texture.BUTTONS.bind();
		Shader.HUD.bind();
		Renderer.renderVBO(vbo, bufferSize);
		text.renderGUI();
	}

}
