package com.ruizhi.nolan.config;

public class Appconfig {
    private static String baseUrl = "https://XXX";
    private static String downloadUrl = "https://XXX/api/download";
    private static String allPicsUrl = "https://XXX/api/pic_getall";
    private static String uploadUrl = "https://XXX/api/pic_insert";

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static String getDownloadUrl() {
        return downloadUrl;
    }

    public static String getAllPicsUrl() {
        return allPicsUrl;
    }

    public static String getUploadUrl() {
        return uploadUrl;
    }
}
