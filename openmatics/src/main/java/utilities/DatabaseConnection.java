package utilities;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import dataproviders.ConfigFileReader;

public class DatabaseConnection {

	private  static Connection conn = null;
	private static String databaseType;
	
	private static Map<String, Connection> dbConnections = new HashMap<String, Connection>();
	
	public DatabaseConnection() {			
		
	}
	
	public static Connection getConnection(String dbName) throws Exception {
		//jdbc:mysql://localhost:3306/testdb"+?useSSL=false		
		Connection conn = null;
		String mysql = "mysql";	
		String sqlserver = "sqlserver";
				
        switch (dbName) {        
        
        case "mysql" :
        	if(dbConnections.get(dbName) == null) {
        		Class.forName(ConfigFileReader.getConfigFileReader().getMySqlDriver());            	
            	String tmpmysql = "jdbc:mysql://";
            	String host = ConfigFileReader.getConfigFileReader().getMySqlHost();
            	String port = ConfigFileReader.getConfigFileReader().getMySqlPort();
            	String dbname = ConfigFileReader.getConfigFileReader().getMySqlDatabase();
            	String user = ConfigFileReader.getConfigFileReader().getMySqlUser();
            	String password = ConfigFileReader.getConfigFileReader().getMySqlPassword();
            	String mysqlstring = tmpmysql+host+":"+port+"/"+dbname+"?useSSL=false&allowPublicKeyRetrieval=true";
            	System.out.println("mysqlstring is : "+mysqlstring);
            	conn = DriverManager.getConnection(mysqlstring,user,password);
        		dbConnections.put(mysql, conn); 
        		}
        case "sqlserver" :
        	if(dbConnections.get(dbName) == null) {
        		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
        		//Class.forName("net.sourceforge.jtds.jdbc.Driver"); 
            	String tmpsqlserver = "jdbc:sqlserver://";
            	String host = ConfigFileReader.getConfigFileReader().getSqlServerHost();
            	String port = ConfigFileReader.getConfigFileReader().getSqlServerPort();
            	String dbname = ConfigFileReader.getConfigFileReader().getSqlServerDatabase();
            	String user = ConfigFileReader.getConfigFileReader().getSqlUser();
            	String password = ConfigFileReader.getConfigFileReader().getSqlPassword();
            	String mysqlstring = tmpsqlserver+host+";instanceName=SQLEXPRESS;databaseName=AutomationDB";
            	//String mysqlstring = tmpsqlserver+host+":"+port+";DatabaseName="+dbname;
            	System.out.println("sqlserverstring is : "+mysqlstring);
            	conn = DriverManager.getConnection(mysqlstring,user,password);
        		dbConnections.put(sqlserver, conn); 
        		}
        }        
        return dbConnections.get(dbName);
	}
	
	
	public static List<String> getSelectQueryResult(Connection con,String query) throws Exception {
		
		System.out.println("connection con is :"+con.toString());
		Statement stmt = null;
		ResultSet resultset = null;	
		ResultSetMetaData rsmd = null;
		int colCount = 0;
		StringBuilder resultbuilder = null;
		List<String> list = new ArrayList<String>();
		try {
			stmt = con.createStatement();		
			resultset = stmt.executeQuery(query);	
			rsmd=resultset.getMetaData();
			colCount = rsmd.getColumnCount();
			int loop = 1;
			
			
			while(resultset.next()) {
				resultbuilder = new StringBuilder();
				while(loop <=colCount) {
					resultbuilder.append(resultset .getString(loop));
					if((loop+1) > colCount) {
						list.add(resultbuilder.toString());
						resultbuilder = null;
						break;
					}				
					resultbuilder.append(" ");
					loop++;
				}
				//list.add(resultbuilder.toString());
				loop = 1;
			}
			
		}catch(Exception e) {			
		}
		finally {
			if(resultset!=null) {
				resultset.close();
			}
			if(stmt!=null) {			
				stmt.close();			
			}
		}
		return list;
	}
	

