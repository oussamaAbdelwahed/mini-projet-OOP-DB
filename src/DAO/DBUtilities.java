package DAO;



public class DBUtilities {
	public static StringBuilder prepareForSelectPersonAddress(String parametersList) {
		StringBuilder sb= new StringBuilder();
		if(parametersList.equals("*")) {
			sb.append("select p.*,a.* from personne p join adresse a on p.address = a.id");
			
		}
		else {
			sb.append("select ");
			String [] tmp = parametersList.split(",");
			for(String s : tmp) {
				sb.append("p."+s+",");
			}
			sb.append("a.*");
			sb.append(" from personne p join adresse a on p.address = a.id");
		}
		 return sb;
	}
	
	public static StringBuilder prepareForSelect(String tablename,String parametersList) {
		StringBuilder sb= new StringBuilder();
		if(parametersList.equals("*")) {
			sb.append("select * from "+ tablename);
			
		}else {
			sb.append("select ");
			String [] tmp = parametersList.split(",");
			for(String s : tmp) {
				sb.append(s+",");
			}
			sb.replace(sb.length()-1, sb.length(), "");
			sb.append(" from "+tablename);
		}
		 return sb;
	}
	
}