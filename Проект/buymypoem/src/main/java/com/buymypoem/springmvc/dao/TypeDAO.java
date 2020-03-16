package com.buymypoem.springmvc.dao;

import com.buymypoem.springmvc.model.Composition;
import com.buymypoem.springmvc.model.Genre;
import com.buymypoem.springmvc.model.Type;
import com.buymypoem.springmvc.model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

public class TypeDAO {
    JdbcTemplate temp;

    public void setTemp(JdbcTemplate temp) {
        this.temp = temp;
    }

    private static final String sqlAllTypes = "select * from type";
    public List<Type> getAllTypes(){
        List<Type> typeList = temp.query(sqlAllTypes, new RowMapper<Type>() {
            public Type mapRow(ResultSet resultSet, int i) throws SQLException {
                Type t = new Type();
                t.setTypeID(resultSet.getInt("typeID"));
                t.setTitle(resultSet.getString("title"));
                t.setDescription(resultSet.getString("description"));
                return t;
            }
        });
        return typeList;
    }
}
