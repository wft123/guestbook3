package com.hanains.guestbook.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanains.guestbook.util.DBClose;
import com.hanains.guestbook.util.DBConnect;
import com.hanains.guestbook.vo.GuestBookVo;

@Repository
public class GuestBookDao {
	DBConnect dbconnect = null;
	String sql="";
	
	public GuestBookDao() {
		dbconnect = new DBConnect();
	}
	
	public List<GuestBookVo> getGuestBook(){
		Connection con = dbconnect.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<GuestBookVo> list = new ArrayList<GuestBookVo>();
		GuestBookVo vo = null;
		
		String sql = "select no,name,password,message,to_char(reg_date,'YYYY-MM-DD HH:MI:SS') from guestbook order by reg_date desc";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				vo = new GuestBookVo();
				vo.setNo(rs.getLong(1));
				vo.setName(rs.getString(2));
				vo.setPassword(rs.getString(3));
				vo.setMessage(rs.getString(4));
				vo.setReg_date(rs.getString(5));
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBClose.close(con,pstmt,rs);
		}
		
		return list;
	}
	
	public void add(GuestBookVo vo){
		Connection con = dbconnect.getConnection();
		PreparedStatement pstmt = null;
		
		String sql ="insert into guestbook values(guestbook_seq.nextval, ?, ?, ?, SYSDATE)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getMessage());
			
			pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			DBClose.close(con,pstmt);
		}
		
	}
	
	public int delete(int no, String password){
		Connection con = dbconnect.getConnection();
		PreparedStatement pstmt = null;
		
		int result =0;
		try {
			sql = "delete from guestbook where no=? and password=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);
			pstmt.setString(2, password);
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBClose.close(con,pstmt);
		}
		
		return result;
	}
}
