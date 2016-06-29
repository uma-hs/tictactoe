package com.uma.android.tic_tac_toe.game;

public interface GameState {
	public boolean isDraw();

	public boolean isWin();

	public char[][] getBoard();

	public void execute(Move move);

	public boolean isValidMove(Move move);

	public Player getWinner();

	public int getWinStateValue();
}
