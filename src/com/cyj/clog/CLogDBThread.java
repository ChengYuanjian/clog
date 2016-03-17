package com.cyj.clog;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import com.cyj.clog.util.DBUtil;
import com.cyj.clog.util.PropertyUtil;
import com.cyj.clog.util.StringUtil;

public class CLogDBThread extends Thread {

	protected static LinkedBlockingQueue<CLogInfo> CLOG_QUEUE = null;
	private static CLogDBThread instance = null;
	private static Connection connection = null;
	private static boolean IS_CONNECTION_ALIVE = false;

	static {
		int size = StringUtil.isEmpty(PropertyUtil
				.getProperty("clog.queue.size")) ? 1000 : Integer
				.parseInt(PropertyUtil.getProperty("clog.queue.size"));
		CLOG_QUEUE = new LinkedBlockingQueue<CLogInfo>(size);
		
		try {
			connection = createLoneConnection();
			IS_CONNECTION_ALIVE = true;
		} catch (Exception e) {
			System.err.println("Cannot create database connection");
			e.printStackTrace();
			IS_CONNECTION_ALIVE = false;
		}
	}
	
	public synchronized static CLogDBThread getInstance(){
		if(instance==null)
			instance = new CLogDBThread();
		return instance;
	}

	private CLogDBThread() {
		super("CLogDBThread");
		new KeepConnectionThread().start();
	}

	public void run() {	
		while(true){
			try {
				System.out.println("get log from queue");
				List<CLogInfo> buffList = new ArrayList<CLogInfo>();
				buffList.add(CLOG_QUEUE.take());		
				CLOG_QUEUE.drainTo(buffList);
				
				if(buffList.size()>0){
					for(CLogInfo loginfo:buffList){
						DBUtil.writeLogToDB(connection, loginfo);
					}
				}
			} catch (InterruptedException e) {
				System.err.println("Failed to get loginfo from queue");
			}
		}

	}
	

	private static Connection createLoneConnection() throws Exception {
		Class.forName(PropertyUtil.getProperty("clog.db.driver"));
		return DriverManager.getConnection(PropertyUtil
				.getProperty("clog.db.url"), PropertyUtil
				.getProperty("clog.db.user"), PropertyUtil
				.getProperty("clog.db.pwd"));
	}

	class KeepConnectionThread extends Thread {
		int i = 0;

		public void run() {
			while (true) {
				try {
					if (!IS_CONNECTION_ALIVE || connection == null
							|| connection.isClosed()) {
						connection = createLoneConnection();
						i++;
						System.err.println("Reconnect to database->" + i);
					}
					Thread.sleep(5000);
				} catch (Exception e) {
				}
			}
		}
	}

}
