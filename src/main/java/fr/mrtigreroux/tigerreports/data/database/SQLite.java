package fr.mrtigreroux.tigerreports.data.database;

import fr.mrtigreroux.tigerreports.tasks.TaskScheduler;
import fr.mrtigreroux.tigerreports.utils.ConfigUtils;

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * @author MrTigreroux
 */

public class SQLite extends Database {

    public static final String USERS_TABLE = "('uuid' TEXT NOT NULL PRIMARY KEY, 'name' TEXT, 'cooldown' TEXT, 'immunity' TEXT, 'notifications' TEXT, 'true_appreciations' INTEGER DEFAULT '0', 'uncertain_appreciations' INTEGER DEFAULT '0', 'false_appreciations' INTEGER DEFAULT '0', 'reports' INTEGER DEFAULT '0', 'reported_times' INTEGER DEFAULT '0', 'processed_reports' INTEGER DEFAULT '0')";
    public static final String REPORTS_TABLE = "('report_id' INTEGER PRIMARY KEY, 'status' TEXT NOT NULL DEFAULT 'Waiting', 'appreciation' TEXT, 'date' TEXT, 'reported_uuid' TEXT, 'reporter_uuid' TEXT, 'reason' TEXT, 'reported_ip' TEXT, 'reported_location' TEXT, 'reported_messages' TEXT, 'reported_gamemode' TEXT, 'reported_on_ground' INTEGER, 'reported_sneak' INTEGER, 'reported_sprint' INTEGER, 'reported_health' TEXT, 'reported_food' TEXT, 'reported_effects' TEXT, 'reporter_ip' TEXT, 'reporter_location' TEXT, 'reporter_messages' TEXT, 'archived' INTEGER NOT NULL DEFAULT 0)";
    public static final String COMMENTS_TABLE = "('report_id' INTEGER NOT NULL, 'comment_id' INTEGER PRIMARY KEY, 'status' TEXT, 'date' TEXT, 'author' TEXT, 'message' TEXT)";

    private final File databaseFile;

    public SQLite(TaskScheduler taskScheduler, File databaseFolder, String databaseFileName) {
        super(taskScheduler);
        databaseFile = new File(databaseFolder, databaseFileName);
    }

    @Override
    public void openConnection() throws Exception {
        if (!databaseFile.exists()) {
            try {
                databaseFile.createNewFile();
            } catch (IOException ex) {
                logError(ConfigUtils.getInfoMessage("Failed creation of " + databaseFile.getName() + " file.",
                        "La creation du fichier " + databaseFile.getName() + " a echoue."), ex);
                throw ex;
            }
        }

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + databaseFile);
        } catch (ClassNotFoundException missing) {
            logError(ConfigUtils.getInfoMessage("SQLite is missing.", "SQLite n'est pas installe."), null);
            throw missing;
        } catch (SQLException ex) {
            logError(ConfigUtils.getInfoMessage("An error has occurred during the connection to the SQLite database:",
                    "Une erreur s'est produite lors de la connexion a la base de donnees SQLite:"), ex);
            throw ex;
        }
        return;
    }

    @Override
    public void initialize() {
        update("CREATE TABLE IF NOT EXISTS tigerreports_users " + USERS_TABLE, null);
        update("CREATE TABLE IF NOT EXISTS tigerreports_reports " + REPORTS_TABLE, null);
        update("CREATE TABLE IF NOT EXISTS tigerreports_comments " + COMMENTS_TABLE, null);
    }

    @Override
    public boolean isConnectionValid() throws SQLException {
        return connection != null && !connection.isClosed();
    }

    @Override
    public void updateUserName(String uuid, String name) {
        taskScheduler.runTaskAsynchronously(() -> {
            executeTransaction(() -> {
                update("INSERT OR IGNORE INTO tigerreports_users (uuid,name) VALUES (?,?)", Arrays.asList(uuid, name));
                update("UPDATE tigerreports_users SET name = ? WHERE uuid = ?", Arrays.asList(name, uuid));
            });
        });
    }

}
