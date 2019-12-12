package com.example.demo.services;

import com.example.demo.mapper.PictureMapper;
import com.example.demo.models.PictureResponse;
import com.example.demo.models.PictureUploadSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.List;

// 首先在mysql命令行输入下面命令创建表：
//  create table picture (id integer not null auto_increment, user_id varchar(50), pic_category varchar(50), pic_desc varchar(100), pic_path varchar(100), create_time varchar(50), primary key (id)) default charset=utf8;
// 删除表命令是： drop table picture;

@Service
public class PictureServiceImpl implements PictureService {
    @Value("${file.rootPath}")
    private String ROOT_PATH;
    @Value("${file.sonPath}")
    private String SON_PATH;
    @Value("${server.port}")
    private String POST;
    // 对类成员变量、方法及构造函数进行标注,完成自动装配的工作
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String insertOnePicture(PictureUploadSend model, MultipartFile file) {
        if (file==null || file.isEmpty()){
            throw new NullPointerException("文件为空");
        }
        // 设置文件上传后的路径
        String filePath = ROOT_PATH + SON_PATH;
        // 为防止文件重名被覆盖，文件名取名为：时间戳
        String fileName = Calendar.getInstance().getTimeInMillis() + "." + model.getPic_format()/*prefix*/;
        // 创建中文路径
        File dest = new File(filePath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()){
            // 假如文件不存在即重新创建新的文件已防止异常发生
            dest.getParentFile().mkdirs();
        }
        try {
            //transferTo（dest）方法将上传文件写到服务器上指定的文件
            file.transferTo(dest);
            //保存到数据库表中
            String filePathNew = SON_PATH + fileName;
            String profilePhoto = saveUploadFile(model, filePathNew);
            System.out.println(profilePhoto);
            return profilePhoto;
        } catch (Exception e) {
            return dest.toString();
        }
    }

    private String saveUploadFile(PictureUploadSend model, String filePathNew) {
        // 在本地电脑测试
        //获取本机IP
        String host = null;
        try {
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            //LOG.error("get server host Exception e:", e);
        }

        // 在服务器端测试
//        String host = "改成你自己的IP或域名";

        String user_id = "" + model.getUser_id();
        String pic_category = "" + model.getPic_category();
        String pic_desc = "" + model.getPic_desc();
        String pic_path = "" + host + ":" + POST + filePathNew;
        String create_time = "" + System.currentTimeMillis(); // model.getCreate_time()

        jdbcTemplate.update("insert into picture(user_id, pic_category, pic_desc, pic_path, create_time) values(?, ?, ?, ?, ?)",
                user_id,
                pic_category,
                pic_desc,
                pic_path,
                create_time);

        return pic_path;
    }

    @Override
    public List<PictureResponse> getAllPictures(int page, int sum, int type) {
        String sql = "select * from picture";
        List<PictureResponse> responses = jdbcTemplate.query(sql, new PictureMapper());
        return responses;
    }
}
