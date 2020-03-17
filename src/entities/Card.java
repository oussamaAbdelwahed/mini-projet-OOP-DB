package entities;

public class Card {
   private long confCode;
   private int internetCode;
   private short dabCode;
   
   private Account account;
   
   public Account getAccount() {
	return account;
}

public void setAccount(Account account) {
	this.account = account;
}

public Card() {}

	public long getConfCode() {
		return confCode;
	}

	public void setConfCode(long confCode) {
		this.confCode = confCode;
	}
	
	public int getInternetCode() {
		return internetCode;
	}
	
	public void setInternetCode(int internetCode) {
		this.internetCode = internetCode;
	}
	
	public short getDabCode() {
		return dabCode;
	}
	
	public void setDabCode(short dabCode) {
		this.dabCode = dabCode;
	}
   
   
}
