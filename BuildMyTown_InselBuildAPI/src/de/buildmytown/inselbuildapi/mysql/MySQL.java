package de.buildmytown.inselbuildapi.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;

import de.buildmytown.inselbuildapi.InselBuildAPI;

public class MySQL {

private final InselBuildAPI plugin;
	
	public MySQL(final InselBuildAPI plugin) {
		this.plugin = plugin;
	}
	
	private int attempts = 0;

    public String port = "3306";

    public Connection con;

    public boolean isConnected(){
        return con != null;
    }

    public void connect(){
        if(!isConnected()){
            try {

                con = DriverManager.getConnection("jdbc:mysql://" + plugin.getData().getMySQLConfig().getHost() + ":" + port + "/" + plugin.getData().getMySQLConfig().getDatabase() + "?autoReconnect=true", plugin.getData().getMySQLConfig().getUsername(), plugin.getData().getMySQLConfig().getPassword());
                Bukkit.getConsoleSender().sendMessage(plugin.getPrefix() + "§aEs konnte erfolgreich mit der Datenbank verbunden werden");
                /* Create Tables */
               plugin.getData().getOnlineTimeManager().createTables();
            } catch (SQLException e) {
                Bukkit.getConsoleSender().sendMessage(plugin.getPrefix() + "§cEs konnte nicht mit der Datenbank verbunden werden");
            }
        }
    }

    public void disconnect(){
        try {
            con.close();
            Bukkit.getConsoleSender().sendMessage(plugin.getPrefix() + "§aDie Verbindung zur Datenbank konnte erfolgreich geschlossen werden");
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(plugin.getPrefix() + "§cDie Verbindung zur Datenbank konnte nicht geschlossen werden");
        }
    }

    private PreparedStatement getStatement(String sql){
        if(isConnected()){
            PreparedStatement ps;
            try {
                ps = con.prepareStatement(sql);
                return ps;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public ResultSet getResult(String sql){
    	if(attempts < 5) {
            PreparedStatement ps;
            ResultSet rs;
            try {
                ps = getStatement(sql);
                rs = ps.executeQuery();
                attempts = 0;
                return rs;
            } catch (SQLException e) {
            	connect();
                attempts++;
                getResult(sql);
            }
    	}
        return null;
    }

    public void update(String qry) {
    	if(attempts < 5) {
        try {
            java.sql.Statement st = con.createStatement();
            st.executeUpdate(qry);
            attempts = 0;
            st.close();
        } catch (SQLException e) {
            connect();
            attempts++;
            update(qry);
        }
    	}
    }

	
	
}
