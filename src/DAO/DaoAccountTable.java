package DAO;
import DBConnection.DBConnection;
import entities.Client;
import entities.Address;
import entities.Account;
import entities.SavingAccount;
import entities.CurrentAccount;
import entities_enums.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.sql.*;

public class DaoAccountTable {
	public static boolean addAccountMonney(long idAccount,double amount,double threshhold) {
		String accType= threshhold == Account.THRESHOLD_CURRENT_ACCOUNT ? "COURANT" : "EPARGNE";
		try  {
			PreparedStatement myStmt1 = DBConnection.getPreparedStatement("update compte set solde=solde+? where solde+?>=? and id=? where TYPE=?");
			myStmt1.setDouble(1, amount);
			myStmt1.setDouble(2, amount);
			myStmt1.setDouble(3, threshhold);
			myStmt1.setLong(4, idAccount);
			myStmt1.setString(5, accType);
			int res1 = myStmt1.executeUpdate();
			if(res1>0) 
				return true ;
			else 
				return false;
		}catch (Exception exc) {
			 exc.printStackTrace();
			 return false ;
		 }
	}
	
	
	public static boolean TransactionFromAccounts(long idSource,long idDestinataire,double amount,double threshhold) {
		Connection con = DBConnection.getConnection();;
		try {
			String accType= threshhold == Account.THRESHOLD_CURRENT_ACCOUNT ? "COURANT" : "EPARGNE";
			con.setAutoCommit(false);
			PreparedStatement myStmt1 = con.prepareStatement("update compte c1 set c1.solde=c1.solde-? where c1.solde-?>=? and c1.id=? and TYPE = ?");
			PreparedStatement myStmt2 =con.prepareStatement("update compte c2 set c2.solde=c2.solde+? where c2.id=?");
			myStmt1.setDouble(1, amount);
			myStmt1.setDouble(2, amount);
			myStmt1.setDouble(3, threshhold);
			myStmt1.setLong(4, idSource);
			myStmt1.setString(5, accType);
			int res1=myStmt1.executeUpdate();
			myStmt2.setDouble(1, amount);
			myStmt2.setLong(2, idDestinataire);
			int res2=myStmt2.executeUpdate();
			if(res1>0 && res2>0 ) {
				con.commit();
				return true;
			}
			else 
				return false;
		}catch (Exception exc) {
			 try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			 exc.printStackTrace();
			 return false ;
		 }
	}
}