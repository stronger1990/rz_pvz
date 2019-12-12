package com.example.demo.mapper;

import com.example.demo.models.PictureResponse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

// Picture表的字段：user_id, pic_category, pic_desc, pic_path, create_time
public class PictureMapper implements RowMapper<PictureResponse> {
    @Override
    public PictureResponse mapRow(ResultSet resultSet, int i) throws SQLException {
        PictureResponse response = new PictureResponse();
        response.setUser_id(resultSet.getString("user_id"));
        response.setPic_category(resultSet.getString("pic_category"));
        response.setPic_desc(resultSet.getString("pic_desc"));
        response.setPic_path(resultSet.getString("pic_path"));
        response.setCreate_time(resultSet.getString("create_time"));

        return response;
    }
}
