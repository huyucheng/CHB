package com.chris.hunger.Bean;

import java.util.List;

/**
 * Created by huyucheng on 2016/3/22.
 */
public class CollectionBean {


    /**
     * pageSize : 5
     * start : 0
     * size : 5
     * total : 0
     * totalPage : 0
     * request : null
     * id : 1
     * shopid : 1
     * userid : 1
     * shop : {"pageSize":5,"start":0,"size":5,"total":0,"totalPage":0,"request":null,"id":1,"username":"chen","password":"chen","phone":"13893767729","address":"斜塘翰林缘12幢1210室","shopphoto":"https://fuss10.elemecdn.com/1/63/d8912e4fc90e7b58253f4bc8718ecjpeg.jpeg?imageMogr2/thumbnail/95x95/format/webp/quality/85","axisX":null,"axisY":null,"isServing":0,"sendtime":null,"registertime":1457502365000,"updatetime":null,"shoptype":1,"minconsume":null,"sendexpense":3,"email":null,"qrcode":null,"shopmessage":null,"telephone":null,"identify":null,"license":"http://www.sucaihuo.com/modals/0/8/demo/images/141201112113177.jpg","name":"陈师傅炒饭","distance":null,"curmonthsales":null,"businessstarttime":null,"businessendtime":null,"rank":null,"examinetyype":1,"saleCount":0,"orderFields":[]}
     */

    private List<FavouriteEntity> favourite;

    public void setFavourite(List<FavouriteEntity> favourite) {
        this.favourite = favourite;
    }

    public List<FavouriteEntity> getFavourite() {
        return favourite;
    }

    public static class FavouriteEntity {
        private int pageSize;
        private int start;
        private int size;
        private int total;
        private int totalPage;
        private Object request;
        private int id;
        private int shopid;
        private int userid;
        /**
         * pageSize : 5
         * start : 0
         * size : 5
         * total : 0
         * totalPage : 0
         * request : null
         * id : 1
         * username : chen
         * password : chen
         * phone : 13893767729
         * address : 斜塘翰林缘12幢1210室
         * shopphoto : https://fuss10.elemecdn.com/1/63/d8912e4fc90e7b58253f4bc8718ecjpeg.jpeg?imageMogr2/thumbnail/95x95/format/webp/quality/85
         * axisX : null
         * axisY : null
         * isServing : 0
         * sendtime : null
         * registertime : 1457502365000
         * updatetime : null
         * shoptype : 1
         * minconsume : null
         * sendexpense : 3.0
         * email : null
         * qrcode : null
         * shopmessage : null
         * telephone : null
         * identify : null
         * license : http://www.sucaihuo.com/modals/0/8/demo/images/141201112113177.jpg
         * name : 陈师傅炒饭
         * distance : null
         * curmonthsales : null
         * businessstarttime : null
         * businessendtime : null
         * rank : null
         * examinetyype : 1
         * saleCount : 0
         * orderFields : []
         */

