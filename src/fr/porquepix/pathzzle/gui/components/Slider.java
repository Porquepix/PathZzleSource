package fr.porquepix.pathzzle.gui.components;

import static org.lwjgl.opengl.GL15.*;

import org.lwjgl.BufferUtils;

import fr.porquepix.pathzzle.gui.GuiComponent;
import fr.porquepix.pathzzle.input.InputManager;
import fr.porquepix.pathzzle.render.Renderer;
import fr.porquepix.pathzzle.render.Shader;
import fr.porquepix.pathzzle.render.Texture;
import fr.porquepix.pathzzle.utils.Color4f;

public class Slider extends GuiComponent {

	private float value;
	private float x, y;
	private float width;
	private float mx;
	private boolean mouseGrabbed = false;
	private float xMouseGrabbed = 0.0F;

	private Color4f pointerColor;

	public Slider(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.width = w;

		this.value = 0.0f;
		
		this.pointerColor = new Color4f(0.5F, 0.5F, 0.5F, 1.0F);
		
		createVBO();
	}

	private void createVBO() {
		buffer = BufferUtils.createFloatBuffer(2 * 4 * (2 + 4 + 2));

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

		buffer.put(Renderer.quadData(x, y, width, 10.0f, 0, 0, new Color4f(0.1f, 0.1f, 0.1f, 1f), 0, 1.0f, 1.0f));
		bufferSize += 4;
		buffer.put(Renderer.quadData(mx, y - 1.0f, 12.0f, 12.0f, 0, 0, new Color4f(0.5f, 0.5f, 0.5f, 1f), 0, 1.0f, 1.0f));
		bufferSize += 4;

		buffer.flip();
	}

	@Override
	public void update() {
		if ((!InputManager.getMouse(0)) && (this.mouseGrabbed)) {
			mouseGrabbed = false;
		}

		float mouseX = InputManager.getMousePosition().x();
		float mouseY = InputManager.getMousePosition().y();

		if ((mouseX >= mx) && (mouseY >= y - 1.0F) && (mouseX <= mx + 12.0F) && (mouseY <= y - 1.0F + 12.0F)) {
			this.pointerColor.r = 1.0F;
			if (InputManager.getMouse(0)) {
				xMouseGrabbed = (mx - mouseX);
				mouseGrabbed = true;
			}
		}

		if (this.mouseGrabbed) {
			pointerColor.r = 1.0F;
			mx = (mouseX + xMouseGrabbed);

			if (mx < x)
				mx = x;
			if (mx > x + width - 12.0F)
				mx = (x + width - 12.0F);

			this.value = ((mx - x) / (width - 12.0F));
		} else {
			this.pointerColor.r = 0.5F;
		}


		updateVBO();
	}

	@Override
	public void renderGUI() {
		Texture.unbind();
		Shader.PARTICLE.bind();
		Renderer.renderVBO(vbo, bufferSize);
	}

	public int getPercentage() {
		return (int) (value * 100);
	}
	 

	public void setValue(float v) {
		this.value = v;
		this.mx = (this.x + (this.width - 12.0F) * this.value);
	}

	public float getValue() {
		return value;
	}

}
