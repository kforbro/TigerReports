package fr.mrtigreroux.tigerreports.data.constants;

import fr.mrtigreroux.tigerreports.data.config.Message;
import fr.mrtigreroux.tigerreports.utils.MessageUtils;
import org.bukkit.command.CommandSender;

/**
 * @author MrTigreroux
 */

public enum Permission {

    REPORT,
    REPORT_EXEMPT,
    STAFF,
    STAFF_TELEPORT,
    STAFF_ARCHIVE,
    STAFF_ARCHIVE_AUTO,
    STAFF_DELETE,
    STAFF_ADVANCED,
    MANAGE;

    public String get() {
        return "tigerreports." + name().toLowerCase().replace("_", ".");
    }

    public boolean check(CommandSender s) {
        if (!s.hasPermission(get())) {
            MessageUtils.sendErrorMessage(s, Message.PERMISSION_COMMAND.get());
            return false;
        }
        return true;
    }

}
