package com.cyj.clog;

import com.cyj.clog.util.PropertyUtil;

public class CLog {

	private static int STACK_DEEPTH = PropertyUtil
			.getProperty("clog.stack.deepth") == null ? 4 : Integer
			.parseInt(PropertyUtil.getProperty("clog.stack.deepth"));
	private static boolean isLogRunning = false;

	public static void trace(String logMsg) {
		pushClogToQueue(CLogLevel.TRACE, logMsg, null, null, null);
	}

	public static void trace(String logMsg, String logUsr) {
		pushClogToQueue(CLogLevel.TRACE, logMsg, logUsr, null, null);
	}

	public static void trace(String logMsg, String logUsr, String logCode) {
		pushClogToQueue(CLogLevel.TRACE, logMsg, logUsr, logCode, null);
	}
	
	public static void debug(String logMsg) {
		pushClogToQueue(CLogLevel.DEBUG, logMsg, null, null, null);
	}

	public static void debug(String logMsg, String logUsr) {
		pushClogToQueue(CLogLevel.DEBUG, logMsg, logUsr, null, null);
	}

	public static void debug(String logMsg, String logUsr, String logCode) {
		pushClogToQueue(CLogLevel.DEBUG, logMsg, logUsr, logCode, null);
	}
	
	public static void info(String logMsg) {
		pushClogToQueue(CLogLevel.INFO, logMsg, null, null, null);
	}

	public static void info(String logMsg, String logUsr) {
		pushClogToQueue(CLogLevel.INFO, logMsg, logUsr, null, null);
	}

	public static void info(String logMsg, String logUsr, String logCode) {
		pushClogToQueue(CLogLevel.INFO, logMsg, logUsr, logCode, null);
	}
	
	public static void warn(String logMsg) {
		pushClogToQueue(CLogLevel.WARN, logMsg, null, null, null);
	}

	public static void warn(String logMsg, String logUsr) {
		pushClogToQueue(CLogLevel.WARN, logMsg, logUsr, null, null);
	}

	public static void warn(String logMsg, String logUsr, String logCode) {
		pushClogToQueue(CLogLevel.WARN, logMsg, logUsr, logCode, null);
	}
	
	public static void error(Exception e) {
		pushClogToQueue(CLogLevel.ERROR, null, null, null, e);
	}
	
	public static void error(String logMsg,Exception e) {
		pushClogToQueue(CLogLevel.ERROR, logMsg, null, null, e);
	}

	public static void error(String logMsg, String logUsr,Exception e) {
		pushClogToQueue(CLogLevel.ERROR, logMsg, logUsr, null, e);
	}

	public static void error(String logMsg, String logUsr, String logCode,Exception e) {
		pushClogToQueue(CLogLevel.ERROR, logMsg, logUsr, logCode, e);
	}
	
	public static void fatal(Exception e) {
		pushClogToQueue(CLogLevel.FATAL, null, null, null, e);
	}
	
	public static void fatal(String logMsg,Exception e) {
		pushClogToQueue(CLogLevel.FATAL, logMsg, null, null, e);
	}

	public static void fatal(String logMsg, String logUsr,Exception e) {
		pushClogToQueue(CLogLevel.FATAL, logMsg, logUsr, null, e);
	}

	public static void fatal(String logMsg, String logUsr, String logCode,Exception e) {
		pushClogToQueue(CLogLevel.FATAL, logMsg, logUsr, logCode, e);
	}

	private static void pushClogToQueue(CLogLevel level, String logMsg,
			String logUsr, String logCode, Exception e) {
		if(!isLogRunning){
			CLogDBThread.getInstance().start();
			isLogRunning = true;
		}
		
		if(PropertyUtil.getProperty("clog.enable")==null||Boolean.parseBoolean(PropertyUtil.getProperty("clog.enable"))){
			CLogInfo info = createCLogInfo(level, logMsg, logUsr, logCode, e);
			boolean isOffered = CLogDBThread.CLOG_QUEUE.offer(info);

			if (!isOffered)
				System.err.println("Missed log:" + info.toString());
		}
	}

	private static CLogInfo createCLogInfo(CLogLevel level, String logMsg,
			String logUsr, String logCode, Exception e) {
		if(logMsg == null)
			logMsg = "";
		CLogInfo info = new CLogInfo();
		info.setLoglevel(level);
		info.setUsr(logUsr);
		info.setCode(logCode);
	
		StringBuffer envinf=new StringBuffer();
		envinf.append("thid=").append(String.valueOf(Thread.currentThread().getId()));
		envinf.append("|thname=");
		envinf.append(Thread.currentThread().getName());

		if (e == null) {
			StackTraceElement[] stk = (new Throwable()).getStackTrace();
			if (stk != null && stk.length >= STACK_DEEPTH) {
				info.setModule(stk[STACK_DEEPTH - 1].getClassName() + "."
						+ stk[STACK_DEEPTH - 1].getMethodName());
				info.setLineno(stk[STACK_DEEPTH - 1].getLineNumber());
			}
		} else {
			Exception cause = e;
			
			int idx = 0;
			try {
				while (cause!=null&&cause.getCause()!=null&&idx<50) {
					idx++;
					cause = (Exception)cause.getCause();
				}
			} catch (Exception ee) {
				cause = e;
			}
			envinf.append("|causeDeep=").append(String.valueOf(idx));
			
			StackTraceElement[] stk = cause.getStackTrace();
			boolean topElementDone=false;
			logMsg+=" exp info:"+cause.toString();
			logMsg+="\r\nexp stack:";
			for(int i=0;i<stk.length;i++){
				if(stk[i].getLineNumber()<0){
					continue;
				}
				if(!topElementDone){
					info.setModule(stk[i].getClassName()+"."+stk[i].getMethodName());
					info.setLineno(stk[i].getLineNumber());
					logMsg+="\r\nLine"+stk[i].getLineNumber()+"_"+stk[i].getClassName()+"."+stk[i].getMethodName();
					topElementDone=true;
				}else{
					logMsg+="\r\nLine"+stk[i].getLineNumber()+"_"+stk[i].getClassName()+"."+stk[i].getMethodName();
				}
			}
		}
		info.setMsg(logMsg);
		info.setEnvinf(envinf.toString());

		return info;
	}

	private CLog() {}

}
