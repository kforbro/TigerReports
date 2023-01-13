package fr.mrtigreroux.tigerreports.objects.users;

import fr.mrtigreroux.tigerreports.managers.VaultManager;

/**
 * @author MrTigreroux
 */
public interface UserData {

    String getName();

    String getDisplayName(VaultManager vm);

    String getDisplayName(VaultManager vm, boolean staff);

}
