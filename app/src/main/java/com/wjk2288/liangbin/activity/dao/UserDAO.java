package com.wjk2288.liangbin.activity.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wjk2288.liangbin.activity.db.UserDB;
import com.wjk2288.liangbin.activity.shop.bean.CartBean;
import com.wjk2288.liangbin.activity.table.UserTable;

import java.util.ArrayList;

import static com.wjk2288.liangbin.activity.shop.base.BasePager.context;

/**
 * Created by Administrator on 2017/7/19.
 */

public class UserDAO {

    private static UserDB db;

    public UserDAO(Context context) {
        db = new UserDB(context, 1);
    }

    private static UserDAO userDAO = new UserDAO(context);

    public static UserDAO getInstance() {
        return userDAO;
    }


    //添加商品
    public void addGoods(CartBean cartBean) {
        if (cartBean == null) {
            throw new NullPointerException("数据不能为空");
        }

        SQLiteDatabase database = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserTable.GOODS_ID, cartBean.getGoodsId());
        values.put(UserTable.GOODS_NAME, cartBean.getGoodsName());
        values.put(UserTable.GOODS_PRICE, cartBean.getGoodsPrice());
        values.put(UserTable.GOODS_CONTENT, cartBean.getGoodsContent());
        values.put(UserTable.GOODS_IMAGE_URL, cartBean.getImageUrl());
        values.put(UserTable.GOODS_NUMBER, cartBean.getGoodsNumber());
        values.put(UserTable.GOODS_ATTR_NAME, cartBean.getAttrName());
        database.replace(UserTable.TABLE_NAME, null, values);
    }


    //删除商品
    public void deleteGoods(String goodsId) {

        SQLiteDatabase database = db.getWritableDatabase();

        database.delete(UserTable.TABLE_NAME, UserTable.GOODS_ID + "=?", new String[]{goodsId});

    }

    //查找商品
    public ArrayList<CartBean> getCartGoods() {


        SQLiteDatabase database = db.getWritableDatabase();
        String sql = "select * from " + UserTable.TABLE_NAME;
        Cursor cursor = database.rawQuery(sql, null);

        ArrayList<CartBean> cartBeanArrayList = new ArrayList<>();

        while (cursor.moveToNext()) {

            CartBean cartBean = new CartBean();
            cartBean.setGoodsId(cursor.getString(cursor.getColumnIndex(UserTable.GOODS_ID)));
            cartBean.setGoodsName(cursor.getString(cursor.getColumnIndex(UserTable.GOODS_NAME)));
            cartBean.setGoodsContent(cursor.getString(cursor.getColumnIndex(UserTable.GOODS_CONTENT)));
            cartBean.setGoodsPrice(cursor.getString(cursor.getColumnIndex(UserTable.GOODS_PRICE)));
            cartBean.setGoodsNumber(cursor.getInt(cursor.getColumnIndex(UserTable.GOODS_NUMBER)));
            cartBean.setImageUrl(cursor.getString(cursor.getColumnIndex(UserTable.GOODS_IMAGE_URL)));
            cartBean.setAttrName(cursor.getString(cursor.getColumnIndex(UserTable.GOODS_ATTR_NAME)));
            cartBeanArrayList.add(cartBean);
        }
        cursor.close();
        return cartBeanArrayList;

    }

}