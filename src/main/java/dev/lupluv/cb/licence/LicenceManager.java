package dev.lupluv.cb.licence;

import dev.lupluv.cb.Citybuild;
import dev.lupluv.cb.utils.Strings;
import org.apache.commons.io.IOUtils;
import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class LicenceManager {
    public String licence;

    private static LicenceManager instance;

    public static LicenceManager getInstance() {
        if(instance == null){
            instance = new LicenceManager();
        }
        return instance;
    }

    public void doOnEnable(){
        try {
            checkLicence(new URL("https://wonderbuild.net/licensesystem"));
        } catch (IOException e) {
        }
        if(licence.contains(Citybuild.getFileManager().getLicence())){
            Bukkit.getConsoleSender().sendMessage(Strings.prefix + "§2Die angegebene Lizenz ist gültig.");
        }else{
            Bukkit.getConsoleSender().sendMessage(Strings.prefix + "§cDie Lizenz ist nicht gültig.");
            Bukkit.getPluginManager().disablePlugin(Citybuild.getPlugin());
        }
    }

    public void checkLicence(URL url) throws IOException {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder(128000);
        try{
            reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            int count;
            char[] data = new char[5000];
            while ((count = reader.read(data)) != -1){
                builder.append(data, 0, count);
            }
        }finally {
            IOUtils.closeQuietly(reader);
        }
        licence = builder.toString();
        Bukkit.getConsoleSender().sendMessage(Strings.prefix + "§7Es wurde eine Lizenz gefunden: " + Citybuild.getFileManager().getLicence());
    }

}
