package com.chris.hunger.entity;

import java.sql.Timestamp;

/**
 * TbPhoneValidate entity. @author MyEclipse Persistence Tools
 */

public class PhoneValidate implements java.io.Serializable {

	// Fields

	private Integer id;
	private String phone;
	private String captcha;
	private Timestamp expire;

	// Constructors

	/** default constructor */
	public PhoneValidate() {
	}

	/** minimal constructor */
	public PhoneValidate(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public PhoneValidate(Integer id, String phone, String captcha,
			Timestamp expire) {
		this.id = id;
		this.phone = phone;
		this.captcha = captcha;
		this.expire = expire;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCaptcha() {
		return this.captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public Timestamp getExpire() {
		return this.expire;
	}

	public void setExpire(Timestamp expire) {
		this.expire = expire;
	}

}