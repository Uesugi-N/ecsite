package com.portfolio.model.form;

public class UserForm {
		private long id;
		private String userName;
		private String password;
		private String fullName;
		private long isAdmin;
		
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
		
		public String getPassword() {
			return password;
		}
		
		public void setPassword(String password) {
			this.password = password;
		}
		
		public String getFullName() {
			return fullName;
		}
		
		public void setFullName(String fullName) {
			this.fullName = fullName;
		}
		
		public long getIsAdmin() {
			return isAdmin;
		}
		
		public void setIsAdmin(long isAdmin) {
			this.isAdmin = isAdmin;
		}
	
}
