package com.ruizhi.nolan.config;

public class Appconfig {
    private static String baseUrl = "https://改成你自己的接口.com";
    private static String allPicsUrl = "https://改成你自己的接口/api/pic_getall";

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static String getAllPicsUrl() {
        return allPicsUrl;
    }
}
