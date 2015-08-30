package fr.porquepix.pathzzle.render;

import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;

import fr.porquepix.pathzzle.utils.Color4f;

public class Sky {

	private FloatBuffer buffer;
	private int bufferSize;
	private int vbo;
	
	private float time = 12000;
	
	private Texture day, night;
	private Color4f  glassColor;
	
	public Sky() {
		this.day = Texture.SKY_DAY;
		this.night = Texture.SKY_NIGHT;
		glassColor = new Color4f(0, 0, 0, 0.2f);
		
		createSky();
		
		Shader.SKY.bind();
		connectTextureUnits();
	}

	private void createSky() {
		buffer = BufferUtils.createFloatBuffer(4 * (2 + 4 + 2));
		createVBO();
	}
	
	private void createVBO() {
		fillBuffer();
		
		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		buffer.clear();
	}

	private void fillBuffer() {
		bufferSize = 0;
		buffer.clear();

		buffer.put(Renderer.quadData(0, 0, Display.getWidth(), Display.getHeight(), 0, 0, new Color4f(1, 1, 1, 1), 1, 1.0f, 1.0f));
		bufferSize += 4;
		
		buffer.flip();
	}
	
	public void update() {
		time += 1;
		time %= 24000;
	}
	
	public void render() {
		Shader.SKY.bind();
		bindTexture();
		Renderer.renderVBO(vbo, bufferSize);
		
//		Shader.HUD.bind();
//		Texture.CLOUD.bind();
//		glBegin(GL_QUADS);
//		Renderer.renderQuad(200, 450, 149, 59, new Color4f(1, 1, 1), 0, 1.0f, 1.0f);
//		glEnd();
	}

	private void bindTexture() {
		Texture texture1;
		Texture texture2;
		float blendFactor;
		
		if (time >= 0 && time < 5000) {
			texture1 = night;
			texture2 = night;
			blendFactor = (time - 0) / (5000 - 0);
		} else if (time >= 5000 && time < 8000) {
			texture1 = night;
			texture2 = day;
			blendFactor = (time - 5000) / (8000 - 5000);
			
			glassColor.r = 1 - blendFactor;
			glassColor.g = 1 - blendFactor;
			glassColor.b = 1 - blendFactor;
		} else if (time >= 8000 && time < 21000) {
			texture1 = day;
			texture2 = day;
			blendFactor = (time - 8000) / (21000 - 8000);
		} else {
			texture1 = day;
			texture2 = night;
			blendFactor = (time - 21000) / (24000 - 21000);
			
			glassColor.r = blendFactor;
			glassColor.g = blendFactor;
			glassColor.b = blendFactor;
		}
		
		glActiveTexture(GL_TEXTURE0);
		texture1.bind();
		glActiveTexture(GL_TEXTURE1);
		texture2.bind();
		glActiveTexture(GL_TEXTURE0);
		
		loadBlendFactor(blendFactor);
	}
	
	private void loadBlendFactor(float v) {
		Shader.SKY.setUniform("blendFactor", v);
	}
	
	private void connectTextureUnits() {
		Shader.SKY.setUniform("texture1", 0);
		Shader.SKY.setUniform("texture2", 1);
	}

	public Color4f getGlassColor() {
		return glassColor;
	}
	
	public void setGlassColor(float color) {
		glassColor.r = color;
		glassColor.g = color;
		glassColor.b = color;
	}
	
	public void setTime(float time) {
		this.time = time;
	}
	
}
