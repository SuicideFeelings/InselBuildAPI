package de.buildmytown.inselbuildapi;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.buildmytown.inselbuildapi.managers.OnlineTimeManager;
import de.buildmytown.inselbuildapi.mysql.MySQL;
import de.buildmytown.inselbuildapi.mysql.MySQLFile;
import de.buildmytown.inselbuildapi.objects.server.ServerObject;
import de.buildmytown.inselbuildapi.objects.types.ServerType;
import de.buildmytown.inselbuildapi.scoreboard.PlayerScoreboard;

public class Data {
	
	private InselBuildAPI plugin;
	
	private final String PATH;
	private final String CHANNEL;
	public boolean scoreboard = true;
	private MySQL sql;
	private MySQLFile sqlFile;
	private PlayerScoreboard playerScore;
	private OnlineTimeManager onlinetime;
	private ArrayList<ServerObject> servers;
	
	public Data(final InselBuildAPI plugin) {
		this.plugin = plugin;
		this.CHANNEL = "custom:inselbuildapi";
		this.PATH = "plugins//BuildMyTown-InselBuildAPI//";
		this.onlinetime = new OnlineTimeManager(plugin);
		this.sqlFile = new MySQLFile(this.PATH);
		this.sql = new MySQL(this.plugin);
		this.initServers();
		if(this.scoreboard) {
			this.loadScoreboard();
		}
		startAsyncScheduler();
	}
	
	private void initServers() {
		this.servers = new ArrayList<>();
		for(ServerType type : ServerType.values()) {
			this.servers.add(new ServerObject(this.plugin, type));
		}
	}
	
	@SuppressWarnings("deprecation")
	public void startAsyncScheduler() {
		Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin.getPlugin(), () -> {
			if (!this.getMySQL().isConnected())
				return;
			for (Player all : Bukkit.getOnlinePlayers()) {
				this.onlinetime.addTime(all.getPlayer().getUniqueId().toString(), 1);
			}
		}, 0, 20L * 60);
	}
	
	private void loadScoreboard() {
		this.playerScore = new PlayerScoreboard();
		playerScore.setDefaultSidebarScore(13, "§7§m----------------");
		playerScore.setDefaultSidebarScore(12,"§7| §cLoading...");
		playerScore.setDefaultSidebarScore(11,"§7| §cLoading...");
		playerScore.setDefaultSidebarScore(10, "§r   ");
		playerScore.setDefaultSidebarScore(9, "§7| §cLoading...");
		playerScore.setDefaultSidebarScore(8, "§7| §cLoading...");
		playerScore.setDefaultSidebarScore(7, "§r  ");
		playerScore.setDefaultSidebarScore(6, "§7| §cLoading...");
		playerScore.setDefaultSidebarScore(5, "§7| §cLoading...");
		playerScore.setDefaultSidebarScore(4, "§r ");
		playerScore.setDefaultSidebarScore(3, "§7| §cLoading...");
		playerScore.setDefaultSidebarScore(2, "§7| §cLoading...");
		playerScore.setDefaultSidebarScore(1, "§7§m----------------");
		playerScore.setDefaultSidebarScore(0, "§x§3§6§B§3§0§Es§x§3§4§A§F§0§Eh§x§3§3§A§A§0§Do§x§3§1§A§6§0§Dp§x§2§F§A§1§0§C.§x§2§E§9§D§0§Cb§x§2§C§9§8§0§Bu§x§2§A§9§4§0§Bi§x§2§9§8§F§0§Al§x§2§7§8§B§0§Ad§x§2§5§8§6§0§Am§x§2§4§8§2§0§9y§x§2§2§7§D§0§9t§x§2§0§7§9§0§8o§x§1§F§7§4§0§8w§x§1§D§7§0§0§7n§x§1§B§6§B§0§7.§x§1§A§6§7§0§6d§x§1§8§6§2§0§6e");
	}
	
	public PlayerScoreboard getPlayerScoreboard() {
		return this.playerScore;
	}
	
	public MySQL getMySQL() {
		return this.sql;
	}
	
	public ServerObject getServer(ServerType type) {
		for(ServerObject so : this.servers) {
			if(so.getType() == type) {
				return so;
			}
		}
		return null;
	}
	
	public OnlineTimeManager getOnlineTimeManager() {
		return this.onlinetime;
	}
	
	public String getPluginChannel() {
		return this.CHANNEL;
	}
		
	public MySQLFile getMySQLConfig() {
		return this.sqlFile;
	}

}
