package fr.mrtigreroux.tigerreports.listeners;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import fr.mrtigreroux.tigerreports.TigerReports;
import fr.mrtigreroux.tigerreports.objects.users.OnlineUser;
import fr.mrtigreroux.tigerreports.runnables.MenuUpdater;
import fr.mrtigreroux.tigerreports.utils.UserUtils;

/**
 * @author MrTigreroux
 */

public class InventoryListener implements Listener {
	
	@EventHandler(priority = EventPriority.LOW)
	private void onInventoryDrag(InventoryDragEvent e) {
		if(checkMenuAction(e.getWhoClicked(), e.getInventory()) != null) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOW)
	private void onInventoryClick(InventoryClickEvent e) {
		OnlineUser u = checkMenuAction(e.getWhoClicked(), e.getClickedInventory());
		if(u != null) {
			e.setCancelled(true);
			if(e.getCursor().getType() == Material.AIR) u.getOpenedMenu().click(e.getCurrentItem(), e.getSlot(), e.getClick());
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	private void onInventoryClose(InventoryCloseEvent e) {
		OnlineUser u = UserUtils.getOnlineUser((Player) e.getPlayer());
		MenuUpdater.removeUser(u);
		u.setOpenedMenu(null);
		try {
			TigerReports.getInstance().getDb().startClosing();
		} catch (Exception ignored) {}
	}
	
	private OnlineUser checkMenuAction(HumanEntity whoClicked, Inventory inv) {
		if(!(whoClicked instanceof Player) || inv == null || inv.getType() != InventoryType.CHEST) return null;
		OnlineUser u = UserUtils.getOnlineUser((Player) whoClicked);
		return u.getOpenedMenu() != null ? u : null;
	}
	
}
