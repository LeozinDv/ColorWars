package me.devleo.colorwars.Timer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.devleo.colorwars.Main;
import me.devleo.colorwars.Manager;

public class Iniciar {

	private static int moneypremio = Main.plugin.getConfig().getInt("Premio.Quantia_Money");
	private static String tagpremio = Main.plugin.getConfig().getString("Premio.Tag").replace("&", "§");

	public static void iniciarEvento() {
		if (!Main.plugin.getConfig().contains("Locais.Entrada") || !Main.plugin.getConfig().contains("Locais.Saida")) {
			Main.plugin.getLogger().warning("Alguns locais nao foram setados, evento cancelado automaticamente!");
			return;
		}
		Manager.iniciando = true;
		Manager.acontecendo = true;
		int tempo = Main.plugin.getConfig().getInt("Configuracao.Tempo_Entre_Chamadas");
		BukkitTask task = new BukkitRunnable() {
			int chamadas = Main.plugin.getConfig().getInt("Configuracao.Total_de_Chamadas");

			public void run() {
				if (Manager.acontecendo != true) {
					this.cancel();
					return;
				}
				if (chamadas > 0) {
					for (String msg : Main.plugin.getConfig().getStringList("Evento_Comecando")) {
						Bukkit.broadcastMessage(msg.replace("&", "§").replace("@prefix", Manager.prefix)
								.replace("@chamadas", String.valueOf(chamadas))
								.replace("@participando", String.valueOf(Manager.participando.size()))
								.replace("@moneypremio", String.valueOf(moneypremio)).replace("@tagpremio", tagpremio));
					}
					chamadas--;
					return;
				}
				if (chamadas <= 0) {
					if (Manager.participando.size() < Main.plugin.getConfig().getInt("Configuracao.Minimo_Jogadores")) {
						for (String msg : Main.plugin.getConfig().getStringList("Evento_Cancelado_Falta_Jogadores")) {
							Bukkit.broadcastMessage(msg.replace("&", "§").replace("@prefix", Manager.prefix));
						}
						for (Player p2 : Bukkit.getOnlinePlayers()) {
							if (Manager.participando.contains(p2)) {
								World w = Bukkit.getServer()
										.getWorld(Main.plugin.getConfig().getString("Locais.Saida.Mundo"));
								int x = Main.plugin.getConfig().getInt("Locais.Saida.X");
								int y = Main.plugin.getConfig().getInt("Locais.Saida.Y");
								int z = Main.plugin.getConfig().getInt("Locais.Saida.Z");
								Location lobby = new Location(w, x, y, z);
								lobby.setPitch((float) Main.plugin.getConfig().getDouble("Locais.Saida.Pitch"));
								lobby.setYaw((float) Main.plugin.getConfig().getDouble("Locais.Saida.Yaw"));
								p2.teleport(lobby);
								p2.getInventory().clear();
								p2.getInventory().setArmorContents(null);
								Manager.participando.remove(p2);
							}
						}
						Manager.iniciando = false;
						Manager.acontecendo = false;
						Manager.participando.clear();
						this.cancel();
						return;
					}
					for (String msg : Main.plugin.getConfig().getStringList("Entrada_Trancada")) {
						Bukkit.broadcastMessage(msg.replace("&", "§").replace("@prefix", Manager.prefix)
								.replace("@participando", String.valueOf(Manager.participando.size()))
								.replace("@moneypremio", String.valueOf(moneypremio)).replace("@tagpremio", tagpremio));
					}
					Manager.iniciando = false;
					iniciou();
					this.cancel();
				}
			}
		}.runTaskTimer(Main.plugin, 0L, tempo * 20L);
	}

	private static void iniciou() {
		for (Player p2 : Bukkit.getOnlinePlayers()) {
			if (Manager.participando.contains(p2)) {
				p2.sendMessage(Main.plugin.getConfig().getString("Mensagens_Privadas.PvPOn").replace("&", "§")
						.replace("@prefix", Manager.prefix));
			}
		}
		BukkitTask task = new BukkitRunnable() {
			public void run() {
				if (Manager.acontecendo != true) {
					this.cancel();
					return;
				}
				if (Manager.participando.size() < 1) {
					this.cancel();
					return;
				}
				if (Manager.participando.size() == 1) {
					Player p = Manager.participando.get(0);
					for (String msg : Main.plugin.getConfig().getStringList("Vencedor")) {
						Bukkit.broadcastMessage(msg.replace("&", "§").replace("@prefix", Manager.prefix)
								.replace("@vencedor", p.getName()).replace("@moneypremio", String.valueOf(moneypremio))
								.replace("@tagpremio", tagpremio));
					}
					if (Main.plugin.getConfig().getBoolean("Premio.Ganhar_Money")) {
						Main.money.depositPlayer(p.getName(), moneypremio);
					}
					World w = Bukkit.getServer().getWorld(Main.plugin.getConfig().getString("Locais.Saida.Mundo"));
					int x = Main.plugin.getConfig().getInt("Locais.Saida.X");
					int y = Main.plugin.getConfig().getInt("Locais.Saida.Y");
					int z = Main.plugin.getConfig().getInt("Locais.Saida.Z");
					Location lobby = new Location(w, x, y, z);
					lobby.setPitch((float) Main.plugin.getConfig().getDouble("Locais.Saida.Pitch"));
					lobby.setYaw((float) Main.plugin.getConfig().getDouble("Locais.Saida.Yaw"));
					p.teleport(lobby);
					p.getInventory().clear();
					p.getInventory().setArmorContents(null);
					Main.plugin.getConfig().set("Owner", p.getName());
					Manager.participando.clear();
					Manager.acontecendo = false;
					this.cancel();
				}
			}
		}.runTaskTimer(Main.plugin, 0L, 10 * 20);
	}
}