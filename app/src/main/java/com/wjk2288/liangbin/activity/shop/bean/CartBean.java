package com.wjk2288.liangbin.activity.shop.bean;

/**
 * Created by Administrator on 2017/7/19.
 */

public class CartBean {
    private String goodsName;
    private String goodsContent;
    private String goodsPrice;
    private String imageUrl;
    private String attrName;
    private String goodsId;
    private int goodsNumber;




    public void setChecked(boolean checked) {
        this.isChecked = checked;
    }


    private boolean isChecked = true;

    public boolean isChecked() {
        return isChecked;
    }


    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsContent() {
        return goodsContent;
    }

    public void setGoodsContent(String goodsContent) {
        this.goodsContent = goodsContent;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public int getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(int goodsNumber) {
        this.goodsNumber = goodsNumber;
    }


    @Override
    public String toString() {
        return "CartBean{" +
                "goodsName='" + goodsName + '\'' +
                ", goodsContent='" + goodsContent + '\'' +
                ", goodsPrice='" + goodsPrice + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", attrName='" + attrName + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", goodsNumber=" + goodsNumber +
                '}';
    }
}
