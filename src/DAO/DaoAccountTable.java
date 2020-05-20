package DAO;
import DBConnection.DBConnection;
import entities.Client;
import entities.Address;
import entities.Account;
import entities.Person;
import entities.Counter;
import entities.Card;
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
	@SuppressWarnings("finally")
	public static Account getAccountById(String parametersList , Long id) {
		StringBuilder sb = DBUtilities.prepareForSelectFullAccountDetails(parametersList);
		sb.append(" where a.id =  ?");
		Account account = null ;
		try {
			 PreparedStatement myStmt = DBConnection.getPreparedStatement(sb.toString());
			   myStmt.setLong(1, id);
			   ResultSet res= myStmt.executeQuery();
			   while (res.next()) {
				   Address adresseClient = new Address(res.getLong("cl.address"));
				   Person client = new Client(res.getInt("cl.cin"),res.getString("cl.email"),res.getString("cl.password")
							,adresseClient,res.getString("cl.nom"),res.getString("cl.prenom"),res.getString("cl.tel"),new Date(res.getTimestamp("cl.dateNaiss").getTime())
							,Enum.valueOf(CivilState.class, res.getString("cl.etatCivil")),Enum.valueOf(Sex.class,res.getString("cl.sex")));
				   client.setId(res.getLong("cl.id"));
		
				   Address adresseCounter = new Address(res.getLong("g.address"));
				   Person counter = new Counter(res.getInt("g.cin"),res.getString("g.email"),res.getString("g.password")
							,adresseCounter,res.getString("g.nom"),res.getString("g.prenom"),res.getString("g.tel"),new Date(res.getTimestamp("g.dateNaiss").getTime())
							,Enum.valueOf(CivilState.class, res.getString("g.etatCivil")),Enum.valueOf(Sex.class,res.getString("g.sex")),res.getDouble("g.salaire"),new Date(res.getTimestamp("g.dateEmbauche").getTime()),res.getLong("g.guichet"));
				   counter.setId(res.getLong("g.id"));
				   
				   Card card = new Card(res.getLong("cd.numero"),res.getInt("cd.codeInternet"),res.getShort("cd.codeDab"),new Date(res.getTimestamp("cd.valableJusqua").getTime()));
				   if(res.getString("a.TYPE").equals(Enum.valueOf(TYPE.class,"COURANT").name()) && res.getInt("a.estValable")==1) {
					   account = new CurrentAccount(res.getDouble("a.solde"),new Date(res.getTimestamp("a.dateCreation").getTime()),true
							   ,res.getDouble("a.seuil"),client,counter,card);
					   account.setId(res.getLong("a.id"));
				   }
				   else if(res.getString("a.TYPE").equals(Enum.valueOf(TYPE.class,"EPARGNE").name()) && res.getInt("a.estValable")==1) {
					   account = new SavingAccount(res.getDouble("a.solde"),new Date(res.getTimestamp("a.dateCreation").getTime()),true
							   ,res.getDouble("a.seuil"),client,counter,card);
					   account.setId(res.getLong("a.id"));
				   }
				   else {
					   account = null;
				   }
				   
			   }
		}
		catch (Exception exc) {
			 exc.printStackTrace();
		 }
		finally {
				return account ;
		}
	}
	public static Account InsertAccountSeperatly(Account compte) {
		try {
			PreparedStatement myStmt = DBConnection.getPreparedStatementWithReturnedKey("insert into carte (codeInternet,codeDab,valableJusqua) values (?,?,?,?)");
			myStmt.setLong(1,compte.getCard().getInternetCode());
			myStmt.setLong(2,compte.getCard().getDabCode());
			myStmt.setTimestamp(3,new java.sql.Timestamp(compte.getCard().getExpirigDate().getTime()));
			int resCard = myStmt.executeUpdate();
			long cardId= DBConnection.getKey(myStmt);
			PreparedStatement myStmt1 = DBConnection.getPreparedStatementWithReturnedKey("insert into compte (solde,dateCreation,seuil,estValable,TYPE,client,guichetier,carte) values (?,?,?,?,?,?,?,?)");
			myStmt1.setDouble(1,compte.getMoney());
			myStmt1.setTimestamp(2,new java.sql.Timestamp(new java.util.Date().getTime()));
			myStmt1.setDouble(3,compte.getThreshold());
			myStmt1.setInt(4,1);
			if(compte instanceof CurrentAccount) {
				myStmt1.setString(5,Enum.valueOf(TYPE.class,"COURANT").name());
			}
			else {
				myStmt1.setString(5,Enum.valueOf(TYPE.class,"EPARGNE").name());
			}
			myStmt1.setLong(6,compte.getOwner().getId());
			myStmt1.setLong(7,compte.getCreatedBy().getId());
			myStmt1.setDouble(8,cardId);
			int resAccount = myStmt1.executeUpdate();
			long compteId = DBConnection.getKey(myStmt1);
			if (resCard > 0 &&  resAccount > 0 ) {
				compte.getCard().setConfCode(cardId);
				compte.setId(compteId);
				compte.getOwner().getAccounts().add(compte);
				return compte;
			}
			else 
				return null ;
		}
		catch (Exception exc) {
			 exc.printStackTrace();
			 return null ;
		 }
	}
	public static boolean deleteAccount(long id) {
		try {
			PreparedStatement myStmt= DBConnection.getPreparedStatement("delete from compte where id=?");
			myStmt.setLong(1,id);
			int res = myStmt.executeUpdate();
			if (res > 0) 
				return true ;
			else return false ;
		}
		catch (Exception exc) {
			 exc.printStackTrace();
			 return false ;
		 }
	}
}