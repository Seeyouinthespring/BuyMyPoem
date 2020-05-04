package com.buymypoem.springmvc.dao;

import com.buymypoem.springmvc.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDAO {

    JdbcTemplate temp;
    public void setTemp(JdbcTemplate temp) {
        this.temp = temp;
    }

    private static final String sqlCreateOrder ="insert into ordering (startdate, deadline, cost, description, compositionID, customerID, authorID, typeID, genreID) " +
            "values (?,?,?,?,?,?,?,?,?)";
    private static final String sqlGetOrdersForCustomer = "SELECT ordering.orderingID, ordering.startdate, ordering.deadline, ordering.cost, ordering.description,composition.text, \n" +
            "user_customer.login as cust, user_customer.photo as cust_photo,\n" +
            "user_author.login as auth, user_author.photo as auth_photo,\n" +
            "type.title as ttitle, \n" +
            "genre.title as gtitle FROM ordering\n" +
            "left join composition on ordering.compositionID=composition.compositionID\n" +
            "left join customer on ordering.customerID=customer.customerID \n" +
            "inner join user as user_customer on customer.userID=user_customer.userID \n" +
            "left JOIN author on ordering.authorID=author.authorID \n" +
            "inner JOIN user as user_author on author.userID = user_author.userID \n" +
            "left join type on ordering.typeID=type.typeID \n" +
            "LEFT JOIN genre on ordering.genreID=genre.genreID where ordering.customerID=?";
    private static final String sqlGetOrdersForAuthor = "SELECT ordering.orderingID, ordering.startdate, ordering.deadline, ordering.cost, ordering.description, \n" +
            "composition.text, \n" +
            "user_customer.login as cust, user_customer.photo as cust_photo,\n" +
            "user_author.login as auth, user_author.photo as auth_photo,\n" +
            "type.title as ttitle, \n" +
            "genre.title as gtitle FROM ordering\n" +
            "left join composition on ordering.compositionID=composition.compositionID\n" +
            "left join customer on ordering.customerID=customer.customerID \n" +
            "inner join user as user_customer on customer.userID=user_customer.userID \n" +
            "left JOIN author on ordering.authorID=author.authorID \n" +
            "inner JOIN user as user_author on author.userID = user_author.userID \n" +
            "left join type on ordering.typeID=type.typeID \n" +
            "LEFT JOIN genre on ordering.genreID=genre.genreID where ordering.authorID=?";


    @Autowired
    RequestDAO requestDAO;

    public int createOrder(Request request, int executerID){
        String dateOfStart=new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        String dateOfDeadline=new SimpleDateFormat("yyyy-MM-dd").format(request.getDeadline());
        Object[] params = {
                dateOfStart,
                dateOfDeadline,
                request.getCost(),
                request.getDescription(),
                null,
                requestDAO.getCustomerId(request.getUser().getUserID()),
                requestDAO.getAuthorId(executerID),
                request.getType().getTypeID(),
                request.getGenre().getGenreID()};
        int[] types = {Types.DATE,91,Types.FLOAT,Types.VARCHAR, Types.NULL,4,4,4,4};
        return temp.update(sqlCreateOrder, params, types);
    }

    public List<Order> getAllOrders (int id, String role){
        String sqlGetString;
        Map<String, String> sqlStrings = new HashMap<String, String>();
        sqlStrings.put("Author",sqlGetOrdersForAuthor);
        sqlStrings.put("Customer",sqlGetOrdersForCustomer);
        sqlStrings.put("Service",sqlGetOrdersForAuthor);
        sqlGetString=sqlStrings.get(role);

        Object[] params = {id};
        int [] types = {4};
        return temp.query(sqlGetString, params, types, new RowMapper<Order>() {
            public Order mapRow(ResultSet resultSet, int i) throws SQLException {
                Type t = new Type();
                Genre g = new Genre();
                Composition c = new Composition();
                Order o = new Order();
                User customer = new User();
                User author = new User();
                o.setOrderID(resultSet.getInt("orderingID"));
                o.setStartdate(resultSet.getDate("startdate"));
                o.setDeadline(resultSet.getDate("deadline"));
                o.setCost(resultSet.getFloat("cost"));
                o.setDescription(resultSet.getString("description"));
                c.setText(resultSet.getString("text"));
                o.setComposition(c);
                customer.setLogin(resultSet.getString("cust"));
                customer.setPhoto(resultSet.getString("cust_photo"));
                o.setCustomer(customer);
                author.setLogin(resultSet.getString("auth"));
                author.setPhoto(resultSet.getString("auth_photo"));
                o.setAuthor(author);
                t.setTitle(resultSet.getString("ttitle"));
                o.setType(t);
                g.setTitle(resultSet.getString("gtitle"));
                o.setGenre(g);
                return o;
            }
        });
    }
}
