package fr.porquepix.pathzzle.board.tilesdata;

import fr.porquepix.pathzzle.audio.AudioManager;
import fr.porquepix.pathzzle.board.Board;
import fr.porquepix.pathzzle.board.Tile;
import fr.porquepix.pathzzle.board.TileData;
import fr.porquepix.pathzzle.entities.Player;
import fr.porquepix.pathzzle.entities.Player.PlayerColor;
import fr.porquepix.pathzzle.entities.particules.Particle;
import fr.porquepix.pathzzle.entities.particules.ParticleManager;
import fr.porquepix.pathzzle.entities.particules.ParticlesSystem;
import fr.porquepix.pathzzle.game.GameState;
import fr.porquepix.pathzzle.game.GameState.State;
import fr.porquepix.pathzzle.gui.MenuManager;
import fr.porquepix.pathzzle.gui.menus.LevelsMenu;
import fr.porquepix.pathzzle.math.Vec2;
import fr.porquepix.pathzzle.utils.Animation;
import fr.porquepix.pathzzle.utils.Color4f;

public class LevelEndTile extends TileData {

	protected PlayerColor pColor;
	
	public LevelEndTile(int textureID, Color4f color, boolean solid, PlayerColor pColor) {
		super(textureID, color, solid, new Animation(6, 10, true));
		this.pColor = pColor;
	}	
	
	@Override
	public void onPlayerEnter(Tile t, Board b) {
		if (b.getLevelEndEvent() != null) {
			b.getLevelEndEvent().execute();
		} else {
			GameState.getInstance().setState(State.IN_MENU);
			MenuManager.getInstance().setMenu(new LevelsMenu());
		}
		AudioManager.getInstance().play(4);
	}

	@Override
	public void onPlayerLeave(Tile t, Board b) {
		
	}

	@Override
	public void onMouseEnter(Tile t, Board b) {

	}

	@Override
	public void onMouseLeave(Tile t, Board b) {
		
	}

	@Override
	public void focus(Tile t, Board b) {

	}

	@Override
	public void updateTile(Tile t, Board b) {
		int timer = (Integer) t.variables.get("timer");
		timer++;
		if (timer % 5 == 0) {
			Color4f playerColor = pColor.getColor();
			ParticleManager.getInstance().addParticles(new ParticlesSystem(t.x() * TILE_SIZE + t.x() * TILE_MARGIN + TILE_SIZE / 2 - 3, t.y() * TILE_SIZE + t.y() * TILE_MARGIN + TILE_SIZE / 2 - 3, 1, new Particle(new Color4f(playerColor.r, playerColor.g, playerColor.b, 0.4f), 6, 0.2f, 100, new Vec2(0, 0))));							
		}
		t.variables.put("timer", timer);
	}

	@Override
	public void updateAnim(int frame, Tile t, Board b) {
		t.setTexture(2 + frame);
	}

	@Override
	public void onHighlightEnable(Tile t, Board b) {
		t.setColor(new Color4f(1, 0, 0));
	}

	@Override
	public void onHighlight(Tile t, Board b) {
		
	}
	
	@Override
	public void onHighlightDisable(Tile t, Board b) {
		t.setColor(new Color4f(0, 0, 1));
	}
	
	@Override
	public boolean needHighlight(Tile tile, Board board, Player playerSelected) {
		return pColor.getColor().equals(playerSelected.getColor());
	}
	
}
