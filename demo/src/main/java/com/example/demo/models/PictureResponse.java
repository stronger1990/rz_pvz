package com.example.demo.models;

public class PictureResponse {
    // 用户
    private String user_id;
    // 图片类别
    private String pic_category;
    // 图片描述
    private String pic_desc;
    // 图片创建时间
     private String create_time;
    // 图片链接
    private String pic_path;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPic_category() {
        return pic_category;
    }

    public void setPic_category(String pic_category) {
        this.pic_category = pic_category;
    }

    public String getPic_desc() {
        return pic_desc;
    }

    public void setPic_desc(String pic_desc) {
        this.pic_desc = pic_desc;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getPic_path() {
        return pic_path;
    }

    public void setPic_path(String pic_path) {
        this.pic_path = pic_path;
    }
}
