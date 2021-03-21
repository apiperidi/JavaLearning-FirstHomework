package com.example.database;

import org.h2.tools.Server;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

import static java.lang.System.exit;

public class DBdemo {

    private static final String DB_URL = "jdbc:h2:mem:sample"; //memory database| name: sample
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "";
    private final Properties sqlCommands = new Properties(); //η διεύθυνση στην μνήμη που έχει πάρει το object properties

    private Server h2Server;


    public static void main(String[] args) throws InterruptedException {
        DBdemo demo = new DBdemo();
        // Load SQL commands
        demo.loadSqlCommands();

        // Start H2 database server
        demo.startH2Server();
        // Register JDBC driver and retrieve a connection
        Connection connection = demo.registerDatabaseDriver();
        // Create table
        demo.createTable(connection);
        // Insert Data
demo.InsertTable(connection);
demo.SelectTable(connection);
demo.UpdateTable(connection);
        demo.SelectTable(connection);
        // SQL Commands

        //Introduce artificial delay
        Thread.sleep(3000);

        // Stop H2 database server
        demo.stopH2Server();
    }

    private void loadSqlCommands() {
        try (InputStream inputStream = DBdemo.class.getClassLoader().getResourceAsStream("sql.properties")) {
            System.out.println("ddd");
            if (inputStream == null) {
                System.err.println("Unable to load SQL commands");
                exit(-1);
            }
            sqlCommands.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startH2Server() {
        try {
            //tcpAllowOthers: Να συνδεθούν και άλλοι
            //tcpDaemon : Να σηκωθεί σαν service
            h2Server = Server.createTcpServer("-tcpAllowOthers", "-tcpDaemon");
            h2Server.start();
            System.out.println("H2 Database server is now accepting connections.");
        } catch (SQLException throwables) {
            System.err.println("Unable to start H2 database server.");
            throwables.printStackTrace();
            exit(-1);
        }
    }

    private Connection registerDatabaseDriver() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException throwables)
        //Timeout όταν ο driver δεν μπορεί να συνδεθεί σ' ενα προκαθορισμένο διάστημα
                //και όταν το url είναι null - ότι δεν βρήκε τον driver
        {
            System.err.println("Unable to get a connection from H2 database server.");
            throwables.printStackTrace();
            exit(-1);
        }
        return connection;
    }

    private void createTable(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            int resultRows = statement.executeUpdate(sqlCommands.getProperty("create.table.REGISTRATION"));

            System.out.println("Statement returned " + resultRows);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {

        }
    }

    private void stopH2Server() {
        if (h2Server == null)  // Εάν ο server δεν υπάρχει
        {
            return;
        }
        if (h2Server.isRunning(true))  //Εάν ο server τρέχει
        {
            h2Server.stop();
            h2Server.shutdown();
            System.out.println("H2 Database server has been shutdown.");
        }
    }


    private void InsertTable(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            int resultRows=0;
            resultRows= statement.executeUpdate(sqlCommands.getProperty("insert.table.REGISTRATION"));
            resultRows += statement.executeUpdate(sqlCommands.getProperty("insert.table.REGISTRATION2"));
            System.out.println("Statement inserted " + resultRows);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {

        }
    }


    private void SelectTable(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            ResultSet rs= statement.executeQuery(sqlCommands.getProperty("select.table.REGISTRATION"));
         while (rs.next())
         {
             System.out.printf("ID: %s. Name : %s, Age: %s ",rs.getInt("ID"),rs.getString("LASTNAME"),rs.getInt("AGE") );
             System.out.println();
         }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {

        }
    }

    private void UpdateTable(Connection connection) {
        String query ="Update REGISTRATION set Age =? where FIRSTNAME=?";
        try{
            PreparedStatement prStatement = connection.prepareStatement(query);
            prStatement.setInt(1,8);
            prStatement.setString(2,"Xara");
          int resultRowsUpdated=  prStatement.executeUpdate();
            System.out.println("Statement inserted " + resultRowsUpdated);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {

        }
    }
}
