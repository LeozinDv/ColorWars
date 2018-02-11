package me.devleo.colorwars;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.devleo.colorwars.Timer.CheckTimer;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {

	public static Main plugin;
	public static Economy money = null;

	public void onLoad() {
		plugin = this;
		getServer().getConsoleSender().sendMessage("§e[F_ColorWars] Carregando plugin...");
		saveDefaultConfig();
	}

	public void onEnable() {
		if (Main.plugin.getConfig().getBoolean("Premio.Ganhar_Money")) {
			setupEconomy();
		}
		getCommand("cw").setExecutor(new Comandos());
		getServer().getPluginManager().registerEvents(new Eventos(), this);
		if (Main.plugin.getConfig().getBoolean("AutoStart.Ativo")) {
			CheckTimer.checkTempo();
		}
		getServer().getConsoleSender().sendMessage("§e[F_ColorWars] Plugin ativado com sucesso!");
		getServer().getConsoleSender().sendMessage("§e[F_ColorWars] Versao: " + getDescription().getVersion());
		getServer().getConsoleSender().sendMessage("§e[F_ColorWars] Author: " + getDescription().getAuthors());
	}

	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("§e[F_ColorWars] Plugin desativado!");
	}

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager()
				.getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			money = economyProvider.getProvider();
		}
		return (money != null);
	}
}