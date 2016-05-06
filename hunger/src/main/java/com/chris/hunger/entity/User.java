package com.chris.hunger.entity;

import java.io.Serializable;
import java.util.Date;

public class User  implements Serializable{
    private Integer id;

    private String username;

    private String password;

    private String nickname;

    private String phone;

    private Integer province;

    private Integer city;

    private Integer area;

    private String personphoto;

    private Date registertime;

    private Date updatetime;

    private String address;

    private String email;

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getPersonphoto() {
        return personphoto;
    }

    public void setPersonphoto(String personphoto) {
        this.personphoto = personphoto;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password="
				+ password + ", nickname=" + nickname + ", phone=" + phone
				+ ", province=" + province + ", city=" + city + ", area="
				+ area + ", personphoto=" + personphoto + ", registertime="
				+ registertime + ", updatetime=" + updatetime + ", address="
				+ address + ", email=" + email + "]";
	}
    
    
}