package fr.porquepix.pathzzle.entities;

import static org.lwjgl.opengl.GL15.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import fr.porquepix.pathzzle.board.TileData;
import fr.porquepix.pathzzle.game.Game;
import fr.porquepix.pathzzle.render.Renderer;
import fr.porquepix.pathzzle.utils.Color4f;

public class Player {

	public static final int SIZE = TileData.TILE_SIZE;
	public static final int MARGIN = TileData.TILE_MARGIN;
	
	private FloatBuffer buffer;
	private int bufferSize;
	private int vbo;
	
	private int x, y;
	private PlayerColor color;
	
	public Player(int x, int y, PlayerColor color) {
		this.x = x;
		this.y = y;
		this.color = color;
		
		createPlayer();
	}
	
	public void createPlayer() {
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

		buffer.put(Renderer.quadData(x * SIZE + x * MARGIN, y * SIZE + y * MARGIN, SIZE, SIZE, MARGIN, MARGIN, new Color4f(1, 1, 1, 1), color.getTexture(), 4.0f, 4.0f));
		bufferSize += 4;
		
		buffer.flip();
	}
	
	private void updateVBO() {
		fillBuffer();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferSubData(GL_ARRAY_BUFFER, 0, buffer);
		buffer.clear();
	}
	
	public void update() {
		
	}
	
	public void render() {
		Renderer.renderVBO(vbo, bufferSize);
	}
	
	public int x() {
		return x;
	}
	
	public int y() {
		return y;
	}

	public void move(int x2, int y2) {
		Game.getInstance().addPoint(Math.abs(x - x2) + Math.abs(y - y2));
		
		this.x = x2;
		this.y = y2;
		
		updateVBO();
	}
	
	public Color4f getColor() {
		return color.getColor();
	}
	
	public int getTexture() {
		return color.getTexture();
	}
	
	public void dispose() {
		glDeleteBuffers(vbo);
		buffer.clear();
	}
	
	public enum PlayerColor {
		
		GREEN(0, new Color4f(0, 1, 0)), 
		RED(1, new Color4f(1, 0, 0)), 
		BLUE(2, new Color4f(0, 0, 1)), 
		CYAN(4, new Color4f(0, 1, 1)), 
		YELLOW(5, new Color4f(1, 1, 0)), 
		PINK(6, new Color4f(1, 0, 1));
		
		private int texture;
		private Color4f color;
		
		private PlayerColor(int texture, Color4f color) {
			this.texture = texture;
			this.color = color;
		}
		
		public int getTexture() {
			return texture;
		}
		
		public Color4f getColor() {
			return color;
		}
		
	}

	public Player copy() {
		return new Player(x, y, color);
	}	
}
