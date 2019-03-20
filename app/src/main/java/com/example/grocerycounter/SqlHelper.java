package com.example.grocerycounter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class SqlHelper extends SQLiteOpenHelper {
    public static String db_name = "myshop_1";
    public static String table_name = "shop1";
    public static String col_zero = "id";
    public static String col_one = "name";
    public static String col_two = "amount";
    public static String col_three = "rate";
    public static String col_four = "date";

    public SqlHelper(@Nullable Context context) {
        super(context, db_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table if not exists " + table_name +" ( " + col_zero + " integer Primary key autoincrement ," + col_one + " text , " + col_two + " integer , " + col_three + " real , " + col_four + " text ) ";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insert(ShopObject shopObject){
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_one,shopObject.getName());
        contentValues.put(col_two,shopObject.getAmount());
        contentValues.put(col_three,shopObject.getRate());
        contentValues.put(col_four,shopObject.getDate());

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        long insert = sqLiteDatabase.insert(table_name, null, contentValues);
        if(insert<0){
            return false;
        }else
            return true;
    }


    public Cursor show(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String sql = "select * from " + table_name;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        return cursor;

    }


    public boolean delete(int id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int delete = sqLiteDatabase.delete(table_name, col_zero + " = ? ", new String[]{String.valueOf(id)});
        if(delete>0){
            return true;
        }else
            return false;
    }

    public Cursor getSingleItem(int id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String sql = "select * from " + table_name + " where id  = ? ";
        Cursor cursor = sqLiteDatabase.rawQuery(sql,new String[]{String.valueOf(id)});
        return cursor;
    }

    public boolean update(int amount,int id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(col_two,amount);
        int update = sqLiteDatabase.update(table_name, values, " id  = ? ", new String[]{String.valueOf(id)});
        if(update>0){
            return true;
        }else{
            return false;
        }
    }

    public boolean updateDate(int id,int amount, String date) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(col_two,amount);
        values.put(col_four,date);
        int update = sqLiteDatabase.update(table_name, values, " id  = ? ", new String[]{String.valueOf(id)});
        if(update>0){
            return true;
        }else{
            return false;
        }
    }
}
