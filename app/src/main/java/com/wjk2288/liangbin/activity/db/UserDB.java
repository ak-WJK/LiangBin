package com.wjk2288.liangbin.activity.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wjk2288.liangbin.activity.table.UserTable;

/**
 * Created by Administrator on 2017/7/19.
 */

public class UserDB extends SQLiteOpenHelper {
    public UserDB(Context context, int version) {
        super(context, "goods.db", null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(UserTable.CREATE_TABLE);
         
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
