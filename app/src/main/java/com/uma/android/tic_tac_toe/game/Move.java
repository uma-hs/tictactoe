package com.uma.android.tic_tac_toe.game;

public interface Move {
	public boolean isValid(GameState gameState);

	public void execute(GameState gameState);

	public int getX();

	public int getY();

	public Player getPlayer();
}
