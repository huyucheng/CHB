package com.chris.hunger.Bean;

import java.util.List;

/**
 * Created by huyucheng on 2016/3/21.
 */
public class ShopMenu {

    /**
     * pageSize : 5
     * start : 0
     * size : 5
     * total : 0
     * totalPage : 0
     * request : null
     * id : 1
     * name : 早餐类
     */

    private List<SfclistEntity> sfclist;
    private List<Integer> ssccount;
    /**
     * pageSize : 5
     * start : 0
     * size : 5
     * total : 0
     * totalPage : 0
     * request : null
     * id : 1
     * name : 品牌快餐
     * name1 : null
     * shopList : null
     * shopfirstcategory : {"pageSize":5,"start":0,"size":5,"total":0,"totalPage":0,"request":null,"id":1,"name":null}
     */

    private List<List<SsclistEntity>> ssclist;

    public void setSfclist(List<SfclistEntity> sfclist) {
        this.sfclist = sfclist;
    }

    public void setSsccount(List<Integer> ssccount) {
        this.ssccount = ssccount;
    }

    public void setSsclist(List<List<SsclistEntity>> ssclist) {
        this.ssclist = ssclist;
    }

    public List<SfclistEntity> getSfclist() {
        return sfclist;
    }

    public List<Integer> getSsccount() {
        return ssccount;
    }

    public List<List<SsclistEntity>> getSsclist() {
        return ssclist;
    }

    public static class SfclistEntity {
        private int pageSize;
        private int start;
        private int size;
        private int total;
        private int totalPage;
        private Object request;
        private int id;
        private String name;

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

        public void setName(String name) {
            this.name = name;
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

        public String getName() {
            return name;
        }
    }

    public static class SsclistEntity {
        private int pageSize;
        private int start;
        private int size;
        private int total;
        private int totalPage;
        private Object request;
        private int id;
        private String name;
        private Object name1;
        private Object shopList;
        /**
         * pageSize : 5
         * start : 0
         * size : 5
         * total : 0
         * totalPage : 0
         * request : null
         * id : 1
         * name : null
         */

        private ShopfirstcategoryEntity shopfirstcategory;

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

        public void setName(String name) {
            this.name = name;
        }

        public void setName1(Object name1) {
            this.name1 = name1;
        }

        public void setShopList(Object shopList) {
            this.shopList = shopList;
        }

        public void setShopfirstcategory(ShopfirstcategoryEntity shopfirstcategory) {
            this.shopfirstcategory = shopfirstcategory;
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

        public String getName() {
            return name;
        }

        public Object getName1() {
            return name1;
        }

        public Object getShopList() {
            return shopList;
        }

        public ShopfirstcategoryEntity getShopfirstcategory() {
            return shopfirstcategory;
        }

        public static class ShopfirstcategoryEntity {
            private int pageSize;
            private int start;
            private int size;
            private int total;
            private int totalPage;
            private Object request;
            private int id;
            private Object name;

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

            public void setName(Object name) {
                this.name = name;
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

            public Object getName() {
                return name;
            }
        }
    }
}
