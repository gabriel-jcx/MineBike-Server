# MineBike-Server

## General Overview of the file structure
```MinebikeWorld``` Contains the previously saved world file from MC 1.7.10
```forge-1.12.2-14.23.5.2838-mdk``` The code base for the current project
```forge-server``` The actual server code for you to run through testing.
```spongeplugin-proj``` A example base for the spongeplugin.

## Setting up the work space 
### Setting up the Forge Mod
To setup the minecraft forge mod, 
First run: 
```
./gradlew setupDecompWorkspace --refresh-dependencies
```

For IntelliJ:
  Run ```./gradlew genIntellijRuns```
  Goto File->New->Project from Existing Source
  Choose the created intelliJ project file. (Should be a file with ipr extension)
  Click Ok

 
After the import is done:
  Goto File->Project Structure
  Find "Modules" under Project Settings
  
Goto Run->Edit Configuration:
  ```To run the MC server``` 
  Add New Configuration -> Application -> Put "GradleStartServer" under MainClass.
  ```To run the MC client```
  Add New Configuration -> Application -> Put "GradleStart" under MainClass.

  Click OK.
  
You should be able to build the mod and run it in IntelliJ.




### Setting up the sponge plugin

To setup the workspace for sponge plugin, import `build.gradle` under the `sponge-plugin` folder.

## Build Process

To build the forge-mod or the sponge plugin you can run the following in the corresponding directory:
```
./gradlew build
```

This will generate the corresponding ```jar``` file under ```build/lib/```

## Testing your changes

To test the Mod or Plugin, put the build `jar` file under `forge-server/mods/`

Then run:
```
java -jar forge-1.12.2-14.23.5.2838-universal.jar
```
