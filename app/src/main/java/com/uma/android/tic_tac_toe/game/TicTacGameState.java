package com.uma.android.tic_tac_toe.game;

public class TicTacGameState implements GameState {
    private char board[][];
    private Player winner;
    private int winStateValue = -1;
    private final int SIZE = 3;

    public TicTacGameState() {
        char board[][] = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = '-';
            }
        }
        this.board = board;
    }

    public TicTacGameState(char board[][]) {
        this.board = board;
    }

    @Override
    public boolean isDraw() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == '-')
                    return false;
            }
        }
        return true;
    }

    @Override
    public boolean isWin() {
        boolean computerWon = playerWonTheGame(Player.COMPUTER.getValue());
        if (computerWon) {
            winner = Player.COMPUTER;
            return true;
        }
        boolean humanWon = playerWonTheGame(Player.HUMAN.getValue());
        if (humanWon) {
            winner = Player.HUMAN;
            return true;
        }
        return false;
    }

    @Override
    public char[][] getBoard() {
        return board;
    }

    @Override
    public void execute(Move move) {
        this.board[move.getX()][move.getY()] = move.getPlayer().getValue();

    }

    @Override
    public boolean isValidMove(Move move) {
        if (board[move.getX()][move.getY()] == '-') {
            return true;
        }
        return false;
    }

    @Override
    public Player getWinner() {
        return this.winner;
    }

    @Override
    public int getWinStateValue() {
        return this.winStateValue;
    }

    private boolean playerWonTheGame(char player) {
        // Diagonal
        if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == player)
                ) {
            winStateValue = 6;
            return true;
        }
        if ((board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == player)) {
            winStateValue = 7;
            return true;
        }
        // Row Or Column
        for (int i = 0; i < 3; ++i) {
            if ((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == player)) {
                winStateValue=i;
                return true;
            }
            if ((board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == player)) {
                winStateValue=3+i;
                return true;
            }
        }

        return false;
    }
}
