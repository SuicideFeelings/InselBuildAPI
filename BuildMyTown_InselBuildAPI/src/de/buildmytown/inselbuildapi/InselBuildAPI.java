package de.buildmytown.inselbuildapi;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import de.buildmytown.inselbuildapi.event.PluginMessageEvent;
import de.buildmytown.inselbuildapi.handlers.VaultHandler;
import de.buildmytown.inselbuildapi.listeners.JoinQuit;
import de.buildmytown.inselbuildapi.objects.server.ServerObject;
import de.buildmytown.inselbuildapi.objects.types.ServerType;

public class InselBuildAPI {
	
	private final String PREFIX;
		
	private final Plugin plugin;
	private final Data data;
	
	private final ServerObject currentServer;
	private VaultHandler vault;
	
	public InselBuildAPI(final Plugin plugin, final ServerType type) {
		this.PREFIX = "§2§lBuild§a§lMy§2§lTown §7» ";
		this.plugin = plugin;
		this.currentServer = new ServerObject(this, type);
		this.data = new Data(this);
		this.init();
	}
	
	private void init() {
		this.vault = new VaultHandler();
		Bukkit.getPluginManager().registerEvents(new JoinQuit(this), this.plugin);
		Bukkit.getMessenger().registerOutgoingPluginChannel(this.plugin, this.data.getPluginChannel());
		Bukkit.getMessenger().registerIncomingPluginChannel(this.plugin, this.data.getPluginChannel(), new PluginMessageEvent(this));
        Bukkit.getMessenger().registerOutgoingPluginChannel(this.plugin, "BungeeCord");
	}
	
	public ServerObject getServer(ServerType type) {
		return this.data.getServer(type);
	}
	
	public VaultHandler getVaultHandler() {
		return this.vault;
	}
	
	public void loadScoreboard(boolean bool) {
		this.data.scoreboard = bool;
	}
	
	public boolean loadScoreboard() {
		return this.data.scoreboard;
	}
	
	public ServerObject getCurrentServer() {
		return this.currentServer;
	}
	
	public String getPrefix() {
		return this.PREFIX;
	}
	
	public Data getData() {
		return this.data;
	}
	
	public Plugin getPlugin() {
		return this.plugin;
	}

}
