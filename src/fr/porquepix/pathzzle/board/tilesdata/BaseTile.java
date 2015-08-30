package fr.porquepix.pathzzle.board.tilesdata;

import fr.porquepix.pathzzle.board.Board;
import fr.porquepix.pathzzle.board.Tile;
import fr.porquepix.pathzzle.board.TileData;
import fr.porquepix.pathzzle.utils.Color4f;

public class BaseTile extends TileData {

	public BaseTile(int textureID, Color4f color, boolean solid) {
		super(textureID, color, solid);
	}

	@Override
	public void onPlayerEnter(Tile t, Board b) {
		
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
		
	}

	@Override
	public void updateAnim(int frame, Tile t, Board b) {
		
	}
	
}
