package com.example.demo.util;

import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.IOException;

public class RZStringUtil {
    /**
     * Base64转化为MultipartFile
     *
     * @param data 前台传过来base64的编码
     * @param fileName 图片的文件名
     * @return
     */
    public static MultipartFile base64toMultipart(String data, String fileName) {
        try {
            String[] baseStrs = data.split(",");
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b = decoder.decodeBuffer(baseStrs[1]);
            for(int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            return new Base64MultipartFile(b, baseStrs[0] , fileName);
        } catch (IOException e) {
            //log.error("转换图片异常:");
            e.printStackTrace();
            return null;
        }
    }
}
