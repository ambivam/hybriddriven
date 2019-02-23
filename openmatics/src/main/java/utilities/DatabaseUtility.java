package utilities;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import dataproviders.ConfigFileReader;

public class DatabaseUtility {

	
	private static Map<String, Connection> dbConnections = new HashMap<String, Connection>();
	
	public DatabaseUtility() {			
		
	}
	
	/*
	 * ****************************************************************************** 
	 * Name : getConnection 
	 * Parameters : dbname (Database Name) 
	 * Purpose : To establish the connection with the specified database
	 * ******************************************************************************
	*/
	
	public static Connection getConnection(String dbName)  {
		Connection conn = null;
		String mysql = ConfigFileReader.getConfigFileReader().getMySqlServer();
		String sqlserver = ConfigFileReader.getConfigFileReader().getSqlServer();
				
        switch (dbName) {        
        
	        case "mysql" :
	        	try {
		        	if(dbConnections.get(dbName) == null) {
		        		Log.info("Registering the MYSQL Connection string : "+ConfigFileReader.getConfigFileReader().getMySqlDriver());
		        		Class.forName(ConfigFileReader.getConfigFileReader().getMySqlDriver());            	
		            	String tmpmysql = "jdbc:mysql://";
		            	String host = ConfigFileReader.getConfigFileReader().getMySqlHost();
		            	String port = ConfigFileReader.getConfigFileReader().getMySqlPort();
		            	String dbname = ConfigFileReader.getConfigFileReader().getMySqlDatabase();
		            	String user = ConfigFileReader.getConfigFileReader().getMySqlUser();
		            	String password = ConfigFileReader.getConfigFileReader().getMySqlPassword();
		            	String mysqlstring = tmpmysql+host+":"+port+"/"+dbname+"?useSSL=false&allowPublicKeyRetrieval=true";
		            	Log.info("MYSQL connection string is : "+ mysqlstring);
		            	conn = DriverManager.getConnection(mysqlstring,user,password);
		        		dbConnections.put(mysql, conn); 
		        		Log.info("Connection with MYSql is established");
	        		}
	        	}catch(Exception e) {
	        		Log.error("An error has occured while establishing connection with mysql server");
	        		dbConnections.put(mysql, null);
	        	}
	        	break;
	        case "sqlserver" :
	        	try {
		        	if(dbConnections.get(dbName.trim()) == null) {
		        		
		        		System.out.println("INTO sql server case");
		        		Log.info("Registering the SQLServer Connection string : "+ConfigFileReader.getConfigFileReader().getSqlServerDriver());
		        		Class.forName(ConfigFileReader.getConfigFileReader().getSqlServerDriver()); 	        		
		            	String tmpsqlserver = "jdbc:sqlserver://";
		            	String host = ConfigFileReader.getConfigFileReader().getSqlServerHost();
		            	String port = ConfigFileReader.getConfigFileReader().getSqlServerPort();
		            	String dbname = ConfigFileReader.getConfigFileReader().getSqlServerDatabase();
		            	String user = ConfigFileReader.getConfigFileReader().getSqlUser();
		            	String password = ConfigFileReader.getConfigFileReader().getSqlPassword();
		            	String mysqlstring = tmpsqlserver+host+";instanceName=SQLEXPRESS;databaseName="+dbname;
		            	
		            	//String mysqlstring = tmpsqlserver+host+":"+port+";DatabaseName="+dbname;
		            	conn = DriverManager.getConnection(mysqlstring,user,password);
		            	
		        		dbConnections.put(dbName.trim(), conn); 
		        		Log.info("Connection with SQL Server is established");
	        		}
	        	}catch(Exception e) {
	        		Log.error("An error has occured while establishing connection with sqlserver server");
	        		dbConnections.put(dbName.trim(), null);
	        		
	        	}
	        	break;
	        default:
	        	Log.error("No Matching database server found !!!");
	        	dbConnections.put(dbName.trim(), null); 
        	}  
        	Log.info("Database Connection returned for database "+dbName+" is : "+dbConnections.get(dbName));
        	return dbConnections.get(dbName.trim());
		}
	
