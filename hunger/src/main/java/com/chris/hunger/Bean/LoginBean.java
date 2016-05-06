package com.chris.hunger.Bean;

/**
 * Created by huyucheng on 2016/3/21.
 */
public class LoginBean {

    /**
     * token : DF71DB503452E822296C6E5ECF5317D6
     * status : success
     * user : {"pageSize":5,"start":0,"size":5,"total":0,"totalPage":0,"request":null,"id":11,"username":"123","password":"123","nickname":null,"phone":null,"province":null,"city":null,"area":null,"personphoto":null,"registertime":null,"updatetime":null,"address":null,"email":null}
     */

    private String token;
    private String status;
    /**
     * pageSize : 5
     * start : 0
     * size : 5
     * total : 0
     * totalPage : 0
     * request : null
     * id : 11
     * username : 123
     * password : 123
     * nickname : null
     * phone : null
     * province : null
     * city : null
     * area : null
     * personphoto : null
     * registertime : null
     * updatetime : null
     * address : null
     * email : null
     */

    private UserEntity user;

    public void setToken(String token) {
        this.token = token;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public String getStatus() {
        return status;
    }

    public UserEntity getUser() {
        return user;
    }

    public static class UserEntity {
        private int pageSize;
        private int start;
        private int size;
        private int total;
        private int totalPage;
        private Object request;
        private int id;
        private String username;
        private String password;
        private Object nickname;
        private Object phone;
        private Object province;
        private Object city;
        private Object area;
        private Object personphoto;
        private Object registertime;
        private Object updatetime;
        private Object address;
        private Object email;

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

        public void setNickname(Object nickname) {
            this.nickname = nickname;
        }

        public void setPhone(Object phone) {
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

        public void setPersonphoto(Object personphoto) {
            this.personphoto = personphoto;
        }

        public void setRegistertime(Object registertime) {
            this.registertime = registertime;
        }

        public void setUpdatetime(Object updatetime) {
            this.updatetime = updatetime;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public void setEmail(Object email) {
            this.email = email;
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

        public Object getNickname() {
            return nickname;
        }

        public Object getPhone() {
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

        public Object getPersonphoto() {
            return personphoto;
        }

        public Object getRegistertime() {
            return registertime;
        }

        public Object getUpdatetime() {
            return updatetime;
        }

        public Object getAddress() {
            return address;
        }

        public Object getEmail() {
            return email;
        }
    }
}
