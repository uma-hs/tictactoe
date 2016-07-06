package com.uma.android.tic_tac_toe.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.uma.android.tic_tac_toe.game.EvaluationLevel;
import com.uma.android.tictactoe.R;

public class MainActivity extends AppCompatActivity {
    private Button btnEasy;
    private Button btnDifficult;
    private Button btnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeButtons();
    }

    private void initializeButtons() {
        btnEasy = (Button) findViewById(R.id.btn_easy);
        btnEasy.setOnClickListener(buttonOnClickListener);


        btnDifficult = (Button) findViewById(R.id.btn_difficult);
        btnDifficult.setOnClickListener(buttonOnClickListener);

        btnAbout = (Button) findViewById(R.id.btn_about);
        btnAbout.setOnClickListener(buttonOnClickListener);
    }

    final View.OnClickListener buttonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_easy:
                    startGameActivity(EvaluationLevel.EASY);
                    break;
                case R.id.btn_difficult:
                    startGameActivity(EvaluationLevel.DIFFICULT);
                    break;
                case R.id.btn_about:
                    Toast.makeText(MainActivity.this, "Developed by uhs", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };

    private void startGameActivity(EvaluationLevel evaluationLevel) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("evaluationLevel", evaluationLevel);
        startActivity(intent);
    }

}
