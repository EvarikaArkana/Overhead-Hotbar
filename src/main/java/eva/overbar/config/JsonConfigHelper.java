package eva.overbar.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eva.overbar.OverbarClient;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonConfigHelper {
    private static final File folder = new File("config");
    private static File overbarConfig;
    public static Gson configGson = new GsonBuilder().setPrettyPrinting().create();

    public static void init() {
        createConfig();
        readFromConfig();
        writeToConfig();
    }

    public static void createConfig() {
        if (!folder.exists()) {
            folder.mkdir();
        }
        if (folder.isDirectory()) {
            overbarConfig = new File(folder, "overbar.json");
            boolean seemsValid;
            if (overbarConfig.exists()) {
                try {
                    String templateConfigJson = Files.readString(Path.of(overbarConfig.getPath()));
                    seemsValid = templateConfigJson.trim().startsWith("{");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                seemsValid = true;
            }
            if (!overbarConfig.exists() || !seemsValid) {
                if (!seemsValid) {
                    OverbarClient.LOGGER.info("Found invalid config file, creating new config file at './config/overbar.json'.");
                }
                try {
                    overbarConfig.createNewFile();
                    String json = configGson.toJson(OverbarConfig.getInstance());
                    FileWriter writer = new FileWriter(overbarConfig);
                    writer.write(json);
                    writer.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

    public static void readFromConfig() {
        try {
            OverbarConfig config = configGson.fromJson(new FileReader(overbarConfig), OverbarConfig.class);
            OverbarConfig.getInstance().updateConfigs(config);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public static void writeToConfig() {
        try {
            String json = configGson.toJson(OverbarConfig.getInstance());
            FileWriter writer = new FileWriter(overbarConfig, false);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}