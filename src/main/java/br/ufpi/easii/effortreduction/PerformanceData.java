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
	
	private Integer truePositive;
	private Integer falsePositive;
	
	private Double recallAccepted;
	
	/**
	 * @param algorithm
	 * @param attributeSelection
	 * @param totalTraining
	 * @param totalTest
	 * @param truePositive
	 * @param falsePositive
	 * @param recallAccepted
	 */
	public PerformanceData(String algorithm, boolean attributeSelection, Integer totalTraining, Integer totalTest, Integer truePositive, Integer falsePositive, Double recallAccepted) {
		super();
		this.algorithm = algorithm;
		this.attributeSelection = attributeSelection;
		this.totalTraining = totalTraining;
		this.totalTest = totalTest;
		this.truePositive = truePositive;
		this.falsePositive = falsePositive;
		this.recallAccepted = recallAccepted;
	}

	public Double calculateWSS(){
		return (truePositive + falsePositive)/((totalTraining+totalTest)-(1-recallAccepted));
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
		return truePositive;
	}

	/**
	 * @return the falsePositive
	 */
	public Integer getFalsePositive() {
		return falsePositive;
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
		this.truePositive = truePositive;
	}

	/**
	 * @param falsePositive the falsePositive to set
	 */
	public void setFalsePositive(Integer falsePositive) {
		this.falsePositive = falsePositive;
	}

	/**
	 * @param recallAccepted the recallAccepted to set
	 */
	public void setRecallAccepted(Double recallAccepted) {
		this.recallAccepted = recallAccepted;
	}

	public String getDataCSV() {
		return String.format("%s;%b;%d;%d;%d;%d;%.3f;%.3f", algorithm, attributeSelection, totalTraining, totalTest, truePositive, falsePositive, recallAccepted, calculateWSS());
	}
}