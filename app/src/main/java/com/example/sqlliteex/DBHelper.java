package com.example.sqlliteex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_Name = "USER";
    public static final int DB_VERSION = 1;
    public static  String TABLE_NAME = "TBL_USER";
    public static  String ID = "_id";
    public static  String NAME = "name";
    public static  String GENDER = "gender";
    public static  String DES = "des";

    public DBHelper(Context context){
        super(context,DB_Name,null,DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "CREATE TABLE "+TABLE_NAME+" ( "+
                ID+" INTEGER PRIMARY KEY, "+
                NAME+ " TEXT, "+
                GENDER+ " TEXT, "+
                DES+ " TEXT)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS "+ TABLE_NAME;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);

    }

    public String addUser(String user, String gender,String des){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,user);
        contentValues.put(GENDER,gender);
        contentValues.put(DES,des);
        long isAdd = db.insert(TABLE_NAME,null,contentValues);
        if (isAdd == -1){
            return "Add Fail";
        }
        db.close();
        return "Add Success";

    }

    public String updateUser(int id,String user,String gender, String des){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,user);
        contentValues.put(GENDER,gender);
        contentValues.put(DES,des);
        long isUpdate = db.update(TABLE_NAME,contentValues,ID+" = ? ",new String[] {id+""});
        if (isUpdate > 0){
            return "update Success";
        }
        db.close();
        return "update fail";
    }

    public String deleteUser(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        int isDelete = db.delete(TABLE_NAME,ID+" = ? ",new String[] {id+""});
        if (isDelete > 0){
            return "delete success";
        }
        db.close();
        return "delete fail";
    }
    public Cursor getAllUser(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String sql = "SELECT * FROM "+ TABLE_NAME;
        Cursor c = sqLiteDatabase.rawQuery(sql,null);
        return c;
    }



}
