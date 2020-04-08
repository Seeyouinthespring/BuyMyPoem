package com.buymypoem.springmvc.dao;

import com.buymypoem.springmvc.model.Author;
import com.buymypoem.springmvc.model.Customer;
import com.buymypoem.springmvc.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Calendar;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.List;

public class UserDAO {
    JdbcTemplate temp;

    public void setTemp(JdbcTemplate temp) {
        this.temp = temp;
    }

    public User getUserByLogin(String login){
        String sql ="Select * from user where login=?";

        try {
            List<User> uList = temp.query(sql, new Object[]{login}, new RowMapper<User>() {
                public User mapRow(ResultSet resultSet, int i) throws SQLException {
                    User u = new User();
                    u.setUserID(resultSet.getInt("userID"));
                    u.setLogin(resultSet.getString("login"));
                    u.setBirthdate(resultSet.getDate("birthdate"));
                    u.setEmail(resultSet.getString("email"));
                    u.setPassword(resultSet.getString("password"));
                    u.setPhoto(resultSet.getString("photo"));
                    u.setAbout(resultSet.getString("about"));
                    u.setRegistredate(resultSet.getDate("registerdate"));
                    u.setRole(resultSet.getString("role"));
                    return u;
                }
            });

            return uList.get(0);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Author getAuthorById(int id){
        String sql ="Select * from author where userId=?";

        try {
            List<Author> uList = temp.query(sql, new Object[]{id}, new RowMapper<Author>() {
                public Author mapRow(ResultSet resultSet, int i) throws SQLException {
                    Author a = new Author();
                    a.setAuthorID(resultSet.getInt("authorID"));
                    a.setFinishedcompositions(resultSet.getInt("finisedcompositions"));
                    a.setRating(resultSet.getFloat("rating"));
                    a.setCardNumber(resultSet.getString("cardNumber"));
                    return a;
                }
            });

            return uList.get(0);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Customer getCustomerById(int id){
        String sql ="Select * from customer where userId=?";

        try {
            List<Customer> uList = temp.query(sql, new Object[]{id}, new RowMapper<Customer>() {
                public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
                    Customer c =  new Customer();
                    c.setCustomerID(resultSet.getInt("customerID"));
                    c.setPaidcompositionnumber(resultSet.getInt("paidcompositionnumber"));
                    return c;
                }
            });

            return uList.get(0);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }



    public int insertUser(User user) {
        String dateRegisterdate=new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        String dateBirthdate=new SimpleDateFormat("yyyy-MM-dd").format(user.getBirthdate());
        String sql = "insert into user (login, password, email, birthdate, registerdate, role) values (?,?,?,?,?,?);";
        Object[] params = {user.getLogin(), user.getPassword().hashCode(), user.getEmail(), dateBirthdate, dateRegisterdate, user.getRole()};
        int[] types = {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.DATE, Types.DATE, Types.VARCHAR};
        temp.update(sql,params,types);

        user = getUserByLogin(user.getLogin());

        if (user.getRole().equals("Author")){
            insertAuthor(user.getUserID());
        }
        else if (user.getRole().equals("Customer")){
            insertCustomer(user.getUserID());
        }
        return user.getUserID();
    }

    public int insertAuthor(int id) {
        String sqlAuthor="insert into author (userId) VALUES  (?);";
        return temp.update(sqlAuthor, new  Object[]{id}, new int[]{Types.INTEGER});
    }

    public int insertCustomer(int id) {
        String sqlCustomer="insert into customer (userId) VALUES  (?);";
        return temp.update(sqlCustomer, new  Object[]{id}, new int[]{Types.INTEGER});
    }

    public int updateUser(User user) {
        String sql="UPDATE user SET photo = ? WHERE userID = ?;";
        Object[] params = {user.getPhoto(), user.getUserID()};
        int[] types = {Types.VARCHAR, Types.INTEGER};
        return temp.update(sql,params,types);
    }

}
