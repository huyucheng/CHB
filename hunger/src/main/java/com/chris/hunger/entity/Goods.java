package com.chris.hunger.entity;

import java.io.Serializable;
import java.util.Date;

public class Goods  implements Serializable{
    private Integer id;

    private String name;

    private Integer storenumber;

    private String photo;

    private Long price;

    private String description;

    private Date inserttime;

    private Integer shopid;

    private Integer categoryid;

    private Integer salescount;

    private Integer status;

    private Integer achievemoney;

    private Integer reducemoney;

    private Integer rank;
    
    private Goodscategory goodscategory;
    

    public Goodscategory getGoodscategory() {
		return goodscategory;
	}

	public void setGoodscategory(Goodscategory goodscategory) {
		this.goodscategory = goodscategory;
	}


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStorenumber() {
        return storenumber;
    }

    public void setStorenumber(Integer storenumber) {
        this.storenumber = storenumber;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getInserttime() {
        return inserttime;
    }

    public void setInserttime(Date inserttime) {
        this.inserttime = inserttime;
    }

   
    
   

	public Integer getShopid() {
		return shopid;
	}

	public void setShopid(Integer shopid) {
		this.shopid = shopid;
	}

	public Integer getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Integer categoryid) {
        this.categoryid = categoryid;
    }

    public Integer getSalescount() {
        return salescount;
    }

    public void setSalescount(Integer salescount) {
        this.salescount = salescount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAchievemoney() {
        return achievemoney;
    }

    public void setAchievemoney(Integer achievemoney) {
        this.achievemoney = achievemoney;
    }

    public Integer getReducemoney() {
        return reducemoney;
    }

    public void setReducemoney(Integer reducemoney) {
        this.reducemoney = reducemoney;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}