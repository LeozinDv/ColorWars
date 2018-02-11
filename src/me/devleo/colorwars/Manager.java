package me.devleo.colorwars;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class Manager {

	public static String prefix = Main.plugin.getConfig().getString("Configuracao.Prefix").replace("&", "§");
	public static boolean iniciando = false;
	public static boolean acontecendo = false;
	public static ArrayList<Player> participando = new ArrayList();

	public static int getFreeSlots(Player p) {
		int slots = 36;
		for (ItemStack item : p.getInventory().getContents()) {
			if (item != null && item.getType() != Material.AIR) {
				slots--;
			}
		}
		return slots;
	}

	public static void giveItens(Player p) {
		p.getInventory().clear();
		List<String> lore = new ArrayList();

		ItemStack i0 = new ItemStack(Material.INK_SACK, 1, (short) 6);
		ItemMeta i02 = i0.getItemMeta();
		i02.setDisplayName("§3Ciano Break");
		lore.add("§7Use para quebrar armaduras da cor Ciano!");
		i02.setLore(lore);
		i0.setItemMeta(i02);

		ItemStack i1 = new ItemStack(Material.INK_SACK, 1, (short) 14);
		ItemMeta i12 = i1.getItemMeta();
		i12.setDisplayName("§6Laranja Break");
		lore.clear();
		lore.add("§7Use para quebrar armaduras da cor Laranja!");
		i12.setLore(lore);
		i1.setItemMeta(i12);

		ItemStack i2 = new ItemStack(Material.INK_SACK, 1, (short) 9);
		ItemMeta i22 = i2.getItemMeta();
		i22.setDisplayName("§dRosa Break");
		lore.clear();
		lore.add("§7Use para quebrar armaduras da cor Rosa!");
		i22.setLore(lore);
		i2.setItemMeta(i22);

		ItemStack i3 = new ItemStack(Material.INK_SACK, 1, (short) 10);
		ItemMeta i32 = i3.getItemMeta();
		i32.setDisplayName("§aVerde Break");
		lore.clear();
		lore.add("§7Use para quebrar armaduras da cor Verde!");
		i32.setLore(lore);
		i3.setItemMeta(i32);

		p.getInventory().setItem(0, i0);
		p.getInventory().setItem(1, i1);
		p.getInventory().setItem(2, i2);
		p.getInventory().setItem(3, i3);
		// random
		Random r = new Random();
		int o = r.nextInt(100);
		if (o <= 25) {
			ItemStack capacete = new ItemStack(Material.LEATHER_HELMET);
			LeatherArmorMeta capacete2 = (LeatherArmorMeta) capacete.getItemMeta();
			capacete2.setDisplayName("§aVerde Armor");
			capacete2.setColor(Color.LIME);
			capacete.setItemMeta(capacete2);
			p.getInventory().setHelmet(capacete);
		} else if (o > 25 && o <= 50) {
			ItemStack capacete = new ItemStack(Material.LEATHER_HELMET);
			LeatherArmorMeta capacete2 = (LeatherArmorMeta) capacete.getItemMeta();
			capacete2.setDisplayName("§dRosa Armor");
			capacete2.setColor(Color.FUCHSIA);
			capacete.setItemMeta(capacete2);
			p.getInventory().setHelmet(capacete);
		} else if (o > 50 && o <= 75) {
			ItemStack capacete = new ItemStack(Material.LEATHER_HELMET);
			LeatherArmorMeta capacete2 = (LeatherArmorMeta) capacete.getItemMeta();
			capacete2.setDisplayName("§6Laranja Armor");
			capacete2.setColor(Color.ORANGE);
			capacete.setItemMeta(capacete2);
			p.getInventory().setHelmet(capacete);
		} else if (o > 75 && o <= 100) {
			ItemStack capacete = new ItemStack(Material.LEATHER_HELMET);
			LeatherArmorMeta capacete2 = (LeatherArmorMeta) capacete.getItemMeta();
			capacete2.setDisplayName("§3Ciano Armor");
			capacete2.setColor(Color.AQUA);
			capacete.setItemMeta(capacete2);
			p.getInventory().setHelmet(capacete);
		}

		int a = r.nextInt(100);
		if (a <= 25) {
			ItemStack peitoral = new ItemStack(Material.LEATHER_CHESTPLATE);
			LeatherArmorMeta peitoral2 = (LeatherArmorMeta) peitoral.getItemMeta();
			peitoral2.setDisplayName("§aVerde Armor");
			peitoral2.setColor(Color.LIME);
			peitoral.setItemMeta(peitoral2);
			p.getInventory().setChestplate(peitoral);
		} else if (a > 25 && a <= 50) {
			ItemStack peitoral = new ItemStack(Material.LEATHER_CHESTPLATE);
			LeatherArmorMeta peitoral2 = (LeatherArmorMeta) peitoral.getItemMeta();
			peitoral2.setDisplayName("§dRosa Armor");
			peitoral2.setColor(Color.FUCHSIA);
			peitoral.setItemMeta(peitoral2);
			p.getInventory().setChestplate(peitoral);
		} else if (a > 50 && a <= 75) {
			ItemStack peitoral = new ItemStack(Material.LEATHER_CHESTPLATE);
			LeatherArmorMeta peitoral2 = (LeatherArmorMeta) peitoral.getItemMeta();
			peitoral2.setDisplayName("§6Laranja Armor");
			peitoral2.setColor(Color.ORANGE);
			peitoral.setItemMeta(peitoral2);
			p.getInventory().setChestplate(peitoral);
		} else if (a > 75 && a <= 100) {
			ItemStack peitoral = new ItemStack(Material.LEATHER_CHESTPLATE);
			LeatherArmorMeta peitoral2 = (LeatherArmorMeta) peitoral.getItemMeta();
			peitoral2.setDisplayName("§3Ciano Armor");
			peitoral2.setColor(Color.AQUA);
			peitoral.setItemMeta(peitoral2);
			p.getInventory().setChestplate(peitoral);
		}

		int b = r.nextInt(100);
		if (b <= 25) {
			ItemStack calca = new ItemStack(Material.LEATHER_LEGGINGS);
			LeatherArmorMeta calca2 = (LeatherArmorMeta) calca.getItemMeta();
			calca2.setDisplayName("§aVerde Armor");
			calca2.setColor(Color.LIME);
			calca.setItemMeta(calca2);
			p.getInventory().setLeggings(calca);
		} else if (b > 25 && b <= 50) {
			ItemStack calca = new ItemStack(Material.LEATHER_LEGGINGS);
			LeatherArmorMeta calca2 = (LeatherArmorMeta) calca.getItemMeta();
			calca2.setDisplayName("§dRosa Armor");
			calca2.setColor(Color.FUCHSIA);
			calca.setItemMeta(calca2);
			p.getInventory().setLeggings(calca);
		} else if (b > 50 && b <= 75) {
			ItemStack calca = new ItemStack(Material.LEATHER_LEGGINGS);
			LeatherArmorMeta calca2 = (LeatherArmorMeta) calca.getItemMeta();
			calca2.setDisplayName("§6Laranja Armor");
			calca2.setColor(Color.ORANGE);
			calca.setItemMeta(calca2);
			p.getInventory().setLeggings(calca);
		} else if (b > 75 && b <= 100) {
			ItemStack calca = new ItemStack(Material.LEATHER_LEGGINGS);
			LeatherArmorMeta calca2 = (LeatherArmorMeta) calca.getItemMeta();
			calca2.setDisplayName("§3Ciano Armor");
			calca2.setColor(Color.AQUA);
			calca.setItemMeta(calca2);
			p.getInventory().setLeggings(calca);
		}

		int c = r.nextInt(100);
		if (c <= 25) {
			ItemStack bota = new ItemStack(Material.LEATHER_BOOTS);
			LeatherArmorMeta bota2 = (LeatherArmorMeta) bota.getItemMeta();
			bota2.setDisplayName("§aVerde Armor");
			bota2.setColor(Color.LIME);
			bota.setItemMeta(bota2);
			p.getInventory().setBoots(bota);
		} else if (c > 25 && c <= 50) {
			ItemStack bota = new ItemStack(Material.LEATHER_BOOTS);
			LeatherArmorMeta bota2 = (LeatherArmorMeta) bota.getItemMeta();
			bota2.setDisplayName("§dRosa Armor");
			bota2.setColor(Color.FUCHSIA);
			bota.setItemMeta(bota2);
			p.getInventory().setBoots(bota);
		} else if (c > 50 && c <= 75) {
			ItemStack bota = new ItemStack(Material.LEATHER_BOOTS);
			LeatherArmorMeta bota2 = (LeatherArmorMeta) bota.getItemMeta();
			bota2.setDisplayName("§6Laranja Armor");
			bota2.setColor(Color.ORANGE);
			bota.setItemMeta(bota2);
			p.getInventory().setBoots(bota);
		} else if (c > 75 && c <= 100) {
			ItemStack bota = new ItemStack(Material.LEATHER_BOOTS);
			LeatherArmorMeta bota2 = (LeatherArmorMeta) bota.getItemMeta();
			bota2.setDisplayName("§3Ciano Armor");
			bota2.setColor(Color.AQUA);
			bota.setItemMeta(bota2);
			p.getInventory().setBoots(bota);
		}
	}

	public static void sendActionBar(Player p, String msg) {
		if (Main.plugin.getConfig().getBoolean("Configuracao.Usar_Action_Bar")) {
			IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + msg + "\"}");
			PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(ppoc);
		}
	}
}