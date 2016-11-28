package com.example.rune.mastercut;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public final class Contract {
    private Contract(){}

    public static class FightRecord implements BaseColumns{
        public static final String TABLE_NAME = "longsword";
        public static final String KEY_ID = "_id";
        public static final String DATE = "date";
        public static final String TIME = "time";
        public static final String RESULT = "result";
    }
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FightRecord.TABLE_NAME + " (" +
                    FightRecord._ID + " INTEGER PRIMARY KEY," +
                    FightRecord.KEY_ID + TEXT_TYPE + COMMA_SEP +
                    FightRecord.DATE + TEXT_TYPE + COMMA_SEP +
                    FightRecord.TIME + TEXT_TYPE + COMMA_SEP +
                    FightRecord.RESULT + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FightRecord.TABLE_NAME;

    public static class DBHelper extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "FightRecord.db";

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }
    }
}
