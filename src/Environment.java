import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Sam'sLib
 * @author samtebbs, 15:34:52 - 18 Apr 2015
 */
public class Environment {
    
    /**
     * Create a new UNIX bash command alias
    * @param filePath
    * @param name
    * @param command
    * @throws IOException
    *
     */
    public static void makeSystemAlias(String filePath, String name, String command) throws IOException{
	File f = new File(filePath.replace("~", System.getProperty("user.home")));
	f.createNewFile();
	// Create file writer with append property
	FileWriter fw = new FileWriter(f, true);
	// Write alias
	fw.write(String.format("alias %s=\"%s\"\n", name, command));
	fw.close();
    }
    
    /**
     * Create a new UNIX bash command alias and run a command aftewards
    * @param filePath
    * @param name
    * @param command
    * @param postCommand
    * @throws IOException
    *
     */
    public static void makeSystemAlias(String filePath, String name, String command, String postCommand) throws IOException {
	makeSystemAlias(filePath, name, command);
	if(postCommand != null && !postCommand.isEmpty()) Runtime.getRuntime().exec(postCommand);
    }

}
