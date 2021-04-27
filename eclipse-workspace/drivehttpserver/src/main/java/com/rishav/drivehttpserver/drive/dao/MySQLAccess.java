package com.rishav.drivehttpserver.drive.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import com.rishav.drivehttpserver.drive.Ride;

public class MySQLAccess {
	private static MySQLAccess mysqlaccess = null;
	
	public static MySQLAccess getInstance() throws Exception 
    { 
        if (mysqlaccess == null) 
        	mysqlaccess = new MySQLAccess(); 
  
        return mysqlaccess; 
    } 
	
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public MySQLAccess() throws Exception {
		openDataBase();
	}
	
	public void openDataBase() throws Exception {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/drive?"
                            + "");

            
            //preparedStatement = connect
            //        .prepareStatement("SELECT * FROM User;");
            //resultSet = preparedStatement.executeQuery();
            //writeResultSet(resultSet);
            
            //CreateRide(new Ride("Start", "End", "Jack", "Started", 3.45, 5));

            /* Remove again the insert comment
            preparedStatement = connect
            .prepareStatement("delete from Drive.comments where myuser= ? ; ");
            preparedStatement.setString(1, "Test");
            preparedStatement.executeUpdate();
            

            resultSet = statement
            .executeQuery("select * from feedback.comments");
            writeMetaData(resultSet);
            */

        } catch (Exception e) {
            throw e;
        }
    }
	
	public ResultSet Read(String query) throws SQLException {
		preparedStatement = connect.prepareStatement(query);
	    return preparedStatement.executeQuery();
	}
	
	private void writeMetaData(ResultSet resultSet) throws SQLException {
        //  Now get some metadata from the database
        // Result set get the result of the SQL query

        System.out.println("The columns in the table are: ");

        System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
        for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
            System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
        }
    }
	
	public void CreateRide(Ride ride) throws Exception {
		try {
			System.out.println("INSERT INTO Ride(Start, End, Driver, Status, Price, CarID) Values (" + ride.Description() + ");");
			preparedStatement = connect
			        .prepareStatement("INSERT INTO Ride(Start, End, Driver, Status, Price, CarID) Values (" + ride.Description() + ");");
			int res = preparedStatement.executeUpdate();
			//writeResultSet(resultSet);
		 } catch (Exception e) {
	            throw e;
		 } 
	}
	
	public void Write(String update) throws Exception {
		try {
			preparedStatement = connect.prepareStatement(update);
			int res = preparedStatement.executeUpdate();
			//writeResultSet(resultSet);
		 } catch (Exception e) {
	            throw e;
		 }
	}

    private void writeResultSet(ResultSet resultSet) throws SQLException {
        // ResultSet is initially before the first data set
        while (resultSet.next()) {
            // It is possible to get the columns via name
            // also possible to get the columns via the column number
            // which starts at 1
            // e.g. resultSet.getSTring(2);
            String user = resultSet.getString("Username");
            String website = resultSet.getString("Email");
            String summary = resultSet.getString("Driver");
            System.out.println("Username: " + user);
            System.out.println("Email: " + website);
            System.out.println("Driver: " + summary);
        }
    }

    // You need to close the resultSet
    public void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }
}
