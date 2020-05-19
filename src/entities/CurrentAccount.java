package entities;

public class CurrentAccount extends Account {
	
	public CurrentAccount() {super();}
	
	public CurrentAccount(double money,double threshold,Card card) {
		super(money,threshold,card);
	}
    public boolean transfer(double money,Account c) {
    	return true;
    }
}
