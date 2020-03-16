package com.buymypoem.springmvc.dao;

import com.buymypoem.springmvc.model.Genre;
import com.buymypoem.springmvc.model.Type;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class GenreDAO {
    JdbcTemplate temp;

    public void setTemp(JdbcTemplate temp) {
        this.temp = temp;
    }

    private static final String sqlAllGenres = "select * from genre";
    public List<Genre> getAllGenres(){
        List<Genre> genreList = temp.query(sqlAllGenres, new RowMapper<Genre>() {
            public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
                Genre g = new Genre();
                g.setGenreID(resultSet.getInt("genreID"));
                g.setTitle(resultSet.getString("title"));
                g.setDescription(resultSet.getString("description"));
                return g;
            }
        });
        return genreList;
    }
}
