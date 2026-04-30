package com.example.a0644_a1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class SnackDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "cinefast.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_SNACKS = "snacks";
    private Context context;

    public SnackDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_SNACKS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "price REAL, " +
                "image TEXT)");

        insertSnack(db, "Popcorn", 8.99, "popcorn");
        insertSnack(db, "Nachos", 7.99, "nachos");
        insertSnack(db, "Soft Drink", 5.99, "soft_drink");
        insertSnack(db, "Candy Mix", 6.99, "candy_mix");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SNACKS);
        onCreate(db);
    }

    private void insertSnack(SQLiteDatabase db, String name, double price, String image) {
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("price", price);
        cv.put("image", image);
        db.insert(TABLE_SNACKS, null, cv);
    }

    public ArrayList<snack> getAllSnacks() {
        ArrayList<snack> snacks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SNACKS, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                String imageName = cursor.getString(cursor.getColumnIndexOrThrow("image"));
                int resId = context.getResources().getIdentifier(
                        imageName, "drawable", context.getPackageName());
                snacks.add(new snack(name, "", price, resId));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return snacks;
    }
}