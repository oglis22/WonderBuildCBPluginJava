package dev.lupluv.cb.mysql;

import dev.lupluv.cb.Citybuild;

import java.sql.*;

public class MySQL {

    public static final String mysqlPrefix = "§6§l[CitybuildSystem - MySQL] §7";

    public MySQL(String host, String port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    String host;
    String port;
    String database;
    String username;
    String password;
    Connection con;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public void connect() {
        if(!isConnected()){
            Citybuild.sendColoredConsoleMessage(mysqlPrefix + "Connecting to the Database.");
            Citybuild.sendColoredConsoleMessage(mysqlPrefix + "Host: '" + host + "'");
            Citybuild.sendColoredConsoleMessage(mysqlPrefix + "Port: '" + port + "'");
            Citybuild.sendColoredConsoleMessage(mysqlPrefix + "Database: '" + database + "'");
            Citybuild.sendColoredConsoleMessage(mysqlPrefix + "Username: '" + username + "'");
            Citybuild.sendColoredConsoleMessage(mysqlPrefix + "Password: '********'");
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&characterEncoding=utf8&useUnicode=true", username, password);
                Citybuild.sendColoredConsoleMessage(mysqlPrefix + "Successfully connected to the Database. §4❤");
            } catch (SQLException e) {
                Citybuild.sendColoredConsoleMessage(mysqlPrefix + "An error occurred while closing the Database Connection.");
                e.printStackTrace();
            }
        }
    }

    public void disconnect() {
        if(isConnected()){
            Citybuild.sendColoredConsoleMessage(mysqlPrefix + "Closing the Database Connection.");
            try {
                con.close();
                Citybuild.sendColoredConsoleMessage(mysqlPrefix + "Successfully closed Database Connection. §4✘");
            } catch (SQLException e) {
                Citybuild.sendColoredConsoleMessage(mysqlPrefix + "An error occurred while closing the Database Connection.");
                e.printStackTrace();
            }

        }
    }

    public boolean isConnected(){
        return (con == null ? false : true);
    }

    public void update(String qry){
        try {
            PreparedStatement ps = con.prepareStatement(qry);
            ps.executeUpdate();
        } catch (SQLException e) {
            Citybuild.sendColoredConsoleMessage(mysqlPrefix + "An error occurred while updating MySQL table.");
            e.printStackTrace();
        }
    }

    public void update(String qry, String... args){
        try {
            PreparedStatement ps = con.prepareStatement(qry);
            for (int i = 1; i <= args.length; i++) {
                ps.setString(i, args[i-1]);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            Citybuild.sendColoredConsoleMessage(mysqlPrefix + "An error occurred while updating MySQL table.");
            e.printStackTrace();
        }
    }

    public ResultSet getResult(String qry) {
        try {
            PreparedStatement ps = con.prepareStatement(qry);
            return ps.executeQuery();
        } catch (SQLException e) {
            Citybuild.sendColoredConsoleMessage(mysqlPrefix + "An error occurred while reading MySQL table.");
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getResult(String qry, String... args) {
        try {
            PreparedStatement ps = con.prepareStatement(qry);
            for (int i = 1; i <= args.length; i++) {
                ps.setString(i, args[i-1]);
            }
            return ps.executeQuery();
        } catch (SQLException e) {
            Citybuild.sendColoredConsoleMessage(mysqlPrefix + "An error occurred while reading MySQL table.");
            e.printStackTrace();
        }
        return null;
    }

}
