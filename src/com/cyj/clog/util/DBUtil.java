package com.cyj.clog.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.cyj.clog.CLogInfo;

public class DBUtil {
	
	private static String HOST_IP = getHostIP();
	private static String HOST_NAME = getHostName();
	
	public static void writeLogToDB(Connection conn, CLogInfo loginfo){
		PreparedStatement ps = null;
		try {
			System.out.println("start to write log to db:"+loginfo.toString());
			System.out.println(conn.isClosed());
			String sql = PropertyUtil.getProperty("clog.db.sql");
			ps = conn.prepareStatement(sql);
			System.out.println("start to wrisss");
			ps.setString(2, PropertyUtil.getProperty("clog.sysname"));
			ps.setString(3, loginfo.getLoglevel().name());
			ps.setInt(5, loginfo.getLineno());
			ps.setString(6, loginfo.getModule());
			ps.setString(7, loginfo.getEnvinf());
			ps.setString(8, loginfo.getUsr());
			ps.setString(9, loginfo.getCallt());
			ps.setString(10, loginfo.getCode());
			ps.setString(11, HOST_IP);
			ps.setString(12, HOST_NAME);
			ps.setTimestamp(13, new Timestamp(System.currentTimeMillis()));
			int msglength = loginfo.getMsg().getBytes().length;
			System.out.println("msglength:"+msglength);
			if(msglength>4000){
				for(int i=1;i<msglength/4000+2;i++){
					ps.setInt(1, i);
					if(i==msglength/4000+1)
						ps.setString(4, loginfo.getMsg().substring(4000*(i-1)));
					else
						ps.setString(4, loginfo.getMsg().substring(4000*(i-1), 4000*i-1));
					ps.executeUpdate();
				}
			}else{
				ps.setInt(1, 0);
				ps.setString(4, loginfo.getMsg());
				ps.executeUpdate();
			}
			
		} catch (SQLException e) {
			System.err.println("Failed to write log to database");
			e.printStackTrace();
		}finally{
			try {
				if(ps!=null)
					ps.close();
			} catch (SQLException e) {
				System.err.println("Failed to close PreparedStatement");
				e.printStackTrace();
			}
		}
	}
	
	
	private static String getHostIP(){
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return "";
		}
	}
	
	private static String getHostName(){
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			return "";
		}
	}
	
}
