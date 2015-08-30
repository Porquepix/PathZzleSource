package fr.porquepix.pathzzle.board.tilesdata;

import fr.porquepix.pathzzle.audio.AudioManager;
import fr.porquepix.pathzzle.board.Board;
import fr.porquepix.pathzzle.board.Tile;
import fr.porquepix.pathzzle.board.TileData;
import fr.porquepix.pathzzle.entities.particules.Particle;
import fr.porquepix.pathzzle.entities.particules.ParticleManager;
import fr.porquepix.pathzzle.entities.particules.ParticlesSystem;
import fr.porquepix.pathzzle.math.Vec2;
import fr.porquepix.pathzzle.utils.Color4f;

public class BreakableTile extends TileData {

	public BreakableTile(int textureID, Color4f color, boolean solid) {
		super(textureID, color, solid);
	}

	@Override
	public void onPlayerEnter(Tile t, Board b) {
		
	}

	@Override
	public void onPlayerLeave(Tile t, Board b) {
		t.setTexture(9);
		t.setSolid(true);
		ParticleManager.getInstance().addParticles(new ParticlesSystem(t.x() * TILE_SIZE + t.x() * TILE_MARGIN + TILE_SIZE / 2 - 3, t.y() * TILE_SIZE + t.y() * TILE_MARGIN + TILE_SIZE / 2 - 3, 15, new Particle(new Color4f(1, 1, 1, 0.5f), 6, 0.2f, 40, new Vec2(0, 0), true)));
		AudioManager.getInstance().play(5);
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
		
	}

	@Override
	public void updateAnim(int frame, Tile t, Board b) {
		
	}

}
