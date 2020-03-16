package com.buymypoem.springmvc.dao;

import com.buymypoem.springmvc.model.User;
import com.google.inject.internal.asm.$Type;
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

    public int getAuthorId(int id){
        try {
            String sql = "SELECT authorID FROM author WHERE userID=" + id;
            return temp.queryForObject(sql, Integer.class);
        }catch (Exception e){
            return 0;
        }
    }

    public int insertUser(User user) {
        String dateRegisterdate=new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        String dateBirthdate=new SimpleDateFormat("yyyy-MM-dd").format(user.getBirthdate());
        String sql = "insert into user (login, password, email,birthdate, registerdate, role) values (?,?,?,?,?,?);";
        Object[] params = {user.getLogin(), user.getPassword().hashCode(), user.getEmail(), dateRegisterdate, dateBirthdate, user.getRole()};
        int[] types = {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.DATE, Types.DATE, Types.VARCHAR};
        temp.update(sql,params,types);

        user = getUserByLogin(user.getLogin());

        if (user.getRole().equals("Author")){
            insertAuthor(user.getUserID());
        }
        return user.getUserID();
    }

    public int insertAuthor(int id) {
        String sqlAuthor="insert into author (userId) VALUES  (?);";
        return temp.update(sqlAuthor, new  Object[]{id}, new int[]{Types.INTEGER});
    }
}
