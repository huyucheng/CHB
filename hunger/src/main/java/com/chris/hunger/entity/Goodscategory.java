	package com.chris.hunger.entity;

	import java.io.Serializable;

	public class Goodscategory  implements Serializable{
		private Integer id;

		private String name;

		
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

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}