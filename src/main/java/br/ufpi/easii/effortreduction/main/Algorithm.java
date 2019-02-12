/**
 * 
 */
package br.ufpi.easii.effortreduction.main;

import weka.classifiers.bayes.ComplementNaiveBayes;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.misc.InputMappedClassifier;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Utils;

/**
 * @author gleys
 *
 */
public class Algorithm {

	private NaiveBayes naiveBayes;
	private ComplementNaiveBayes complementNaiveBayes;
	private SMO smo;
	private J48 j48;
	private RandomForest randomForest;
	private InputMappedClassifier inputMappedClassifier;

	/**
	 * 
	 */
	public Algorithm() {
		super();
		inicializaAlgoritmos();
	}

	public void inicializaAlgoritmos() {
		try {
			naiveBayes = new NaiveBayes();
//			naiveBayes.setOptions(Utils.splitOptions("-R 1"));

			complementNaiveBayes = new ComplementNaiveBayes();
			complementNaiveBayes.setOptions(Utils.splitOptions("-S 1.0"));

			smo = new SMO();
			smo.setOptions(Utils.splitOptions("-C 1.0 -L 0.001 -P 1.0E-12 -N 0 -V -1 -W 1 -K \"weka.classifiers.functions.supportVector.PolyKernel -E 1.0 -C 250007\" -calibrator \"weka.classifiers.functions.Logistic -R 1.0E-8 -M -1 -num-decimal-places 4\""));

			j48 = new J48();
			j48.setOptions(Utils.splitOptions("-C 0.25 -M 2"));

			randomForest = new RandomForest();
			randomForest.setOptions(Utils.splitOptions("-P 100 -I 100 -num-slots 1 -K 0 -M 1.0 -V 0.001 -S 1"));
			
			inputMappedClassifier = new InputMappedClassifier();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void build(String fileTraining, String fileTest, boolean selectionAttr) {
		if (selectionAttr) {
			buildSelectionAttr(fileTraining, fileTest);
		} else {
			buildNoSelectionAttr(fileTraining, fileTest);
		}
	}

	private void buildSelectionAttr(String fileTraining, String fileTest) {
		// TODO Auto-generated method stub

	}

	private void buildNoSelectionAttr(String fileTraining, String fileTest) {
		// TODO Auto-generated method stub

	}

	public NaiveBayes getNaiveBayes() {
		return naiveBayes;
	}

	public void setNaiveBayes(NaiveBayes naiveBayes) {
		this.naiveBayes = naiveBayes;
	}

	public ComplementNaiveBayes getComplementNaiveBayes() {
		return complementNaiveBayes;
	}

	public void setComplementNaiveBayes(ComplementNaiveBayes complementNaiveBayes) {
		this.complementNaiveBayes = complementNaiveBayes;
	}

	public SMO getSmo() {
		return smo;
	}

	public void setSmo(SMO smo) {
		this.smo = smo;
	}

	public J48 getJ48() {
		return j48;
	}

	public void setJ48(J48 j48) {
		this.j48 = j48;
	}

	public RandomForest getRandomForest() {
		return randomForest;
	}

	public void setRandomForest(RandomForest randomForest) {
		this.randomForest = randomForest;
	}

	public InputMappedClassifier getInputMappedClassifier() {
		return inputMappedClassifier;
	}

	public void setInputMappedClassifier(InputMappedClassifier inputMappedClassifier) {
		this.inputMappedClassifier = inputMappedClassifier;
	}
	
	

}
