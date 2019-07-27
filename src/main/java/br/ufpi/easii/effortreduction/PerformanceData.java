/**
 * 
 */
package br.ufpi.easii.effortreduction;

/**
 * @author Gleison Andrade
 *
 */
public class PerformanceData {
	private String algorithm;
	private boolean attributeSelection;
	
	private Integer totalTraining;
	private Integer totalTest;
	
	private Integer trueNegative;
	private Integer falseNegative;
	
	private Integer truePositive;
	private Integer falsePositive;
	
	private Double recallAccepted;
	
	private Integer countAttribute;
	
	private Integer freq_max;
	private Integer freq_min;


	public PerformanceData(String algorithm, boolean attributeSelection, Integer totalTraining, Integer totalTest,
			Integer trueNegative, Integer falseNegative, Integer truePositive, Integer falsePositive,
			Double recallAccepted, Integer countAttribute, Integer freq_max, Integer freq_min) {
		super();
		this.algorithm = algorithm;
		this.attributeSelection = attributeSelection;
		this.totalTraining = totalTraining;
		this.totalTest = totalTest;
		this.trueNegative = trueNegative;
		this.falseNegative = falseNegative;
		this.truePositive = truePositive;
		this.falsePositive = falsePositive;
		this.recallAccepted = recallAccepted;
		this.countAttribute = countAttribute;
		this.freq_max = freq_max;
		this.freq_min = freq_min;
	}

	public Double calculateWSS(){
		return (trueNegative + falseNegative)/((totalTraining+totalTest)-(1-recallAccepted));
	}

	/**
	 * @return the attributeSelection
	 */
	public boolean isAttributeSelection() {
		return attributeSelection;
	}

	/**
	 * @param attributeSelection the attributeSelection to set
	 */
	public void setAttributeSelection(boolean attributeSelection) {
		this.attributeSelection = attributeSelection;
	}

	/**
	 * @return the algorithm
	 */
	public String getAlgorithm() {
		return algorithm;
	}

	/**
	 * @return the totalTraining
	 */
	public Integer getTotalTraining() {
		return totalTraining;
	}

	/**
	 * @return the totalTest
	 */
	public Integer getTotalTest() {
		return totalTest;
	}

	/**
	 * @return the truePositive
	 */
	public Integer getTruePositive() {
		return trueNegative;
	}

	/**
	 * @return the falsePositive
	 */
	public Integer getFalsePositive() {
		return falseNegative;
	}

	/**
	 * @return the recallAccepted
	 */
	public Double getRecallAccepted() {
		return recallAccepted;
	}

	/**
	 * @param algorithm the algorithm to set
	 */
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	/**
	 * @param totalTraining the totalTraining to set
	 */
	public void setTotalTraining(Integer totalTraining) {
		this.totalTraining = totalTraining;
	}

	/**
	 * @param totalTest the totalTest to set
	 */
	public void setTotalTest(Integer totalTest) {
		this.totalTest = totalTest;
	}

	/**
	 * @param truePositive the truePositive to set
	 */
	public void setTruePositive(Integer truePositive) {
		this.trueNegative = truePositive;
	}

	/**
	 * @param falsePositive the falsePositive to set
	 */
	public void setFalsePositive(Integer falsePositive) {
		this.falseNegative = falsePositive;
	}

	/**
	 * @param recallAccepted the recallAccepted to set
	 */
	public void setRecallAccepted(Double recallAccepted) {
		this.recallAccepted = recallAccepted;
	}

	/**
	 * @return the trueNegative
	 */
	public Integer getTrueNegative() {
		return trueNegative;
	}

	/**
	 * @param trueNegative the trueNegative to set
	 */
	public void setTrueNegative(Integer trueNegative) {
		this.trueNegative = trueNegative;
	}

	/**
	 * @return the falseNegative
	 */
	public Integer getFalseNegative() {
		return falseNegative;
	}

	/**
	 * @param falseNegative the falseNegative to set
	 */
	public void setFalseNegative(Integer falseNegative) {
		this.falseNegative = falseNegative;
	}

	/**
	 * @return the countAttribute
	 */
	public Integer getCountAttribute() {
		return countAttribute;
	}

	/**
	 * @param countAttribute the countAttribute to set
	 */
	public void setCountAttribute(Integer countAttribute) {
		this.countAttribute = countAttribute;
	}

	public String getDataCSV() {
//		"Algorithm;Max;MinSelection;CountAttribute;TotalTraining;TotalTest;TrueNegative;FalseNegative;TruePositive;FalsePositive;RecallAccepted;WSS\n"
		return String.format("%s;%d;%d;%b;%d;%d;%d;%d;%d;%d;%d;%.3f;%.3f", algorithm, freq_max, freq_min, attributeSelection, countAttribute, totalTraining, totalTest, trueNegative, falseNegative, truePositive, falsePositive, recallAccepted, calculateWSS());
	}
}