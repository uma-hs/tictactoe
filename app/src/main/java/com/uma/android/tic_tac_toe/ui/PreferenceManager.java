package com.uma.android.tic_tac_toe.ui;

import android.content.Context;
import android.content.SharedPreferences;

import com.uma.android.tic_tac_toe.game.EvaluationLevel;

/**
 * Created by Umamaheshwar HS on 6/17/2016.
 */
public class PreferenceManager {
    private static PreferenceManager sPreferenceManager;
    private final SharedPreferences sharedPreferences;
    private final String PREF_NAME = "TIC_TAC_TOE";

    public final String HUMAN = "HUMAN";
    public final String TIES = "TIES";
    public final String COMPUTER = "COMPUTER";

    public PreferenceManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static PreferenceManager getInstance(Context context) {
        if (sPreferenceManager == null) {
            return new PreferenceManager(context);

        }
        return sPreferenceManager;
    }


    public int getHumanWinCount(EvaluationLevel evaluationLevel) {
        return sharedPreferences.getInt(evaluationLevel.toString() + HUMAN, 0);
    }
    public int getTiesCount(EvaluationLevel evaluationLevel){
        return sharedPreferences.getInt(evaluationLevel.toString()+TIES,0);
    }
    public int getComputerWinCount(EvaluationLevel evaluationLevel){
        return sharedPreferences.getInt(evaluationLevel.toString()+COMPUTER,0);
    }
    public void setHumanWinCount(EvaluationLevel evaluationLevel) {
        int count= sharedPreferences.getInt(evaluationLevel.toString() + HUMAN, 0);
        sharedPreferences.edit().putInt(evaluationLevel.toString() + HUMAN,count+1).apply();
    }
    public void setTiesCount(EvaluationLevel evaluationLevel){
        int count= sharedPreferences.getInt(evaluationLevel.toString()+TIES,0);
        sharedPreferences.edit().putInt(evaluationLevel.toString() + TIES,count+1).apply();
    }
    public void setComputerWinCount(EvaluationLevel evaluationLevel){
        int count= sharedPreferences.getInt(evaluationLevel.toString()+COMPUTER,0);
        sharedPreferences.edit().putInt(evaluationLevel.toString() + COMPUTER,count+1).apply();
    }
    public int getTotalGamesCount(EvaluationLevel evaluationLevel){
        return  getComputerWinCount(evaluationLevel)+getHumanWinCount(evaluationLevel)+getTiesCount(evaluationLevel);
    }
}
