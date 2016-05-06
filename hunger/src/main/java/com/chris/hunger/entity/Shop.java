package com.chris.hunger.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;




public class Shop  implements Serializable{
	
    private Integer id;

    private String username;

    private String password;

    private String phone;

    private Integer province;

    private Integer city;

    private Integer area;

    private String address;

    private String shopphoto;

    
    private Date registertime;
    
    private Date updatetime;

    private Integer shoptype;

    private Integer minconsume;

    private Integer sendexpense;

    private String email;

    private String qrcode;

    private String shopmessage;

    private String telephone;

    private String identify;

    private String license;

    private String name;

    private Date businessstarttime;

    private Date businessendtime;

    private Integer rank;
    
    private Integer examinetyype;
    
    private int saleCount = 0;
    
    

	public int getSaleCount() {
		return saleCount;
	}

	public void setSaleCount(int saleCount) {
		this.saleCount = saleCount;
	}

	public Integer getExaminetyype() {
		return examinetyype;
	}

	public void setExaminetyype(Integer examinetyype) {
		this.examinetyype = examinetyype;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShopphoto() {
        return shopphoto;
    }

    public void setShopphoto(String shopphoto) {
        this.shopphoto = shopphoto;
    }

    public Date getRegistertime() {
        return registertime;
    }

    public void setRegistertime(Date registertime) {
        this.registertime = registertime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getShoptype() {
        return shoptype;
    }

    public void setShoptype(Integer shoptype) {
        this.shoptype = shoptype;
    }

    public Integer getMinconsume() {
        return minconsume;
    }

    public void setMinconsume(Integer minconsume) {
        this.minconsume = minconsume;
    }

    public Integer getSendexpense() {
        return sendexpense;
    }

    public void setSendexpense(Integer sendexpense) {
        this.sendexpense = sendexpense;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getShopmessage() {
        return shopmessage;
    }

    public void setShopmessage(String shopmessage) {
        this.shopmessage = shopmessage;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBusinessstarttime() {
        return businessstarttime;
    }

    public void setBusinessstarttime(Date businessstarttime) {
        this.businessstarttime = businessstarttime;
    }

    public Date getBusinessendtime() {
        return businessendtime;
    }

    public void setBusinessendtime(Date businessendtime) {
        this.businessendtime = businessendtime;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
    
    
    

	public class OrderField{
		private String fieldName;
		private String order;


		public OrderField(String fieldName, String order) {
			super();
			this.fieldName = fieldName;
			this.order = order;
		}
		public String getFieldName() {
			return fieldName;
		}
		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}
		public String getOrder() {
			return order;
		}
		public void setOrder(String order) {
			this.order = order;
		}

	}
	
	
	    private  List<OrderField> orderFields = new ArrayList<OrderField>();
	    /**
	     * 按銷量排序
	     * @param isAsc
	     * @return
	     */
	    public  Shop orderbySaleCount(boolean isAsc){
	    	orderFields.add(new OrderField("saleCount",isAsc ? "ASC":"DESC"));
	    	return this;
	    } 
	    /**
	     * 按配送費排序
	     * @param isAsc
	     * @return
	     */
	    public  Shop orderbySendExpense(boolean isAsc){
	    	orderFields.add(new OrderField("sendExpense",isAsc ? "ASC":"DESC"));
	    	return this;
	    }
	    /**
	     * 按評分排序
	     * @param isAsc
	     * @return
	     */
	    public  Shop orderbyRank(boolean isAsc){
	    	orderFields.add(new OrderField("rank", isAsc ? "ASC":"DESC"));
	    	return this;
	    }
	    
    
}