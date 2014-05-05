package com.example.appbroker;

public class Stocks {

	public String strKod;
	public String strSeri;
	public String strPiyasa;
	public double dblEnDusuk1;
	public double dblEnDusuk2;
	public double dblEnIyiAlis;
	public double dblEnIyiSatis;
	public double dblEnYuksek1;
	public double dblEnYuksek2;
	public double dblFarkGunluk;
	public double dblFarkSeans;
	public double dblGunEnDusuk;
	public double dblGunEnYuksek;
	public double dblKapanis1;
	public double dblKapanis2;
	public double dblOncekiKapanis1;
	public double dblOncekiKapanis2;
	public double dblOncekiSon;
	public double dblOrtalama1;
	public double dblOrtalama2;
	public double dblSon;
	public double dblSonAdet;
	public double dblTaban;
	public double dblTavan;
	public double dblToplamAdet1;
	public double dblToplamAdet2;
	public double dblToplamHacim1;
	public double dblToplamHacim2;
	public double dblYuzdeDegisim;
	public double dblYuzdeDegisimGunluk;
	public double dblFiyatAdimi;
	public String strAd;
	public String strEndeks;
	public String strSaat;
	public String strGrupKodu;
	public String invest;
	public String follow;
	public String firstInvest;
	public String lastInvest;
	private String lastValue;
	

	public Stocks(String strKod, double dblGunEnDusuk, double dblGunEnYuksek,
			double dblSon, double dblTaban, double dblTavan,
			 double dblYuzdeDegisimGunluk, String strAd,
			String strSaat, String invest, String follow, String firstInvest,
			String lastInvest,String lastValue) {
		super();
		this.strKod = strKod;
		this.dblGunEnDusuk = dblGunEnDusuk;
		this.dblGunEnYuksek = dblGunEnYuksek;
		this.dblSon = dblSon;
		this.dblTaban = dblTaban;
		this.dblTavan = dblTavan;
		this.dblYuzdeDegisimGunluk = dblYuzdeDegisimGunluk;
		this.strAd = strAd;
		this.strSaat = strSaat;
		this.invest = invest;
		this.follow = follow;
		this.firstInvest = firstInvest;
		this.lastInvest = lastInvest;
		this.lastValue = lastValue;
	}

	public Stocks(String strKod, String strSeri, String strPiyasa,
			double dblEnDusuk1, double dblEnDusuk2, double dblEnIyiAlis,
			double dblEnIyiSatis, double dblEnYuksek1, double dblEnYuksek2,
			double dblFarkGunluk, double dblFarkSeans, double dblGunEnDusuk,
			double dblGunEnYuksek, double dblKapanis1, double dblKapanis2,
			double dblOncekiKapanis1, double dblOncekiKapanis2,
			double dblOncekiSon, double dblOrtalama1, double dblOrtalama2,
			double dblSon, double dblSonAdet, double dblTaban, double dblTavan,
			double dblToplamAdet1, double dblToplamAdet2,
			double dblToplamHacim1, double dblToplamHacim2,
			double dblYuzdeDegisim, double dblYuzdeDegisimGunluk,
			double dblFiyatAdimi, String strAd, String strEndeks,
			String strSaat, String strGrupKodu, String invest, String follow,
			String firstInvest, String lastInvest) {
		super();
		this.strKod = strKod;
		this.strSeri = strSeri;
		this.strPiyasa = strPiyasa;
		this.dblEnDusuk1 = dblEnDusuk1;
		this.dblEnDusuk2 = dblEnDusuk2;
		this.dblEnIyiAlis = dblEnIyiAlis;
		this.dblEnIyiSatis = dblEnIyiSatis;
		this.dblEnYuksek1 = dblEnYuksek1;
		this.dblEnYuksek2 = dblEnYuksek2;
		this.dblFarkGunluk = dblFarkGunluk;
		this.dblFarkSeans = dblFarkSeans;
		this.dblGunEnDusuk = dblGunEnDusuk;
		this.dblGunEnYuksek = dblGunEnYuksek;
		this.dblKapanis1 = dblKapanis1;
		this.dblKapanis2 = dblKapanis2;
		this.dblOncekiKapanis1 = dblOncekiKapanis1;
		this.dblOncekiKapanis2 = dblOncekiKapanis2;
		this.dblOncekiSon = dblOncekiSon;
		this.dblOrtalama1 = dblOrtalama1;
		this.dblOrtalama2 = dblOrtalama2;
		this.dblSon = dblSon;
		this.dblSonAdet = dblSonAdet;
		this.dblTaban = dblTaban;
		this.dblTavan = dblTavan;
		this.dblToplamAdet1 = dblToplamAdet1;
		this.dblToplamAdet2 = dblToplamAdet2;
		this.dblToplamHacim1 = dblToplamHacim1;
		this.dblToplamHacim2 = dblToplamHacim2;
		this.dblYuzdeDegisim = dblYuzdeDegisim;
		this.dblYuzdeDegisimGunluk = dblYuzdeDegisimGunluk;
		this.dblFiyatAdimi = dblFiyatAdimi;
		this.strAd = strAd;
		this.strEndeks = strEndeks;
		this.strSaat = strSaat;
		this.strGrupKodu = strGrupKodu;
		this.invest = invest;
		this.follow = follow;
		this.firstInvest = firstInvest;
		this.lastInvest = lastInvest;
	}

