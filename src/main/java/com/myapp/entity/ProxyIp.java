package com.myapp.entity;

/**
 * Created by gaorui on 16/12/26.
 */
public class ProxyIp {

	private String ip;
	private int port;
	private String area; // 地区
	private String type; // http | https
	
	private boolean work;

	public void setWork(boolean work) {
		this.work = work;
	}

	public ProxyIp(String ip, int port, String area, String type) {
		super();
		this.ip = ip;
		this.port = port;
		this.area = area;
		this.type = type;
		//this.work=0; // 默认即为false；
		
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public String getArea() {
		return area;
	}

	public String getType() {
		return type;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isWork() {
		return work;
	}







	 
	
	
 
	

}
