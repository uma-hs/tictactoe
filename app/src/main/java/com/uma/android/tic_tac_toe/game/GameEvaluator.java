package com.uma.android.tic_tac_toe.game;

public interface GameEvaluator {
	public Move getBestMove(GameState state, Player computer, Player user,EvaluationLevel evaluationLevel);
}
