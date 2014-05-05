package com.example.appbroker;

public class StocksHeader {
	public double dblSon;
	public double dblYuzdeDegisimGunluk;
	public String strAd;
	public String strKod;
	
	public StocksHeader() {
		super();
		
	}
	
	public StocksHeader(double dblSon, double dblYuzdeDegisimGunluk,
			String strAd, String strKod) {
		super();
		this.dblSon = dblSon;
		this.dblYuzdeDegisimGunluk = dblYuzdeDegisimGunluk;
		this.strAd = strAd;
		this.strKod = strKod;
	}
	public double getDblSon() {
		return dblSon;
	}
	public void setDblSon(double dblSon) {
		this.dblSon = dblSon;
	}
	public double getDblYuzdeDegisimGunluk() {
		return dblYuzdeDegisimGunluk;
	}
	public void setDblYuzdeDegisimGunluk(double dblYuzdeDegisimGunluk) {
		this.dblYuzdeDegisimGunluk = dblYuzdeDegisimGunluk;
	}
	public String getStrAd() {
		return strAd;
	}
	public void setStrAd(String strAd) {
		this.strAd = strAd;
	}
	public String getStrKod() {
		return strKod;
	}
	public void setStrKod(String strKod) {
		this.strKod = strKod;
	}
	
	

}
