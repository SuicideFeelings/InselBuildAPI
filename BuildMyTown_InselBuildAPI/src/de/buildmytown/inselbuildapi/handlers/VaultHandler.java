package de.buildmytown.inselbuildapi.handlers;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class VaultHandler {
	
	private Economy economy = null;
	private Permission permissions = null;
	private Chat chat = null;

	public VaultHandler() {
		if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
			setupEconomy();
			setupChat();
			setupPermissions();
		} else {
			Bukkit.getConsoleSender().sendMessage("§4Vault-Plugin wurde nicht gefunden!");
		}
	}

	private void setupEconomy() {
		RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
		if (rsp != null)
			economy = rsp.getProvider();
	}

	private void setupChat() {
		RegisteredServiceProvider<Chat> rsp = Bukkit.getServicesManager().getRegistration(Chat.class);
		if (rsp != null)
			chat = rsp.getProvider();
	}

	private void setupPermissions() {
		RegisteredServiceProvider<Permission> rsp = Bukkit.getServicesManager().getRegistration(Permission.class);
		if (rsp != null)
			permissions = rsp.getProvider();
	}

	public boolean hasEconomy() {
		return economy != null;
	}

	public double getBalance(OfflinePlayer target) {
		if (!hasEconomy())
			throw new UnsupportedOperationException("Vault Economy wurde nicht gefunden!");
		return economy.getBalance(target);
	}

	public String withdraw(OfflinePlayer target, double amount) {
		if (!hasEconomy())
			throw new UnsupportedOperationException("Vault Economy wurde nicht gefunden!");
		return economy.withdrawPlayer(target, amount).errorMessage;
	}

	public String deposit(OfflinePlayer target, double amount) {
		if (!hasEconomy())
			throw new UnsupportedOperationException("Vault Economy wurde nicht gefunden!");
		
		return economy.depositPlayer(target, amount).errorMessage;
	}

	public Economy getEconomy() {
		return economy;
	}

	public Permission getPermissions() {
		return permissions;
	}

	public Chat getChat() {
		return chat;
	}

}
