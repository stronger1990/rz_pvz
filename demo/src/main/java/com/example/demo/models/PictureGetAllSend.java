package com.example.demo.models;

public class PictureGetAllSend {
    // 用户
    private String user_id;
    // 图片类别
    private String pic_category;
    // 页数-暂时没用到，等要做分页时候再做
    private String page;
    // 每次请求的数量-暂时没用到，等要做分页时候再做
    private String sum;

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

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }
}
