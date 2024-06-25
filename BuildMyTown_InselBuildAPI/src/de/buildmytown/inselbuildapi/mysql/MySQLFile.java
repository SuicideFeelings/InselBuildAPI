package de.buildmytown.inselbuildapi.mysql;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

public class MySQLFile {

	private String host;
	private String database;
	private String username;
	private String password;
	
	public MySQLFile(final String path) {
		this.createFile(path);
	}

    private void createFile(final String path) {
        File file = new File(path);
        YamlConfiguration mysql = YamlConfiguration.loadConfiguration(file);

        if(!mysql.contains("host")) {
            mysql.set("host", "127.0.0.1");
        }
        
        if(!mysql.contains("database")) {
            mysql.set("database", "Database");
        }
        if(!mysql.contains("username")) {
            mysql.set("username", "Username");
        }
        if(!mysql.contains("password")) {
            mysql.set("password", "Password");
        }

        try {
            mysql.save(file);
        }catch(Exception e) {
        	
        }

        host = mysql.getString("host");
        database = mysql.getString("database");
        username = mysql.getString("username");
        password = mysql.getString("password");
    }

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public String getDatabase() {
		return database;
	}
	
	public String getHost() {
		return this.host;
	}
	
}