        private ShopEntity shop;

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public void setRequest(Object request) {
            this.request = request;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setShopid(int shopid) {
            this.shopid = shopid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public void setShop(ShopEntity shop) {
            this.shop = shop;
        }

        public int getPageSize() {
            return pageSize;
        }

        public int getStart() {
            return start;
        }

        public int getSize() {
            return size;
        }

        public int getTotal() {
            return total;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public Object getRequest() {
            return request;
        }

        public int getId() {
            return id;
        }

        public int getShopid() {
            return shopid;
        }

        public int getUserid() {
            return userid;
        }

        public ShopEntity getShop() {
            return shop;
        }

        public static class ShopEntity {
            private int pageSize;
            private int start;
            private int size;
            private int total;
            private int totalPage;
            private Object request;
            private int id;
            private String username;
            private String password;
            private String phone;
            private String address;
            private String shopphoto;
            private Object axisX;
            private Object axisY;
            private int isServing;
            private Object sendtime;
            private long registertime;
            private Object updatetime;
            private int shoptype;
            private Object minconsume;
            private double sendexpense;
            private Object email;
            private Object qrcode;
            private Object shopmessage;
            private Object telephone;
            private Object identify;
            private String license;
            private String name;
            private Object distance;
            private Object curmonthsales;
            private Object businessstarttime;
            private Object businessendtime;
            private Object rank;
            private int examinetyype;
            private int saleCount;
            private List<?> orderFields;

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public void setStart(int start) {
                this.start = start;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public void setTotalPage(int totalPage) {
                this.totalPage = totalPage;
            }

            public void setRequest(Object request) {
                this.request = request;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public void setShopphoto(String shopphoto) {
                this.shopphoto = shopphoto;
            }

            public void setAxisX(Object axisX) {
                this.axisX = axisX;
            }

            public void setAxisY(Object axisY) {
                this.axisY = axisY;
            }

            public void setIsServing(int isServing) {
                this.isServing = isServing;
            }

            public void setSendtime(Object sendtime) {
                this.sendtime = sendtime;
            }

            public void setRegistertime(long registertime) {
                this.registertime = registertime;
            }

            public void setUpdatetime(Object updatetime) {
                this.updatetime = updatetime;
            }

            public void setShoptype(int shoptype) {
                this.shoptype = shoptype;
            }

            public void setMinconsume(Object minconsume) {
                this.minconsume = minconsume;
            }

            public void setSendexpense(double sendexpense) {
                this.sendexpense = sendexpense;
            }

            public void setEmail(Object email) {
                this.email = email;
            }

            public void setQrcode(Object qrcode) {
                this.qrcode = qrcode;
            }

            public void setShopmessage(Object shopmessage) {
                this.shopmessage = shopmessage;
            }

            public void setTelephone(Object telephone) {
                this.telephone = telephone;
            }

            public void setIdentify(Object identify) {
                this.identify = identify;
            }

            public void setLicense(String license) {
                this.license = license;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setDistance(Object distance) {
                this.distance = distance;
            }

            public void setCurmonthsales(Object curmonthsales) {
                this.curmonthsales = curmonthsales;
            }

            public void setBusinessstarttime(Object businessstarttime) {
                this.businessstarttime = businessstarttime;
            }

            public void setBusinessendtime(Object businessendtime) {
                this.businessendtime = businessendtime;
            }

            public void setRank(Object rank) {
                this.rank = rank;
            }

            public void setExaminetyype(int examinetyype) {
                this.examinetyype = examinetyype;
            }

            public void setSaleCount(int saleCount) {
                this.saleCount = saleCount;
            }

            public void setOrderFields(List<?> orderFields) {
                this.orderFields = orderFields;
            }

            public int getPageSize() {
                return pageSize;
            }

            public int getStart() {
                return start;
            }

            public int getSize() {
                return size;
            }

            public int getTotal() {
                return total;
            }

            public int getTotalPage() {
                return totalPage;
            }

            public Object getRequest() {
                return request;
            }

            public int getId() {
                return id;
            }

            public String getUsername() {
                return username;
            }

            public String getPassword() {
                return password;
            }

            public String getPhone() {
                return phone;
            }

            public String getAddress() {
                return address;
            }

            public String getShopphoto() {
                return shopphoto;
            }

            public Object getAxisX() {
                return axisX;
            }

            public Object getAxisY() {
                return axisY;
            }

            public int getIsServing() {
                return isServing;
            }

            public Object getSendtime() {
                return sendtime;
            }

            public long getRegistertime() {
                return registertime;
            }

            public Object getUpdatetime() {
                return updatetime;
            }

            public int getShoptype() {
                return shoptype;
            }

            public Object getMinconsume() {
                return minconsume;
            }

            public double getSendexpense() {
                return sendexpense;
            }

            public Object getEmail() {
                return email;
            }

            public Object getQrcode() {
                return qrcode;
            }

            public Object getShopmessage() {
                return shopmessage;
            }

            public Object getTelephone() {
                return telephone;
            }

            public Object getIdentify() {
                return identify;
            }

            public String getLicense() {
                return license;
            }

            public String getName() {
                return name;
            }

            public Object getDistance() {
                return distance;
            }

            public Object getCurmonthsales() {
                return curmonthsales;
            }

            public Object getBusinessstarttime() {
                return businessstarttime;
            }

            public Object getBusinessendtime() {
                return businessendtime;
            }

            public Object getRank() {
                return rank;
            }

            public int getExaminetyype() {
                return examinetyype;
            }

            public int getSaleCount() {
                return saleCount;
            }

            public List<?> getOrderFields() {
                return orderFields;
            }
        }
    }
}
