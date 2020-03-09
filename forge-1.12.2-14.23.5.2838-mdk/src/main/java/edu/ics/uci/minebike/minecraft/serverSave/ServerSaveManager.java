package edu.ics.uci.minebike.minecraft.serverSave;

import java.nio.file.FileSystems;
import java.util.Map;

//
public class ServerSaveManager {
    String cwd = FileSystems.getDefault().getPath(".").toAbsolutePath().toString();
    Map<String, String> npcs;
    public void ServerSaveManager(){
        System.out.println("Current Dir:" + cwd);
    }
}
