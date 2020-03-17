package entities;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import entities_enums.CivilState;
import entities_enums.Sex;

public abstract class Person {
    private long id;
    private int cin;
    private String firstname,lastname,tel;
    private Date birthdate;
    
    
    private Window window=null;
    private CivilState civilState;
    private Sex sex;
    
    public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getCin() {
		return cin;
	}
	public void setCin(int cin) {
		this.cin = cin;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	
	
	public Window getWindow() {
		return window;
	}
	public void setWindow(Window window) {
		this.window = window;
	}
	public CivilState getCivilState() {
		return civilState;
	}
	public void setCivilState(CivilState civilState) {
		this.civilState = civilState;
	}
	public Sex getSex() {
		return sex;
	}
	public void setSex(Sex sex) {
		this.sex = sex;
	}
	public List<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	private List<Account> accounts = new LinkedList<>();
    public Person() {}
    
    
}
