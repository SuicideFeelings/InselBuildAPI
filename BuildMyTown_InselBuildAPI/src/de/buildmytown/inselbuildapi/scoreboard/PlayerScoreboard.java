package de.buildmytown.inselbuildapi.scoreboard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

public class PlayerScoreboard {

	private final Map<UUID, ScoreboardUtil> scoreboards = new HashMap<>();
	private final Map<Integer, String> defaults = new HashMap<>();
	

	public void setSidebarScore(int slot, String content) {
		if(slot < 0) throw new IllegalArgumentException("slot must be > 0");
		if(slot > 16) throw new IllegalArgumentException("slot must be < 16");
		this.scoreboards.forEach((uuid, scoreboardUtil) -> scoreboardUtil.setSidebarScore(slot, content));
	}
	
	public void setDefaultSidebarScore(int slot, String content) {
		if(slot < 0) throw new IllegalArgumentException("slot must be > 0");
		if(slot > 16) throw new IllegalArgumentException("slot must be < 16");
		setSidebarScore(slot, content);
		
		if(content == null) defaults.remove(slot);
		else defaults.put(slot, content);
	}
	
	public void remove(Player p) {
		this.scoreboards.remove(p.getUniqueId());
	}
	
	public Map<UUID, ScoreboardUtil> getScoreboards(){
		return this.scoreboards;
	}
	
	public ScoreboardUtil getScoreboardUtil(Player p) {
		return this.scoreboards.computeIfAbsent(p.getUniqueId(), uuid -> {
			Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
			p.setScoreboard(scoreboard);
			ScoreboardUtil scoreboardUtil = new ScoreboardUtil(scoreboard);
			defaults.forEach(scoreboardUtil::setSidebarScore);
			return scoreboardUtil;
		});
	}
	
}
