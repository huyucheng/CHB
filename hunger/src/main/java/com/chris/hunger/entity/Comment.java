package com.chris.hunger.entity;

import java.io.Serializable;
import java.util.Date;

public class Comment  implements Serializable{
   
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer shopid;
	private Shop shop;
	private Integer goodsid;
    private Integer orderid;

    private Integer userid;

    private Date commenttime;

    private Integer rank;

    private String content;
    private String appendComment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Date getCommenttime() {
        return commenttime;
    }

    public void setCommenttime(Date commenttime) {
        this.commenttime = commenttime;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    

	public Integer getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(Integer goodsid) {
		this.goodsid = goodsid;
	}

	public String getAppendComment() {
		return appendComment;
	}

	public void setAppendComment(String appendComment) {
		this.appendComment = appendComment;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Integer getShopid() {
		return shopid;
	}

	public void setShopid(Integer shopid) {
		this.shopid = shopid;
	}
	
	
}