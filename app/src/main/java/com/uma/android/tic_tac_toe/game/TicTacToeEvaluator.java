package com.uma.android.tic_tac_toe.game;

import java.util.ArrayList;
import java.util.List;

public class TicTacToeEvaluator implements GameEvaluator {
	final static int SIZE = 3;
	static Point bestMove = null;
	static String TAG=TicTacToeEvaluator.class.getName();

	@SuppressWarnings("unused")
	@Override
	public Move getBestMove(GameState state, Player computer, Player user,EvaluationLevel evaluationLevel) {
		char board[][] = new char[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				board[i][j] = state.getBoard()[i][j];
			}
		}
		bestMove = null;

		minimax(board, 0,evaluationLevel.getLevel(), computer.getValue(), computer.getValue(),
				user.getValue());
		if (bestMove == null)
			return null;
		else
			return new TicTacToePlayerMove(computer, bestMove.x, bestMove.y);

	}

	protected int minimax(char board[][], int depth, int evaluationLevel,char currentPlayer,
						  char maximizingPlayer, char minimizingPlayer) {
		if (playerWonTheGame(board, maximizingPlayer))
			return 1;
		if (playerWonTheGame(board, minimizingPlayer))
			return -1;
		if(depth==evaluationLevel){
			return 0;
		}
		List<Point> pointsAvailable = getAvailablePoints(board);
		if (pointsAvailable.isEmpty())
			return 0;
		//Log.d(TAG, "minimax: At depth " + depth );

		int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;

		for (int i = 0; i < pointsAvailable.size(); ++i) {
			Point point = pointsAvailable.get(i);
			if (currentPlayer == maximizingPlayer) {
				board[point.x][point.y] = currentPlayer;
				int currentScore = minimax(board, depth + 1,evaluationLevel, minimizingPlayer,
						maximizingPlayer, minimizingPlayer);
				max = Math.max(currentScore, max);

				if (currentScore >= 0) {
					if (depth == 0)
						bestMove = point;
				}
				if (currentScore == 1) {
					board[point.x][point.y] = '-';
					break;
				}
				if (i == pointsAvailable.size() - 1 && max < 0) {
					if (depth == 0)
						bestMove = point;
				}
			} else {
				board[point.x][point.y] = currentPlayer;
				int currentScore = minimax(board, depth + 1, evaluationLevel,maximizingPlayer,
						maximizingPlayer, minimizingPlayer);
				min = Math.min(currentScore, min);
				if (min == -1) {
					board[point.x][point.y] = '-';
					break;
				}
			}
			board[point.x][point.y] = '-'; // Reset this point
		}
		return currentPlayer == maximizingPlayer ? max : min;
	}

	static List<Point> getAvailablePoints(char[][] board) {
		List<Point> availablePoints = new ArrayList<Point>();
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (board[i][j] == '-') {
					availablePoints.add(new Point(i, j));
				}
			}
		}

		return availablePoints;
	}

	protected boolean playerWonTheGame(char[][] board, char player) {
		// Diagonal
		if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == player)
				|| (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == player)) {

			return true;
		}
		// Row Or Column
		for (int i = 0; i < 3; ++i) {
			if ((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == player)
					|| (board[0][i] == board[1][i]
					&& board[0][i] == board[2][i] && board[0][i] == player)) {

				return true;
			}
		}

		return false;
	}

}

class Point {
	int x;
	int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;

	}
}