	public Stocks() {
		super();
	}
	public String getLastValue() {
        return lastValue;
    }
    public void setLastValue(String lastValue) {
        this.lastValue = lastValue;
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

	public String getStrKod() {
		return strKod;
	}

	public void setStrKod(String strKod) {
		this.strKod = strKod;
	}

	public String getStrSeri() {
		return strSeri;
	}

	public void setStrSeri(String strSeri) {
		this.strSeri = strSeri;
	}

	public String getStrPiyasa() {
		return strPiyasa;
	}

	public void setStrPiyasa(String strPiyasa) {
		this.strPiyasa = strPiyasa;
	}

	public double getDblEnDusuk1() {
		return dblEnDusuk1;
	}

	public void setDblEnDusuk1(double dblEnDusuk1) {
		this.dblEnDusuk1 = dblEnDusuk1;
	}

	public double getDblEnDusuk2() {
		return dblEnDusuk2;
	}

	public void setDblEnDusuk2(double dblEnDusuk2) {
		this.dblEnDusuk2 = dblEnDusuk2;
	}

	public double getDblEnIyiAlis() {
		return dblEnIyiAlis;
	}

	public void setDblEnIyiAlis(double dblEnIyiAlis) {
		this.dblEnIyiAlis = dblEnIyiAlis;
	}

	public double getDblEnIyiSatis() {
		return dblEnIyiSatis;
	}

	public void setDblEnIyiSatis(double dblEnIyiSatis) {
		this.dblEnIyiSatis = dblEnIyiSatis;
	}

	public double getDblEnYuksek1() {
		return dblEnYuksek1;
	}

	public void setDblEnYuksek1(double dblEnYuksek1) {
		this.dblEnYuksek1 = dblEnYuksek1;
	}

	public double getDblEnYuksek2() {
		return dblEnYuksek2;
	}

	public void setDblEnYuksek2(double dblEnYuksek2) {
		this.dblEnYuksek2 = dblEnYuksek2;
	}

	public double getDblFarkGunluk() {
		return dblFarkGunluk;
	}

	public void setDblFarkGunluk(double dblFarkGunluk) {
		this.dblFarkGunluk = dblFarkGunluk;
	}

	public double getDblFarkSeans() {
		return dblFarkSeans;
	}

	public void setDblFarkSeans(double dblFarkSeans) {
		this.dblFarkSeans = dblFarkSeans;
	}

	public double getDblGunEnDusuk() {
		return dblGunEnDusuk;
	}

	public void setDblGunEnDusuk(double dblGunEnDusuk) {
		this.dblGunEnDusuk = dblGunEnDusuk;
	}

	public double getDblGunEnYuksek() {
		return dblGunEnYuksek;
	}

	public void setDblGunEnYuksek(double dblGunEnYuksek) {
		this.dblGunEnYuksek = dblGunEnYuksek;
	}

	public double getDblKapanis1() {
		return dblKapanis1;
	}

	public void setDblKapanis1(double dblKapanis1) {
		this.dblKapanis1 = dblKapanis1;
	}

	public double getDblKapanis2() {
		return dblKapanis2;
	}

	public void setDblKapanis2(double dblKapanis2) {
		this.dblKapanis2 = dblKapanis2;
	}

	public double getDblOncekiKapanis1() {
		return dblOncekiKapanis1;
	}

	public void setDblOncekiKapanis1(double dblOncekiKapanis1) {
		this.dblOncekiKapanis1 = dblOncekiKapanis1;
	}

	public double getDblOncekiKapanis2() {
		return dblOncekiKapanis2;
	}

	public void setDblOncekiKapanis2(double dblOncekiKapanis2) {
		this.dblOncekiKapanis2 = dblOncekiKapanis2;
	}

	public double getDblOncekiSon() {
		return dblOncekiSon;
	}

	public void setDblOncekiSon(double dblOncekiSon) {
		this.dblOncekiSon = dblOncekiSon;
	}

	public double getDblOrtalama1() {
		return dblOrtalama1;
	}

	public void setDblOrtalama1(double dblOrtalama1) {
		this.dblOrtalama1 = dblOrtalama1;
	}

	public double getDblOrtalama2() {
		return dblOrtalama2;
	}

	public void setDblOrtalama2(double dblOrtalama2) {
		this.dblOrtalama2 = dblOrtalama2;
	}

	public double getDblSon() {
		return dblSon;
	}

	public void setDblSon(double dblSon) {
		this.dblSon = dblSon;
	}

	public double getDblSonAdet() {
		return dblSonAdet;
	}

	public void setDblSonAdet(double dblSonAdet) {
		this.dblSonAdet = dblSonAdet;
	}

	public double getDblTaban() {
		return dblTaban;
	}

	public void setDblTaban(double dblTaban) {
		this.dblTaban = dblTaban;
	}

	public double getDblTavan() {
		return dblTavan;
	}

	public void setDblTavan(double dblTavan) {
		this.dblTavan = dblTavan;
	}

	public double getDblToplamAdet1() {
		return dblToplamAdet1;
	}

	public void setDblToplamAdet1(double dblToplamAdet1) {
		this.dblToplamAdet1 = dblToplamAdet1;
	}

	public double getDblToplamAdet2() {
		return dblToplamAdet2;
	}

	public void setDblToplamAdet2(double dblToplamAdet2) {
		this.dblToplamAdet2 = dblToplamAdet2;
	}

	public double getDblToplamHacim1() {
		return dblToplamHacim1;
	}

	public void setDblToplamHacim1(double dblToplamHacim1) {
		this.dblToplamHacim1 = dblToplamHacim1;
	}

	public double getDblToplamHacim2() {
		return dblToplamHacim2;
	}

	public void setDblToplamHacim2(double dblToplamHacim2) {
		this.dblToplamHacim2 = dblToplamHacim2;
	}

	public double getDblYuzdeDegisim() {
		return dblYuzdeDegisim;
	}

	public void setDblYuzdeDegisim(double dblYuzdeDegisim) {
		this.dblYuzdeDegisim = dblYuzdeDegisim;
	}

	public double getDblYuzdeDegisimGunluk() {
		return dblYuzdeDegisimGunluk;
	}

	public void setDblYuzdeDegisimGunluk(double dblYuzdeDegisimGunluk) {
		this.dblYuzdeDegisimGunluk = dblYuzdeDegisimGunluk;
	}

	public double getDblFiyatAdimi() {
		return dblFiyatAdimi;
	}

	public void setDblFiyatAdimi(double dblFiyatAdimi) {
		this.dblFiyatAdimi = dblFiyatAdimi;
	}

	public String getStrAd() {
		return strAd;
	}

	public void setStrAd(String strAd) {
		this.strAd = strAd;
	}

	public String getStrEndeks() {
		return strEndeks;
	}

	public void setStrEndeks(String strEndeks) {
		this.strEndeks = strEndeks;
	}

	public String getStrSaat() {
		return strSaat;
	}

	public void setStrSaat(String strSaat) {
		this.strSaat = strSaat;
	}

	public String getStrGrupKodu() {
		return strGrupKodu;
	}

	public void setStrGrupKodu(String strGrupKodu) {
		this.strGrupKodu = strGrupKodu;
	}



}
