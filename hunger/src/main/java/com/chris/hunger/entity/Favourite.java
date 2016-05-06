package com.chris.hunger.entity;

import java.io.Serializable;

public class Favourite  implements Serializable{
  
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer shopid;

    private Integer userid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShopid() {
        return shopid;
    }

    public void setShopid(Integer shopid) {
        this.shopid = shopid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}