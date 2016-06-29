package com.uma.android.tic_tac_toe.game;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by Umamaheshwar HS on 6/11/2016.
 */
public enum EvaluationLevel implements Serializable {
    EASY(2), MEDIUM(6), DIFFICULT(20);
    private int level;

    private EvaluationLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        if (this.equals(DIFFICULT)) {
            return this.level;
        } else {
            Random rn = new Random();
            return 2 + rn.nextInt(4);
        }
    }

    public String toString() {
        String stringValue = "";
        switch (this) {
            case EASY:
                stringValue = "Easy";
                break;
            case MEDIUM:
                stringValue = "Medium";
                break;
            case DIFFICULT:
                stringValue = "Hard";
                break;

        }
        return stringValue;
    }
}
