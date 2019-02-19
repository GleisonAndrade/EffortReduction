/**
 * 
 */
package br.ufpi.easii.effortreduction.file;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * @author Gleison Andrade
 *
 */
public class CSVToArff {
	
	private String dataArff = "";
	
	public void generateArffFile(String path, String fileCSVName, String sep, String fileArffName){
		dataArff += relationBuild(fileArffName);
		
		readFile(path, fileCSVName, sep);
		
		dataArff += classBuild();
		
		saveArff(path, fileArffName);
	}
	
	private void saveArff(String path, String fileArffName) {
		FilesUtil.generateFile(path + "\\" + fileArffName + ".arff", dataArff);		
	}

	public void readFile(String path, String fileName, String sep){
		BufferedReader bufferedReader = null;
		String line = "";
		
		try {
			bufferedReader = new BufferedReader(new FileReader(path + "\\" + fileName + ".csv"));
			line = bufferedReader.readLine();
			
			splitAttributes(line);
			
			dataBuild();
			while ((line = bufferedReader.readLine()) != null){
				dataArff += (line + "\n");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void splitAttributes(String line) {
		String [] data = line.split(",");
		int max = data.length;
		for (int i = 1; i < max; i++){
			dataArff  += attributeBuild(data[i]);
		}
	}

	private String relationBuild(String dataName) {
		return "@relation \'" + dataName + "\'\n\n";
	}

	private String attributeBuild(String name) {
		return "@attribute " + name + " numeric\n";
	}
	
	private String classBuild(){
		return "@attribute finalEvaluate {ACCEPTED,REJECTED}\n\n";
	}

	private String dataBuild() {
		return "@data\n";
	}

}
