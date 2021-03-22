package com.example.database;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import org.h2.tools.Server;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Arrays;
import java.util.Properties;

import static java.lang.System.exit;

public class DBdemo {

    private static final String DB_URL = "jdbc:h2:mem:sample"; //memory database στο heap| name: sample
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "";
    private final Properties sqlCommands = new Properties(); //η διεύθυνση στην μνήμη που έχει πάρει το object properties
private final Lorem generator = LoremIpsum.getInstance();
    private Server h2Server;


    public static void main(String[] args)  {
        DBdemo demo = new DBdemo();
        // Load SQL commands
        demo.loadSqlCommands();

        // Start H2 database server
        demo.startH2Server();
        // Register JDBC driver and retrieve a connection
        Connection connection = demo.registerDatabaseDriver();

       //θα θέλει commit
        enableTrancactionMode(connection);


        // Create table
        demo.createTable(connection);
        // Insert Data
        demo.InsertTable(connection);
        demo.InsertTableBatch(connection);
        demo.InsertTableBatchGenerator(connection);
        try {
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //
        demo.SelectTable(connection);
        demo.UpdateTable(connection);
        demo.SelectTable(connection);
        // SQL Commands
        demo.DeleteTable(connection);
        //Introduce artificial delay
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Stop H2 database server
        demo.stopH2Server();
    }

    private static void enableTrancactionMode(Connection connection) {
        try {
            connection.setAutoCommit(false);  //Εάν βάλω false θα πρέπει να κάνω commit ή rollback
        } catch (SQLException throwables) {

            throwables.printStackTrace();
        }
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
            resultRows  = statement.executeUpdate(sqlCommands.getProperty("insert.table.REGISTRATION"));
            resultRows += statement.executeUpdate(sqlCommands.getProperty("insert.table.REGISTRATION2"));
            resultRows += statement.executeUpdate(sqlCommands.getProperty("insert.table.REGISTRATION3"));
            resultRows += statement.executeUpdate(sqlCommands.getProperty("insert.table.REGISTRATION4"));
            resultRows += statement.executeUpdate(sqlCommands.getProperty("insert.table.REGISTRATION5"));

            System.out.println("Statement inserted " + resultRows);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.err.println("Error occurred while inserting data");
        }

    }

    private void InsertTableBatch(Connection connection) {
        try ( PreparedStatement prStatement = connection.prepareStatement(sqlCommands.getProperty("insertBatch.table.REGISTRATION"))) {
            prStatement.setLong(1,6);
            prStatement.setString(2,"Manos");
            prStatement.setString(3,"Rallis");
            prStatement.setInt(4,10);
            prStatement.addBatch();
            prStatement.clearParameters();

            prStatement.setLong(1,7);
            prStatement.setString(2,"Anastasios");
            prStatement.setString(3,"Rallis");
            prStatement.setInt(4,18);
            prStatement.addBatch();

            int[] resultRows =prStatement.executeBatch();
            System.out.println("Statement inserted " + Arrays.stream(resultRows).sum());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.err.println("Error occurred while inserting data");
        }

    }

    private void generateData (PreparedStatement prStatement, int HowMany) throws SQLException {
        for (int i=0; i<HowMany; i++) {
            prStatement.clearParameters();
            prStatement.setLong(1, 8 + i);
            prStatement.setString(2, generator.getFirstName());
            prStatement.setString(3, generator.getLastName());
            prStatement.setInt(4, 50);
            prStatement.addBatch();
        }
    }

    private void InsertTableBatchGenerator(Connection connection) {
        try ( PreparedStatement prStatement = connection.prepareStatement(sqlCommands.getProperty("insertBatch.table.REGISTRATION"))) {

            generateData(prStatement,10);
            int[] resultRows =prStatement.executeBatch();
            System.out.println("Statement inserted " + Arrays.stream(resultRows).sum());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.err.println("Error occurred while inserting data");
        }

    }



    private void SelectTable(Connection connection) {
        try (Statement statement = connection.createStatement();
             ResultSet rs= statement.executeQuery(sqlCommands.getProperty("select.table.REGISTRATION")) )
        {
             while (rs.next())
         {
             System.out.printf("ID: %s. Name: %s. Age: %s ",rs.getLong("ID"),rs.getString("LASTNAME"),rs.getInt("AGE") );
             System.out.println();
         }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.err.println("Error occurred while selecting data");
        }

    }

    private void UpdateTable(Connection connection) {
        String query ="Update REGISTRATION set Age =AGE+5 where FIRSTNAME=?";
        try{
            PreparedStatement prStatement = connection.prepareStatement(query);
           // prStatement.setInt(1,8);
            prStatement.setString(1,"Xara");
          int resultRowsUpdated=  prStatement.executeUpdate();
            System.out.println("Statement inserted " + resultRowsUpdated);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.err.println("Error occurred while updating data");
        }

    }

    private void DeleteTable(Connection connection) {
    }


}
