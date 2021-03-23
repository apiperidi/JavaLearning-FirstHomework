package com.example.database;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;
import static java.lang.System.exit;



public class DBdemoLogin {
    private static final Logger loggerAng = LoggerFactory.getLogger(DBdemoLogin.class);
    private static final String DB_URL = "jdbc:h2::sample"; //memory database στο heap| name: sample
    // private static final String DB_URL = "jdbc:h2:~/sample"; //arxeia sto home directory
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "";
    private final Properties sqlCommands = new Properties(); //η διεύθυνση στην μνήμη που έχει πάρει το object properties
    private final Lorem generator = LoremIpsum.getInstance(); // random names
    private Server h2Server, webServer; //http://localhost:8082/ kai JDBC url: jdbc:h2:mem:sample
    private HikariDataSource hikariDatasource;




    public static void main(String[] args) throws InterruptedException {
        DBdemoLogin demo = new DBdemoLogin();
        // Load SQL commands
        demo.loadSqlCommands();

        // Start H2 database server
        demo.startH2Server();
        // Register JDBC driver and retrieve a connection
       // Connection connection = demo.registerDatabaseDriver();
        demo.initiateConnectionPooling();


     /*   εάν η βάση δεν είναι στο memory
     if (demo.BooleancreateTable()) {
            // Insert data
            demo.insertTableBatchGenerator();
        }*/


        // Create table
        demo.createTable();
        // Insert Data

        demo.insertTableBatchGenerator();
        demo.SelectTable();
        demo.deleteTable();
        demo.SelectTable();
        // SQL Commands

        //Introduce artificial delay

        Thread.sleep(3000);


        // Stop H2 database server
        // Stop H2 database server via shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> demo.stopH2Server()));

        while (true) {
        }
    }

    private void initiateConnectionPooling() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.h2.Driver"); //δηλώνω τον driver
        config.setJdbcUrl(DB_URL);
        config.setUsername(DB_USERNAME);
        config.setPassword(DB_PASSWORD);

        config.setConnectionTimeout(10000);
        config.setIdleTimeout(60000);
        config.setMaxLifetime(1800000);
        config.setMinimumIdle(1);
        config.setMaxLifetime(5);
        config.setAutoCommit(true);

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtsCacheSize", "500");
        hikariDatasource = new HikariDataSource(config);
    }

    private void loadSqlCommands() {
        try (InputStream inputStream = DBdemo.class.getClassLoader().getResourceAsStream("sql.properties")) {
            System.out.println("ddd");
            if (inputStream == null) {
                loggerAng.error("warn enableTranscactionMode");

                exit(-1);
            }
            sqlCommands.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startH2Server() {

            //tcpAllowOthers: Να συνδεθούν και άλλοι
            //tcpDaemon : Να σηκωθεί σαν service
        try {
            h2Server = Server.createTcpServer("-tcpAllowOthers", "-tcpDaemon");
            h2Server.start();
            webServer = Server.createWebServer("-webAllowOthers", "-webDaemon");
            webServer.start();
            loggerAng.info("H2 Database server is now accepting connections.");
        } catch (SQLException throwables) {
            loggerAng.error("Unable to start H2 database server.", throwables);
            exit(-1);
        }
        loggerAng.info("H2 server has started with status '{}'.", h2Server.getStatus());


    }

    private void createTable() {
        try (Statement statement = hikariDatasource.getConnection().createStatement()) {
            int resultRows = statement.executeUpdate(sqlCommands.getProperty("create.table.REGISTRATION"));

            loggerAng.info("Statement returned {}" , resultRows);

        } catch (SQLException throwables) {
            loggerAng.error("Unable to create table",throwables);
exit(-1);
        }

    }

    private boolean BooleancreateTable() {
        try (Statement statement = hikariDatasource.getConnection().createStatement()) {
            int resultRows = statement.executeUpdate(sqlCommands.getProperty("create.table.REGISTRATION"));

            loggerAng.debug("Statement returned {}.", resultRows);
            return true;
        } catch (SQLException throwables) {
            loggerAng.warn("Unable to create target database table. It already exists.");
            return false;
        }
    }


    private void stopH2Server() {

        if (h2Server == null || webServer == null) { // Εάν ο server δεν υπάρχει
            return;
        }

        if (h2Server.isRunning(true)) { //Εάν ο server τρέχει
            h2Server.stop();
            h2Server.shutdown();
        }
        if (webServer.isRunning(true)) {
            webServer.stop();
            webServer.shutdown();
        }
        loggerAng.info("H2 Database server has been shutdown.");

    }

    private void generateData (PreparedStatement prStatement, int HowMany) throws SQLException {
        for (int i=0; i<HowMany; i++) {
            prStatement.clearParameters();
            prStatement.setLong(1, 8 + i);
            prStatement.setString(2, generator.getFirstName());
            prStatement.setString(3, generator.getLastName());
            prStatement.setInt(4, ThreadLocalRandom.current().nextInt(18,70));
            prStatement.addBatch();
        }
    }

    private void insertTableBatchGenerator() {
        try ( PreparedStatement prStatement = hikariDatasource.getConnection().prepareStatement(sqlCommands.getProperty("insertBatch.table.REGISTRATION"))) {

            generateData(prStatement,10);
            int[] resultRows =prStatement.executeBatch();
            System.out.println("Statement inserted " + Arrays.stream(resultRows).sum());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.err.println("Error occurred while inserting data");
        }

    }

    private void SelectTable() {
        try (Statement statement = hikariDatasource.getConnection().createStatement();
             ResultSet rs= statement.executeQuery(sqlCommands.getProperty("select.table.REGISTRATION")) )
        {
            while (rs.next())
            {
                loggerAng.info("ID: {} Name: {}. Age: {}",rs.getLong("ID"),rs.getString("LASTNAME"),rs.getInt("AGE") );
              //  System.out.println();
            }

        } catch (SQLException throwables) {
            loggerAng.error("Error occurred while selecting data",throwables);

        }

    }

    private void updateTable(Connection connection) {
        String query ="Update REGISTRATION set Age =AGE+5 where FIRSTNAME=?";
        try{
            PreparedStatement prStatement = connection.prepareStatement(query);
            // prStatement.setInt(1,8);
            prStatement.setString(1,"Xara");
            int resultRowsUpdated=  prStatement.executeUpdate();
            System.out.println("Statement inserted " + resultRowsUpdated);

        } catch (SQLException throwables) {
            loggerAng.error("Error occurred while updating data",throwables);


        }

    }

    private void deleteTable() {
        try (Statement statement = hikariDatasource.getConnection().createStatement()) {
            int resultRows = statement.executeUpdate(sqlCommands.getProperty("delete.table.REGISTRATION"));

            System.out.println("Statement Delete " + resultRows);

        } catch (SQLException throwables) {
            loggerAng.error("Error occurred while deleting data",throwables);
        }
    }


}

