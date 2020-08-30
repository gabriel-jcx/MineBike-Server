package edu.ics.uci.minebike.minecraft.serverSave;

import edu.ics.uci.minebike.minecraft.client.AI.GamePlayTracker;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;

// TODO: should this class be made into a singleton?
@SideOnly(Side.SERVER)
public class ServerSaveManager {
    private static String cwd = null;
    private static String saveFolderName = null;
    private static File saveFolder = null;

    // store the absolute path to files
    private static ArrayList<String> PlayerSaveFileNames = new ArrayList<>();

    public ServerSaveManager(){
        this.cwd = FileSystems.getDefault().getPath(".").toAbsolutePath().toString();
        this.saveFolderName = cwd.concat("/MineBikeSaves/");
        this.saveFolder = FileSystems.getDefault().getPath(saveFolderName).toFile();
        if(!isFolderExist(saveFolderName)){
            saveFolder.mkdir();
        }
        // read all the player files to
        for(final File fileEntry: saveFolder.listFiles()){
            PlayerSaveFileNames.add(fileEntry.getAbsolutePath().toString());
        }
    }

    public boolean savePlayerToFile(EntityPlayer player, GamePlayTracker object){
        String name = player.getName();
        File save = FileSystems.getDefault().getPath(saveFolderName.concat(name)).toFile();
        try(FileWriter fileWriter = new FileWriter(save)){
            fileWriter.write(object.toJson().toString());
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean isFolderExist(String name){
        Path path = FileSystems.getDefault().getPath(name);
        File folder = path.toFile();
        if(folder.isDirectory())
            return true; // return true if directory
        return false;
    }

    public static String getSaveFolderName(){
        return saveFolderName;
    }

}
