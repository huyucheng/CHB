package com.chris.hunger.utils;

/**
 * Created by dell on 2016/3/12.
 */
public interface  GetURLUtil {
    String SHOP_LOCAL_PHOTO="http://10.6.12.58:8080/";
    String ADDRESS_BaseURL = "http://10.6.12.58:8080/chb/user/";
    String Order_BaseURL = "http://10.6.12.58:8080/chb/order/";
    String SHOP_BASEURL="http://10.6.12.58:8080/chb/shopCategory/shop.do?";
    String GOODS_BASEURL="http://10.6.12.58:8080/chb/shop/queryGoodsCategoryList.do?id=1";
    String MENU_BASEURL="http://10.6.12.58:8080/chb/shopCategory/shopCategory.do";
    //登录
    String LOGIN_URL="http://10.6.12.58:8080/chb/user/";
    //注册
    String REGISTER_URL = "http://10.6.12.58:8080/chb/user/";
    //重置密码
    String RESET_URL = "http://10.6.12.58:8080/chb/user/";

    String RANK_BASEURL="http://10.6.12.58:8080/chb/shop/queryShopOrder.do?order=rank";

    String SEND_BASEURL="http://10.6.12.58:8080/chb/shop/queryShopOrder.do?order=sendExpense";
    String DISTANCE_BASEURL="http://10.6.12.58:8080/chb/shop/queryDistance.do?";
    String SALES_BASEURL="http://10.6.12.58:8080/chb/shop/queryShopOrder.do?order=curmonthsales";

    String COMMENT_BASEURL="http://10.6.12.58:8080/chb/comment/shopCommentList.do?id=4";
    String ADDRESS_BASEURL="http://10.6.12.58:8080/chb/user/addLove.do?userid=1&shopid=1";
    String LOVE_ADD_BASEURL="http://10.6.12.58:8080/chb/user/addLove.do?";
    String LOVE_QUERY_BASEURL="http://10.6.12.58:8080/chb/user/queryLove.do?";
    String UPLOADIMAGE="http://10.6.12.58:8080/chb/user/updateInfo.do?";
}
