package com.buymypoem.springmvc.dao;

import com.buymypoem.springmvc.model.*;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

public class MarkDAO {
    JdbcTemplate temp;
    public void setTemp(JdbcTemplate temp) {
        this.temp = temp;
    }

    private static final String sqlGetMark = "select * from Mark where compositionID=? and userID=?";
    private static final String sqlDropMark = "delete from Mark where compositionID=? and userID=?";
    private static final String sqlAddLike = "insert into Mark (compositionID, userID, mark) values (?,?,true)";
    private static final String sqlAddDislike = "insert into Mark (compositionID, userID, mark) values (?,?,false)";
    private static final String sqlChangeToLike = "update Mark set mark=true where compositionID=? and userID=?";
    private static final String sqlChangeToDislike = "update Mark set mark=false where compositionID=? and userID=?";

    public Mark getMark (int comp_id, int user_id){
        Object[] params = {comp_id, user_id};
        int[] types = {Types.INTEGER, Types.INTEGER};
        try {
            return temp.queryForObject(sqlGetMark, params, types, (resultSet, i) -> {
                Mark m = new Mark();
                m.setMarkID(resultSet.getInt("markID"));
                m.setCompositionID(resultSet.getInt("compositionID"));
                m.setUserID(resultSet.getInt("userID"));
                m.setMark(resultSet.getBoolean("mark"));
                return m;
            });
        }catch (Exception e){
            return null;
        }
    }

    public int dropMark(int comp_id, int user_id){
        Object[] params = {comp_id, user_id};
        int[] types = {4,4};
        return temp.update(sqlDropMark,params,types);
    }

    public int addMark(long comp_ID,int user_ID, boolean check){
        String sqlString;
        if (check) {sqlString = sqlAddLike;}
        else {sqlString = sqlAddDislike;}
        Object[] params = {comp_ID,user_ID};
        int[] types ={4,4};
        return temp.update(sqlString,params,types);
    }

    public int changeMark(long comp_ID,int user_ID, boolean check){
        String sqlString;
        if (check) {sqlString = sqlChangeToLike;}
        else {sqlString = sqlChangeToDislike;}
        Object[] params = {comp_ID,user_ID};
        int[] types ={4,4};
        return temp.update(sqlString,params,types);
    }
}
