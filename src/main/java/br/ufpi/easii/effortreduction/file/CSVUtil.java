/**
 * 
 */
package br.ufpi.easii.effortreduction.file;

import java.util.ArrayList;
import java.util.List;

import br.ufpi.easii.effortreduction.PerformanceData;

/**
 * @author Gleison Andrade
 *
 */


public class CSVUtil {
	public static final String separator = ";";
	public static final String endLine = "\n";
	public static final String header = "Algorithm;Selection;CountAttribute;TotalTraining;TotalTest;TrueNegative;FalseNegative;TruePositive;FalsePositive;RecallAccepted;WSS\n";
	
	private String fileName;
	private List<PerformanceData> listData = new ArrayList<>();
//	private FilesUtil filesUtil = new FilesUtil();
	
	public void addData(String algorithm, boolean attributeSelection, Integer totalTraining, Integer totalTest,
			Integer trueNegative, Integer falseNegative, Integer truePositive, Integer falsePositive,
			Double recallAccepted, Integer countAttribute){
		addData(new PerformanceData(algorithm, attributeSelection, totalTraining, totalTest, trueNegative, falseNegative, truePositive, falsePositive, recallAccepted, countAttribute));
	}
	
	public void addData(PerformanceData data){
		listData.add(data);
	}
	
	public void generateCSV(){
		String data = header;
		
		for (PerformanceData performanceData : listData) {
			data = data.concat(performanceData.getDataCSV()+endLine);
		}
		
		FilesUtil.generateFile(fileName + ".csv", data);
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the listData
	 */
	public List<PerformanceData> getListData() {
		return listData;
	}

	/**
	 * @param listData the listData to set
	 */
	public void setListData(List<PerformanceData> listData) {
		this.listData = listData;
	}	

}