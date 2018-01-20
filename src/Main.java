import java.io.File;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		String json=null;
		try{
    		json = new Scanner(new File("/home/dragos/eclipse-workspace/ParserJson/src/json_eg.txt")).useDelimiter("\\Z").next();
    	}catch(Exception e) {
    		System.out.println(e);
    	}
		JSONParser jsonParser=new JSONParser(json);
		jsonParser.validateJSON();
		System.out.println("Nothing wrong with this json string");
	}

}
