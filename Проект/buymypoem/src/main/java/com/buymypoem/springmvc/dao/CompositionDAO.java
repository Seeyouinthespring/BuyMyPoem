package com.buymypoem.springmvc.dao;

import com.buymypoem.springmvc.model.Composition;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CompositionDAO {

    JdbcTemplate temp;

    public void setTemp(JdbcTemplate temp) {
        this.temp = temp;
    }

    public List<Composition> getAllCompositions(){
        return temp.query("select * from composition", new RowMapper<Composition>() {
            public Composition mapRow(ResultSet resultSet, int i) throws SQLException {
                Composition comp= new Composition();
                comp.setCompositionID(resultSet.getInt(1));
                comp.setTitle(resultSet.getString(2));
                comp.setLikes(resultSet.getInt(3));
                comp.setDislikes(resultSet.getInt(4));
                comp.setText(resultSet.getString(5));
                comp.setAuthorID(resultSet.getInt(6));
                comp.setGenreID(resultSet.getInt(7));
                comp.setTypeID(resultSet.getInt(8));
                comp.setStatus(resultSet.getString(9));
                return comp;
            }
        });
    }
}