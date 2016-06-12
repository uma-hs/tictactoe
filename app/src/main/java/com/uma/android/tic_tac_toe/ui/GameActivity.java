package com.uma.android.tic_tac_toe.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.uma.android.tic_tac_toe.game.EvaluationLevel;
import com.uma.android.tictactoe.R;
import com.uma.android.tic_tac_toe.game.GameEvaluator;
import com.uma.android.tic_tac_toe.game.GameState;
import com.uma.android.tic_tac_toe.game.Move;
import com.uma.android.tic_tac_toe.game.Player;
import com.uma.android.tic_tac_toe.game.TicTacGameState;
import com.uma.android.tic_tac_toe.game.TicTacToeEvaluator;
import com.uma.android.tic_tac_toe.game.TicTacToePlayerMove;

public class GameActivity extends AppCompatActivity {
    private static String TAG =GameActivity.class.getCanonicalName();
    private Player human;
    private Player computer;
    private GameEvaluator evaluator;
    private GameState state;
    final private int[] imageIDS = {R.id.tile1, R.id.tile2, R.id.tile3, R.id.tile4, R.id.tile5, R.id.tile6, R.id.tile7, R.id.tile8, R.id.tile9};
    private ImageView[] tiles = new ImageView[3 * 3];
    private TextView statusBar;
    private Button playAgainBtn;
    private EvaluationLevel evaluationLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_main);
        initializeGame();
    }

    private void initializeGame() {
        human = Player.HUMAN;
        computer = Player.COMPUTER;
        evaluator = new TicTacToeEvaluator();
        state = new TicTacGameState();

        statusBar = (TextView) findViewById(R.id.statusText);
        playAgainBtn = (Button) findViewById(R.id.btnPlayAgain);
        playAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
        for (int i = imageIDS.length - 1; i > -1; i--) {
            tiles[i] = (ImageView) findViewById(imageIDS[i]);
            tiles[i].setOnClickListener(tilesOnclickListener);
        }

    }

    final View.OnClickListener tilesOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ImageView view = (ImageView) v;
            int index = -1;
            for (int i = imageIDS.length - 1; i > -1; i--) {
                if (imageIDS[i] == v.getId()) {
                    index = i;
                    break;
                }
            }
            if (index == -1) return;
            int row = index / 3;
            int col = index % 3;
            Move move = new TicTacToePlayerMove(human, row, col);
            if (!move.isValid(state))
                return;
            move.execute(state);
            view.setImageResource(R.drawable.x);

            boolean gameOver = checkForGameOver();
            if (!gameOver) {
                new EvaluateComputerMoveAsyncTask().execute();
            }
        }
    };

    protected boolean checkForGameOver() {
        boolean gameOver = false;
        if (state.isDraw()) {
            gameOver = true;
            statusBar.setText(R.string.status_draw);
        } else if (state.isWin()) {
            gameOver = true;
            if (human == state.getWinner()) {
                statusBar.setText(R.string.status_won);
            } else {
                statusBar.setText(R.string.status_lost);
            }
        }
        if (gameOver) {
            for (int imgID : imageIDS) {
                findViewById(imgID).setClickable(false);
            }
            playAgainBtn.setVisibility(View.VISIBLE);
        }
        return gameOver;
    }

    protected void resetGame() {
        for (ImageView tile : tiles) {
            tile.setImageResource(R.drawable.empty);
            tile.setClickable(true);
            statusBar.setText(R.string.status_start);
            playAgainBtn.setVisibility(View.INVISIBLE);
            state = new TicTacGameState();
        }
    }

    private class EvaluateComputerMoveAsyncTask extends AsyncTask<Void, Void, Move> {
        @Override
        protected void onPostExecute(Move ticTacToePlayerMove) {

            if (ticTacToePlayerMove == null) {
                checkForGameOver();
                return;
            }
            ticTacToePlayerMove.execute(state);
            int id = ticTacToePlayerMove.getX() * 3 + ticTacToePlayerMove.getY();
            ImageView oppView = (ImageView) findViewById(imageIDS[id]);
            oppView.setImageResource(R.drawable.o);
            statusBar.setText(R.string.status_yourmove);
            checkForGameOver();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            statusBar.setText(R.string.status_computer_thinking);
        }

        @Override
        protected Move doInBackground(Void... params) {
            Move computerMove = evaluator.getBestMove(state, computer, human,evaluationLevel);
            if(computerMove!=null)
                 Log.d(TAG,"compuer_move : "+ computerMove.getX() +" "+computerMove.getY());
            else
                Log.d(TAG,"compuer_move : "+"NULL computer move");
            return computerMove;
        }
    }

}
