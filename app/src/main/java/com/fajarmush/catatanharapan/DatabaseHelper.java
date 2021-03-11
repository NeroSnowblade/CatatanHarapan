package com.fajarmush.catatanharapan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final int VERSION = 1;
    private static final String DB_NAME = "CatatanHarapan";
    private static final String TABLE_NAME = "data_note";

    private static String ID = "id_note";
    private static String TITLE = "judul_note";
    private static String DESCRIPTION = "deskripsi_note";
    private static String DATE = "tanggal_note";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TITLE + " TEXT, " + DESCRIPTION + " TEXT," + DATE + " TEXT);";
        database.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }

    void insert(String title, String description, String date) {
        SQLiteDatabase database = this.getWritableDatabase();

        // Values Container
        ContentValues values = new ContentValues();
        values.put(TITLE, title);
        values.put(DESCRIPTION, description);
        values.put(DATE, date);

        long result = database.insert(TABLE_NAME, null, values);
        if (result == -1) {
            Toast.makeText(context, "Create Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Create Success", Toast.LENGTH_SHORT).show();
        }
    }

    void edit(String id, String title, String description, String date) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE, title);
        values.put(DESCRIPTION, description);
        values.put(DATE, date);

        long result = database.update(TABLE_NAME, values, "id_note=?", new String[]{id});
        if (result == -1) {
            Toast.makeText(context, "Save Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Save Success", Toast.LENGTH_SHORT).show();
        }
    }

    void delete(String id) {
        SQLiteDatabase database = this.getWritableDatabase();
        long result = database.delete(TABLE_NAME, "id_note=?", new String[]{id});
        if (result == -1) {
            Toast.makeText(context, "Delete Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Delete Success", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor read() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = null;
        if (database != null) {
            cursor = database.rawQuery(query, null);
        }
        return cursor;
    }
}
