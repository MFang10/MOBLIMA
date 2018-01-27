//Author: Tony Zeng
package cathay;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class FileMapManager {
	
	public FileMapManager() {};

	@SuppressWarnings("unchecked")
	public static Map<Object,Object> read(String filePath) {
		Map<Object,Object> map=new HashMap<>();
		//System.out.println(filePath);
	 	File file = new File(filePath); 
	 	//if(file!=null) System.out.println("file not null");
		/*
		 * Check the existence of the file.
		 */
	 	if(file.length()!=0)
	 	{
	 		try {
	 			
	 			FileInputStream fis = new FileInputStream(file);  
				ObjectInputStream ois = new ObjectInputStream(fis);  
				map = (HashMap<Object,Object>) ois.readObject();  
				ois.close();  	
				fis.close();
	 		}
	 		catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace(); 
			}
	 	}
		return map;		
	}
	
    public static void updateFile(String filePath,Map<Object,Object> map) {
		try  
	    {   
			File file = new File(filePath); 
			FileOutputStream fos = new FileOutputStream(file);  
			ObjectOutputStream oos = new ObjectOutputStream(fos);  
			oos.writeObject(map);  
			oos.flush();  
			oos.close();  
			fos.close(); 
			//map.clear();
		 }
		 catch (IOException e)  
		 {  
		    e.printStackTrace();  
		 }
	 	
	}
}