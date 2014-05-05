package com.example.appbroker;

public class Currency {
	private String name;
	private String code;
	private double buy;
	private double sell;
	private double ebuy;
	private double esell;
	private String tl;
	private String way;
	private String date;
	private String invest;
	private String follow;
	private String firstInvest;
	private String lastInvest;
	private String lastValue;
	public Currency(String name, String code, double buy, double sell,
			double ebuy, double esell, String tl, String way, String date,
			String invest, String follow,String firstInvest, String lastInvest,String lastValue) {
		super();
		this.name = name;
		this.code = code;
		this.buy = buy;
		this.sell = sell;
		this.ebuy = ebuy;
		this.esell = esell;
		this.tl = tl;
		this.way = way;
		this.date = date;
		this.invest = invest;
		this.follow = follow;
		this.firstInvest = firstInvest;
		this.lastInvest = lastInvest;
		this.lastValue = lastValue;
	}
	public Currency(String name, String code, double buy, double sell,
			double ebuy, double esell, String tl, String way, String date,
			String invest, String follow) {
		super();
		this.name = name;
		this.code = code;
		this.buy = buy;
		this.sell = sell;
		this.ebuy = ebuy;
		this.esell = esell;
		this.tl = tl;
		this.way = way;
		this.date = date;
		this.invest = invest;
		this.follow = follow;
		
	}

	public Currency() {
		// TODO Auto-generated constructor stub
	}

	

	public String getLastValue() {
        return lastValue;
    }
    public void setLastValue(String lastValue) {
        this.lastValue = lastValue;
    }
    public String getFirstInvest() {
		return firstInvest;
	}

	public void setFirstInvest(String firstInvest) {
		this.firstInvest = firstInvest;
	}

	public String getLastInvest() {
		return lastInvest;
	}

	public void setLastInvest(String lastInvest) {
		this.lastInvest = lastInvest;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getBuy() {
		return buy;
	}

	public void setBuy(double buy) {
		this.buy = buy;
	}

	public double getSell() {
		return sell;
	}

	public void setSell(double sell) {
		this.sell = sell;
	}

	public double getEbuy() {
		return ebuy;
	}

	public void setEbuy(double ebuy) {
		this.ebuy = ebuy;
	}

	public double getEsell() {
		return esell;
	}

	public void setEsell(double esell) {
		this.esell = esell;
	}

	public String getTl() {
		return tl;
	}

	public void setTl(String tl) {
		this.tl = tl;
	}

	public String getWay() {
		return way;
	}

	public void setWay(String way) {
		this.way = way;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getInvest() {
		return invest;
	}

	public void setInvest(String invest) {
		this.invest = invest;
	}

	public String getFollow() {
		return follow;
	}

	public void setFollow(String follow) {
		this.follow = follow;
	}
	
	
	
	
}

