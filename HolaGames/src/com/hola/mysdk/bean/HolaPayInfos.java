package com.hola.mysdk.bean;

public class HolaPayInfos {
	@Override
	public String toString() {
		return "HolaPayInfos [orderId=" + orderId + ", proPrice=" + proPrice
				+ ", proName=" + proName + ", proId=" + proId + ", proAmount="
				+ proAmount + ", serverId=" + serverId + ", servername="
				+ servername + ", exchangeRate=" + exchangeRate
				+ ", gameMoneyName=" + gameMoneyName + ", uid=" + uid
				+ ", extend=" + extend + ", notifyUrl=" + notifyUrl
				+ ", comfrom=" + comfrom + ", gameRolerInfo=" + gameRolerInfo
				+ "]";
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public float getProPrice() {
		return proPrice;
	}
	public void setProPrice(float proPrice) {
		this.proPrice = proPrice;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getProId() {
		return proId;
	}
	public void setProId(String proId) {
		this.proId = proId;
	}
	public int getProAmount() {
		return proAmount;
	}
	public void setProAmount(int proAmount) {
		this.proAmount = proAmount;
	}
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	public String getServername() {
		return servername;
	}
	public void setServername(String servername) {
		this.servername = servername;
	}
	public int getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(int exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public String getGameMoneyName() {
		return gameMoneyName;
	}
	public void setGameMoneyName(String gameMoneyName) {
		this.gameMoneyName = gameMoneyName;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getExtend() {
		return extend;
	}
	public void setExtend(String extend) {
		this.extend = extend;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getComfrom() {
		return comfrom;
	}
	public void setComfrom(String comfrom) {
		this.comfrom = comfrom;
	}
	public HolaRolerInfo getGameRolerInfo() {
		return gameRolerInfo;
	}
	public void setGameRolerInfo(HolaRolerInfo gameRolerInfo) {
		this.gameRolerInfo = gameRolerInfo;
	}
	private String orderId;  //订单号
	private float proPrice;	 //商品价格
	private String proName;	 //商品名称
	private String proId;	 //商品ID
	private int proAmount; 	 //商品数量
	private String serverId; //服务器ID
	private String servername;//服务器名称
	private int exchangeRate; //兑换比率
	private String gameMoneyName; //货币名称
	private String uid;		   //sdk服务器回传给游戏服务器的id
	private String extend;	   //额外字段
	private String notifyUrl;  //支付回调通知地址
	private String comfrom;	   //
	private HolaRolerInfo gameRolerInfo;

}
