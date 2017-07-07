package com.wjk2288.liangbin.activity.shop.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/6.
 */

public class TypeBean {


    private MetaBean meta;
    private int version;
    private DataBean data;

    public MetaBean getMeta() {
        return meta;
    }

    public void setMeta(MetaBean meta) {
        this.meta = meta;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class MetaBean {
        /**
         * status : 0
         * server_time : 2017-07-06 16:45:13
         * account_id : 0
         * cost : 0.02865195274353
         * errdata : null
         * errmsg :
         */

        private int status;
        private String server_time;
        private int account_id;
        private double cost;
        private Object errdata;
        private String errmsg;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getServer_time() {
            return server_time;
        }

        public void setServer_time(String server_time) {
            this.server_time = server_time;
        }

        public int getAccount_id() {
            return account_id;
        }

        public void setAccount_id(int account_id) {
            this.account_id = account_id;
        }

        public double getCost() {
            return cost;
        }

        public void setCost(double cost) {
            this.cost = cost;
        }

        public Object getErrdata() {
            return errdata;
        }

        public void setErrdata(Object errdata) {
            this.errdata = errdata;
        }

        public String getErrmsg() {
            return errmsg;
        }

        public void setErrmsg(String errmsg) {
            this.errmsg = errmsg;
        }
    }


    public static class DataBean {


        private boolean has_more;
        private int num_items;
        private List<ItemsBean> items;

        public boolean isHas_more() {
            return has_more;
        }

        public void setHas_more(boolean has_more) {
            this.has_more = has_more;
        }

        public int getNum_items() {
            return num_items;
        }

        public void setNum_items(int num_items) {
            this.num_items = num_items;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {



            private String cat_id;
            private String cat_name;
            private String cover_img;
            private String new_cover_img;
            private String cover_new_img;
            private List<List<SecondBean>> second;

            public String getCat_id() {
                return cat_id;
            }

            public void setCat_id(String cat_id) {
                this.cat_id = cat_id;
            }

            public String getCat_name() {
                return cat_name;
            }

            public void setCat_name(String cat_name) {
                this.cat_name = cat_name;
            }

            public String getCover_img() {
                return cover_img;
            }

            public void setCover_img(String cover_img) {
                this.cover_img = cover_img;
            }

            public String getNew_cover_img() {
                return new_cover_img;
            }

            public void setNew_cover_img(String new_cover_img) {
                this.new_cover_img = new_cover_img;
            }

            public String getCover_new_img() {
                return cover_new_img;
            }

            public void setCover_new_img(String cover_new_img) {
                this.cover_new_img = cover_new_img;
            }

            public List<List<SecondBean>> getSecond() {
                return second;
            }

            public void setSecond(List<List<SecondBean>> second) {
                this.second = second;
            }

            public static class SecondBean {
                /**
                 * cat_id : 46
                 * cat_name : 装饰摆件
                 * cover_img :
                 * new_cover_img :
                 * cover_new_img :
                 */

                private String cat_id;
                private String cat_name;
                private String cover_img;
                private String new_cover_img;
                private String cover_new_img;

                public String getCat_id() {
                    return cat_id;
                }

                public void setCat_id(String cat_id) {
                    this.cat_id = cat_id;
                }

                public String getCat_name() {
                    return cat_name;
                }

                public void setCat_name(String cat_name) {
                    this.cat_name = cat_name;
                }

                public String getCover_img() {
                    return cover_img;
                }

                public void setCover_img(String cover_img) {
                    this.cover_img = cover_img;
                }

                public String getNew_cover_img() {
                    return new_cover_img;
                }

                public void setNew_cover_img(String new_cover_img) {
                    this.new_cover_img = new_cover_img;
                }

                public String getCover_new_img() {
                    return cover_new_img;
                }

                public void setCover_new_img(String cover_new_img) {
                    this.cover_new_img = cover_new_img;
                }
            }
        }
    }
}
