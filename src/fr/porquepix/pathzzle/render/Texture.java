package fr.porquepix.pathzzle.render;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class Texture {

	public static final Texture TILES = new Texture("/tiles/tiles.png", GL_NEAREST);
	public static final Texture PLAYERS = new Texture("/players/players.png", GL_NEAREST);
	public static final Texture SKY_DAY = new Texture("/others/sky.png", GL_NEAREST);
	public static final Texture SKY_NIGHT = new Texture("/others/sky_night.png", GL_NEAREST);
	public static final Texture HUD = new Texture("/hud/hud.png", GL_NEAREST);
	public static final Texture BUTTONS = new Texture("/gui/buttons.png", GL_NEAREST);
	public static final Texture LOGO = new Texture("/gui/PathZzle.png", GL_NEAREST);
	public static final Texture CLOUD = new Texture("/others/cloud.png", GL_NEAREST);
	
	private int id;
	private int width;
	private int height;
	private IntBuffer buffer;
	
	public Texture(String path, int filter) {
		int [] pixels = null;
		
		try {
			BufferedImage image = ImageIO.read(Texture.class.getResource("/textures" + path));
			this.width = image.getWidth();
			this.height = image.getHeight();
			pixels = new int[this.width * this.height];
			image.getRGB(0, 0, this.width, this.height, pixels, 0, width);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int[] data = new int[this.width * this.height];
		for (int i = 0; i < data.length; i++) {
			int a = (pixels[i] & 0xFF000000) >> 24;
			int r = (pixels[i] & 0xFF0000) >> 16;
			int g = (pixels[i] & 0xFF00) >> 8;
			int b = (pixels[i] & 0xFF);
			
			data[i] = a << 24 | b << 16 | g << 8 | r;
		}
		
		int id = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, id);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filter);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filter);
		
		buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		
		this.id = id;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public void bind() {
		glBindTexture(GL_TEXTURE_2D, this.id);
	}
	
	public int getID() {
		return id;
	}
	
	public static void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	public IntBuffer getBuffer() {
		return buffer;
	}
	
}
