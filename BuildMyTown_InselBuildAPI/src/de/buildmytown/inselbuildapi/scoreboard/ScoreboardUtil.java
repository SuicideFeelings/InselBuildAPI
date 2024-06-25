package de.buildmytown.inselbuildapi.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import de.buildmytown.inselbuildapi.InselBuildAPI;

public class ScoreboardUtil {

	private final Scoreboard scoreboard;
	private final Objective sidbarObjective;

	private final String displayName = "ㅶ";

	public ScoreboardUtil(final InselBuildAPI plugin) {
		this(Bukkit.getScoreboardManager().getMainScoreboard());
	}

	@SuppressWarnings("deprecation")
	public ScoreboardUtil(Scoreboard scoreboard) {
		this.scoreboard = scoreboard;

		if (this.scoreboard.getObjective("sidebar") != null) {
			scoreboard.getObjective("sidebar").unregister();
		}
		this.sidbarObjective = this.scoreboard.registerNewObjective("sidebar", "dummy", displayName);
		this.sidbarObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
	}


	public void setSidebarScore(int slot, String content) {
		if (slot < 0)
			throw new IllegalArgumentException("slot must be > 0");
		if (slot > 16)
			throw new IllegalArgumentException("slot must be < 16");

		Team team = getOrCreateTeam("sidebar" + slot);
		String entry = getEntry(slot);

		if (content == null) {
			this.scoreboard.resetScores(entry);
			return;
		}
		team.setPrefix(content);
		team.addEntry(entry);
		this.sidbarObjective.getScore(entry).setScore(slot);

	}


	private Team getOrCreateTeam(String name) {
		Team team = scoreboard.getTeam(name);
		if (team != null)
			return team;
		return scoreboard.registerNewTeam(name);
	}

	private String getEntry(int slot) {
		return ChatColor.values()[slot].toString() + ChatColor.values()[slot + 1];
	}

	@SuppressWarnings("deprecation")
	public void setPlayerTeams(Player p, String teamName, String Prefix, ChatColor color) {
		Team players = getOrCreateTeam(teamName);

		players.setPrefix(Prefix);
		players.setColor(color);
		for (Player target : Bukkit.getOnlinePlayers()) {
			if (players.getPlayers().contains(target)) {
				players.addEntry(target.getName());
				continue;
			}
			players.addEntry(target.getName());
		}

	}
	
}
