package fr.porquepix.pathzzle.board;

import fr.porquepix.pathzzle.entities.Player;

public interface TileEvent {

	public void onCreate(Tile t, Board b);
	public void onPlayerEnter(Tile t, Board b);
	public void onPlayerLeave(Tile t, Board b);
	public void onMouseEnter(Tile t, Board b);
	public void onMouseLeave(Tile t, Board b);
	public void focus(Tile t, Board b);
	public void updateTile(Tile t, Board b);
	public void updateAnim(int frame, Tile t, Board b);
	public void onHighlight(Tile t, Board b);
	public void onHighlightEnable(Tile t, Board b);
	public void onHighlightDisable(Tile t, Board b);
	public boolean needHighlight(Tile tile, Board board, Player playerSelected);
	
}
