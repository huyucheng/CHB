package com.chris.hunger.Bean;

import java.util.List;

/**
 * Created by huyucheng on 2016/3/16.
 */
public class AddressBean{

    /**
     * addresssList : [{"pageSize":5,"start":0,"size":5,"total":0,"totalPage":0,"request":null,"id":11,"phone":"67664","province":null,"city":null,"area":null,"address":"多了糯","userid":1,"name":"逗你","sex":"先生","gpsaddress":null,"ordernum":null},{"pageSize":5,"start":0,"size":5,"total":0,"totalPage":0,"request":null,"id":12,"phone":"13063709455","province":null,"city":null,"area":null,"address":"东南811","userid":1,"name":"Mook","sex":"先生","gpsaddress":null,"ordernum":null},{"pageSize":5,"start":0,"size":5,"total":0,"totalPage":0,"request":null,"id":13,"phone":"13063709455","province":null,"city":null,"area":null,"address":"益达傻逼","userid":1,"name":"胡育诚","sex":"先生","gpsaddress":null,"ordernum":null}]
     * success : 1
     */

    private int success;
    /**
     * pageSize : 5
     * start : 0
     * size : 5
     * total : 0
     * totalPage : 0
     * request : null
     * id : 11
     * phone : 67664
     * province : null
     * city : null
     * area : null
     * address : 多了糯
     * userid : 1
     * name : 逗你
     * sex : 先生
     * gpsaddress : null
     * ordernum : null
     */

    private List<AddresssListEntity> addresssList;

    public void setSuccess(int success) {
        this.success = success;
    }

    public void setAddresssList(List<AddresssListEntity> addresssList) {
        this.addresssList = addresssList;
    }

    public int getSuccess() {
        return success;
    }

    public List<AddresssListEntity> getAddresssList() {
        return addresssList;
    }

    public static class AddresssListEntity {
        private int pageSize;
        private int start;
        private int size;
        private int total;
        private int totalPage;
        private Object request;
        private int id;
        private String phone;
        private Object province;
        private Object city;
        private Object area;
        private String address;
        private int userid;
        private String name;
        private String sex;
        private Object gpsaddress;
        private Object ordernum;

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

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setProvince(Object province) {
            this.province = province;
        }

        public void setCity(Object city) {
            this.city = city;
        }

        public void setArea(Object area) {
            this.area = area;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public void setGpsaddress(Object gpsaddress) {
            this.gpsaddress = gpsaddress;
        }

        public void setOrdernum(Object ordernum) {
            this.ordernum = ordernum;
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

        public String getPhone() {
            return phone;
        }

        public Object getProvince() {
            return province;
        }

        public Object getCity() {
            return city;
        }

        public Object getArea() {
            return area;
        }

        public String getAddress() {
            return address;
        }

        public int getUserid() {
            return userid;
        }

        public String getName() {
            return name;
        }

        public String getSex() {
            return sex;
        }

        public Object getGpsaddress() {
            return gpsaddress;
        }

        public Object getOrdernum() {
            return ordernum;
        }
    }
}
