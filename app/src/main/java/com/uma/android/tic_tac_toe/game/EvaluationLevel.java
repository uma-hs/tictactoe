package com.uma.android.tic_tac_toe.game;

/**
 * Created by Umamaheshwar HS on 6/11/2016.
 */
public enum EvaluationLevel {
    EASY(2),MEDIUM(4),DIFFICULT(12);
    private int level;
    private EvaluationLevel(int level){
        this.level=level;
    }
    public int getLevel(){
        return this.level;
    }
}
