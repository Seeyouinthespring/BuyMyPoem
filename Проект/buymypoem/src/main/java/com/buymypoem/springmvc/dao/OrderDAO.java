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

    private static final String sqlCreateOrder ="insert into ordering (startdate, deadline, cost, description, compositionID, customerID, authorID, typeID, genreID, status) " +
            "values (?,?,?,?,?,?,?,?,?,'Processing')";

    private static final String sqlGetOrdersForCustomer = "SELECT ordering.*, composition.text," +
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

    private static final String sqlGetOrdersForAuthor = "SELECT ordering.*, composition.text," +
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

    private static final String sqlGetOrderById= "SELECT ordering.orderingID, ordering.startdate, ordering.deadline, ordering.cost, ordering.description, \n" +
            "composition.text, composition.compositionID, ordering.status, ordering.authorID, ordering.customerID, \n" +
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
            "LEFT JOIN genre on ordering.genreID=genre.genreID where ordering.orderingID=?";
    private static final String sqlAddCompositionToOrder ="Update ordering set compositionID = ? where orderingID = ?";
    private static final String sqlDropCompositionFromOrder ="Update ordering set compositionID = null where -100!=? and orderingID = ?";
    private static final String sqlChangeOrderingStatusReady= "Update ordering set status = 'Ready' where orderingID = ?";
    private static final String sqlChangeOrderingStatusProcessing= "Update ordering set status = 'Processing' where orderingID = ?";
    private static final String sqlChangeOrderingStatusCanceledByAuthor="Update ordering set status = 'CanceledByAuthor' where orderingID = ?";
    private static final String sqlChangeOrderingStatusCanceledByCustomer="Update ordering set status = 'CanceledByCustomer' where orderingID = ?";
    private static final String sqlDropOrder = "delete from ordering where orderingID=?";
    private static final String sqlAuthorOrdersInc ="update author set finisedcompositions = finisedcompositions+1 where authorID=?";
    private static final String sqlCustomerOrdersInc ="update customer set paidcompositionnumber = paidcompositionnumber+1 where customerID=?";

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
                o.setStatus(resultSet.getString("status"));
                o.setAuthorID(resultSet.getInt("authorID"));
                o.setCustomerID(resultSet.getInt("customerID"));
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

    public Order getOrderById(int id){
        Object[] params = {id};
        int[] types = {Types.INTEGER};
        return temp.queryForObject(sqlGetOrderById, params, types, (resultSet, i) -> {
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
                c.setCompositionID(resultSet.getInt("compositionID"));
                o.setComposition(c);
                o.setStatus(resultSet.getString("status"));
                o.setAuthorID(resultSet.getInt("authorID"));
                o.setCustomerID(resultSet.getInt("customerID"));
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
        });
    }

    public int changeStatus(int id, String checkString){
        Map<String, String> sqlStrings = new HashMap<String, String>();
        sqlStrings.put("ready", sqlChangeOrderingStatusReady);
        sqlStrings.put("processing", sqlChangeOrderingStatusProcessing);
        sqlStrings.put("canceledA", sqlChangeOrderingStatusCanceledByAuthor);
        sqlStrings.put("canceledC", sqlChangeOrderingStatusCanceledByCustomer);
        Object[] params = {id};
        int[] types = {4};
        return temp.update(sqlStrings.get(checkString), params, types);
    }

    public int Composition_ToFrom_Order(int comp_id,int ord_id, boolean checkString){
        Map<Boolean, String> sqlStrings = new HashMap<Boolean, String>();
        sqlStrings.put(true, sqlAddCompositionToOrder);
        sqlStrings.put(false, sqlDropCompositionFromOrder);
        Object[] params = {comp_id,ord_id};
        int[] types = {4,4};
        return temp.update(sqlStrings.get(checkString), params, types);
    }

    public int dropOrder(int id){
        Object[] params = {id};
        int[] types = {4};
        return temp.update(sqlDropOrder, params, types);
    }

    public int updateStatisticsAuthor (int authorID){
        Object[] params = {authorID};
        int[] types = {4};
        return temp.update(sqlAuthorOrdersInc, params, types);
    }

    public int updateStatisticsCustomer (int customerID){
        Object[] params = {customerID};
        int[] types = {4};
        return temp.update(sqlCustomerOrdersInc, params, types);
    }

}
