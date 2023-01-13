package fr.mrtigreroux.tigerreports.objects.menus;

import fr.mrtigreroux.tigerreports.data.config.ConfigFile;
import fr.mrtigreroux.tigerreports.data.config.ConfigSound;
import fr.mrtigreroux.tigerreports.data.config.Message;
import fr.mrtigreroux.tigerreports.data.constants.MenuItem;
import fr.mrtigreroux.tigerreports.managers.VaultManager;
import fr.mrtigreroux.tigerreports.objects.CustomItem;
import fr.mrtigreroux.tigerreports.objects.users.User;
import fr.mrtigreroux.tigerreports.utils.ConfigUtils;
import fr.mrtigreroux.tigerreports.utils.MessageUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * @author MrTigreroux
 */

public class ReasonMenu extends Menu {

    final User tu;
    private final VaultManager vm;

    public ReasonMenu(User u, int page, User tu, VaultManager vm) {
        super(u, 54, page, null);
        this.tu = tu;
        this.vm = vm;
    }

    @Override
    public Inventory onOpen() {
        Inventory inv = getInventory(Message.REASON_TITLE.get().replace("_Target_", tu.getName()), true);

        inv.setItem(4, MenuItem.REASONS.get());
        int firstReason = 1;
        if (page >= 2) {
            inv.setItem(size - 7, MenuItem.PAGE_SWITCH_PREVIOUS.get());
            firstReason += (page - 1) * 27;
        }

        FileConfiguration configFile = ConfigFile.CONFIG.get();
        String targetDisplayName = tu.getDisplayName(vm, false);
        final String REASON_MESSAGE = Message.REASON.get();
        final String REASON_DETAILS_MESSAGE = Message.REASON_DETAILS.get();
        final String LINE_BREAK_SYMBOL = ConfigUtils.getLineBreakSymbol();

        for (int reasonIndex = firstReason; reasonIndex <= firstReason + 26; reasonIndex++) {
            String path = "Config.DefaultReasons.Reason" + reasonIndex;
            if (!ConfigUtils.exists(configFile, path))
                break;
            String blankCheck = configFile.getString(path);
            if (blankCheck != null && blankCheck.equals("blank"))
                continue;

            String reason = configFile.getString(path + ".Name");
            String lore = configFile.getString(path + ".Lore");
            inv.setItem(reasonIndex - firstReason + 18,
                    new CustomItem().fromConfig(configFile, path + ".Item")
                            .name(REASON_MESSAGE.replace("_Reason_", reason))
                            .lore(REASON_DETAILS_MESSAGE.replace("_Player_", targetDisplayName)
                                    .replace("_Reason_", reason)
                                    .replace("_Lore_", MessageUtils.translateColorCodes(lore != null ? lore : ""))
                                    .split(LINE_BREAK_SYMBOL))
                            .hideFlags(true)
                            .create());
        }

        if (ConfigUtils.exists(configFile, "Config.DefaultReasons.Reason" + (firstReason + 27)))
            inv.setItem(size - 3, MenuItem.PAGE_SWITCH_NEXT.get());

        return inv;
    }

    @Override
    public void onClick(ItemStack item, int slot, ClickType click) {
        if (slot >= 18 && slot <= size - 10) {
            ConfigSound.MENU.play(p);
            p.chat("/tigerreports:report " + tu.getName() + " " + ConfigFile.CONFIG.get()
                    .getString("Config.DefaultReasons.Reason" + (getItemGlobalIndex(slot)) + ".Name"));
            p.closeInventory();
        }
    }

}
