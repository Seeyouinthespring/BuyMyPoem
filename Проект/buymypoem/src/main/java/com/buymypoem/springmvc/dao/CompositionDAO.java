package com.buymypoem.springmvc.dao;

import com.buymypoem.springmvc.model.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

public class CompositionDAO {

    public static final int PAGE_SIZE = 2;
    JdbcTemplate temp;

    private static final String sqlAllCompositions = "select compositionID, title, description, likes, dislikes, login, typeID, genreID,status " +
            "from author join composition on composition.authorID = author.authorID " +
            "join user on user.userID=author.userID limit ?," + PAGE_SIZE;
    private static final String sqlPublishedCompositions = "select compositionID, title, description, likes, dislikes, login, typeID, genreID,status " +
            "from author join composition on composition.authorID = author.authorID " +
            "join user on user.userID=author.userID WHERE composition.status='Опубликовано' limit ? ," + PAGE_SIZE;
    private static final String sqlAddComposition = "insert into composition (title, description, likes, dislikes, text, authorID, genreID, typeID, status) " +
            "values(?,?,?,?,?,?,?,?,?)";

    public void setTemp(JdbcTemplate temp) {
        this.temp = temp;
    }

    public int countCompositions(){
        String sql = "select count(*) from composition;";
        return temp.queryForObject(sql, Integer.class);
    }

    public int countPublishedCompositions(){
        String sql = "select count(*) from composition WHERE status='Опубликовано';";
        return temp.queryForObject(sql, Integer.class);
    }


    public List<Composition> getCompositions(int page, boolean isAll) {
        String sqlString;
        if (isAll) sqlString=sqlAllCompositions;
        else sqlString=sqlPublishedCompositions;

        int first = PAGE_SIZE * (page - 1);
        Object[] params = {first};
        int[] types = {Types.INTEGER};
        List<Composition> compositionList = temp.query(sqlString,params,types, new RowMapper<Composition>() {
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


    /*public List<Composition> getAllCompositionsForUser(int page) {
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
    }*/

    public int addComposition(Composition comp){
        String sql = "SELECT authorID from author where userID="+comp.getUser().getUserID()+"; ";

        List<Author> aList = temp.query(sql, new RowMapper<Author>() {
            public Author mapRow(ResultSet resultSet, int i) throws SQLException {
                Author a = new Author();
                a.setAuthorID(resultSet.getInt("authorID"));
                return a;
            }
        });
        Object[] params = {comp.getTitle(),comp.getDescription(),0,0,comp.getText(),
                        aList.get(0).getAuthorID(),comp.getGenre().getGenreID(),comp.getType().getTypeID(),"В черновике"};
        int[] types = {Types.VARCHAR, Types.VARCHAR,Types.INTEGER,Types.INTEGER, Types.LONGVARCHAR, Types.INTEGER,Types.INTEGER,Types.INTEGER,Types.VARCHAR};
        return temp.update(sqlAddComposition,params,types);
    }
}