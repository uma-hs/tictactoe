package com.uma.android.tic_tac_toe.game;

public enum Player {
	HUMAN('X'), COMPUTER('O');
	private char player;

	private Player(char c) {
		this.player = c;
	}

	public char getValue() {
		return this.player;
	}
}
