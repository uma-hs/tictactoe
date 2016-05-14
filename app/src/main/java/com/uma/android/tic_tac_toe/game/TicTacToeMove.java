package com.uma.android.tic_tac_toe.game;

public class TicTacToeMove implements Move {
	private int x;
	private int y;

	public TicTacToeMove(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public boolean isValid(GameState gameState) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void execute(GameState gameState) {
		// TODO Auto-generated method stub

	}

	@Override
	public Player getPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

}
