package me.devleo.colorwars;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.devleo.colorwars.Timer.Iniciar;

public class Comandos implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (!(sender instanceof Player)) {
			return true;
		}
		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("cw")) {
			if (args.length == 0) {
				p.sendMessage(Manager.prefix + " Principais comandos:");
				p.sendMessage(" §7/cw participar - Participa do evento");
				p.sendMessage(" §7/cw sair - Sai do evento");
				p.sendMessage(" §7/cw camarote - Teleporta ao camarote do evento");
				p.sendMessage(" §7/cw status - Mostra as informações do evento");
				if (p.hasPermission("cw.admin")) {
					p.sendMessage("    §4(Comandos - Admin)");
					p.sendMessage(" §c/cw forceinicio - Inicia o evento");
					p.sendMessage(" §c/cw cancelar - Desliga o evento");
					p.sendMessage(" §c/cw setarlocal [local] - Seta um local do evento(Entrada,Saída,Camarote)");
					p.sendMessage(" §c/cw recarregar - Recarrega à configuração");
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("participar")) {
				if (!Manager.acontecendo) {
					p.sendMessage(Main.plugin.getConfig().getString("Mensagens_Privadas.NaoAcontecendo")
							.replace("&", "§").replace("@prefix", Manager.prefix));
					return true;
				}
				if (Manager.acontecendo && !Manager.iniciando) {
					p.sendMessage(Main.plugin.getConfig().getString("Mensagens_Privadas.JaIniciado").replace("&", "§")
							.replace("@prefix", Manager.prefix));
					return true;
				}
				if (Manager.participando.contains(p)) {
					p.sendMessage(Main.plugin.getConfig().getString("Mensagens_Privadas.JaParticipando")
							.replace("&", "§").replace("@prefix", Manager.prefix));
					return true;
				}
				if (Manager.getFreeSlots(p) != 36) {
					p.sendMessage(Main.plugin.getConfig().getString("Mensagens_Privadas.EsvazieInv").replace("&", "§")
							.replace("@prefix", Manager.prefix));
					return true;
				}
				if (p.getInventory().getHelmet() != null || p.getInventory().getChestplate() != null
						|| p.getInventory().getLeggings() != null || p.getInventory().getBoots() != null) {
					p.sendMessage(Main.plugin.getConfig().getString("Mensagens_Privadas.EsvazieInv").replace("&", "§")
							.replace("@prefix", Manager.prefix));
					return true;
				}
				Manager.participando.add(p);
				p.setFoodLevel(20);
				p.setHealth(p.getMaxHealth());
				p.setGameMode(GameMode.ADVENTURE);
				for (PotionEffect efeitos : p.getActivePotionEffects()) {
					p.removePotionEffect(PotionEffectType.getById(efeitos.getType().getId()));
				}
				World w = Bukkit.getServer().getWorld(Main.plugin.getConfig().getString("Locais.Entrada.Mundo"));
				int x = Main.plugin.getConfig().getInt("Locais.Entrada.X");
				int y = Main.plugin.getConfig().getInt("Locais.Entrada.Y");
				int z = Main.plugin.getConfig().getInt("Locais.Entrada.Z");
				Location lobby = new Location(w, x, y, z);
				lobby.setPitch((float) Main.plugin.getConfig().getDouble("Locais.Entrada.Pitch"));
				lobby.setYaw((float) Main.plugin.getConfig().getDouble("Locais.Entrada.Yaw"));
				p.teleport(lobby);
				Manager.giveItens(p);
				for (Player p2 : Bukkit.getOnlinePlayers()) {
					if (Manager.participando.contains(p2)) {
						if (Main.plugin.getConfig().getBoolean("Configuracao.Ativar_MSG_JoineLeave")) {
							p2.sendMessage(
									Main.plugin.getConfig().getString("Mensagens_Privadas.Entrou").replace("&", "§")
											.replace("@prefix", Manager.prefix).replace("@jogador", p.getName())
											.replace("@participando", String.valueOf(Manager.participando.size())));
						}
					}
				}
			}
			if (args[0].equalsIgnoreCase("sair")) {
				if (!Manager.acontecendo) {
					p.sendMessage(Main.plugin.getConfig().getString("Mensagens_Privadas.NaoAcontecendo")
							.replace("&", "§").replace("@prefix", Manager.prefix));
					return true;
				}
				if (Manager.participando.contains(p)) {
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
					Manager.participando.remove(p);
					for (Player p2 : Bukkit.getOnlinePlayers()) {
						if (Manager.participando.contains(p2)) {
							if (Main.plugin.getConfig().getBoolean("Configuracao.Ativar_MSG_JoineLeave")) {
								p2.sendMessage(
										Main.plugin.getConfig().getString("Mensagens_Privadas.Saiu").replace("&", "§")
												.replace("@prefix", Manager.prefix).replace("@jogador", p.getName())
												.replace("@participando", String.valueOf(Manager.participando.size())));
							}
						}
					}
				} else {
					p.sendMessage(Main.plugin.getConfig().getString("Mensagens_Privadas.NaoParticipando")
							.replace("&", "§").replace("@prefix", Manager.prefix));
					return true;
				}
			}
			if (args[0].equalsIgnoreCase("camarote")) {
				if (!Manager.acontecendo) {
					p.sendMessage(Main.plugin.getConfig().getString("Mensagens_Privadas.NaoAcontecendo")
							.replace("&", "§").replace("@prefix", Manager.prefix));
					return true;
				}
				if (!Main.plugin.getConfig().contains("Locais.Camarote")) {
					p.sendMessage(Manager.prefix + " §cO camarote ainda não foi setado!");
					return true;
				}
				World w = Bukkit.getServer().getWorld(Main.plugin.getConfig().getString("Locais.Camarote.Mundo"));
				int x = Main.plugin.getConfig().getInt("Locais.Camarote.X");
				int y = Main.plugin.getConfig().getInt("Locais.Camarote.Y");
				int z = Main.plugin.getConfig().getInt("Locais.Camarote.Z");
				Location lobby = new Location(w, x, y, z);
				lobby.setPitch((float) Main.plugin.getConfig().getDouble("Locais.Camarote.Pitch"));
				lobby.setYaw((float) Main.plugin.getConfig().getDouble("Locais.Camarote.Yaw"));
				p.teleport(lobby);
				p.sendMessage(Main.plugin.getConfig().getString("Mensagens_Privadas.Camarote").replace("&", "§"));
			}
			if (args[0].equalsIgnoreCase("status")) {
				if (Manager.acontecendo) {
					p.sendMessage(Manager.prefix + " §eInformações do evento:");
					p.sendMessage("  §7Acontecendo: §aSim");
					p.sendMessage("  §7Jogadores Participando: §f" + Manager.participando.size());
					p.sendMessage("  §7Último Ganhador: §f" + Main.plugin.getConfig().getString("Owner"));
					return true;
				}
				p.sendMessage(Manager.prefix + " §eInformações do evento:");
				p.sendMessage("  §7Acontecendo: §4Não");
				p.sendMessage("  §7Último Ganhador: §f" + Main.plugin.getConfig().getString("Owner"));
			}
			if (args[0].equalsIgnoreCase("forceinicio")) {
				if (!p.hasPermission("cw.admin")) {
					p.sendMessage(Manager.prefix + " §cVocê não tem permissão!");
					return true;
				}
				if (Manager.acontecendo) {
					p.sendMessage(Main.plugin.getConfig().getString("Mensagens_Privadas.JaAcontecendo")
							.replace("&", "§").replace("@prefix", Manager.prefix));
					return true;
				}
				p.sendMessage(Manager.prefix + " §eIniciando evento!");
				Iniciar.iniciarEvento();
			}
			if (args[0].equalsIgnoreCase("cancelar")) {
				if (!p.hasPermission("cw.admin")) {
					p.sendMessage(Manager.prefix + " §cVocê não tem permissão!");
					return true;
				}
				if (!Manager.acontecendo) {
					p.sendMessage(Main.plugin.getConfig().getString("Mensagens_Privadas.NaoAcontecendo")
							.replace("&", "§").replace("@prefix", Manager.prefix));
					return true;
				}
				p.sendMessage(Manager.prefix + " §eEvento encerrado!");
				Manager.acontecendo = false;
				Manager.iniciando = false;
				for (String msg : Main.plugin.getConfig().getStringList("Evento_Cancelado_Staff")) {
					Bukkit.broadcastMessage(
							msg.replace("&", "§").replace("@prefix", Manager.prefix).replace("@staff", p.getName()));
				}
				for (Player p2 : Bukkit.getOnlinePlayers()) {
					if (Manager.participando.contains(p2)) {
						World w = Bukkit.getServer().getWorld(Main.plugin.getConfig().getString("Locais.Saida.Mundo"));
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
				Manager.participando.clear();
			}
			if (args[0].equalsIgnoreCase("setarlocal")) {
				if (!p.hasPermission("cw.admin")) {
					p.sendMessage(Manager.prefix + " §cVocê não tem permissão!");
					return true;
				}
				if (args.length == 1) {
					p.sendMessage(Manager.prefix + " §cEscolha um local: Entrada, Saida, Camarote");
					return true;
				}
				if (args[1].equalsIgnoreCase("entrada")) {
					Main.plugin.getConfig().set("Locais.Entrada.Mundo", p.getWorld().getName());
					Main.plugin.getConfig().set("Locais.Entrada.X", p.getLocation().getBlockX());
					Main.plugin.getConfig().set("Locais.Entrada.Y", p.getLocation().getBlockY());
					Main.plugin.getConfig().set("Locais.Entrada.Z", p.getLocation().getBlockZ());
					Main.plugin.getConfig().set("Locais.Entrada.Pitch", p.getLocation().getPitch());
					Main.plugin.getConfig().set("Locais.Entrada.Yaw", p.getLocation().getYaw());
					Main.plugin.saveConfig();
					p.sendMessage(Manager.prefix + " §aLocal setado com sucesso!");
				}
				if (args[1].equalsIgnoreCase("saida")) {
					Main.plugin.getConfig().set("Locais.Saida.Mundo", p.getWorld().getName());
					Main.plugin.getConfig().set("Locais.Saida.X", p.getLocation().getBlockX());
					Main.plugin.getConfig().set("Locais.Saida.Y", p.getLocation().getBlockY());
					Main.plugin.getConfig().set("Locais.Saida.Z", p.getLocation().getBlockZ());
					Main.plugin.getConfig().set("Locais.Saida.Pitch", p.getLocation().getPitch());
					Main.plugin.getConfig().set("Locais.Saida.Yaw", p.getLocation().getYaw());
					Main.plugin.saveConfig();
					p.sendMessage(Manager.prefix + " §aLocal setado com sucesso!");
				}
				if (args[1].equalsIgnoreCase("camarote")) {
					Main.plugin.getConfig().set("Locais.Camarote.Mundo", p.getWorld().getName());
					Main.plugin.getConfig().set("Locais.Camarote.X", p.getLocation().getBlockX());
					Main.plugin.getConfig().set("Locais.Camarote.Y", p.getLocation().getBlockY());
					Main.plugin.getConfig().set("Locais.Camarote.Z", p.getLocation().getBlockZ());
					Main.plugin.getConfig().set("Locais.Camarote.Pitch", p.getLocation().getPitch());
					Main.plugin.getConfig().set("Locais.Camarote.Yaw", p.getLocation().getYaw());
					Main.plugin.saveConfig();
					p.sendMessage(Manager.prefix + " §aLocal setado com sucesso!");
				}
			}
			if (args[0].equalsIgnoreCase("recarregar")) {
				if (!p.hasPermission("cw.admin")) {
					p.sendMessage(Manager.prefix + " §cVocê não tem permissão!");
					return true;
				}
				Main.plugin.reloadConfig();
				p.sendMessage(Manager.prefix + " §aConfiguração recarregada!");
			}
		}
		return false;
	}
}