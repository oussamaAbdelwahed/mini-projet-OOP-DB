package DAO;
import DBConnection.DBConnection;
import entities.Client;
import entities.Counter;
import entities.Address;
import entities.Person;

import entities_enums.*;
import java.sql.*;
import java.util.Date;
public class DaoPersonTable {

	@SuppressWarnings("finally")
		public static Person Authentificate(String email,String pwd) {
			Person p = null;
			try {
				PreparedStatement myStmt = DBConnection.getPreparedStatement("select p.*,a.* from personne p  join adresse a on p.address = a.id where p.email=? and p.password=?");
				myStmt.setString(1,email);
				myStmt.setString(2, pwd);
				ResultSet res=myStmt.executeQuery();
				while (res.next()) {
					Address adresse = new Address(Enum.valueOf(Continent.class, res.getString("continent")),res.getString("pays"),res.getString("gouvernorat"),res.getString("ville")
							   ,res.getString("rue"),res.getInt("codePostal"));
					if(res.getString("TYPE").equals("GUICHETIER")) {				 
						p = new Counter(res.getInt("cin"),res.getString("email"),res.getString("password")
								,adresse,res.getString("nom"),res.getString("prenom"),res.getString("tel"),new Date(res.getTimestamp("dateNaiss").getTime())
								,Enum.valueOf(CivilState.class, res.getString("etatCivil")),Enum.valueOf(Sex.class,res.getString("sex")),res.getDouble("salaire"),new Date(res.getTimestamp("dateEmbauche").getTime()),res.getLong("guichet"));
								p.setId(res.getLong("id"));
					}else {
						p = new Client(res.getInt("cin"),res.getString("email"),res.getString("password")
								,adresse,res.getString("nom"),res.getString("prenom"),res.getString("tel"),new Date(res.getTimestamp("dateNaiss").getTime())
								,Enum.valueOf(CivilState.class, res.getString("etatCivil")),Enum.valueOf(Sex.class,res.getString("sex"))); 
						p.setId(res.getLong("id"));
					}	
				}
			}catch (Exception exc) {
				 exc.printStackTrace();
			 }finally {
				return p;
			}
		}
	
