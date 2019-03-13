package com.bawei.demo.demo2;

import java.util.List;

public class User {

    public boolean error;
    public List<ResultsBean> results;

    public class ResultsBean {

        public String _id;
        public String createdAt;
        public String desc;
        public String publishedAt;
        public String source;
        public String type;
        public String url;
        public boolean used;
        public String who;
    }
}
