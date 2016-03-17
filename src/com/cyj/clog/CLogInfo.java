package com.cyj.clog;

public class CLogInfo {

	private CLogLevel loglevel = CLogLevel.INFO;
	private String usr = null;
	private String callt = "MYSELF";
	private String module = null;
	private int lineno = -1;
	private String code = null;
	private String msg = null;
	private String logTime; // yyyy-MM-dd HH:mm:ss
	private String envinf;

	public String getCallt() {
		return callt;
	}
	public void setCallt(String callt) {
		this.callt = callt;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getEnvinf() {
		return envinf;
	}
	public void setEnvinf(String envinf) {
		this.envinf = envinf;
	}
	public int getLineno() {
		return lineno;
	}
	public void setLineno(int lineno) {
		this.lineno = lineno;
	}
	public CLogLevel getLoglevel() {
		return loglevel;
	}
	public void setLoglevel(CLogLevel loglevel) {
		this.loglevel = loglevel;
	}
	public String getLogTime() {
		return logTime;
	}
	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getUsr() {
		return usr;
	}
	public void setUsr(String usr) {
		this.usr = usr;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("{loglevel:");
		sb.append(this.getLoglevel().name());
		sb.append(",logtime:");
		sb.append(this.getLogTime());
		sb.append(",usr:");
		sb.append(this.getUsr());
		sb.append(",module:");
		sb.append(this.getModule());
		sb.append(",lineno:");
		sb.append(this.getLineno());
		sb.append(",msg:");
		sb.append(this.getMsg());
		sb.append(",envinf:");
		sb.append(this.getEnvinf());
		sb.append("}");
		return sb.toString();
	}
}
