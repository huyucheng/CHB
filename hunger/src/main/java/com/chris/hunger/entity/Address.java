package com.chris.hunger.entity;

import java.io.Serializable;

public class Address  implements Serializable{
	private Integer id;

	private String phone;

	private Integer province;

	private Integer city;

	private Integer area;

	private String address;

	private Integer userid;

	private String name;
	private String sex;
	private String gpsaddress;

	public String getSex() {
		return sex;
	}


	private Integer ordernum;


	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getGpsaddress() {
		return gpsaddress;
	}

	public void setGpsaddress(String gpsaddress) {
		this.gpsaddress = gpsaddress;
	}





	public Integer getOrdernum() {
		return ordernum;
	}

	public void setOrdernum(Integer ordernum) {
		this.ordernum = ordernum;
	}

	public Integer getId() {

		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}