	public static boolean insertQuery(Connection con,String query) throws Exception {
		Statement stmt = null;
		int rowsUpdated = 0;
		boolean result = false;
		try {
			
			stmt = con.createStatement();
			rowsUpdated = stmt.executeUpdate(query);
			System.out.println("Rows Inserted are : "+rowsUpdated);
			
			if(rowsUpdated > 0) {
				result = true;
			}
			
			
		}catch(Exception e) {			
		}
		finally {			
			if(stmt!=null) {			
				stmt.close();			
			}
		}
		return result;
	}
	
	public static boolean createTableQuery(Connection con,String query) throws Exception {
		Statement stmt = null;
		int tableCreated = -1;
		boolean result = false;
		try {
			
			stmt = con.createStatement();
			stmt.executeUpdate(query);
			result = true;
			System.out.println("Table created successfully");
			
		}catch(Exception e) {	
			
			System.out.println("Exception generated while creating table  ");
			result = false;
		}
		finally {			
			if(stmt!=null) {			
				stmt.close();			
			}
		}
		return result;
	}
	
	
	public static boolean dropTableQuery(Connection con,String query) throws Exception {
		Statement stmt = null;
		int tableCreated = -1;
		boolean result = false;
		try {
			
			stmt = con.createStatement();
			stmt.executeUpdate(query);
			result = true;
			System.out.println("Table dropped successfully");
			
		}catch(Exception e) {	
			System.out.println("Exception generated while dropping table ");
			result = false;
		}
		finally {			
			if(stmt!=null) {			
				stmt.close();			
			}
		}
		return result;
	}
	
	public static boolean updateQuery(Connection con,String query) throws Exception {
		Statement stmt = null;
		int rowsUpdated = 0;
		boolean result = false;
		try {
			
			stmt = con.createStatement();
			rowsUpdated = stmt.executeUpdate(query);
			System.out.println("Rows updated are : "+rowsUpdated);
			
			if(rowsUpdated > 0) {
				result = true;
			}			
			
		}catch(Exception e) {			
		}
		finally {			
			if(stmt!=null) {			
				stmt.close();			
			}
		}
		return result;
	}
	
	
	
	/*
	 * public static void main(String ar[]) throws Exception {
	 * 
	 * List<String> sb = getSelectQueryResult(getConnection("mysql"),
	 * "select * from members");
	 * 
	 * for(String arrList : sb) { System.out.println(arrList); }
	 * 
	 * //System.out.println(sb.toString());
	 * //System.out.println(sb.toString().indexOf("Ambica 32"));
	 * 
	 * //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
	 * 
	 * String insert = "UPDATE members SET age = 3 WHERE NAME = 'Takshu'";
	 * 
	 * System.out.println("Inserted results status is : "+
	 * updateQuery(getConnection("mysql"),insert));
	 * 
	 * List<String> sb2 = getSelectQueryResult(getConnection("mysql"),
	 * "select * from members");
	 * 
	 * for(String arrList : sb2) { System.out.println(arrList); } }
	 * 
	 */
	
	public static void main(String a[]) throws Exception {
		/*
		 * String tableCreation =
		 * "CREATE TABLE members_code2 (NAME VARCHAR(255),ID INTEGER)";
		 * 
		 * System.out.println(DatabaseConnection.createTableQuery(DatabaseConnection.
		 * getConnection("mysql"),tableCreation));
		 * 
		 * String tableDeletion = "DROP TABLE members_code2";
		 * 
		 * System.out.println(DatabaseConnection.dropTableQuery(DatabaseConnection.
		 * getConnection("mysql"),tableDeletion));
		 */
		
		/*
		 * String tableCreationsqlserver =
		 * "CREATE TABLE SSMembers (NAME varchar(255),ID int)";
		 * System.out.println(DatabaseConnection.createTableQuery(DatabaseConnection.
		 * getConnection("sqlserver"),tableCreationsqlserver));
		 * 
		 * String insertSQLQuery =
		 * "INSERT INTO SSMembers (NAME,ID) VALUES('BATHULA',100)";
		 * 
		 * insertQuery(getConnection("sqlserver"),insertSQLQuery);
		 * 
		 * String sqlselect = "SELECT * from SSMembers"; List<String> sb =
		 * getSelectQueryResult(getConnection("sqlserver"), sqlselect); for(String
		 * arrList : sb) { System.out.println(arrList); }
		 */
	}
}