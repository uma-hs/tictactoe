package com.uma.android.tic_tac_toe.game;

public class TicTacToePlayerMove extends TicTacToeMove {
	Player player;

	public TicTacToePlayerMove(Player player, int x, int y) {
		super(x, y);
		this.player = player;

	}

	public Player getPlayer() {
		return this.player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public boolean isValid(GameState gameState) {
		return gameState.isValidMove(this);
	}

	@Override
	public void execute(GameState gameState) {
		gameState.execute(this);

	}

}
