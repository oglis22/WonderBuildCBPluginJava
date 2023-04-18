package dev.lupluv.cb.utils;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.apache.commons.io.IOUtils.DEFAULT_BUFFER_SIZE;

public class FileManager {

    public FileManager() throws IOException {
        loadFiles();
    }

    public void loadFiles() throws IOException {
        File folder = new File("plugins//Citybuild");
        File warpsFolder = new File("plugins//Citybuild//Warps");
        File configFile = new File("plugins//Citybuild//config.yml");
        File moneyFile = new File("plugins//Citybuild//money.yml");
        File homesFile = new File("plugins//Citybuild//homes.yml");
        File mysqlFile = new File("plugins//Citybuild//mysql.yml");
        File chestShopFile = new File("plugins//Citybuild//chestshops.yml");
        File voteFile = new File("plugins//Citybuild//votes.yml");
        File elevatorFile = new File("plugins//Citybuild//elevators.yml");
        File randFile = new File("plugins//Citybuild//rand.yml");
        File statsFile = new File("plugins//Citybuild//stats.yml");
        File worthFile = new File("plugins//Citybuild//worth.yml");
        File jobsFile = new File("plugins//Citybuild//jobs.json");
        File broadcastFile = new File("plugins//Citybuild//broadcast.yml");
        if(!folder.exists()) folder.mkdir();
        if(!warpsFolder.exists()) warpsFolder.mkdir();
        if(!configFile.exists()){
            configFile.createNewFile();
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(configFile);
            cfg.set("Licence", "wonderbuild47298734validd892374923");
            cfg.save(configFile);
        }
        if(!moneyFile.exists()){
            moneyFile.createNewFile();
        }
        if(!homesFile.exists()){
            homesFile.createNewFile();
        }
        if(!mysqlFile.exists()){
            mysqlFile.createNewFile();
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(mysqlFile);
            cfg.set("Host", "localhost");
            cfg.set("Port", "3306");
            cfg.set("Database", "database");
            cfg.set("Username", "username");
            cfg.set("Password", "password");
            cfg.save(mysqlFile);
        }
        if(!chestShopFile.exists()){
            chestShopFile.createNewFile();
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(chestShopFile);
        }
        if(!voteFile.exists()){
            voteFile.createNewFile();
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(voteFile);
        }
        if(!elevatorFile.exists()){
            elevatorFile.createNewFile();
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(elevatorFile);
        }
        if(!randFile.exists()){
            randFile.createNewFile();
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(randFile);
        }
        if(!statsFile.exists()){
            statsFile.createNewFile();
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(statsFile);
        }
        if(!worthFile.exists()){
            worthFile.createNewFile();
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(worthFile);
            for(CbItem cbItem : CbItem.values()){
                cfg.set(cbItem.toString() + ".Buy", 2500.0);
                cfg.set(cbItem.toString() + ".Sell", 2000.0);
            }
            cfg.save(worthFile);
        }
        if(!jobsFile.exists()){
            copyResourceFile("/defaults/jobs.json", "plugins//Citybuild//jobs.json");
        }
        if(!broadcastFile.exists()){
            copyResourceFile("/defaults/broadcast.yml", "plugins//Citybuild//broadcast.yml");
        }
    }

    public File getJobsFile() {
        return new File("plugins//Citybuild//jobs.json");
    }

    public FileConfiguration getJobs(){
        return YamlConfiguration.loadConfiguration(getJobsFile());
    }

    public File getMysqlFile(){
        return new File("plugins//Citybuild//mysql.yml");
    }

    public FileConfiguration getMysql(){
        return YamlConfiguration.loadConfiguration(getMysqlFile());
    }

    public FileConfiguration getBroadcast(){
        return YamlConfiguration.loadConfiguration(new File("plugins//Citybuild//broadcast.yml"));
    }

    public String getLicence(){
        File configFile = new File("plugins//Citybuild//config.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(configFile);
        return cfg.getString("Licence");
    }

    public FileConfiguration getWorthConf(){
        File worthFile = new File("plugins//Citybuild//worth.yml");
        return YamlConfiguration.loadConfiguration(worthFile);
    }

    public Worth getWorth(CbItem cbItem){
        FileConfiguration cfg = getWorthConf();
        if(cfg.get(cbItem.toString() + ".Buy") != null){
            return new Worth(cbItem, cfg.getDouble(cbItem.toString() + ".Buy"), cfg.getDouble(cbItem.toString() + ".Sell"));
        }
        return null;
    }

    public void copyResourceFile(String src, String dest) throws IOException {
        copyInputStreamToFile(this.getClass().getResourceAsStream(src), new File(dest));
    }
    private static void copyInputStreamToFile(InputStream inputStream, File file) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(file, false)) {
            int read;
            byte[] bytes = new byte[DEFAULT_BUFFER_SIZE];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }
    }

}
