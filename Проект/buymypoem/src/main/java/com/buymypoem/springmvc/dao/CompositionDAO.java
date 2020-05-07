package com.buymypoem.springmvc.dao;

import com.buymypoem.springmvc.model.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CompositionDAO {

    public static final int PAGE_SIZE = 3;
    JdbcTemplate temp;

    private static final String sqlAddComposition = "insert into composition (title, description, likes, dislikes, text, authorID, genreID, typeID, status) " +
            "values(?,?,?,?,?,?,?,?,?)";
    private static final String sqlChangeCompositionStatusDraft = "Update Composition set status='В черновике' where compositionID=?";
    private static final String sqlChangeCompositionStatusPublished = "Update Composition set status='Опубликовано' where compositionID=?";
    private static final String sqlChangeCompositionStatusPreviewed = "Update Composition set status='На предпросмотре' where compositionID=?";
    private static final String sqlChangeCompositionStatusBought = "Update Composition set status='Преобретена' where compositionID=?";
    private static final String sqlChangeAuthor = "Update Composition set authorID=null, ownerID=? where compositionID=?";

    public void setTemp(JdbcTemplate temp) {
        this.temp = temp;
    }

    public int countCompositions(String choice, int id) {
        Map<String, String> sqlStrings = new HashMap<String, String>();
        sqlStrings.put("countComposition", "select count(*) from composition;");
        sqlStrings.put("countPublishComp", "select count(*) from composition " +
                "WHERE status='Опубликовано';");
        sqlStrings.put("countCompOfAuthor", "select count(*) from composition " +
                "WHERE status='Опубликовано' and authorID=" + getAuthorId(id));
        sqlStrings.put("countDrafts", "select count(*) from composition " +
                "WHERE status='В черновике' and authorID=" + getAuthorId(id));
        String sql = sqlStrings.get(choice);
        return temp.queryForObject(sql, Integer.class);
    }

    public List<Composition> getCompositions(int page, String sqlComposition, int id) {
        Map<String, String> sqlStrings = new HashMap<String, String>();

        sqlStrings.put("All", "select compositionID, title, description, likes, dislikes, login, photo, typeID, genreID,status " +
                "from author join composition on composition.authorID = author.authorID " +
                "join user on user.userID=author.userID limit ?," + PAGE_SIZE);

        sqlStrings.put("Published", "select compositionID, title, description, likes, dislikes, login, photo, typeID, genreID,status " +
                "from author join composition on composition.authorID = author.authorID " +
                "join user on user.userID=author.userID WHERE composition.status='Опубликовано' limit ? ," + PAGE_SIZE);

        sqlStrings.put("PublishedOfAuthor", "select compositionID, title, description, likes, dislikes, login, photo, typeID, genreID,status " +
                "from author " +
                "join composition on composition.authorID = author.authorID " +
                "join user on user.userID=author.userID " +
                "WHERE composition.status='Опубликовано' and user.userID=" + id +
                " limit ? ," + PAGE_SIZE);

        sqlStrings.put("Drafts", "select compositionID, title, description, likes, dislikes, login, photo, typeID, genreID,status " +
                "from author " +
                "join composition on composition.authorID = author.authorID " +
                "join user on user.userID=author.userID " +
                "WHERE composition.status='В черновике' and user.userID=" + id +
                " limit ? ," + PAGE_SIZE);

        sqlStrings.put("AllMyDrafts", "select compositionID, title, description, likes, dislikes, login, photo, typeID, genreID,status " +
                "from author " +
                "join composition on composition.authorID = author.authorID " +
                "join user on user.userID=author.userID " +
                "WHERE composition.status='В черновике' and 45!=? and user.userID=" + id);

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
                u.setPhoto(resultSet.getString("photo"));
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

    private int getAuthorId(int id) {
        try {
            String sql = "SELECT authorID FROM author WHERE userID=" + id;


            List<Author> aList = temp.query(sql, new RowMapper<Author>() {
                public Author mapRow(ResultSet resultSet, int i) throws SQLException {
                    Author a = new Author();
                    a.setAuthorID(resultSet.getInt("authorID"));
                    return a;
                }
            });

            return aList.get(0).getAuthorID();
        } catch (Exception e) {
            return 0;
        }
    }

    public Composition getCompositionByI(int id){
        String sql ="select *, genre.title as gtitle, type.title as ttitle from author join composition on composition.authorID = author.authorID join user on user.userID=author.userID " +
                "JOIN genre on genre.genreID=composition.genreID " +
                "JOIN type on type.typeID=composition.typeID " +
                "WHERE compositionID=?";

        try {
            List<Composition> compositionList = temp.query(sql, new Object[]{id}, new RowMapper<Composition>() {
                public Composition mapRow(ResultSet resultSet, int i) throws SQLException {
                    User u = new User();
                    Type t = new Type();
                    Genre g = new Genre();
                    Composition comp = new Composition();
                    comp.setCompositionID(resultSet.getInt("compositionID"));
                    comp.setTitle(resultSet.getString("title"));
                    comp.setText(resultSet.getString("text"));
                    comp.setStatus(resultSet.getString("status"));
                    comp.setLikes(resultSet.getInt("likes"));
                    comp.setDislikes(resultSet.getInt("dislikes"));
                    u.setLogin(resultSet.getString("login"));
                    u.setPhoto(resultSet.getString("photo"));
                    comp.setUser(u);
                    t.setTitle(resultSet.getString("ttitle"));
                    comp.setType(t);
                    g.setTitle(resultSet.getString("gtitle"));
                    comp.setGenre(g);
                    comp.setDescription(resultSet.getString("description"));
                    return comp;
                }
            });
            return compositionList.get(0);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public int getCountFindCompositions(int typeId, int genreId, int authorId, String title) {
        String sqlString="select count(*) from composition WHERE status='Опубликовано' ";

        ArrayList<Object> params_list = new ArrayList<Object>();
        ArrayList<Integer> types_list = new ArrayList<Integer>();

        if(typeId!=1){
            sqlString+=" and composition.typeID=? ";
            params_list.add(typeId);
            types_list.add(Types.INTEGER);
        }

        if(genreId!=1){
            sqlString+=" and composition.genreID=? ";
            params_list.add(genreId);
            types_list.add(Types.INTEGER);
        }

        if(authorId!=0){
            sqlString+=" and composition.authorID=? ";
            params_list.add(authorId);
            types_list.add(Types.INTEGER);
        }

        if(!(title.equals(""))){
            sqlString+=" and composition.title=? ";
            params_list.add(title);
            types_list.add(Types.VARCHAR);
        }

        Object[] params=params_list.toArray();
        int[] types=new int[types_list.size()];
        for(int i = 0; i < types_list.size(); i++) types[i] = types_list.get(i);

        return temp.queryForObject(sqlString, params, types, Integer.class);
    }

    public List<Composition> foundCompositions(int page, int typeId, int genreId, int authorId, String title) {

        String sqlString = "select compositionID, title, description, likes, dislikes, login, photo, typeID, genreID,status " +
                "from author join composition on composition.authorID = author.authorID " +
                "join user on user.userID=author.userID WHERE composition.status='Опубликовано' ";

        ArrayList<Object> params_list = new ArrayList<Object>();
        ArrayList<Integer> types_list = new ArrayList<Integer>();

        if(typeId!=1){
            sqlString+=" and composition.typeID=? ";
            params_list.add(typeId);
            types_list.add(Types.INTEGER);
        }

        if(genreId!=1){
            sqlString+=" and composition.genreID=? ";
            params_list.add(genreId);
            types_list.add(Types.INTEGER);
        }

        if(authorId!=0){
            sqlString+=" and composition.authorID=? ";
            params_list.add(authorId);
            types_list.add(Types.INTEGER);
        }

        if(!(title.equals(""))){
            sqlString+=" and composition.title=? ";
            params_list.add(title);
            types_list.add(Types.VARCHAR);
        }

        sqlString +=" limit ? ," + PAGE_SIZE;
        params_list.add(PAGE_SIZE * (page - 1));
        types_list.add(Types.INTEGER);

        Object[] params=params_list.toArray();
        int[] types=new int[types_list.size()];
        for(int i = 0; i < types_list.size(); i++) types[i] = types_list.get(i);

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
                u.setPhoto(resultSet.getString("photo"));
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

    public int changeStatus(int id, String checkString){
        Map<String, String> sqlStrings = new HashMap<String, String>();
        sqlStrings.put("draft", sqlChangeCompositionStatusDraft);
        sqlStrings.put("previewed", sqlChangeCompositionStatusPreviewed);
        sqlStrings.put("published", sqlChangeCompositionStatusPublished);
        sqlStrings.put("bought", sqlChangeCompositionStatusBought);
        Object[] params = {id};
        int[] types = {4};
        return temp.update(sqlStrings.get(checkString), params, types);
    }

    public int changeAuthor(int new_owner_id, int comp_id){
        Object[] params = {new_owner_id,comp_id};
        int[] types = {4,4};
        return temp.update(sqlChangeAuthor, params, types);
    }
}