package zftestsuites;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import utilities.DatabaseUtility;

public class NewTest {
  @Test
  public void test1() throws Exception {
	  System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% INTO Testng %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
	  
	  String tableCreation =  "CREATE TABLE members_code3 (NAME varchar(255),ID int)";
	  
	  System.out.println("The sql string is : "+ tableCreation);
	  System.out.println("The connection string from testng test is : "+DatabaseUtility. getConnection("sqlserver"));
	  
	  System.out.println("Table created :"+DatabaseUtility.createTableQuery(DatabaseUtility. getConnection("sqlserver"),tableCreation));
	  
	  String tableDeletion = "DROP TABLE members_code3";
	  
	  System.out.println("Table deleted is :"+DatabaseUtility.dropTableQuery(DatabaseUtility.getConnection("sqlserver"),tableDeletion));
	  
  }
  @BeforeMethod
  public void beforeMethod() {
	  
  }

  @AfterMethod
  public void afterMethod() {
  }

}
