package fr.porquepix.pathzzle.board;

import java.util.HashMap;
import java.util.Map;

import fr.porquepix.pathzzle.entities.Player;
import fr.porquepix.pathzzle.render.Renderer;
import fr.porquepix.pathzzle.utils.Animation;
import fr.porquepix.pathzzle.utils.Color4f;

public class Tile {

	public static final int PLAYER_END_TILE_FLAG = 1;
	
	protected int x, y;
	protected boolean solid;
	protected int texture;
	private boolean highligth;	
	
	protected final TileData data;
	protected Color4f color;
	protected Board board;
	protected Animation anim;
	public Map<String, Object> variables;

	
	public Tile(final TileData data, int x, int y, Board b) {
		this.data = data;
		this.x = x;
		this.y = y;
		this.board = b;
		this.highligth = false;
		this.variables = new HashMap<String, Object>();
		
		this.solid = data.isSolid();
		this.color = data.getColor().copy();
		this.texture = data.getTexture();
		
		if (data.getAnimation() != null) {
			this.anim = data.getAnimation().copy();
			anim.play();
		}
		
		data.onCreate(this, b);
	}
	
	public void update() {
		if (anim != null) {
			anim.update();
			data.updateAnim(anim.getCurrentFrame(), this, board);
		}
		
		data.updateTile(this, board);
		
		if (highligth) {
			data.onHighlight(this, board);
		}
	}
	
	public void setSolid(boolean solid) {
		this.solid = solid;
	}
	
	public boolean isSolid() {
		return solid;
	}
	
	public int x() {
		return x;
	}

	public int y() {
		return y;
	}
	
	public int screenX() {
		return x * TileData.TILE_SIZE + x * TileData.TILE_MARGIN;
	}

	public int screenY() {
		return y * TileData.TILE_SIZE + y * TileData.TILE_MARGIN;
	}

	public float[] getRenderData() {				
		return Renderer.quadData(x * TileData.TILE_SIZE + x * TileData.TILE_MARGIN, y * TileData.TILE_SIZE + y * TileData.TILE_MARGIN, TileData.TILE_SIZE, TileData.TILE_SIZE, TileData.TILE_MARGIN, TileData.TILE_MARGIN, color, texture, 8.0f, 8.0f);	
	}
	
	public void setTexture(int t) {
		if (this.texture != t) {
			board.updateBuffer();
		}
		this.texture = t;
	}
	
	public void setColor(Color4f c) {
		if (this.color != c) {
			board.updateBuffer();
		}
		this.color = c;
	}
	
	public void highlight(boolean b) {
		if (highligth != b) {
			if (b) {
				data.onHighlightEnable(this, board);
			} else {
				data.onHighlightDisable(this, board);
			}
		}
		this.highligth = b;
	}

	public boolean isHighligthed() {
		return highligth;
	}
	
	public void dispose() {
		board = null;
	}
	
	public void onPlayerEnter() {
		data.onPlayerEnter(this, board);
	}

	public void onPlayerLeave() {
		data.onPlayerLeave(this, board);
	}

	public void onMouseEnter() {
		data.onMouseEnter(this, board);
	}
	
	public void onMouseLeave() {
		data.onMouseLeave(this, board);	
	}

	public void focus() {
		data.focus(this, board);
	}

	public boolean needHighlight(Player playerSelected) {
		return data.needHighlight(this, board, playerSelected);
	}

}