	@SuppressWarnings("finally")
	public static Person getPersonById(String parametersList,Long id)   {
		
		 StringBuilder sb = DBUtilities.prepareForSelectPersonAddress(parametersList);
		 sb.append(" where p.id =  ?");
		 Person p = null;
		 try {
		   PreparedStatement myStmt = DBConnection.getPreparedStatement(sb.toString());
		   myStmt.setLong(1, id);
		   ResultSet res= myStmt.executeQuery();
		   while (res.next()) {
			   Address adresse = new Address(Enum.valueOf(Continent.class, res.getString("continent")),res.getString("pays"),res.getString("gouvernorat"),res.getString("ville")
					   ,res.getString("rue"),res.getInt("codePostal"));
			   if(res.getString("TYPE").equals(Enum.valueOf(TYPE.class,"GUICHETIER").name())) {				 
					 p = new Counter(res.getInt("cin"),res.getString("email"),res.getString("password")
							,adresse,res.getString("nom"),res.getString("prenom"),res.getString("tel"),new Date(res.getTimestamp("dateNaiss").getTime())
							,Enum.valueOf(CivilState.class, res.getString("etatCivil")),Enum.valueOf(Sex.class,res.getString("sex")),res.getDouble("salaire"),new Date(res.getTimestamp("dateEmbauche").getTime()),res.getLong("guichet"));
					 p.setId(res.getLong("id"));
					 adresse.setId(res.getLong("address"));
				}
				else {
					p = new Client(res.getInt("cin"),res.getString("email"),res.getString("password")
							,adresse,res.getString("nom"),res.getString("prenom"),res.getString("tel"),new Date(res.getTimestamp("dateNaiss").getTime())
							,Enum.valueOf(CivilState.class, res.getString("etatCivil")),Enum.valueOf(Sex.class,res.getString("sex"))); 
					p.setId(res.getLong("id"));
					 adresse.setId(res.getLong("address"));
				}	
		 }
		 }
		 catch(SQLException e) {
			 e.printStackTrace();
			 return null ;
		 }
		 finally {
				return p;
			}
	}
	
	
	@SuppressWarnings("finally")
	public static Person updatePersonDatabase(Person person) {
		PreparedStatement myUpdateStmt = null ;
		PreparedStatement myUpdateStmtforadresse = null ;
		Person returnedPerson =null;
		try {
			if (person instanceof Client) {
			 Client C = (Client)person;
			 myUpdateStmt =  DBConnection.getPreparedStatement("update personne set email= ?,password= ?,cin= ?,nom= ?,prenom= ?,dateNaiss= ?,"
						+ "tel= ?,sex= ?,etatCivil= ? where id=?");
			 myUpdateStmt.setString(1,C.getEmail());
			 myUpdateStmt.setString(2,C.getPassword());
			 myUpdateStmt.setInt(3,C.getCin());
			 myUpdateStmt.setString(4,C.getLastname());
			 myUpdateStmt.setString(5,C.getFirstname());
			 myUpdateStmt.setTimestamp(6,new java.sql.Timestamp(C.getBirthdate().getTime()));
			 myUpdateStmt.setString(7,C.getTel());
			 myUpdateStmt.setString(8,C.getSex().name());
			 myUpdateStmt.setString(9,C.getCivilState().name());
			 myUpdateStmt.setLong(10,C.getId());
			 int res = myUpdateStmt.executeUpdate();
			 myUpdateStmtforadresse=DBConnection.getPreparedStatement("update adresse set continent= ?,pays= ?,gouvernorat= ?,ville= ?,rue= ?,codePostal= ?  where id=?");
			 myUpdateStmtforadresse.setString(1, C.getAddress().getContinent().name());
			 myUpdateStmtforadresse.setString(2, C.getAddress().getCountry());
			 myUpdateStmtforadresse.setString(3, C.getAddress().getState());
			 myUpdateStmtforadresse.setString(4, C.getAddress().getCity());
			 myUpdateStmtforadresse.setString(5, C.getAddress().getStreet());
			 myUpdateStmtforadresse.setInt(6, C.getAddress().getZipCode());
			 myUpdateStmtforadresse.setLong(7, C.getAddress().getId());
			 int resAd = myUpdateStmtforadresse.executeUpdate();
			 if(res>0 && resAd>0) {
				 returnedPerson=C;
			 }
			}
			else {
				Counter G=(Counter)person ;
				myUpdateStmt =  DBConnection.getPreparedStatement("update personne set email= ?,password= ?,cin= ?,nom= ?,prenom= ?,dateNaiss= ?,"
						+ "tel= ?,sex= ?,etatCivil= ?,salaire= ?,dateEmbauche= ? where id=?");
				myUpdateStmt.setString(1,G.getEmail());
				 myUpdateStmt.setString(2,G.getPassword());
				 myUpdateStmt.setInt(3,G.getCin());
				 myUpdateStmt.setString(4,G.getLastname());
				 myUpdateStmt.setString(5,G.getFirstname());
				 myUpdateStmt.setTimestamp(6,new java.sql.Timestamp(G.getBirthdate().getTime()));
				 myUpdateStmt.setString(7,G.getTel());
				 myUpdateStmt.setString(8,G.getSex().name());
				 myUpdateStmt.setString(9,G.getCivilState().name());
				 myUpdateStmt.setDouble(10,G.getSalary());
				 myUpdateStmt.setTimestamp(11,new java.sql.Timestamp(G.getHiringDate().getTime()));
				 myUpdateStmt.setLong(12,G.getId());
				 int res = myUpdateStmt.executeUpdate();
				 myUpdateStmtforadresse=DBConnection.getPreparedStatement("update adresse set continent= ?,pays= ?,gouvernorat= ?,ville= ?,rue= ?,codePostal= ?"
							+ " where id=?");
				 myUpdateStmtforadresse.setString(1, G.getAddress().getContinent().name());
				 myUpdateStmtforadresse.setString(2, G.getAddress().getCountry());
				 myUpdateStmtforadresse.setString(3, G.getAddress().getState());
				 myUpdateStmtforadresse.setString(4, G.getAddress().getCity());
				 myUpdateStmtforadresse.setString(5, G.getAddress().getStreet());
				 myUpdateStmtforadresse.setInt(6, G.getAddress().getZipCode());
				 myUpdateStmtforadresse.setLong(7, G.getAddress().getId());
				 int resAd = myUpdateStmtforadresse.executeUpdate();
				 if(res>0 && resAd>0) {
					returnedPerson=G;
				 }
			}
		}
		catch (Exception exc) {
			 exc.printStackTrace();
			 System.out.println("EXECPTION EEEEE");
			 return null;
		 }	
		finally {
			return returnedPerson;
		}
	}


}
