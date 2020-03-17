package entities;

public class Client extends Person{
   private static int clientsNbr;
   private int accountsNbr;
   
   
   public Client() {super();}


public static int getClientsNbr() {
	return clientsNbr;
}


public static void setClientsNbr(int clientsNbr) {
	Client.clientsNbr = clientsNbr;
}


public int getAccountsNbr() {
	return accountsNbr;
}


public void setAccountsNbr(int accountsNbr) {
	this.accountsNbr = accountsNbr;
}
   
}
