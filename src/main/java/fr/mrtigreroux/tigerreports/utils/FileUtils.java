package fr.mrtigreroux.tigerreports.utils;

import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MrTigreroux
 */
public class FileUtils {

    private static final String DEFAULT_ENCODING_CHARSET = "ASCII";

    public static File getPluginDataFile(Plugin plugin, String fileName) {
        return new File(plugin.getDataFolder(), fileName);
    }

    /**
     * @param file encoded in {@value #DEFAULT_ENCODING_CHARSET}
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static List<String> getFileLines(File file) throws FileNotFoundException, IOException {
        return getFileLines(file, DEFAULT_ENCODING_CHARSET);
    }

    public static List<String> getFileLines(File file, String charset)
            throws IOException, SecurityException {
        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fis, charset);
             BufferedReader br = new BufferedReader(isr)) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        }
    }

    /**
     * @param file  encoded in {@value #DEFAULT_ENCODING_CHARSET}
     * @param lines
     */
    public static void setFileLines(File file, List<String> lines) throws IOException, SecurityException {
        setFileLines(file, lines, DEFAULT_ENCODING_CHARSET);
    }

    public static void setFileLines(File file, List<String> lines, String charset)
            throws IOException, SecurityException {
        try (FileOutputStream fos = new FileOutputStream(file);
             OutputStreamWriter osw = new OutputStreamWriter(fos, charset);
             BufferedWriter bw = new BufferedWriter(osw);
             PrintWriter writer = new PrintWriter(bw)) {
            for (String line : lines) {
                writer.println(line);
            }
        }
    }

}
