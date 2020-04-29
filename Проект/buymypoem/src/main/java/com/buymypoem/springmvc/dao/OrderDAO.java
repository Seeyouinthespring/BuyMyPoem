package com.buymypoem.springmvc.dao;

import com.buymypoem.springmvc.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OrderDAO {

    JdbcTemplate temp;
    public void setTemp(JdbcTemplate temp) {
        this.temp = temp;
    }

    private static final String sqlCreateOrder ="insert into ordering (startdate, deadline, cost, description, compositionID, customerID, authorID, typeID, genreID) " +
            "values (?,?,?,?,?,?,?,?,?)";

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
}
