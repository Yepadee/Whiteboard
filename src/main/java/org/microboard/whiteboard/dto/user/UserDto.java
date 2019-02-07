package org.microboard.whiteboard.dto.user;

public class UserDto {
	private long id;
	private String userName;
	
	public UserDto() {}
	
	public UserDto(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
