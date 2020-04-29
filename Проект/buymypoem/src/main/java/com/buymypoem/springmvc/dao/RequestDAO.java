package com.buymypoem.springmvc.dao;

import com.buymypoem.springmvc.model.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class RequestDAO {

    public static final int PAGE_SIZE = 3;

    JdbcTemplate temp;
    public void setTemp(JdbcTemplate temp) {
        this.temp = temp;
    }

    private static final String sqlAllRequests="SELECT request.requestID, request.description, user.login, user.photo, request.publicationdate, request.deadline, request.cost, genre.title as gtitle, type.title as ttitle from request left JOIN genre on request.genreID=genre.genreID left JOIN type on request.typeID=type.typeID left join customer on request.customerID=customer.customerID left JOIN user on customer.userID=user.userID where ?>0 limit ?," + PAGE_SIZE;

    private static final String sqlRequestById="Select request.requestID, request.description, user.userID, user.login, user.photo, request.publicationdate, request.deadline, request.cost, genre.genreID, genre.title as gtitle, type.typeID, type.title as ttitle from request left JOIN genre on request.genreID=genre.genreID left JOIN type on request.typeID=type.typeID left join customer on request.customerID=customer.customerID left JOIN user on customer.userID=user.userID where request.requestID=?";
    private static final String sqlAllPersonalRequests="SELECT request.requestID, request.description, user.login, user.photo, request.publicationdate, request.deadline, request.cost," +
            " genre.title as gtitle, type.title as ttitle from request " +
            "left JOIN genre on request.genreID=genre.genreID " +
            "left JOIN type on request.typeID=type.typeID " +
            "left join customer on request.customerID=customer.customerID " +
            "left JOIN user on customer.userID=user.userID " +
            "where request.customerID=? limit ?," + PAGE_SIZE;
    private static final String sqlCountAllRequests="select count(*) from request where ?>0";
    private static final String sqlCountPersonalRequests="select count(*) from request where customerID=?";
    private static final String sqlAddRequest="insert into request (description, customerID, publicationdate, deadline, cost, genreID, typeID) values " +
            "(?,?,?,?,?,?,?)";
    private static final String sqlAddResponse="insert into authorrequest (authorID, requestID) values (?,?) ";
    private static final String sqlGetAllResponsesForRequest="SELECT user.userID,login,birthdate,about FROM authorrequest " +
            "left join author on author.authorID=authorrequest.authorID " +
            "left join user on author.userID=user.userID where requestID=?";
    private static final String sqlForCheckResponse="SELECT * FROM authorrequest WHERE authorID=? && requestID=?";
    private static final String sqlDeleteRequest="delete from request where requestID = ?";
    private static final String sqlDeleteAllResponses="delete from authorrequest where requestID = ?";

    public int countRequests(boolean isPersonal, int id){
        String sqlCountString;
        Object[] params = new Object[1];
        if (isPersonal) {
            sqlCountString=sqlCountPersonalRequests;
            params[0]=getCustomerId(id);
        }
        else {
            sqlCountString=sqlCountAllRequests;
            params[0]=100;
        }
        int[] types = {Types.INTEGER};
        return temp.queryForObject(sqlCountString, params, types, Integer.class);
    }

    public List<Request> getRequests(int page, boolean isPersonal, int id){
        int first = PAGE_SIZE * (page - 1);
        String sqlGetString;
        Object[] params = new Object[2];
        if (isPersonal){
            sqlGetString=sqlAllPersonalRequests;
            params[0]= getCustomerId(id);
        }
        else {
            sqlGetString=sqlAllRequests;
            params[0]=100;
        }
        params[1]= first;
        int[] types= {Types.INTEGER,Types.INTEGER};
        return temp.query(sqlGetString, params, types, new RowMapper<Request>() {
            public Request mapRow(ResultSet resultSet, int i) throws SQLException{
                User u = new User();
                Type t = new Type();
                Genre g = new Genre();
                Request r = new Request();
                r.setRequestID(resultSet.getInt("requestID"));
                r.setDescription(resultSet.getString("description"));
                u.setLogin(resultSet.getString("login"));
                u.setPhoto(resultSet.getString("photo"));
                r.setUser(u);
                r.setPublicationdate(resultSet.getDate("publicationdate"));
                r.setDeadline(resultSet.getDate("deadline"));
                r.setCost(resultSet.getFloat("cost"));
                t.setTitle(resultSet.getString("ttitle"));
                r.setType(t);
                g.setTitle(resultSet.getString("gtitle"));
                r.setGenre(g);
                return r;
            }
        });
    }

    public Request getRequestById(int id){
        Object[] params = {id};
        int[] types = {Types.INTEGER};

        return temp.queryForObject(sqlRequestById, params, types, (resultSet, i) -> {
            User u = new User();
            Type t = new Type();
            Genre g = new Genre();
            Request r = new Request();
            r.setRequestID(resultSet.getInt("requestID"));
            r.setDescription(resultSet.getString("description"));
            u.setUserID(resultSet.getInt("userID"));
            u.setLogin(resultSet.getString("login"));
            u.setPhoto(resultSet.getString("photo"));
            r.setUser(u);
            r.setPublicationdate(resultSet.getDate("publicationdate"));
            r.setDeadline(resultSet.getDate("deadline"));
            r.setCost(resultSet.getFloat("cost"));
            t.setTypeID(resultSet.getInt("typeID"));
            t.setTitle(resultSet.getString("ttitle"));
            r.setType(t);
            g.setGenreID(resultSet.getInt("genreID"));
            g.setTitle(resultSet.getString("gtitle"));
            r.setGenre(g);
            return r;
        });
    }

    public int getCustomerId(int id) {
        try {
            String sql = "SELECT customerID FROM customer WHERE userID=" + id;
            List<Customer> cList = temp.query(sql, new RowMapper<Customer>() {
                public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
                    Customer c = new Customer();
                    c.setCustomerID(resultSet.getInt("customerID"));
                    return c;
                }
            });
            return cList.get(0).getCustomerID();
        } catch (Exception e) {
            return 0;
        }
    }

    public int addRequest(Request request){
        String dateOfPublication=new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        String dateOfDeadline=new SimpleDateFormat("yyyy-MM-dd").format(request.getDeadline());
        Object[] params = {request.getDescription(), getCustomerId(request.getUser().getUserID()), dateOfPublication, dateOfDeadline,
                request.getCost(), request.getGenre().getGenreID(), request.getType().getTypeID()};
        int[] types = {Types.VARCHAR, Types.INTEGER, Types.DATE, Types.DATE, Types.FLOAT, Types.INTEGER, Types.INTEGER};
        return temp.update(sqlAddRequest,params,types);
    }

    public int addResponse(AuthorRequest areq){
        Object[] params = {getAuthorId(areq.getAuthorID()),areq.getRequestID()};
        int[] types = {Types.INTEGER, Types.INTEGER};
        return temp.update(sqlAddResponse,params,types);
    }

    public int getAuthorId(int id) {
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

    public List<User> getAllResponses(int id){
        Object[] params = {id};
        int[] types = {Types.INTEGER};
        return temp.query(sqlGetAllResponsesForRequest, params, types, new RowMapper<User>() {
            public User mapRow(ResultSet resultSet, int i) throws SQLException{
                User u = new User();
                u.setUserID(resultSet.getInt("userID"));
                u.setLogin(resultSet.getString("login"));
                u.setBirthdate(resultSet.getDate("birthdate"));
                u.setAbout(resultSet.getString("about"));
                return u;
            }
        });
    }

    public boolean checkResponse(int authID,int reqID){
        Object[] params = {authID, reqID,};
        int[] types = {4,4};
        try {
            AuthorRequest authorRequest;
            authorRequest = temp.queryForObject(sqlForCheckResponse, params, types, (resultSet, i) -> {
                AuthorRequest ar = new AuthorRequest();
                ar.setAuthorID(resultSet.getInt("authorID"));
                ar.setRequestID(resultSet.getInt("requestID"));
                return ar;
            });
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public int dropRequest(int id){
        Object[] params = {id};
        int[] types = {4};
        return temp.update(sqlDeleteRequest,params,types);
    }

    public int dropAllResponses(int id){
        Object[] params = {id};
        int[] types = {4};
        return temp.update(sqlDeleteAllResponses,params,types);
    }
}
