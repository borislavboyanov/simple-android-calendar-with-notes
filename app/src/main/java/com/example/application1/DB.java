package com.example.application1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DB extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "appdb.db";


    public DB (Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.rawQuery("SELECT DISTINCT table_name FROM appdb.db WHERE table_name='user'", null);
        } catch (SQLiteException e) {

            db.execSQL("CREATE TABLE IF NOT EXISTS user(user_id INTEGER PRIMARY KEY AUTOINCREMENT, email VARCHAR(255) NOT NULL, password VARCHAR(255) NOT NULL)");
            db.execSQL("CREATE TABLE IF NOT EXISTS notes(user_id INTEGER, date_of_notes VARCHAR(10), note TEXT, FOREIGN KEY(user_id) REFERENCES user(user_id))");
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS notes");
        onCreate(db);

    }

    public boolean createUser(Context context, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT email FROM user WHERE email='" + email + "' LIMIT 1", null);
        boolean exists = cur.moveToFirst();

        if (exists) {
            Toast.makeText(context, "This user already exists!", Toast.LENGTH_SHORT).show();
            cur.close();;
            return false;
        } else {
            cur.close();
            db.execSQL("INSERT INTO user(email, password) VALUES ('" + email + "','" + password + "')");
            Toast.makeText(context, "User successfully registered!", Toast.LENGTH_SHORT).show();
        }

        db.close();
        return true;
    }

    public boolean logIn(Context context, String email, String password) {
        Cursor cur;
        SQLiteDatabase db = this.getReadableDatabase();
        if (!email.equals("") && !password.equals("")) {
            try {
                cur = db.rawQuery("SELECT email, password FROM user WHERE email='" + email + "' AND password='" + password + "'" , null);
                boolean exists = cur.moveToFirst();

                 if (!exists) {
                    Toast.makeText(context, "This user does not exist!", Toast.LENGTH_SHORT).show();

                    return false;

                } else {

                    if (password.equals(cur.getString(cur.getColumnIndex("password")))) {
                        Toast.makeText(context, "Successful login!", Toast.LENGTH_SHORT).show();

                        return true;

                    } else {
                        Toast.makeText(context, "The password is wrong!", Toast.LENGTH_SHORT).show();

                        return false;

                    }
                }
            } catch (Exception e) {
                Toast.makeText(context, "There are no registered users!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();

                return false;
            }
        } else {
            Toast.makeText(context, "Type correct input data!", Toast.LENGTH_SHORT).show();

            return false;
        }
    }

    public String loadNotes(Context context, String email, String date) {
        Cursor cur = null;
        SQLiteDatabase db = this.getReadableDatabase();
        boolean exists = false;

        Toast.makeText(context, "Loading notes by " + email + " from " + date, Toast.LENGTH_SHORT).show();
        try {
            cur = db.rawQuery("SELECT user_id, note, date_of_notes FROM notes WHERE date_of_notes='" + date + "' AND user_id=(SELECT user_id FROM user WHERE email='" + email + "')", null);
            exists = cur.moveToFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!exists) {
            return "";
        } else {
            return cur.getString(cur.getColumnIndex("note"));
        }
    }

    public boolean updateNotes(Context context, String text, String email, String date) {
        Cursor cur;
        SQLiteDatabase db = this.getWritableDatabase();
        boolean exists;

        try {
            cur = db.rawQuery("SELECT note FROM notes WHERE date_of_notes='" + date + "' AND user_id=(SELECT user_id FROM user WHERE email='" + email + "')", null);
            exists = cur.moveToFirst();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Notes do not exist! Proceeding to insert!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(exists) {
            Toast.makeText(context, "Notes exist!", Toast.LENGTH_SHORT).show();
            db.execSQL("UPDATE notes SET note='" + text + "' WHERE date_of_notes='" + date + "' AND user_id=(SELECT user_id FROM user WHERE email='" + email + "')");
            Toast.makeText(context, "Notes successfully updated!", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(context, "Notes do not exist! Proceeding to insert!", Toast.LENGTH_SHORT).show();
            db.execSQL("INSERT INTO notes(user_id, date_of_notes, note) VALUES ((SELECT user_id FROM user WHERE user.email='" + email + "'), '" + date + "', '" + text + "')");
            Toast.makeText(context, "Notes successfully inserted!", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}
