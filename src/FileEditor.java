package cathay;
import java.util.*;
import java.io.*;

/* This class is a customised file editor for the MOBLIMA application*/

public class FileEditor {

	/* Constructor */
	public FileEditor() {}

	
	
	/* Create writers and readers */
	
	BufferedWriter bw = null;
	FileWriter fw = null;
    PrintWriter pw = null;
    
	BufferedReader br = null;
	FileReader fr = null;
	File file = null;
	
	
	
	/* Create a list of Strings */
	
    private String line = null;
    List<String> all = null;
	
    
    
	/* Check file existence*/
	
	public boolean checkExistence(String fileName) {
		file = new File(fileName);
		return file.exists();
	}
	
	
	
	/* writeFile allows append and overwrite. If file not found, create new file.*/
	
	public boolean writeFile(String fileName, String content, boolean overWrite){
		
		try {

			file = new File(fileName);
			
			if(!file.exists()) {
				file.createNewFile();
			}
			
			if (overWrite) fw = new FileWriter(file);
			else fw = new FileWriter(file, true);
			
			bw = new BufferedWriter(fw);
			pw = new PrintWriter(bw);
			
			pw.println(content);
			pw.flush();
			pw.close();
			
			return true;
			
		}catch(IOException e) {  
			System.out.println("Trouble with file IO. Exiting");
			return false;
		}
		
	}

	
	
	/* Normal file reading, modify "Movie Rating: 0.0" to "Movie Rating: NA" when printing */
	
	public void readFile(String fileName) {
		try {
			file = new File(fileName);
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			
			String text = null;
			while((text = br.readLine())!=null) {
			    if (text.equals("Movie Rating: 0.0")) text = "Movie Rating: NA";
				System.out.println(text);
			}
			fr.close();
			br.close();
		}catch(Exception e) {
			System.out.println("This file does not exist");
			System.out.println("Trouble with file IO. Exiting");
		}
	}


    
	/* Store the contents of a file into a list of Strings */
	
	public List<String> fileToList(String sourceFileName){
		try {
			all = new LinkedList<String>();
			file = new File(sourceFileName);
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			
			while((line = br.readLine())!= null) {
				all.add(line);
			}
			
			fr.close();
			br.close();

			return all; 
			
		}catch(Exception e) {
			
			System.out.println("Trouble with file IO. Exiting");
			return null;
		}
	}
	
	
	
    /* Allows replacement of a keyword or a line containing the keyword - search and replace */
	
    public boolean update(String fileName, String oldLine, String newLine, boolean wholeLine) {
    	
        try {
            file = new File(fileName);
            all = new LinkedList<String>();
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            boolean found = false;
            
            
            while ((line = br.readLine()) != null) {
                if ((line.contains(oldLine)) && !found) {           // Capture the first appearance of the target
                	if (wholeLine) line = line.replace(line, newLine);
                	else line = line.replaceAll(oldLine, newLine);
                	found = true;
                }
                all.add(line);
            }
            
            if(!found) {
            	System.out.println("Target not found.");
            	return false;
            }
            
            fr.close();
            br.close();

            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            pw  = new PrintWriter(bw);
            
            for(String s : all)
                 pw.println(s);
            pw.flush();
            pw.close();
            
            return true;
            
        } catch (Exception ex) {
        	System.out.println("Trouble with file IO. Exiting");
            return false;
        }
    }
    
    
    
    
    /* Remove a line from the file */
    
    public boolean removeLine(String fileName, String cue) {
    	
        try {
            file = new File(fileName);
            all = new LinkedList<String>();
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            boolean found = false;
            
            while ((line = br.readLine()) != null) {
                if ((line.contains(cue))) {            // Capture the first appearance of the target
                	found = true;
                }
                
                else all.add(line);
            }
            
            if(!found) {
            	System.out.println("Target not found.");
            	return false;
            }

            fr.close();
            br.close();

            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            pw  = new PrintWriter(bw);
            
            for(String s : all)
                 pw.println(s);
            pw.flush();
            pw.close();
            
            return true;
            
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    
    
    
    /* Convert a String list to String */
    
    public String listToString(List<String> aList) {
    	try {
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            pw  = new PrintWriter(bw);
            
            String text = null;
            for(String s : all)
                 text += s;
            pw.flush();
            pw.close();
            return text;
            
    	} catch (Exception ex) {
    		System.out.println("Trouble with file IO. Exiting");
            return null;
    	}
    }
    
    
    
    /* Extract contents of a target line based on cue. getSub determines whether the whole line or the subsequent portion */
    
    public String extract(String fileName, String cue, boolean getSub) {
    	
        try {
            file = new File(fileName);
 
			if(!file.exists()) {
				System.out.println("No relevant records. Exiting...");
				return null;
			}
			
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            boolean found = false;
            String key = null;
            
            while ((line = br.readLine()) != null) {
            	
                if (line.contains(cue)) {
                	String newCue = cue.substring(cue.length()-2,cue.length()-1); // In this application all the cues are predefined. Get the ":" as the key identifier
                	if(!getSub) key = line;
                	else key = line.substring(line.indexOf(newCue)+2, line.length()); //Target substring content start from indexOf(':')+2. e.g. Name: A
                	found = true;
                	break;
                }

            }
            
            if(!found) {
            	System.out.println("Target not found!");
            	return null;
            }

            fr.close();
            br.close();
            
            return key;
            
        } catch(Exception ex) {
        	System.out.println("No relevant records. Exiting...");
            return null;
        }

    }
    
}


