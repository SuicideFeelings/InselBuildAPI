package de.buildmytown.inselbuildapi.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import de.buildmytown.inselbuildapi.InselBuildAPI;
import de.buildmytown.inselbuildapi.objects.types.PlayerTask;

public class PluginMessageEvent implements PluginMessageListener{

	private final InselBuildAPI plugin;
	
	public PluginMessageEvent(final InselBuildAPI plugin) {
		this.plugin = plugin;
	}
	@Override
	public void onPluginMessageReceived(String channel, Player p, byte[] data) {
		if(channel.equals(this.plugin.getData().getPluginChannel())) {
			for(PlayerTask task : PlayerTask.values()) {
				if(task.getData().getBytes().equals(data)) {
					Bukkit.getConsoleSender().sendMessage("Channel: " + channel);
					Bukkit.getConsoleSender().sendMessage("Data: " + data);
				}
			}

		}
	}

}
