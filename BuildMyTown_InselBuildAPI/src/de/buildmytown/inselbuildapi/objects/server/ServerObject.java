package de.buildmytown.inselbuildapi.objects.server;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import de.buildmytown.inselbuildapi.InselBuildAPI;
import de.buildmytown.inselbuildapi.objects.types.PlayerTask;
import de.buildmytown.inselbuildapi.objects.types.ServerTask;
import de.buildmytown.inselbuildapi.objects.types.ServerType;

public class ServerObject{
	
	private final ServerType type;
	private final Plugin plugin;
	private final InselBuildAPI api;
	
	public ServerObject(final InselBuildAPI plugin, final ServerType type) {
		this.type = type;
		this.plugin = plugin.getPlugin();
		this.api = plugin;
	}
	
	public void sendPlayer(Player p) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("Connect");
			out.writeUTF(this.type.getName());
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		p.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
	}
	
	public void sendPlayerTask(Player p, PlayerTask task) {
		p.sendPluginMessage(plugin, api.getData().getPluginChannel(), task.getData().getBytes());
	}
	
	public void sendServerTask(ServerTask task) {
		this.plugin.getServer().sendPluginMessage(plugin, api.getData().getPluginChannel(), task.getData().getBytes());
	}
	
	
	public ServerType getType() {
		return this.type;
	}

}
