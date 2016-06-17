package com.uma.android.tic_tac_toe.game;

import java.io.Serializable;

/**
 * Created by Umamaheshwar HS on 6/11/2016.
 */
public enum EvaluationLevel implements Serializable {
    EASY(2), MEDIUM(4), DIFFICULT(12);
    private int level;

    private EvaluationLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return this.level;
    }

    public String toString() {
        String stringValue="";
        switch (this) {
            case EASY:
                stringValue= "Easy";
            break;
            case MEDIUM:
                stringValue= "Medium";
            break;
            case DIFFICULT:
                stringValue= "Hard";
            break;

        }
        return stringValue;
    }
}
