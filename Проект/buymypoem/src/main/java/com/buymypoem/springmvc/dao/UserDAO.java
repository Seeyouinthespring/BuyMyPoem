package com.buymypoem.springmvc.dao;

import com.buymypoem.springmvc.model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Calendar;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.List;

public class UserDAO {
    JdbcTemplate temp;

    public void setTemp(JdbcTemplate temp) {
        this.temp = temp;
    }

    public User getUserByLogin(String login){
        String sql ="Select * from user where login=?";
        try {
            return temp.queryForObject(sql, new  Object[]{login}, new BeanPropertyRowMapper<User>(User.class));
        }catch (Exception e){
            return null;
        }
    }

    public int insertUser(User user){
        String dateRegisterdate=new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        String dateBirthdate=new SimpleDateFormat("yyyy-MM-dd").format(user.getBirthdate());
        String sql = "insert into user (login, password, email,birthdate, registerdate) values (?,?,?,?,?);";
        Object[] params = {user.getLogin(), user.getPassword(), user.getEmail(), dateBirthdate, dateRegisterdate};
        int[] types = {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.DATE, Types.DATE};
        return temp.update(sql,params,types);
    }
}