	/*
	 * ****************************************************************************** 
	 * Name : getSelectQueryResult 
	 * Parameters : con (Connection instance) ,query(select sql query string)
	 * Purpose : To return the list of selected rows filterd by selected select query
	 * ******************************************************************************
	*/
	public static List<String> getSelectQueryResult(Connection con,String query)  {
		
		
		Statement stmt = null;
		ResultSet resultset = null;	
		ResultSetMetaData rsmd = null;
		int colCount = 0;
		StringBuilder resultbuilder = null;
		List<String> list = null;
		
		
		try {
			list = new ArrayList<String>();
			try {
				stmt = con.createStatement();
				Log.info("Statement Instance Created successfull ");
			}catch(Exception e) {
				Log.error("Failed to create Statement Instance ");
			}
			try {
				resultset = stmt.executeQuery(query);	
				Log.info("Resultset Instance Created successfull ");
			}catch(Exception e) {
				Log.error("Failed to create Resultset Instance ");
			}
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
				loop = 1;
			}
			
		}catch(Exception e) 
		{	
			Log.error("An exception has occured while executing select query : "+query);
			list = null;
		}
		finally {
			if(resultset!=null) {
				try{
					resultset.close();		
					Log.info("Closing the ResultSet Instance");
				}catch(Exception e) {
					Log.error("An exception has occured while closing the Resultset instance");
				}
			}
			if(stmt!=null) {
				try{
					stmt.close();	
					Log.info("Closing the Statement Instance");
				}catch(Exception e) {
					Log.error("An exception has occured while closing the Statement instance");
				}
			}
		}
		return list;
	}
	
	/*
	 * ****************************************************************************** 
	 * Name : insertQuery 
	 * Parameters : con (Connection instance) ,query(Insert sql query string)
	 * Purpose : To insert the record specified in query string
	 * ******************************************************************************
	*/	
	
	public static boolean insertQuery(Connection con,String query) {
		Statement stmt = null;
		int rowsInserted = 0;
		boolean result = false;
		try {			
			try {
				stmt = con.createStatement();
				Log.info("Statement Instance Created successfull ");
			}catch(Exception e) {
				Log.error("Failed to create Statement instance");
			}
			rowsInserted = stmt.executeUpdate(query);
			Log.info("Rows Inserted are : "+rowsInserted);			
			if(rowsInserted > 0) {
				result = true;
			}			
			
		}catch(Exception e) {
			Log.error("An exception has occured while inserting the records");	
		}
		finally {			
			if(stmt!=null) 
				try{
					stmt.close();	
					Log.info("Closing the Statement Instance");
				}catch(Exception e) {
					Log.error("An exception has occured while closing the Statement instance");
				}
		}
		return result;
	}
	
	/*
	 * ****************************************************************************** 
	 * Name : createTableQuery 
	 * Parameters : con (Connection instance) ,query(Create table sql query string)
	 * Purpose : To create the table specified in query string
	 * ******************************************************************************
	*/	
	
	public static boolean createTableQuery(Connection con,String query) {
		Statement stmt = null;
		boolean result = false;
		try {
			
			try {
				stmt = con.createStatement();
				Log.info("Statement Instance Created successfull ");
			}catch(Exception e) {
				Log.error("Failed to create Statement instance");
			}
			stmt.executeUpdate(query);
			result = true;
			Log.info("Table created successfully");
			
		}catch(Exception e) {	
			
			Log.error("Exception generated while creating table  ");
			result = false;
		}
		finally {			
			if(stmt!=null) {			
				try{
					stmt.close();	
					Log.info("Closing the Statement Instance");
				}catch(Exception e) {
					Log.error("An exception has occured while closing the Statement instance");
				}		
			}
		}
		return result;
	}
	
	/*
	 * ****************************************************************************** 
	 * Name : dropTableQuery 
	 * Parameters : con (Connection instance) ,query(Drop table sql query string)
	 * Purpose : To drop the table specified in query string
	 * ******************************************************************************
	*/	
	
	public static boolean dropTableQuery(Connection con,String query)  {
		Statement stmt = null;		
		boolean result = false;
		try {
			
			try {
				stmt = con.createStatement();
				Log.info("Statement Instance Created successfull ");
			}catch(Exception e) {
				Log.error("Failed to create Statement instance");
			}
			stmt.executeUpdate(query);
			result = true;
			Log.info("Table dropped successfully");
			
		}catch(Exception e) {	
			Log.error("Exception generated while dropping table ");
			result = false;
		}
		finally {			
			if(stmt!=null) {			
				try{
					stmt.close();	
					Log.info("Closing the Statement Instance");
				}catch(Exception e) {
					Log.error("An exception has occured while closing the Statement instance");
				}		
			}
		}
		return result;
	}
	
	/*
	 * ****************************************************************************** 
	 * Name : updateQuery 
	 * Parameters : con (Connection instance) ,query(update sql query string)
	 * Purpose : To update the record specified in query string
	 * ******************************************************************************
	*/		
	
	public static boolean updateQuery(Connection con,String query)  {
		Statement stmt = null;
		int rowsUpdated = 0;
		boolean result = false;
		try {
			
			try {
				stmt = con.createStatement();
				Log.info("Statement Instance Created successfull ");
			}catch(Exception e) {
				Log.error("Failed to create Statement instance");
			}
			
			rowsUpdated = stmt.executeUpdate(query);
			Log.info("Rows updated are : "+rowsUpdated);
			
			if(rowsUpdated > 0) {
				result = true;
			}			
			
		}catch(Exception e) {	
			Log.error("Exception generated while Updating records ");
		}
		finally {			
			if(stmt!=null) {			
				try{
					stmt.close();	
					Log.info("Closing the Statement Instance");
				}catch(Exception e) {
					Log.error("An exception has occured while closing the Statement instance");
				}			
			}
		}
		return result;
	}
	
	//*************************************************************************************************************	
	
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
	
	
	 public static void main(String a[]) throws Exception 
	 {
	  
	  String tableCreation =  "CREATE TABLE members_code3 (NAME varchar(255),ID int)";
	  
	  System.out.println(DatabaseUtility.createTableQuery(DatabaseUtility. getConnection("sqlserver"),tableCreation));
	  
	  String tableDeletion = "DROP TABLE members_code2";
	  
	  System.out.println(DatabaseUtility.dropTableQuery(DatabaseUtility.getConnection("sqlserver"),tableDeletion));
	 }
	  
	 //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
	  
	  
	  /*String tableCreationsqlserver =
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
	 * 
	 * }*/
	
}