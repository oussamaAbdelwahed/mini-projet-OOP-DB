package entities;

import java.util.Date;

//l entite Guichetier
public class Counter extends Person{
	private double salary;
	private Date hiringDate;
	private static int countersNbr;
	
	public Counter() {super();}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public Date getHiringDate() {
		return hiringDate;
	}

	public void setHiringDate(Date hiringDate) {
		this.hiringDate = hiringDate;
	}

	public static int getCountersNbr() {
		return countersNbr;
	}

	public static void setCountersNbr(int countersNbr) {
		Counter.countersNbr = countersNbr;
	}
	
	public Client saveClient(Client c) {
		return null;
	}
	
	public boolean deleteClient(Client c) {
		return true;
	}
	
	public boolean openAccount(Account c) {
		return true;
	}
	
	public boolean closeAccount(Account c) {
		return true;
	}
	
	
}
