package com.chris.hunger.entity;

import java.io.Serializable;
import java.util.List;

public class Shopsecondcategory implements Serializable{
    private Integer id;

    private String name;
    
    private String name1;
    
    
    private List<Shop> shopList ;
    
    public List<Shop> getShopList() {
		return shopList;
	}

	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	private Shopfirstcategory shopfirstcategory;
    


	public Shopfirstcategory getShopfirstcategory() {
		return shopfirstcategory;
	}

	public void setShopfirstcategory(Shopfirstcategory shopfirstcategory) {
		this.shopfirstcategory = shopfirstcategory;
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
        this.name = name == null ? null : name.trim();
    }
}