package com.buymypoem.springmvc.dao;

import com.google.inject.internal.cglib.proxy.$Dispatcher;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentDAO {

    JdbcTemplate temp;
    public void setTemp(JdbcTemplate temp) {
        this.temp = temp;
    }

    private final String sqlGetCommentsForComposition= "SELECT comment.commentID, comment.text, comment.sendingdate, user.login, user.photo, composition.compositionID " +
            "FROM comment left join commentcomposition on comment.commentID=commentcomposition.commentID " +
            "left join user on comment.userID=user.userID " +
            "left join composition on commentcomposition.compositionID=composition.compositionID " +
            "where composition.compositionID=?";
    private final String sqlGetCommentsForRequest= "SELECT comment.commentID, comment.text, comment.sendingdate, user.login, user.photo, request.requestID " +
            "FROM comment left join commentrequest on comment.commentID=commentrequest.commentID " +
            "left join user on comment.userID=user.userID " +
            "left join request on commentrequest.requestID=request.requestID " +
            "where request.requestID=?";
    private final String sqlGetCommentsForOrder= "SELECT comment.commentID, comment.text, comment.sendingdate, user.login, user.photo, ordering.orderingID " +
            "FROM comment left join commentordering on comment.commentID=commentordering.commentID " +
            "left join user on comment.userID=user.userID " +
            "left join ordering on commentordering.orderingID=ordering.orderingID " +
            "where ordering.orderingID=?";
    private final String sqlAddComment="Insert into Comment (text,sendingdate,userID) values (?,?,?)";
    private final String sqlAddCommentRequestLink="Insert into CommentRequest (commentID,requestID) values (?,?)";
    private final String sqlAddCommentCompositionLink="Insert into CommentComposition (commentID,compositionID) values (?,?)";
    private final String sqlAddCommentOrderLink="Insert into CommentOreder (commentID,orderID) values (?,?)";
    private final String sqlDeleteAllRequestLinks="DELETE from CommentRequest where requestID=?";
    private final String sqlDeleteComment = "delete from Comment where commentID=?";

    private final Map<String, String> sqlStringsForCommentLink = new HashMap<String, String>();

    public List<Comment> GetCommentsForRequest(int id, String checkString){
        Map<String, String> sqlStrings = new HashMap<String, String>();
        sqlStrings.put("composition",sqlGetCommentsForComposition);
        sqlStrings.put("request",sqlGetCommentsForRequest);
        sqlStrings.put("order",sqlGetCommentsForOrder);
        Object[] params = {id};
        int[] types = {Types.INTEGER};
        return temp.query(sqlStrings.get(checkString), params, types, new RowMapper<Comment>() {
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
        return keyHolder.getKey().longValue();
    }

    public int addCommentLink(long commentID,int targetID, String checkString){
        Map<String, String> sqlStringsForCommentLink = new HashMap<String, String>();
        sqlStringsForCommentLink.put("composition",sqlAddCommentCompositionLink);
        sqlStringsForCommentLink.put("request", sqlAddCommentRequestLink);
        sqlStringsForCommentLink.put("order",sqlAddCommentOrderLink);
        Object[] params = {commentID,targetID};
        int[] types ={4,4};
        return temp.update(sqlStringsForCommentLink.get(checkString),params,types);
    }

    public int dropComment(int com_id){
        Object[] params = {com_id};
        int[] types = {4};
        return temp.update(sqlDeleteComment,com_id,4);
    }

    public int dropAllCommentRequestLinks(int req_id){
        Object[] params = {req_id};
        int[] types = {4};
        return temp.update(sqlDeleteAllRequestLinks,params,types);
    }
}
