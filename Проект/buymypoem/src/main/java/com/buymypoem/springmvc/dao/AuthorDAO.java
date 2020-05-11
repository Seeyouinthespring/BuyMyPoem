package com.buymypoem.springmvc.dao;

import com.buymypoem.springmvc.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

public class AuthorDAO {
    JdbcTemplate temp;
    int PAGE_SIZE = 3;

    public void setTemp(JdbcTemplate temp) {
        this.temp = temp;
    }

    public List<User> getAllAuthor(int page){
        String sql = "SELECT * FROM user WHERE role='Author' limit ?," + PAGE_SIZE;
        int first = PAGE_SIZE * (page - 1);
        Object[] params = {first};
        int[] types = {Types.INTEGER};

        return temp.query(sql, params, types, new RowMapper<User>() {
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User author = new User();
                author.setLogin(resultSet.getString("login"));
                author.setPhoto(resultSet.getString("photo"));
                author.setAbout(resultSet.getString("about"));
                author.setBirthdate(resultSet.getDate("Birthdate"));
                return author;
            }
        });
    }

    public int countPages(){
        String sql = "SELECT count(*) FROM user WHERE role='Author'";
        return temp.queryForObject(sql, Integer.class);
    }

    @Autowired CompositionDAO compositionDAO;

    public int countPagesAutorById(int id) {
        String sql = "SELECT count(*) FROM composition WHERE status='Опубликовано' and authorID=?";
        Object[] params = {compositionDAO.getAuthorId(id)};
        int[] types = {Types.INTEGER};
        return temp.queryForObject(sql, params, types, Integer.class);
    }
}
