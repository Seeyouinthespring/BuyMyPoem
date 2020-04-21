package com.buymypoem.springmvc.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import com.buymypoem.springmvc.model.*;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class CommentDAO {

    JdbcTemplate temp;
    public void setTemp(JdbcTemplate temp) {
        this.temp = temp;
    }

    private final String sqlGetCommentsForRequest= "SELECT comment.commentID, comment.text, comment.sendingdate, user.login, user.photo, request.requestID " +
            "FROM comment left join commentrequest on comment.commentID=commentrequest.commentID " +
            "left join user on comment.userID=user.userID " +
            "left join request on commentrequest.requestID=request.requestID " +
            "where request.requestID=?";
    private final String sqlAddComment="Insert into Comment (text,sendingdate,userID) values (?,?,?)";
    private final String sqlAddCommentRequestLink="Insert into CommentRequest (commentID,requestID) values (?,?)";


    public List<Comment> GetCommentsForRequest(int id){
        Object[] params = {id};
        int[] types = {Types.INTEGER};
        return temp.query(sqlGetCommentsForRequest, params, types, new RowMapper<Comment>() {
            public Comment mapRow(ResultSet resultSet, int i) throws SQLException {
                User u = new User();
                Comment comment = new Comment();
                comment.setCommentID(resultSet.getInt("commentID"));
                comment.setSendingDate(resultSet.getDate("sendingdate"));
                comment.setText(resultSet.getString("text"));
                u.setLogin(resultSet.getString("login"));
                u.setPhoto(resultSet.getString("photo"));
                comment.setUser(u);
                return comment;
            }
        });
    }

    public long addComment(Comment comment){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String dateOfSending=new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());

        temp.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlAddComment, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, comment.getText());
            ps.setString(2, dateOfSending);
            ps.setObject(3, comment.getUser().getUserID());
            return ps;
        }, keyHolder);

        //temp.update(connection -> {
            //PreparedStatement ps = connection.prepareStatement(sqlAddComment);
            //ps.setString(1, comment.getText());
            //ps.setString(2, dateOfSending);
            //ps.setObject(3, comment.getUser().getUserID());
          //  return ps;
        //}, keyHolder);

        //KeyHolder keyHolder = new GeneratedKeyHolder();
        //String dateOfSending=new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        //Object[] params = {comment.getText(),dateOfSending,comment.getUser().getUserID()};
        //int[] types ={Types.VARCHAR, Types.DATE, Types.INTEGER};
        //temp.update(sqlAddComment,params,types,keyHolder);
        return keyHolder.getKey().longValue();
    }

    public int addCommentRequestLink(long comID,int reqID){
        Object[] params = {comID,reqID};
        int[] types ={4,4};
        return temp.update(sqlAddCommentRequestLink,params,types);
    }
}
