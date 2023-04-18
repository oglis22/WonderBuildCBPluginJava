package dev.lupluv.cb.belohnung;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileMangerB {

public static File file = new File("plugins//Belohnungssystem//rewards.yml");;
public static YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);;

    public static void loadFile(){

        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




}
