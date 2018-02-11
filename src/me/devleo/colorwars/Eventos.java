package me.devleo.colorwars;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import br.com.devpaulo.legendchat.api.events.ChatMessageEvent;

public class Eventos implements Listener {

	@EventHandler
	private void chat(ChatMessageEvent e) {
		if (e.getTags().contains("colorwars")) {
			if (Main.plugin.getConfig().getString("Owner").equals(e.getSender().getName())) {
				e.setTagValue("colorwars", Main.plugin.getConfig().getString("Premio.Tag").replace("&", "§"));
			}
		}
	}

	@EventHandler
	private void sair(PlayerQuitEvent e) {
		if (Manager.participando.contains(e.getPlayer())) {
			Manager.participando.remove(e.getPlayer());
			e.getPlayer().getInventory().clear();
			e.getPlayer().getInventory().setArmorContents(null);
			World w = Bukkit.getServer().getWorld(Main.plugin.getConfig().getString("Locais.Saida.Mundo"));
			int x = Main.plugin.getConfig().getInt("Locais.Saida.X");
			int y = Main.plugin.getConfig().getInt("Locais.Saida.Y");
			int z = Main.plugin.getConfig().getInt("Locais.Saida.Z");
			Location lobby = new Location(w, x, y, z);
			lobby.setPitch((float) Main.plugin.getConfig().getDouble("Locais.Saida.Pitch"));
			lobby.setYaw((float) Main.plugin.getConfig().getDouble("Locais.Saida.Yaw"));
			e.getPlayer().teleport(lobby);
			for (Player p2 : Bukkit.getOnlinePlayers()) {
				if (Manager.participando.contains(p2)) {
					if (Main.plugin.getConfig().getBoolean("Configuracao.Ativar_MSG_JoineLeave")) {
						p2.sendMessage(Main.plugin.getConfig().getString("Mensagens_Privadas.Saiu").replace("&", "§")
								.replace("@prefix", Manager.prefix).replace("@jogador", e.getPlayer().getName())
								.replace("@participando", String.valueOf(Manager.participando.size())));
					}
					if (Main.plugin.getConfig().getBoolean("Configuracao.Usar_Action_Bar")) {
						Manager.sendActionBar(p2,
								"§cOponentes restantes: §f" + (Manager.participando.size() - 1) + ".");
					}
				}
			}
		}
	}

