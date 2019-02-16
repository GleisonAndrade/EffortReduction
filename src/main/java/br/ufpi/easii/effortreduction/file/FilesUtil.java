/**
 * 
 */
package br.ufpi.easii.effortreduction.file;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Gleison Andrade
 *
 */
public class FilesUtil {
	public static final String MAIN_PATH = "D:\\Google Drive\\Testes Mestrado\\maps";
	
	private String [][] paths = {{"code-familiarity", "28", "56", "84", "112", "141", "169", "197", "225", "253"},
								{"martony", "320", "640", "960", "1281", "1601", "1921", "2242", "2562", "2882"},
								{"oracle-teste", "50", "100", "150", "201", "251", "301", "352", "402", "452"},
								{"tools-ic", "59", "118", "178", "237", "297", "356", "415", "475", "534"},
								{"thasciano", "141", "282", "423", "564", "705", "846", "986", "1128", "1269"}};
	
	public void generateFile(String name, String data){
		FileWriter writer;
		try {
			writer = new FileWriter(name);
			writer.append(data);
			
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the paths
	 */
	public String[][] getPaths() {
		return paths;
	}

	/**
	 * @param paths the paths to set
	 */
	public void setPaths(String[][] paths) {
		this.paths = paths;
	}
	
	

}