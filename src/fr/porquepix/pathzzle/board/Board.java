package fr.porquepix.pathzzle.board;

import static org.lwjgl.opengl.GL15.*;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;

import fr.porquepix.pathzzle.audio.AudioManager;
import fr.porquepix.pathzzle.board.tilesdata.BaseTile;
import fr.porquepix.pathzzle.entities.Player;
import fr.porquepix.pathzzle.entities.particules.Particle;
import fr.porquepix.pathzzle.entities.particules.ParticleManager;
import fr.porquepix.pathzzle.entities.particules.ParticlesSystem;
import fr.porquepix.pathzzle.input.InputManager;
import fr.porquepix.pathzzle.math.Vec2;
import fr.porquepix.pathzzle.render.Renderer;
import fr.porquepix.pathzzle.render.Shader;
import fr.porquepix.pathzzle.render.Texture;
import fr.porquepix.pathzzle.utils.Color4f;

public class Board {

	private int width, height;
	private Tile[][] tiles;
	private List<Player> players;
	private LevelEndEvent endEvent;
	
	private FloatBuffer buffer;
	private int bufferSize;
	private int vbo;
	private boolean updateBuffer;
	
	private boolean isCreated;
	
	private Tile tileHover;
	private Tile tileSelected;
	private Player playerSelected;
	private List<Tile> playerEndTiles;
	private boolean hightlighted;
	
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		
		tiles = new Tile[width][height];
		players = new ArrayList<Player>();
		playerEndTiles = new ArrayList<Tile>();
	}
	
	public Board(Board board) {
		width = board.width;
		height = board.height;
		
		tiles = new Tile[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				setTile(new Tile(board.tiles[i][j].data, i, j, this));
			}
		}
		
		players = new ArrayList<Player>();
		for (Player p : board.players) {
			players.add(p.copy());
		}
		
		playerEndTiles = new ArrayList<Tile>();
	}

	public void createBoard() {
		buffer = BufferUtils.createFloatBuffer(width * height * 4 * (2 + 4 + 2));
		createVBO();
		
		isCreated = true;
	}
	
	private void createVBO() {
		fillBuffer();
		
		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_DYNAMIC_DRAW);
		buffer.clear();
	}

	private void fillBuffer() {
		bufferSize = 0;
		buffer.clear();

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				buffer.put(tiles[i][j].getRenderData());
				bufferSize += 4;
			}
		}
		
		buffer.flip();
	}
	
	private void updateVBO() {
		fillBuffer();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferSubData(GL_ARRAY_BUFFER, 0, buffer);
		buffer.clear();
	}

	public void update() {
		updateBuffer = false;
		
		float mouseX = InputManager.getMousePosition().x();
		float mouseY = InputManager.getMousePosition().y();
		
		// Update tiles
		boolean mouseInter = false;
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				tiles[i][j].update();
				
				if (!mouseInter) {
					Vec2 tileScreenCoord = getTileScreenCoord(i, j);					
					if (mouseX > tileScreenCoord.x() && mouseY > tileScreenCoord.y() && mouseX < tileScreenCoord.x() + TileData.TILE_SIZE && mouseY < tileScreenCoord.y() + TileData.TILE_SIZE) {
						mouseInter = true;
						if (tileHover == null || tileHover != tiles[i][j]) {
							tiles[i][j].onMouseEnter();
						}
						tileHover = tiles[i][j];
					}
				}
			}
		}
		if (!mouseInter) {
			if (tileHover != null) {
				tileHover.onMouseLeave();				
			}
			tileHover = null;
		}
		
		
		if (InputManager.getMouseDown(0)) {
			if (playerSelected != null && tileHover != null && tileHover.isHighligthed()) {
				tiles[playerSelected.x()][playerSelected.y()].onPlayerLeave();
				playerSelected.move(tileHover.x(), tileHover.y());
				tiles[playerSelected.x()][playerSelected.y()].onPlayerEnter();
				
				tileSelected = null;
				playerSelected = null;
				
				AudioManager.getInstance().play(3);
			} else if (tileHover != null) {
				tileSelected = tileHover;
				tileSelected.focus();
				playerSelected = null;
				AudioManager.getInstance().play(3);			
			} else {
				tileSelected = null;
				playerSelected = null;
			}
			
			hightlighted = false;
			clearPlayerEndTiles();
		}
		
		for (Player p : players) {
			p.update();
			
			if (tileSelected != null && p.x() == tileSelected.x() && p.y() == tileSelected.y()) {
				playerSelected = p;
				if (!hightlighted) {
					hightlightTile(p.x(), p.y());					
				}
			}
		}
		
		if (updateBuffer) {
			updateVBO();
		}
	}
	
	private void clearPlayerEndTiles() {
		if (playerEndTiles.size() > 0) {
			updateBuffer();
		}
		for (Tile tile : playerEndTiles) {
			tile.highlight(false);
		}
		playerEndTiles.clear();
	}

	private void hightlightTile(int x, int y) {
		hightlightTile(x, y, 1, 0);
		hightlightTile(x, y, -1, 0);
		hightlightTile(x, y, 0, 1);
		hightlightTile(x, y, 0, -1);
		
		hightlighted = true;
	}

	private void hightlightTile(int x, int y, int stepX, int stepY) {
		if (tiles[x][y].needHighlight(playerSelected)) {
			tiles[x][y].highlight(true);
			playerEndTiles.add(tiles[x][y]);
			return;
		}
		
		if (stepX < 0 && x == 0) {
			if ((playerSelected.x() == x && playerSelected.y() == y)) {
				return;
			}
			tiles[x][y].highlight(true);
			playerEndTiles.add(tiles[x][y]);
			return;
		}
		
		if (stepX > 0 && x == tiles.length - 1) {
			if ((playerSelected.x() == x && playerSelected.y() == y)) {
				return;
			}
			tiles[x][y].highlight(true);
			playerEndTiles.add(tiles[x][y]);
			return;
		}
		
		if (stepY < 0 && y == 0) {
			if ((playerSelected.x() == x && playerSelected.y() == y)) {
				return;
			}
			tiles[x][y].highlight(true);
			playerEndTiles.add(tiles[x][y]);
			return;
		}
		
		if (stepY > 0 && y == tiles[0].length - 1) {
			if ((playerSelected.x() == x && playerSelected.y() == y)) {
				return;
			}
			tiles[x][y].highlight(true);
			playerEndTiles.add(tiles[x][y]);
			return;
		}
		
		if (tiles[x + stepX][y + stepY].isSolid()) {
			if ((playerSelected.x() == x && playerSelected.y() == y)) {
				return;
			}
			tiles[x][y].highlight(true);
			playerEndTiles.add(tiles[x][y]);
			return;
		}
		
		if (hasPlayer(x + stepX, y + stepY)) {
			if ((playerSelected.x() == x && playerSelected.y() == y)) {
				return;
			}
			tiles[x][y].highlight(true);
			playerEndTiles.add(tiles[x][y]);
			return;
		}
		
		hightlightTile(x + stepX, y + stepY, stepX, stepY);
	}

	public void render() {	
		Shader.MAIN.bind();
		Texture.TILES.bind();
		Renderer.renderVBO(vbo, bufferSize);
		
		Texture.PLAYERS.bind();
		for (Player p : players) {
			p.render();
		}
	}
	
	public void setTile(Tile t) {
		int i = t.x();
		int j = t.y();
		
		tiles[i][j] = t;
		
		ParticleManager.getInstance().addParticles(new ParticlesSystem(i * TileData.TILE_SIZE + i * TileData.TILE_MARGIN + TileData.TILE_SIZE / 2 - 3, j * TileData.TILE_SIZE + j * TileData.TILE_MARGIN + TileData.TILE_SIZE / 2 - 3, 15, new Particle(new Color4f(1, 1, 1, 1f), 6, 0.3f, 80, new Vec2(0, 0), true)));
	}
	
	public void addPlayer(Player p) {
		players.add(p);
	}
	
	public boolean hasPlayer(int x, int y) {
		for (Player p : players) {
			if (x == p.x() && y == p.y()) {
				return true;
			}
		}
		return false;
	}

	public int getWidth() {
		return tiles.length * BaseTile.TILE_SIZE + (tiles.length - 1) * BaseTile.TILE_MARGIN;
	}
	
	public int getHeight() {
		return tiles[0].length * BaseTile.TILE_SIZE+ (tiles[0].length - 1) * BaseTile.TILE_MARGIN;
	}
	
	public Vec2 getTileScreenCoord(int x, int y) {
		return new Vec2((Display.getWidth() - getWidth()) / 2 + tiles[x][y].screenX(), (Display.getHeight() - getHeight()) / 2 + tiles[x][y].screenY());
	}
	
	public void updateBuffer() {
		if (isCreated) {
			updateBuffer = true;
		}
	}

	public Player getSelectedPlayer() {
		return playerSelected;
	}

	public void dispose() {
		glDeleteBuffers(vbo);
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				tiles[i][j].dispose();
			}
		}
		tiles = null;
		for (Player p : players) {
			p.dispose();
		}
		players.clear();
	}
	
	public void setEndEvent(LevelEndEvent endEvent) {
		this.endEvent = endEvent;
	}

	public LevelEndEvent getLevelEndEvent() {
		return endEvent;
	}
	
}
