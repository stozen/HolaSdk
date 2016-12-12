package com.hola.mysdk.bean;

public class HolaRolerInfo {
	
	public static final int HOLA_LOGIN_GAME = 16;
	public static final int HOLA_LEVEL_UP   = 17;
	public static final int HOLA_CREATE_ROLE= 18;
	public static final int HOLA_EXIT_GAME  = 19;
	private String Rola_Id;
	@Override
	public String toString() {
		return "HolaRolerInfo [Rola_Id=" + Rola_Id + ", Role_Name=" + Role_Name
				+ ", Role_Grade=" + Role_Grade + ", Role_Balance="
				+ Role_Balance + ", Role_Vip=" + Role_Vip + ", Role_UserParty="
				+ Role_UserParty + ", Server_Name=" + Server_Name
				+ ", Server_Id=" + Server_Id + "]";
	}
	public String getRola_Id() {
		return Rola_Id;
	}
	public void setRola_Id(String rola_Id) {
		Rola_Id = rola_Id;
	}
	public String getRole_Name() {
		return Role_Name;
	}
	public void setRole_Name(String role_Name) {
		Role_Name = role_Name;
	}
	public String getRole_Grade() {
		return Role_Grade;
	}
	public void setRole_Grade(String role_Grade) {
		Role_Grade = role_Grade;
	}
	public String getRole_Balance() {
		return Role_Balance;
	}
	public void setRole_Balance(String role_Balance) {
		Role_Balance = role_Balance;
	}
	public String getRole_Vip() {
		return Role_Vip;
	}
	public void setRole_Vip(String role_Vip) {
		Role_Vip = role_Vip;
	}
	public String getRole_UserParty() {
		return Role_UserParty;
	}
	public void setRole_UserParty(String role_UserParty) {
		Role_UserParty = role_UserParty;
	}
	public String getServer_Name() {
		return Server_Name;
	}
	public void setServer_Name(String server_Name) {
		Server_Name = server_Name;
	}
	public String getServer_Id() {
		return Server_Id;
	}
	public void setServer_Id(String server_Id) {
		Server_Id = server_Id;
	}
	public static int getHolaLoginGame() {
		return HOLA_LOGIN_GAME;
	}
	public static int getHolaLevelUp() {
		return HOLA_LEVEL_UP;
	}
	public static int getHolaCreateRole() {
		return HOLA_CREATE_ROLE;
	}
	public static int getHolaExitGame() {
		return HOLA_EXIT_GAME;
	}
	private String Role_Name;
	private String Role_Grade;
	private String Role_Balance;
	private String Role_Vip;
	private String Role_UserParty;
	private String Server_Name;
	private String Server_Id;

}
