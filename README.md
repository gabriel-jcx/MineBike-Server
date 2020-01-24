# MineBike-Server

To setup the MineBike-Server, unzip the forge source.

run: ./gredlew setupDecompWorkspace --refresh-dependencies

For IntelliJ:
  Goto File->New->Project from Existing Source
  Choose File "build.gradle"
  Click Ok
  Remember to Check "Use auto-import"
 
After the import is done:
  Goto File->Project Structure
  Find "Modules" under Project Settings
  Under "forge-1.12.2-14.23.5.2838-mdk_main" add the "spongeforge-1.12.2-2838-7.1.8.jar"
  
Goto Run->Edit Configuration:
  Add New Configuration -> Application -> Put "GradleStartServer" under MainClass.
  Click OK.
  
You should be able to build and run the server.
