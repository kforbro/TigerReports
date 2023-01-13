package fr.mrtigreroux.tigerreports.listeners;

import fr.mrtigreroux.tigerreports.data.database.Database;
import fr.mrtigreroux.tigerreports.logs.Logger;
import fr.mrtigreroux.tigerreports.managers.UsersManager;
import fr.mrtigreroux.tigerreports.objects.users.User;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;

/**
 * @author MrTigreroux
 */
public class InventoryListener implements Listener {

    private final Database db;
    private final UsersManager um;

    public InventoryListener(Database db, UsersManager um) {
        this.db = db;
        this.um = um;
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onInventoryDrag(InventoryDragEvent e) {
        Logger.EVENTS.info(() -> "onInventoryDrag(): " + e.getWhoClicked().getName());
        if (checkMenuAction(e.getWhoClicked(), e.getInventory()) != null) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onInventoryClick(InventoryClickEvent e) {
        Logger.EVENTS.info(() -> "onInventoryClick(): " + e.getWhoClicked().getName());
        Inventory inv = e.getClickedInventory();
        User u = checkMenuAction(e.getWhoClicked(), inv);
        if (u != null) {
            if (inv.getType() == InventoryType.CHEST) {
                e.setCancelled(true);
                if (e.getCursor().getType() == Material.AIR) {
                    u.getOpenedMenu().click(e.getCurrentItem(), e.getSlot(), e.getClick());
                }
            } else if (inv.getType() == InventoryType.PLAYER
                    && (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY
                    || e.getAction() == InventoryAction.COLLECT_TO_CURSOR)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onInventoryClose(InventoryCloseEvent e) {
        if (!(e.getPlayer() instanceof Player)) {
            return;
        }

        User u = um.getOnlineUser((Player) e.getPlayer());
        if (u != null) {
            Logger.EVENTS.info(() -> "onInventoryClose(): " + u.getName());
            u.setOpenedMenu(null);
            try {
                db.startClosing();
            } catch (Exception ignored) {
            }
        }
    }

    private User checkMenuAction(HumanEntity whoClicked, Inventory inv) {
        if (!(whoClicked instanceof Player) || inv == null) {
            return null;
        }
        User u = um.getOnlineUser((Player) whoClicked);
        return u.getOpenedMenu() != null ? u : null;
    }

}
