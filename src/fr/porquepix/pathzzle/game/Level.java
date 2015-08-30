package fr.porquepix.pathzzle.game;

import static org.lwjgl.opengl.GL15.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.FloatBuffer;
import java.util.Random;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;

import fr.porquepix.pathzzle.Main;
import fr.porquepix.pathzzle.board.Board;
import fr.porquepix.pathzzle.board.LevelEndEvent;
import fr.porquepix.pathzzle.board.Tile;
import fr.porquepix.pathzzle.board.TileData;
import fr.porquepix.pathzzle.entities.Player;
import fr.porquepix.pathzzle.entities.Player.PlayerColor;
import fr.porquepix.pathzzle.math.Vec3;
import fr.porquepix.pathzzle.render.Renderer;
import fr.porquepix.pathzzle.render.Shader;
import fr.porquepix.pathzzle.utils.Config;

public class Level {

	private int width, height;
	
	private Board board;
	
	private FloatBuffer buffer;
	private int bufferSize;
	private int vbo;
	private float red = 0;
	
	// +FIX ME
	public Level() {
		Random r = new Random();
		
		width = r.nextInt(20) + 5;
		height = r.nextInt(20) + 5;
		
		board = new Board(width, height);
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				
				Tile t;
				if (r.nextFloat() < 0.005 * width) {
					t = new Tile(TileData.SOLID_TILE, i, j, board);
				} else {
					t = new Tile(TileData.BASE_TILE, i, j, board);	
				}
				
				board.setTile(t);
				
			}
		}
		
		PlayerColor p = null;
		for (int i = 0; i < r.nextInt(5) + 1 ; i++) {
			p = PlayerColor.values()[r.nextInt(5) + 1];
			board.addPlayer(new Player(r.nextInt(width), r.nextInt(height), p));
		}
		switch (p) {
			case BLUE:
				board.setTile(new Tile(TileData.LEVEL_END_TILE_BLUE, r.nextInt(width), r.nextInt(height), board));
				break;
			case RED:
				board.setTile(new Tile(TileData.LEVEL_END_TILE_RED, r.nextInt(width), r.nextInt(height), board));
				break;
			case GREEN:
				board.setTile(new Tile(TileData.LEVEL_END_TILE_GREEN, r.nextInt(width), r.nextInt(height), board));
				break;
			case CYAN:
				board.setTile(new Tile(TileData.LEVEL_END_TILE_CYAN, r.nextInt(width), r.nextInt(height), board));
				break;
			case YELLOW:
				board.setTile(new Tile(TileData.LEVEL_END_TILE_YELLOW, r.nextInt(width), r.nextInt(height), board));
				break;
			case PINK:
				board.setTile(new Tile(TileData.LEVEL_END_TILE_PINK, r.nextInt(width), r.nextInt(height), board));
				break;
		}
		
		board.createBoard();
		
		createVBO();
	}
	
	public Level(final int level) {
		loadLevel(Integer.toString(level));
		
		if (level > (int) Config.getInstance().get(Config.MAX_BASE_LEVEL))
			Config.getInstance().put(Config.MAX_BASE_LEVEL, level);
		
		board.setEndEvent(new LevelEndEvent() {
			@Override
			public void execute() {
				Game.getInstance().setLevel(new Level(level + 1));
			}
		});
		
		createVBO();
	}

	public Level(Level level) {
		width = level.width;
		height = level.height;
		board = new Board(level.board);
		board.createBoard();
		board.setEndEvent(level.board.getLevelEndEvent());
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
		
		buffer.put(Renderer.quadData(-25, -25, board.getWidth() + 50, board.getHeight() + 50, 0, 0, Main.getInstance().getSky().getGlassColor(), 0, 1.0f, 1.0f));
		bufferSize += 4;
		
		buffer.flip();	
	}

	private void loadLevel(String path) {
		BufferedImage map = getBufferedImage(path + ".png");
		
		this.width = map.getWidth();
		this.height = map.getHeight();
		
		int[] pixels = new int[width * height];
		map.getRGB(0, 0, width, height, pixels, 0, width);
		
		board = new Board(width, height);
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int k = i + j * width;
				
				Tile t;
				if (pixels[k] == 0xFF000000) {
					t = new Tile(TileData.SOLID_TILE, i, j, board);
				} else if (pixels[k] == 0xFF7F807F) {
					t = new Tile(TileData.LEVEL_END_TILE_GREEN, i, j, board);
				} else if (pixels[k] == 0xFF807F7F) {
					t = new Tile(TileData.LEVEL_END_TILE_RED, i, j, board);
				} else if (pixels[k] == 0xFF7F7F80) {
					t = new Tile(TileData.LEVEL_END_TILE_BLUE, i, j, board);
				} else if (pixels[k] == 0xFF7F8080) {
					t = new Tile(TileData.LEVEL_END_TILE_CYAN, i, j, board);
				} else if (pixels[k] == 0xFF80807F) {
					t = new Tile(TileData.LEVEL_END_TILE_YELLOW, i, j, board);
				} else if (pixels[k] == 0xFF807F80) {
					t = new Tile(TileData.LEVEL_END_TILE_PINK, i, j, board);
				} else if (pixels[k] == 0xFF010000) {
					t = new Tile(TileData.BREAKABLE_TILE, i, j, board);
				} else {
					t = new Tile(TileData.BASE_TILE, i, j, board);	
				}
				board.setTile(t);
				
				Player p;
				switch (pixels[k]) {
					case 0xFF00FF00:
						p = new Player(i, j, Player.PlayerColor.GREEN);
						board.addPlayer(p);
						break;
					
					case 0xFFFF0000:
						p = new Player(i, j, Player.PlayerColor.RED);
						board.addPlayer(p);
						break;
						
					case 0xFF0000FF:
						p = new Player(i, j, Player.PlayerColor.BLUE);
						board.addPlayer(p);
						break;
				
					case 0xFF00FFFF:
						 p = new Player(i, j, Player.PlayerColor.CYAN);
						board.addPlayer(p);
						break;
						
					case 0xFFFFFF00:
						 p = new Player(i, j, Player.PlayerColor.YELLOW);
						board.addPlayer(p);
						break;
						
					case 0xFFFF00FF:
						 p = new Player(i, j, Player.PlayerColor.PINK);
						board.addPlayer(p);
						break;
				}
			}
		}
		board.createBoard();
	}
	
	private BufferedImage getBufferedImage(String path) {
		BufferedImage map = null;
		try {
			URL src = Level.class.getResource("/levels/" + path);
			if (src == null) return null;
			map = ImageIO.read(src);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public void update() {
		if (Main.getInstance().getSky().getGlassColor().r != red) {
			red = Main.getInstance().getSky().getGlassColor().r;
			updateVBO();
		}
		
		board.update();
	}
	
	public void render() {
		Matrix4f.translate(new Vec3((Display.getWidth() - board.getWidth()) / 2, (Display.getHeight() - board.getHeight()) / 2, 0).toLWJGLVec(), Main.getInstance().modelMatrix, Main.getInstance().modelMatrix);
		
		Shader.PARTICLE.bind();
		Renderer.renderVBO(vbo, bufferSize);
		
		board.render();
	}
	
	public Board getBoard() {
		return board;
	}

	public void dispose() {
		board.dispose();
	}
	
	
}
