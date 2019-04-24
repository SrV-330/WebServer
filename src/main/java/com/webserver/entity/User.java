package com.webserver.entity;

public class User {
	
	private String userName;
	private String userPwd;
	private String nickName;
	private Integer age;
	
	
	
	public User(String userName, String userPwd, String nickName, Integer age) {
		this.userName = userName;
		this.userPwd = userPwd;
		this.nickName = nickName;
		this.age = age;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	

}
