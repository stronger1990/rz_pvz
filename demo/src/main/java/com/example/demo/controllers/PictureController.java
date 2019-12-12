package com.example.demo.controllers;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.models.PictureGetAllSend;
import com.example.demo.models.PictureResponse;
import com.example.demo.models.PictureUploadSend;
import com.example.demo.services.PictureService;
import com.example.demo.util.RZStringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PictureController {
    // 使用日志打印：https://blog.csdn.net/u014248473/article/details/88072072
    private static final Log log = LogFactory.getLog(PictureController.class);

    @Autowired
    private PictureService pictureService;

    // 作用是将controller的方法返回的对象通过适当的转换器转换为指定的格式之后,写入到response对象的body区
    @ResponseBody
    // 配置访问POST
    @RequestMapping(value = "/api/pic_insert", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String insertPicture(@RequestBody PictureUploadSend pictureSend) {
        // 返回到前端的JSON数据
        JSONObject result = new JSONObject();

        if (pictureSend == null) {
            result.put("message", "fail");
            return result.toJSONString();
        }

        String userName = pictureSend.getUser_id();
        String picBase64 = pictureSend.getPic_content();
        String picFormat = pictureSend.getPic_format();

        if (userName==null || userName.isEmpty() || picBase64==null || picBase64.isEmpty()){
            result.put("message", "fail");
            return result.toJSONString();
        }

        String picPath = pictureService.insertOnePicture(pictureSend, RZStringUtil.base64toMultipart(picBase64, picFormat));

        if (picPath==null || picPath.isEmpty()) {
            result.put("message", "fail");
            return result.toJSONString();
        }

        result.put("message", "success");
        result.put("Path", picPath);

        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value = "/api/pic_getall", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String getAllPictures(@RequestBody PictureGetAllSend send) {
        JSONObject result = new JSONObject();

        if (send == null || send.getUser_id()==null || send.getUser_id().isEmpty()) {
            result.put("message", "fail");
            return result.toJSONString();
        }

        List<PictureResponse> responses = pictureService.getAllPictures(0,0,0);

        result.put("message", "success");
        result.put("data", responses);


        return result.toJSONString();
    }
}
