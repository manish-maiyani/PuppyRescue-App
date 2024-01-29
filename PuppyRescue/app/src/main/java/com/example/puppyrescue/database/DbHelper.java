package com.example.puppyrescue.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.puppyrescue.database.DbSchema.PuppyTable;

public class DbHelper extends SQLiteOpenHelper
{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "puppyBase.db";

    public DbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table " + PuppyTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                PuppyTable.Cols.UUID + ", " +
                PuppyTable.Cols.NAME + ", " +
                PuppyTable.Cols.BREED + ", " +
                PuppyTable.Cols.GENDER + ", "  +
                PuppyTable.Cols.AGE + ", "  +
                PuppyTable.Cols.LAT + ", "  +
                PuppyTable.Cols.LON +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
