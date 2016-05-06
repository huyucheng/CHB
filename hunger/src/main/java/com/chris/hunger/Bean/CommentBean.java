package com.chris.hunger.Bean;

import java.util.List;

/**
 * Created by huyucheng on 2016/3/18.
 */
public class CommentBean {
    /**
     * pageSize : 5
     * start : 0
     * size : 5
     * total : 0
     * totalPage : 0
     * request : null
     * id : 4
     * shopid : 4
     * shop : {"pageSize":5,"start":0,"size":5,"total":0,"totalPage":0,"request":null,"id":4,"username":null,"password":null,"phone":null,"city":null,"address":null,"shopphoto":null,"axisX":null,"axisY":null,"isServing":null,"sendtime":null,"registertime":null,"updatetime":null,"shoptype":null,"minconsume":null,"sendexpense":null,"email":null,"qrcode":null,"shopmessage":null,"telephone":null,"identify":null,"license":null,"name":null,"distance":null,"curmonthsales":null,"businessstarttime":null,"businessendtime":null,"rank":null,"examinetyype":null,"saleCount":0,"orderFields":[]}
     * goodsid : 4
     * orderid : 1
     * userid : 4
     * commenttime : 1458738532000
     * rank : 3
     * content : ll
     * appendComment : null
     * order : null
     * user : null
     */

    private List<ShopCommentListEntity> shopCommentList;

    public void setShopCommentList(List<ShopCommentListEntity> shopCommentList) {
        this.shopCommentList = shopCommentList;
    }

    public List<ShopCommentListEntity> getShopCommentList() {
        return shopCommentList;
    }

    public static class ShopCommentListEntity {
        private int pageSize;
        private int start;
        private int size;
        private int total;
        private int totalPage;
        private Object request;
        private int id;
        private int shopid;
        /**
         * pageSize : 5
         * start : 0
         * size : 5
         * total : 0
         * totalPage : 0
         * request : null
         * id : 4
         * username : null
         * password : null
         * phone : null
         * city : null
         * address : null
         * shopphoto : null
         * axisX : null
         * axisY : null
         * isServing : null
         * sendtime : null
         * registertime : null
         * updatetime : null
         * shoptype : null
         * minconsume : null
         * sendexpense : null
         * email : null
         * qrcode : null
         * shopmessage : null
         * telephone : null
         * identify : null
         * license : null
         * name : null
         * distance : null
         * curmonthsales : null
         * businessstarttime : null
         * businessendtime : null
         * rank : null
         * examinetyype : null
         * saleCount : 0
         * orderFields : []
         */

        private ShopEntity shop;
        private int goodsid;
        private int orderid;
        private int userid;
        private long commenttime;
        private int rank;
        private String content;
        private Object appendComment;
        private Object order;
        private Object user;

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

        public void setShop(ShopEntity shop) {
            this.shop = shop;
        }

        public void setGoodsid(int goodsid) {
            this.goodsid = goodsid;
        }

        public void setOrderid(int orderid) {
            this.orderid = orderid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public void setCommenttime(long commenttime) {
            this.commenttime = commenttime;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setAppendComment(Object appendComment) {
            this.appendComment = appendComment;
        }

        public void setOrder(Object order) {
            this.order = order;
        }

        public void setUser(Object user) {
            this.user = user;
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

        public ShopEntity getShop() {
            return shop;
        }

        public int getGoodsid() {
            return goodsid;
        }

        public int getOrderid() {
            return orderid;
        }

        public int getUserid() {
            return userid;
        }

        public long getCommenttime() {
            return commenttime;
        }

        public int getRank() {
            return rank;
        }

        public String getContent() {
            return content;
        }

        public Object getAppendComment() {
            return appendComment;
        }

        public Object getOrder() {
            return order;
        }

        public Object getUser() {
            return user;
        }

        public static class ShopEntity {
            private int pageSize;
            private int start;
            private int size;
            private int total;
            private int totalPage;
            private Object request;
            private int id;
            private Object username;
            private Object password;
            private Object phone;
            private Object city;
            private Object address;
            private Object shopphoto;
            private Object axisX;
            private Object axisY;
            private Object isServing;
            private Object sendtime;
            private Object registertime;
            private Object updatetime;
            private Object shoptype;
            private Object minconsume;
            private Object sendexpense;
            private Object email;
            private Object qrcode;
            private Object shopmessage;
            private Object telephone;
            private Object identify;
            private Object license;
            private Object name;
            private Object distance;
            private Object curmonthsales;
            private Object businessstarttime;
            private Object businessendtime;
            private Object rank;
            private Object examinetyype;
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

            public void setUsername(Object username) {
                this.username = username;
            }

            public void setPassword(Object password) {
                this.password = password;
            }

            public void setPhone(Object phone) {
                this.phone = phone;
            }

            public void setCity(Object city) {
                this.city = city;
            }

            public void setAddress(Object address) {
                this.address = address;
            }

            public void setShopphoto(Object shopphoto) {
                this.shopphoto = shopphoto;
            }

            public void setAxisX(Object axisX) {
                this.axisX = axisX;
            }

            public void setAxisY(Object axisY) {
                this.axisY = axisY;
            }

            public void setIsServing(Object isServing) {
                this.isServing = isServing;
            }

            public void setSendtime(Object sendtime) {
                this.sendtime = sendtime;
            }

            public void setRegistertime(Object registertime) {
                this.registertime = registertime;
            }

            public void setUpdatetime(Object updatetime) {
                this.updatetime = updatetime;
            }

            public void setShoptype(Object shoptype) {
                this.shoptype = shoptype;
            }

            public void setMinconsume(Object minconsume) {
                this.minconsume = minconsume;
            }

            public void setSendexpense(Object sendexpense) {
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

            public void setLicense(Object license) {
                this.license = license;
            }

            public void setName(Object name) {
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

            public void setExaminetyype(Object examinetyype) {
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

            public Object getUsername() {
                return username;
            }

            public Object getPassword() {
                return password;
            }

            public Object getPhone() {
                return phone;
            }

            public Object getCity() {
                return city;
            }

            public Object getAddress() {
                return address;
            }

            public Object getShopphoto() {
                return shopphoto;
            }

            public Object getAxisX() {
                return axisX;
            }

            public Object getAxisY() {
                return axisY;
            }

            public Object getIsServing() {
                return isServing;
            }

            public Object getSendtime() {
                return sendtime;
            }

            public Object getRegistertime() {
                return registertime;
            }

            public Object getUpdatetime() {
                return updatetime;
            }

            public Object getShoptype() {
                return shoptype;
            }

            public Object getMinconsume() {
                return minconsume;
            }

            public Object getSendexpense() {
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

            public Object getLicense() {
                return license;
            }

            public Object getName() {
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

            public Object getExaminetyype() {
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
    /**
     * pageSize : 5
     * start : 0
     * size : 5
     * total : 0
     * totalPage : 0
     * request : null
     * id : 1
     * shopid : 2
     * shop : null
     * goodsid : 2
     * orderid : 2
     * userid : 2
     * commenttime : 1418486400000
     * rank : 2
     * content : 2
     * appendComment : null
     */


}
