package com.chris.hunger.entity;

public class Sendperson {
    private Integer id;

    private Integer personstatus;

    private String name;

    private String phone;

    private Integer sex;
    private Integer shopid;
    public Integer getShopid() {
		return shopid;
	}

	public void setShopid(Integer shopid) {
		this.shopid = shopid;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPersonstatus() {
        return personstatus;
    }

    public void setPersonstatus(Integer personstatus) {
        this.personstatus = personstatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}