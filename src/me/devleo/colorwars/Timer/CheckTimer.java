package me.devleo.colorwars.Timer;

import java.util.Calendar;

import org.bukkit.Bukkit;

import me.devleo.colorwars.Main;
import me.devleo.colorwars.Manager;

public class CheckTimer {

	public static void checkTempo() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable() {
			public void run() {
				Calendar c = Calendar.getInstance();
				String horario = Main.plugin.getConfig().getString("AutoStart.Horario");
				String split[] = horario.split(":");
				int hora = Integer.valueOf(split[0]);
				int minuto = Integer.valueOf(split[1]);
				if (Main.plugin.getConfig().getString("AutoStart.Dia").equalsIgnoreCase("segunda")) {
					if (c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
						if ((c.get(Calendar.HOUR_OF_DAY) == hora) && (c.get(Calendar.MINUTE) == minuto)) {
							if (!Manager.acontecendo) {
								Iniciar.iniciarEvento();
							}
						}
					}
				}
				if (Main.plugin.getConfig().getString("AutoStart.Dia").equalsIgnoreCase("terca")) {
					if (c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
						if ((c.get(Calendar.HOUR_OF_DAY) == hora) && (c.get(Calendar.MINUTE) == minuto)) {
							if (!Manager.acontecendo) {
								Iniciar.iniciarEvento();
							}
						}
					}
				}
				if (Main.plugin.getConfig().getString("AutoStart.Dia").equalsIgnoreCase("quarta")) {
					if (c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
						if ((c.get(Calendar.HOUR_OF_DAY) == hora) && (c.get(Calendar.MINUTE) == minuto)) {
							if (!Manager.acontecendo) {
								Iniciar.iniciarEvento();
							}
						}
					}
				}
				if (Main.plugin.getConfig().getString("AutoStart.Dia").equalsIgnoreCase("quinta")) {
					if (c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
						if ((c.get(Calendar.HOUR_OF_DAY) == hora) && (c.get(Calendar.MINUTE) == minuto)) {
							if (!Manager.acontecendo) {
								Iniciar.iniciarEvento();
							}
						}
					}
				}
				if (Main.plugin.getConfig().getString("AutoStart.Dia").equalsIgnoreCase("sexta")) {
					if (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
						if ((c.get(Calendar.HOUR_OF_DAY) == hora) && (c.get(Calendar.MINUTE) == minuto)) {
							if (!Manager.acontecendo) {
								Iniciar.iniciarEvento();
							}
						}
					}
				}
				if (Main.plugin.getConfig().getString("AutoStart.Dia").equalsIgnoreCase("sabado")) {
					if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
						if ((c.get(Calendar.HOUR_OF_DAY) == hora) && (c.get(Calendar.MINUTE) == minuto)) {
							if (!Manager.acontecendo) {
								Iniciar.iniciarEvento();
							}
						}
					}
				}
				if (Main.plugin.getConfig().getString("AutoStart.Dia").equalsIgnoreCase("domingo")) {
					if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
						if ((c.get(Calendar.HOUR_OF_DAY) == hora) && (c.get(Calendar.MINUTE) == minuto)) {
							if (!Manager.acontecendo) {
								Iniciar.iniciarEvento();
							}
						}
					}
				}
			}
		}, 0L, 200L);
	}
}