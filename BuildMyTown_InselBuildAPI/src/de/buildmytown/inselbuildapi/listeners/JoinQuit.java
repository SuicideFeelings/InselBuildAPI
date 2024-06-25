package de.buildmytown.inselbuildapi.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.buildmytown.inselbuildapi.InselBuildAPI;

public class JoinQuit implements Listener{

	private InselBuildAPI plugin;
	
	public JoinQuit(final InselBuildAPI plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		this.plugin.getData().getOnlineTimeManager().registerPlayer(e.getPlayer());
	}
	
}
