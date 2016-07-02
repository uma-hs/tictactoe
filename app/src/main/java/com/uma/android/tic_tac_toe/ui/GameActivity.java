package com.uma.android.tic_tac_toe.ui;

import android.content.Intent;
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

import java.util.Random;

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
    private volatile boolean firstPlayerHuman;
    private volatile boolean usersTurn;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_main);
        initializeGame();
    }

    private void initializeGame() {
        Intent intent=getIntent();
        //
        evaluationLevel=(EvaluationLevel)intent.getSerializableExtra("evaluationLevel");
        ((TextView)findViewById(R.id.level)).setText(String.format(getResources().getString(R.string.level),evaluationLevel.toString()));

        statusBar = (TextView) findViewById(R.id.statusText);

        preferenceManager=PreferenceManager.getInstance(this);

        human = Player.HUMAN;
        computer = Player.COMPUTER;
        evaluator = new TicTacToeEvaluator();
        state = new TicTacGameState();
        initialisePlayers();
        resetWinCounts();
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
    private void initialisePlayers(){
        firstPlayerHuman=preferenceManager.getTotalGamesCount(evaluationLevel)%2==0;
        if(!firstPlayerHuman){
            statusBar.setText(R.string.computers_turn);
            usersTurn=false;
            selectComputerMove();
            usersTurn=true;
        }else{
            statusBar.setText(R.string.your_turn);
            usersTurn=true;
        }
    }

    final View.OnClickListener tilesOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG,"On Click Tile");
            if(!usersTurn) return;
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
            Log.d(TAG,"Executing Move.."+row +" " +col);
            move.execute(state);
            view.setImageResource(R.drawable.x);
            usersTurn=false;

            boolean gameOver = checkForGameOver();
            Log.d(TAG,"After Executing Move: GameOver Flag is : "+gameOver);
            if (!gameOver) {
                new EvaluateComputerMoveAsyncTask().execute();
            }
        }
    };

    protected boolean checkForGameOver() {
        boolean gameOver = false;
         if (state.isWin()) {
            gameOver = true;
            strikeWinningTile(state.getWinner(),state.getWinStateValue());
            if (human == state.getWinner()) {
                statusBar.setText(R.string.status_won);
                preferenceManager.setHumanWinCount(evaluationLevel);
            } else {
                statusBar.setText(R.string.status_lost);
                preferenceManager.setComputerWinCount(evaluationLevel);
            }
        }else if (state.isDraw()) {
            gameOver = true;
            statusBar.setText(R.string.status_draw);
            preferenceManager.setTiesCount(evaluationLevel);
        }
        if (gameOver) {
            usersTurn=false;
            for (int imgID : imageIDS) {
                findViewById(imgID).setClickable(false);
            }
            //playAgainBtn.setVisibility(View.VISIBLE);
        }
        return gameOver;
    }

    protected void strikeWinningTile(Player winner,int winStateValue){

        if(winStateValue<3){
            int row=winStateValue;
            int imageStartingIndex=row*3;
            for(int i=0;i<3;i++){
               ImageView tile=(ImageView)findViewById(imageIDS[imageStartingIndex+i]);
                if(winner==human){
                    tile.setImageResource(R.drawable.x1);
                }else{
                    tile.setImageResource(R.drawable.o1);
                }
            }

        }else if(winStateValue<6){
                int column=winStateValue-3;
            for(int i=0;i<3;i++){
                    ImageView tile=(ImageView)findViewById(imageIDS[column+(i*3)]);
                    if(winner==human){
                        tile.setImageResource(R.drawable.x2);
                    }else{
                        tile.setImageResource(R.drawable.o2);
                    }

            }
        }
            else if(winStateValue==6){
            ImageView tile1=(ImageView)findViewById(imageIDS[0]);
            ImageView tile2=(ImageView)findViewById(imageIDS[4]);
            ImageView tile3=(ImageView)findViewById(imageIDS[8]);
            int imageID=winner==human?R.drawable.x3:R.drawable.o3;

            tile1.setImageResource(imageID);
            tile2.setImageResource(imageID);
            tile3.setImageResource(imageID);


        }else{

            ImageView tile1=(ImageView)findViewById(imageIDS[2]);
            ImageView tile2=(ImageView)findViewById(imageIDS[4]);
            ImageView tile3=(ImageView)findViewById(imageIDS[6]);
            int imageID=winner==human?R.drawable.x4:R.drawable.o4;

            tile1.setImageResource(imageID);
            tile2.setImageResource(imageID);
            tile3.setImageResource(imageID);
        }

    }

    protected void resetGame() {
        Log.d(TAG,"Inside Reset Game..."+"\n\n\n");

        state = new TicTacGameState();
        for (ImageView tile : tiles) {
            tile.setImageResource(R.drawable.empty);
            tile.setClickable(true);
            //playAgainBtn.setVisibility(View.INVISIBLE)
        }
        //statusBar.setText(R.string.status_start);
        initialisePlayers();
        resetWinCounts();

    }

    private void resetWinCounts(){
        int humanWins=preferenceManager.getHumanWinCount(evaluationLevel);
        int computerWins=preferenceManager.getComputerWinCount(evaluationLevel);
        int ties=preferenceManager.getTiesCount(evaluationLevel);
        ((TextView)findViewById(R.id.human)).setText(String.format(getResources().getString(R.string.human),humanWins));

        ((TextView)findViewById(R.id.ties)).setText(String.format(getResources().getString(R.string.ties),ties));

        ((TextView)findViewById(R.id.computer)).setText(String.format(getResources().getString(R.string.computer),computerWins));
    }
    private void selectComputerMove(){
        int imageID=new Random().nextInt(8)+1;
        int row = imageID / 3;
        int col = imageID % 3;
        Move move = new TicTacToePlayerMove(computer, row, col);
        if (!move.isValid(state))
            return;
        Log.d(TAG,"Executing Move.."+row +" " +col);
        move.execute(state);
        ImageView oppView = (ImageView) findViewById(imageIDS[imageID]);
        oppView.setImageResource(R.drawable.o);
        statusBar.setText(R.string.your_turn);

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
            statusBar.setText(R.string.your_turn);
            if(checkForGameOver()){
                usersTurn=false;
            }else {
                usersTurn=true;
            };


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            statusBar.setText(R.string.computers_turn);
        }

        @Override
        protected Move doInBackground(Void... params) {
            Log.d(TAG,"Starting doInBackGround()....");
            Move computerMove = evaluator.getBestMove(state, computer, human,evaluationLevel);
            try {
                Thread.sleep(400L);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.d(TAG,"Exception in doInBackground()");
            }
            if(computerMove!=null)
                 Log.d(TAG,"compuer_move : "+ computerMove.getX() +" "+computerMove.getY());
            else
                Log.d(TAG,"compuer_move : "+"NULL computer move");
            return computerMove;
        }
    }

}
