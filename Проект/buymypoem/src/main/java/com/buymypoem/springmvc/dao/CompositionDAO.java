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
import java.util.List;

public class CompositionDAO {

    public static final int PAGE_SIZE = 2;
    JdbcTemplate temp;

    public void setTemp(JdbcTemplate temp) {
        this.temp = temp;
    }

    public int selectAllComposition(){
        String sql = "select count(*) from composition;";
        return temp.queryForObject(sql, Integer.class);
    }

    public int selectPublishedComposition(){
        String sql = "select count(*) from composition WHERE status='Опубликовано';";
        return temp.queryForObject(sql, Integer.class);
    }


    public List<Composition> getAllCompositions(int page) {
        int first = PAGE_SIZE * (page - 1);
        String sql2 = "select compositionID, title, description, likes, dislikes, login, typeID, genreID,status " +
                "from author " +
                "join composition on composition.authorID = author.authorID " +
                "join user on user.userID=author.userID limit " + first + "," + PAGE_SIZE;

        List<Composition> compositionList = temp.query(sql2, new RowMapper<Composition>() {
            public Composition mapRow(ResultSet resultSet, int i) throws SQLException {
                User u = new User();
                Type t = new Type();
                Genre g = new Genre();
                Composition comp = new Composition();
                comp.setCompositionID(resultSet.getInt("compositionID"));
                comp.setTitle(resultSet.getString("title"));
                comp.setStatus(resultSet.getString("status"));
                comp.setLikes(resultSet.getInt("likes"));
                comp.setDislikes(resultSet.getInt("dislikes"));
                u.setLogin(resultSet.getString("login"));
                comp.setUser(u);
                t.setTypeID(resultSet.getInt("typeID"));
                comp.setType(t);
                g.setGenreID(resultSet.getInt("genreID"));
                comp.setGenre(g);
                comp.setDescription(resultSet.getString("description"));
                return comp;
            }
        });
        for (Composition comp : compositionList) {
            String sqlType = "SELECT * FROM type where typeID=?";
            Type type = temp.queryForObject(sqlType, new Object[]{comp.getType().getTypeID()}, new BeanPropertyRowMapper<Type>(Type.class));
            comp.setType(type);

            String sqlGenre = "SELECT * FROM Genre where genreID=?";
            Genre genre = temp.queryForObject(sqlGenre, new Object[]{comp.getGenre().getGenreID()}, new BeanPropertyRowMapper<Genre>(Genre.class));
            comp.setGenre(genre);
        }
        return compositionList;
    }


    public List<Composition> getAllCompositionsForUser(int page) {
        int first = PAGE_SIZE * (page - 1);
        String sql2 = "select compositionID, title, description, likes, dislikes, login, typeID, genreID,status " +
                "from author " +
                "join composition on composition.authorID = author.authorID " +
                "join user on user.userID=author.userID WHERE composition.status='Опубликовано' limit " + first + "," + PAGE_SIZE;

        List<Composition> compositionList = temp.query(sql2, new RowMapper<Composition>() {
            public Composition mapRow(ResultSet resultSet, int i) throws SQLException {
                User u = new User();
                Type t = new Type();
                Genre g = new Genre();
                Composition comp = new Composition();
                comp.setCompositionID(resultSet.getInt("compositionID"));
                comp.setTitle(resultSet.getString("title"));
                comp.setStatus(resultSet.getString("status"));
                comp.setLikes(resultSet.getInt("likes"));
                comp.setDislikes(resultSet.getInt("dislikes"));
                u.setLogin(resultSet.getString("login"));
                comp.setUser(u);
                t.setTypeID(resultSet.getInt("typeID"));
                comp.setType(t);
                g.setGenreID(resultSet.getInt("genreID"));
                comp.setGenre(g);
                comp.setDescription(resultSet.getString("description"));
                return comp;
            }
        });
        for (Composition comp : compositionList) {
            String sqlType = "SELECT * FROM type where typeID=?";
            Type type = temp.queryForObject(sqlType, new Object[]{comp.getType().getTypeID()}, new BeanPropertyRowMapper<Type>(Type.class));
            comp.setType(type);

            String sqlGenre = "SELECT * FROM Genre where genreID=?";
            Genre genre = temp.queryForObject(sqlGenre, new Object[]{comp.getGenre().getGenreID()}, new BeanPropertyRowMapper<Genre>(Genre.class));
            comp.setGenre(genre);
        }
        return compositionList;
    }
}