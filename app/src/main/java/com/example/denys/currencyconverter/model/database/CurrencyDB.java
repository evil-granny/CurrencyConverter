package com.example.denys.currencyconverter.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.example.denys.currencyconverter.model.ValuteGetted;

import java.util.ArrayList;
import java.util.List;


public class CurrencyDB extends SQLiteOpenHelper {

    public static abstract class FeedEntry implements BaseColumns {
        public static final int DATABASE_VERSION = 2;
        public static final String DATABASE_NAME = "currency_db";
        public static final String TABLE_NAME = "currencys";

        public static final String KEY_ID = "_id";
        public static final String KEY_NAME = "name";
        public static final String KEY_VALUE = "value";
        public static final String KEY_ORDER = "order_key";
    }


    public CurrencyDB(Context context) {
        super(context, FeedEntry.DATABASE_NAME, null, FeedEntry.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + FeedEntry.TABLE_NAME +
                " (" +
                FeedEntry.KEY_ID + " INTEGER PRIMARY KEY, " +
                FeedEntry.KEY_NAME + " TEXT, " +
                FeedEntry.KEY_VALUE + " REAL, " +
                FeedEntry.KEY_ORDER + " INTEGER DEFAULT 2 " +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + FeedEntry.TABLE_NAME);
        onCreate(db);
    }

    public void createOrUpdate(String key, ContentValues values) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query(
                FeedEntry.TABLE_NAME,
                null,
                FeedEntry.KEY_NAME + " = ?",
                new String[]{key},
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            sqLiteDatabase.update(FeedEntry.TABLE_NAME, values, FeedEntry.KEY_NAME + " = ?", new String[]{key});
        } else {
            sqLiteDatabase.insert(FeedEntry.TABLE_NAME, null, values);
        }
        cursor.close();
        sqLiteDatabase.close();
    }

    public String[] getCurrencies() {
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query(
                FeedEntry.TABLE_NAME,
                new String[]{FeedEntry.KEY_NAME},
                null,
                null,
                null,
                null,
                FeedEntry.KEY_ORDER + " ASC, " + FeedEntry.KEY_NAME + " ASC"
        );

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                list.add(
                        cursor.getString(cursor.getColumnIndex(FeedEntry.KEY_NAME))
                );
                cursor.moveToNext();
            }
        }
        cursor.close();
        sqLiteDatabase.close();

        return list.toArray(new String[list.size()]);
    }

    public float getVal(String key) {
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.query(
                FeedEntry.TABLE_NAME,
                new String[]{FeedEntry.KEY_VALUE},
                FeedEntry.KEY_NAME + " = ?",
                new String[]{key},
                null,
                null,
                null,
                "1");

        float result = 1;

        if (cursor.moveToFirst()) {
            result = cursor.getFloat(cursor.getColumnIndex(FeedEntry.KEY_VALUE));
        }
        cursor.close();
        db.close();

        return result;
    }

    public List<ValuteGetted> getAll() {
        ArrayList<ValuteGetted> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(FeedEntry.TABLE_NAME, new String[]{FeedEntry.KEY_NAME, FeedEntry.KEY_VALUE}, null, null, null, null, FeedEntry.KEY_NAME);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                list.add(
                        new ValuteGetted(
                                cursor.getString(cursor.getColumnIndex(FeedEntry.KEY_NAME)),
                                cursor.getString(cursor.getColumnIndex(FeedEntry.KEY_VALUE))
                        )
                );
                cursor.moveToNext();
            }
        }
        cursor.close();
        sqLiteDatabase.close();

        return list;
    }
}
