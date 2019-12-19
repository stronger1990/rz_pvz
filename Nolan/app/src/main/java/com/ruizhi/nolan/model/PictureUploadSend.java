package com.ruizhi.nolan.model;

public class PictureUploadSend {
    // 用户
    private String user_id;
    // 图片类别
    private String pic_category;
    // 图片描述
    private String pic_desc;
    // 图片创建时间
     private String create_time;
    // 经过Base64编码的图片字符串
    private String pic_content;
    // 图片格式
    private String pic_format;

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

    public String getPic_content() {
        return pic_content;
    }

    public void setPic_content(String pic_content) {
        this.pic_content = pic_content;
    }

    public String getPic_format() {
        return pic_format;
    }

    public void setPic_format(String pic_format) {
        this.pic_format = pic_format;
    }
}
