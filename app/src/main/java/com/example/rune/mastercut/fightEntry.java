package com.example.rune.mastercut;

import com.orm.SugarRecord;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * Created by Rune on 12/6/2016.
 */

public class fightEntry extends SugarRecord {
    boolean result;

    public fightEntry(){
    }

    public fightEntry(boolean result){
        this.result = result;
    }
}