	@EventHandler
	private void cmd(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		if (Manager.acontecendo && Manager.participando.contains(p)) {
			if (Main.plugin.getConfig().getBoolean("Configuracao.Bloquear_Comandos")) {
				if (!e.getMessage().startsWith("/cw")) {
					p.sendMessage(Main.plugin.getConfig().getString("Mensagens_Privadas.Comandos").replace("&", "§")
							.replace("@prefix", Manager.prefix));
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	private void morrer(PlayerDeathEvent e) {
		if (Manager.participando.contains(e.getEntity())) {
			e.setDroppedExp(0);
			e.getDrops().clear();
			e.getEntity().getInventory().clear();
			e.getEntity().getInventory().setArmorContents(null);
			World w = Bukkit.getServer().getWorld(Main.plugin.getConfig().getString("Locais.Saida.Mundo"));
			int x = Main.plugin.getConfig().getInt("Locais.Saida.X");
			int y = Main.plugin.getConfig().getInt("Locais.Saida.Y");
			int z = Main.plugin.getConfig().getInt("Locais.Saida.Z");
			Location lobby = new Location(w, x, y, z);
			lobby.setPitch((float) Main.plugin.getConfig().getDouble("Locais.Saida.Pitch"));
			lobby.setYaw((float) Main.plugin.getConfig().getDouble("Locais.Saida.Yaw"));
			e.getEntity().teleport(lobby);
			Manager.participando.remove(e.getEntity());
			for (Player p2 : Bukkit.getOnlinePlayers()) {
				if (Manager.participando.contains(p2)) {
					if (Main.plugin.getConfig().getBoolean("Configuracao.Usar_Action_Bar")) {
						Manager.sendActionBar(p2,
								"§cOponentes restantes: §f" + (Manager.participando.size() - 1) + ".");
					}
				}
			}
		}
	}

	@EventHandler
	private void hitar(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			Player p = (Player) e.getEntity();
			Player damager = (Player) e.getDamager();
			if (Manager.participando.contains(p) && Manager.participando.contains(damager)) {
				if (Manager.iniciando) {
					damager.sendMessage(Main.plugin.getConfig().getString("Mensagens_Privadas.PvPOff").replace("&", "§")
							.replace("@prefix", Manager.prefix));
					e.setCancelled(true);
					return;
				}
				if (damager.getItemInHand() == null || damager.getItemInHand().getType() == Material.AIR
						|| !damager.getItemInHand().hasItemMeta()
						|| !damager.getItemInHand().getItemMeta().hasDisplayName()) {
					e.setCancelled(true);
					return;
				}
				if (p.getInventory().getHelmet() == null && p.getInventory().getChestplate() == null
						&& p.getInventory().getLeggings() == null && p.getInventory().getBoots() == null) {
					e.setDamage(3.5);
				} else {
					e.setDamage(0);
				}
				damager.updateInventory();
				if (p.getInventory().getHelmet() != null) {
					p.getInventory().getHelmet()
							.setDurability((short) (p.getInventory().getHelmet().getDurability() - 1));
				}
				if (p.getInventory().getLeggings() != null) {
					p.getInventory().getLeggings()
							.setDurability((short) (p.getInventory().getLeggings().getDurability() - 1));
				}
				if (p.getInventory().getBoots() != null) {
					p.getInventory().getBoots()
							.setDurability((short) (p.getInventory().getBoots().getDurability() - 1));
				}
				if (p.getInventory().getChestplate() != null) {
					p.getInventory().getChestplate()
							.setDurability((short) (p.getInventory().getChestplate().getDurability() - 1));
				}
				p.updateInventory();
				if (damager.getItemInHand().getItemMeta().getDisplayName().contains("§aVerde")) {
					if (p.getInventory().getHelmet() != null
							&& p.getInventory().getHelmet().getItemMeta().getDisplayName().contains("§aVerde")) {
						int dura = p.getInventory().getHelmet().getDurability() + 3;
						p.getInventory().getHelmet().setDurability((short) dura);
					}
					if (p.getInventory().getChestplate() != null
							&& p.getInventory().getChestplate().getItemMeta().getDisplayName().contains("§aVerde")) {
						int dura = p.getInventory().getChestplate().getDurability() + 3;
						p.getInventory().getChestplate().setDurability((short) dura);
					}
					if (p.getInventory().getLeggings() != null
							&& p.getInventory().getLeggings().getItemMeta().getDisplayName().contains("§aVerde")) {
						int dura = p.getInventory().getLeggings().getDurability() + 3;
						p.getInventory().getLeggings().setDurability((short) dura);
					}
					if (p.getInventory().getBoots() != null
							&& p.getInventory().getBoots().getItemMeta().getDisplayName().contains("§aVerde")) {
						int dura = p.getInventory().getBoots().getDurability() + 3;
						p.getInventory().getBoots().setDurability((short) dura);
					}
				}
				if (damager.getItemInHand().getItemMeta().getDisplayName().contains("§dRosa")) {
					if (p.getInventory().getHelmet() != null
							&& p.getInventory().getHelmet().getItemMeta().getDisplayName().contains("§dRosa")) {
						int dura = p.getInventory().getHelmet().getDurability() + 3;
						p.getInventory().getHelmet().setDurability((short) dura);
					}
					if (p.getInventory().getChestplate() != null
							&& p.getInventory().getChestplate().getItemMeta().getDisplayName().contains("§dRosa")) {
						int dura = p.getInventory().getChestplate().getDurability() + 3;
						p.getInventory().getChestplate().setDurability((short) dura);
					}
					if (p.getInventory().getLeggings() != null
							&& p.getInventory().getLeggings().getItemMeta().getDisplayName().contains("§dRosa")) {
						int dura = p.getInventory().getLeggings().getDurability() + 3;
						p.getInventory().getLeggings().setDurability((short) dura);
					}
					if (p.getInventory().getBoots() != null
							&& p.getInventory().getBoots().getItemMeta().getDisplayName().contains("§dRosa")) {
						int dura = p.getInventory().getBoots().getDurability() + 3;
						p.getInventory().getBoots().setDurability((short) dura);
					}
				}
				if (damager.getItemInHand().getItemMeta().getDisplayName().contains("§6Laranja")) {
					if (p.getInventory().getHelmet() != null
							&& p.getInventory().getHelmet().getItemMeta().getDisplayName().contains("§6Laranja")) {
						int dura = p.getInventory().getHelmet().getDurability() + 3;
						p.getInventory().getHelmet().setDurability((short) dura);
					}
					if (p.getInventory().getChestplate() != null
							&& p.getInventory().getChestplate().getItemMeta().getDisplayName().contains("§6Laranja")) {
						int dura = p.getInventory().getChestplate().getDurability() + 3;
						p.getInventory().getChestplate().setDurability((short) dura);
					}
					if (p.getInventory().getLeggings() != null
							&& p.getInventory().getLeggings().getItemMeta().getDisplayName().contains("§6Laranja")) {
						int dura = p.getInventory().getLeggings().getDurability() + 3;
						p.getInventory().getLeggings().setDurability((short) dura);
					}
					if (p.getInventory().getBoots() != null
							&& p.getInventory().getBoots().getItemMeta().getDisplayName().contains("§6Laranja")) {
						int dura = p.getInventory().getBoots().getDurability() + 3;
						p.getInventory().getBoots().setDurability((short) dura);
					}
				}
				if (damager.getItemInHand().getItemMeta().getDisplayName().contains("§3Ciano")) {
					if (p.getInventory().getHelmet() != null
							&& p.getInventory().getHelmet().getItemMeta().getDisplayName().contains("§3Ciano")) {
						int dura = p.getInventory().getHelmet().getDurability() + 3;
						p.getInventory().getHelmet().setDurability((short) dura);
					}
					if (p.getInventory().getChestplate() != null
							&& p.getInventory().getChestplate().getItemMeta().getDisplayName().contains("§3Ciano")) {
						int dura = p.getInventory().getChestplate().getDurability() + 3;
						p.getInventory().getChestplate().setDurability((short) dura);
					}
					if (p.getInventory().getLeggings() != null
							&& p.getInventory().getLeggings().getItemMeta().getDisplayName().contains("§3Ciano")) {
						int dura = p.getInventory().getLeggings().getDurability() + 3;
						p.getInventory().getLeggings().setDurability((short) dura);
					}
					if (p.getInventory().getBoots() != null
							&& p.getInventory().getBoots().getItemMeta().getDisplayName().contains("§3Ciano")) {
						int dura = p.getInventory().getBoots().getDurability() + 3;
						p.getInventory().getBoots().setDurability((short) dura);
					}
				}
			}
		}
	}

	@EventHandler
	private void fome(FoodLevelChangeEvent e) {
		Player p = (Player) e.getEntity();
		if (Manager.participando.contains(p)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	private void drop(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		if (Manager.participando.contains(p)) {
			e.setCancelled(true);
		}
	}
}