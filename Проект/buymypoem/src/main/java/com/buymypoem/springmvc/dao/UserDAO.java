package com.buymypoem.springmvc.dao;

import com.buymypoem.springmvc.model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

public class UserDAO {
    JdbcTemplate temp;

    public void setTemp(JdbcTemplate temp) {
        this.temp = temp;
    }

    public User getUserByLogin(String login){
        String sql ="Select * from user where login=?";
         //Object[] params = {login};
        //int[] types = {Types.VARCHAR};
        try {
            return temp.queryForObject(sql, new  Object[]{login}, new BeanPropertyRowMapper<User>(User.class));
        }catch (Exception e){
            return null;
        }
    }
}
