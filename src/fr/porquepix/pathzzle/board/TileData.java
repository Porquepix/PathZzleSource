package fr.porquepix.pathzzle.board;

import fr.porquepix.pathzzle.board.tilesdata.BaseTile;
import fr.porquepix.pathzzle.board.tilesdata.BreakableTile;
import fr.porquepix.pathzzle.board.tilesdata.LevelEndTile;
import fr.porquepix.pathzzle.entities.Player;
import fr.porquepix.pathzzle.entities.particules.Particle;
import fr.porquepix.pathzzle.entities.particules.ParticleManager;
import fr.porquepix.pathzzle.entities.particules.ParticlesSystem;
import fr.porquepix.pathzzle.math.Vec2;
import fr.porquepix.pathzzle.utils.Animation;
import fr.porquepix.pathzzle.utils.Color4f;

public abstract class TileData implements TileEvent {
	
	public static final TileData BASE_TILE = new BaseTile(0, new Color4f(1, 1, 1), false);
	public static final TileData SOLID_TILE = new BaseTile(1, new Color4f(1, 1, 1), true);
	public static final TileData LEVEL_END_TILE_RED = new LevelEndTile(2, new Color4f(0, 0, 1), false, Player.PlayerColor.RED);
	public static final TileData LEVEL_END_TILE_GREEN = new LevelEndTile(2, new Color4f(0, 0, 1), false, Player.PlayerColor.GREEN);
	public static final TileData LEVEL_END_TILE_BLUE = new LevelEndTile(2, new Color4f(0, 0, 1), false, Player.PlayerColor.BLUE);
	public static final TileData LEVEL_END_TILE_CYAN = new LevelEndTile(2, new Color4f(0, 0, 1), false, Player.PlayerColor.CYAN);
	public static final TileData LEVEL_END_TILE_YELLOW = new LevelEndTile(2, new Color4f(0, 0, 1), false, Player.PlayerColor.YELLOW);
	public static final TileData LEVEL_END_TILE_PINK = new LevelEndTile(2, new Color4f(0, 0, 1), false, Player.PlayerColor.PINK);
	public static final TileData BREAKABLE_TILE = new BreakableTile(8, new Color4f(1, 1, 1), false);
	
	public static final int TILE_SIZE = 32;
	public static final int TILE_MARGIN = 5;

	protected boolean solid;
	protected int texture;

	protected Color4f color;
	private Animation anim;
	
	public TileData(int textureID, Color4f color, boolean solid) {
		this.texture = textureID;
		this.color = color;
		this.solid = solid;
	}
	
	public TileData(int textureID, Color4f color, boolean solid, Animation anim) {
		this.texture = textureID;
		this.color = color;
		this.solid = solid;
		this.anim = anim;
	}
	
	public boolean isSolid() {
		return solid;
	}

	public Color4f getColor() {
		return color;
	}

	public int getTexture() {
		return texture;
	}
	
	public Animation getAnimation() {
		return anim;
	}
	
	@Override
	public void onCreate(Tile t, Board b) {
		t.variables.put("higtlightTimer", 0);
		t.variables.put("timer", 0);
	}
	
	@Override
	public void onHighlight(Tile t, Board b) {
		int timer = (Integer) t.variables.get("higtlightTimer");
		timer++;
		if (timer % 5 == 0) {
			ParticleManager.getInstance().addParticles(new ParticlesSystem(t.x() * TILE_SIZE + t.x() * TILE_MARGIN + TILE_SIZE / 2 - 3, t.y() * TILE_SIZE + t.y() * TILE_MARGIN + TILE_SIZE / 2 - 3, 1, new Particle(new Color4f(1, 1, 0, 0.4f), 6, 0.2f, 60, new Vec2(0, 0))));	
		}
		t.variables.put("higtlightTimer", timer);
		
		Animation a = (Animation) t.variables.get("higtlightAnim");
		
		a.update();
		t.setTexture(2 + a.getCurrentFrame());
	}

	@Override
	public void onHighlightEnable(Tile t, Board b) {
		t.variables.put("higtlightAnim",  new Animation(6, 10, true).play());
	}

	@Override
	public void onHighlightDisable(Tile t, Board b) {
		t.setTexture(texture);
	}

	@Override
	public boolean needHighlight(Tile tile, Board board, Player playerSelected) {
		return false;
	}

}
