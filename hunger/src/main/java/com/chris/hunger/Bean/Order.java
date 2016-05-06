package com.chris.hunger.Bean;

import com.chris.hunger.entity.Shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Order  implements Serializable{

    private Integer id;  //订单id
    private Double totalprice;// 商品总价

    //用户信息
    private Integer userid; //用户id
    private String userphone;//用户电话
    private String username;//用户名

    private Integer shopid;//商店id
    private List<com.chris.hunger.Bean.Order_good> goods=new ArrayList<>();
    private Shop shop;//商品信息

    private Date ordertime;//下单时间

    private Integer orderstatus;//订单状态 未处理，已处理，已评价

    private Integer sendstatus;//发送状态

    private String memo;//备注

    private Date receivetime;//收单时间

    private Integer rank;//评价等级

    private String comment;//评论

    private String orderdetail;//订单详情

    private Integer sendpersonid;//送货员信息

    private String sendpersonname;
    private String sendpersonphone;

    private Integer addressid;//地址id
    private String address;

    private Integer totalnum;//总数

    private  Map<String, Object>[] orderdelist;//订单商品详情




    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSendpersonphone() {
        return sendpersonphone;
    }

    public void setSendpersonphone(String sendpersonphone) {
        this.sendpersonphone = sendpersonphone;
    }

    public String getSendpersonname() {
        return sendpersonname;
    }

    public void setSendpersonname(String sendpersonname) {
        this.sendpersonname = sendpersonname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public Integer getShopid() {
		return shopid;
	}

	public void setShopid(Integer shopid) {
		this.shopid = shopid;
	}


    public List<com.chris.hunger.Bean.Order_good> getGoods() {
        return goods;
    }

    public void setGoods(List<Order_good> goods) {
        this.goods = goods;
    }




    public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}


	public Integer getTotalnum() {
		return totalnum;
	}

	public void setTotalnum(Integer totalnum) {
		this.totalnum = totalnum;
	}



	public Map<String, Object>[] getOrderdelist() {
		return orderdelist;
	}

	public void setOrderdelist(Map<String, Object>[] orderdelist) {
		this.orderdelist = orderdelist;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    

    public Double getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(Double totalprice) {
		this.totalprice = totalprice;
	}

	public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

   
    public Date getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Date ordertime) {
        this.ordertime = ordertime;
    }

    public Integer getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(Integer orderstatus) {
        this.orderstatus = orderstatus;
    }

    public Integer getSendstatus() {
        return sendstatus;
    }

    public void setSendstatus(Integer sendstatus) {
        this.sendstatus = sendstatus;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getReceivetime() {
        return receivetime;
    }

    public void setReceivetime(Date receivetime) {
        this.receivetime = receivetime;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getOrderdetail() {
        return orderdetail;
    }

    public void setOrderdetail(String orderdetail) {
        this.orderdetail = orderdetail;
    }

    public Integer getSendpersonid() {
        return sendpersonid;
    }

    public void setSendpersonid(Integer sendpersonid) {
        this.sendpersonid = sendpersonid;
    }

    public Integer getAddressid() {
        return addressid;
    }

    public void setAddressid(Integer addressid) {
        this.addressid = addressid;
    }
}