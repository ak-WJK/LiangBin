package com.wjk2288.liangbin.activity.table;

/**
 * Created by Administrator on 2017/7/19.
 */

public class UserTable {

    public static final String TABLE_NAME = "usergoods";

    public static final String GOODS_ID = "goodsid";

    public static final String GOODS_NAME = "goodsname";

    public static final String GOODS_CONTENT = "goodscontent";

    public static final String GOODS_PRICE = "goodsprice";

    public static final String GOODS_NUMBER = "goodsnumber";

    public static final String GOODS_IMAGE_URL = "goodsurl";

    public static final String GOODS_ATTR_NAME = "goodsattr";


    public static final String CREATE_TABLE = " create table " + TABLE_NAME + " ("
            + GOODS_ID + " text primary key,"
            + GOODS_NAME + " text,"
            + GOODS_CONTENT + " text,"
            + GOODS_PRICE + " text, "
            + GOODS_IMAGE_URL + " text, "
            + GOODS_ATTR_NAME + " text, "
            + GOODS_NUMBER + " integer)";


}
