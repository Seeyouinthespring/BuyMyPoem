package com.buymypoem.springmvc.dao;

import com.buymypoem.springmvc.model.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CompositionDAO {

    public static final int PAGE_SIZE = 2;
    JdbcTemplate temp;

    private static final String sqlAddComposition = "insert into composition (title, description, likes, dislikes, text, authorID, genreID, typeID, status) " +
            "values(?,?,?,?,?,?,?,?,?)";

    public void setTemp(JdbcTemplate temp) {
        this.temp = temp;
    }

    public int countCompositions(String choice, int id) {
        Map<String, String> sqlStrings = new HashMap<String, String>();
            sqlStrings.put("countComposition", "select count(*) from composition;");
            sqlStrings.put("countPublishComp", "select count(*) from composition WHERE status='Опубликовано';");
            sqlStrings.put("countCompOfAuthor", "select count(*) from composition WHERE status='Опубликовано' and authorID=" + new UserDAO().getAuthorId(id));
            sqlStrings.put("countDrafts", "select count(*) from composition WHERE status='В черновике' and authorID=" + new UserDAO().getAuthorId(id));
            int idU =new UserDAO().getAuthorId(id);
        String sql = sqlStrings.get(choice);
        return temp.queryForObject(sql, Integer.class);
    }

    public List<Composition> getCompositions(int page, String sqlComposition, int id) {
        Map<String, String> sqlStrings = new HashMap<String, String>();

        sqlStrings.put("All", "select compositionID, title, description, likes, dislikes, login, typeID, genreID,status " +
                                "from author join composition on composition.authorID = author.authorID " +
                                "join user on user.userID=author.userID limit ?," + PAGE_SIZE);

        sqlStrings.put("Published", "select compositionID, title, description, likes, dislikes, login, typeID, genreID,status " +
                                    "from author join composition on composition.authorID = author.authorID " +
                                    "join user on user.userID=author.userID WHERE composition.status='Опубликовано' limit ? ," + PAGE_SIZE);

        sqlStrings.put("PublishedOfAuthor", "select compositionID, title, description, likes, dislikes, login, typeID, genreID,status " +
                                            "from author " +
                                            "join composition on composition.authorID = author.authorID " +
                                            "join user on user.userID=author.userID " +
                                            "WHERE composition.status='Опубликовано' and user.userID="  + id +
                                            " limit ? ," + PAGE_SIZE);

        sqlStrings.put("Drafts", "select compositionID, title, description, likes, dislikes, login, typeID, genreID,status " +
                                "from author " +
                                "join composition on composition.authorID = author.authorID " +
                                "join user on user.userID=author.userID " +
                                "WHERE composition.status='В черновике' and user.userID=" + id +
                                " limit ? ," + PAGE_SIZE);

        String sqlString = sqlStrings.get(sqlComposition);


        int first = PAGE_SIZE * (page - 1);
        Object[] params = {first};
        int[] types = {Types.INTEGER};
        List<Composition> compositionList = temp.query(sqlString, params, types, new RowMapper<Composition>() {
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

    public int addComposition(Composition comp) {
        String sql = "SELECT authorID from author where userID=" + comp.getUser().getUserID() + "; ";

        List<Author> aList = temp.query(sql, new RowMapper<Author>() {
            public Author mapRow(ResultSet resultSet, int i) throws SQLException {
                Author a = new Author();
                a.setAuthorID(resultSet.getInt("authorID"));
                return a;
            }
        });
        Object[] params = {comp.getTitle(), comp.getDescription(), 0, 0, comp.getText(),
                aList.get(0).getAuthorID(), comp.getGenre().getGenreID(), comp.getType().getTypeID(), "В черновике"};
        int[] types = {Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.LONGVARCHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.VARCHAR};
        return temp.update(sqlAddComposition, params, types);
    }

}