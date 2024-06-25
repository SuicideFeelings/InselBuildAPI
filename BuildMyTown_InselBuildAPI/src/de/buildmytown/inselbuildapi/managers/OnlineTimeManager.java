package de.buildmytown.inselbuildapi.managers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;

import de.buildmytown.inselbuildapi.InselBuildAPI;

public class OnlineTimeManager {
private InselBuildAPI plugin;
	
	public OnlineTimeManager(final InselBuildAPI plugin) {
		this.plugin = plugin;
	}

	public void createTables() {

        plugin.getData().getMySQL().update("CREATE TABLE IF NOT EXISTS OnlineTime(Name VARCHAR(16), UUID VARCHAR(64), Time int);");

    }

    public void registerPlayer(Player p)
    {
        if(!existPlayer(p.getUniqueId().toString()))
        {
            plugin.getData().getMySQL().update("INSERT INTO OnlineTime(Name, UUID, Time) VALUES"
                    + "('" + p.getName() + "', '" + p.getUniqueId() + "', '0');");

        }
        else
        {
            plugin.getData().getMySQL().update("UPDATE OnlineTime SET Name='" + p.getName() + "' WHERE UUID='" + p.getUniqueId().toString() + "'");
        }
    }


    public String getUUIDbyName(String playername) {
        String i = "";
        try {
            ResultSet rs = plugin.getData().getMySQL()
                    .getResult("SELECT * FROM OnlineTime WHERE Name= '" + playername + "'");

            if ((!rs.next()) || (String.valueOf(rs.getString("UUID")) == null))
                ;

            i = rs.getString("UUID");

        } catch (SQLException e) {

        }
        return i;
    }

    public int getTime(String uuid)
    {
        int i = 0;
        try {
            ResultSet rs = plugin.getData().getMySQL()
                    .getResult("SELECT * FROM OnlineTime WHERE UUID= '" + uuid + "'");

            if ((!rs.next()) || (String.valueOf(rs.getString("Time")) == null))
                ;

            i = rs.getInt("Time");

        } catch (SQLException e) {

        }
        return i;
    }

    public void addTime(String uuid, int Amount)
    {
        int time = getTime(uuid) + Amount;
        plugin.getData().getMySQL().update("UPDATE OnlineTime SET Time='" + time + "' WHERE UUID='" + uuid + "'");
    }


    public boolean existPlayer(String uuid) {
        try {
            ResultSet rs = plugin.getData().getMySQL().getResult("SELECT * FROM OnlineTime WHERE UUID= '" + uuid + "'");

            if (rs.next()) {
                return rs.getString("UUID") != null;
            }
            rs.close();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean existPlayerName(String name) {
        try {
            ResultSet rs = plugin.getData().getMySQL().getResult("SELECT * FROM OnlineTime WHERE Name= '" + name + "'");

            if (rs.next()) {
                return rs.getString("Name") != null;
            }
            rs.close();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public String getOnlineTime(String uuid) {
        int minutes = getTime(uuid);
        int hours = 0;
        while(minutes > 60){
        	minutes -= 60;
        	hours++;
        }
        return  hours + " Stunden";
        
    }

	
}

