package br.ufpi.easii.effortreduction.teste;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class TrainTestSplit {

	/**
 * 	 * @param args
 * 	 	 */
	public static void main(String[] args) throws Exception {
		/**
 * 		 * prepare training and testing data
 * 		 		 */
		String dataFileName = "iris.arff";	// use appropriate path
		Instances data = (new DataSource(dataFileName)).getDataSet();
		data.setClassIndex(data.numAttributes() - 1);
		data.randomize(new java.util.Random());	// randomize instance order before splitting dataset
		Instances trainData = data.trainCV(2, 0);
		Instances testData = data.testCV(2, 0);
		/**
 * 		 * build classifier model on training split
 * 		 		 */
		Classifier classy = new J48();
		classy.buildClassifier(trainData);
		/**
 * 		 * evaluate classifier model on test split
 * 		 		 */
		Evaluation eval = new Evaluation(trainData);
		eval.evaluateModel(classy, trainData);
		System.out.println("1-NN accuracy on training data:\n" + eval.pctCorrect()/100);
		eval.evaluateModel(classy, testData);
		System.out.println("1-NN accuracy on separate test data:\n" + eval.pctCorrect()/100);
	}

}