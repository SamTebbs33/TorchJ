import java.io.InputStream;
import java.util.Scanner;


/**
 * Sam'sLib
 * @author samtebbs, 22:56:16 - 20 Apr 2015
 */
public class Input {
    
    public static void main(String[] args) {
	System.out.println((new Input(System.in)).readSafeInt());
    }
    
    protected Scanner in;
    
    public Input(InputStream is) {
	in = new Scanner(is);
    }
    
    public String readStr(){
	return in.nextLine();
    }
    
    public int readInt() throws NumberFormatException{
	return Integer.parseInt(readStr());
    }
    
    public int readSafeInt(){
	while(true){
	    try {
		return readInt();
	    } catch (NumberFormatException e) {
	    }
	}
    }
    
    public char readChar(){
	return readStr().charAt(0);
    }

}
