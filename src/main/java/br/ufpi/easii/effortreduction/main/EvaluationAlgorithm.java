/**
 * 
 */
package br.ufpi.easii.effortreduction.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;

import br.ufpi.easii.effortreduction.PerformanceData;
import weka.attributeSelection.BestFirst;
import weka.attributeSelection.CfsSubsetEval;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.misc.InputMappedClassifier;
import weka.core.Instances;
import weka.core.Utils;
import weka.filters.Filter;

/**
 * @author Gleison Andrade
 *
 */
public class EvaluationAlgorithm {
	
	public PerformanceData performanceData(Classifier classifier, Algorithm algorithm, String trainFile, String testFile, boolean selection) throws Exception {
		Instances training = getData(trainFile, 1);
		
		if(selection)
			training = selectFeaturesWithFilter(training);

		Instances testing = getData(testFile, 1);

		InputMappedClassifier classy = algorithm.getInputMappedClassifier();
		classy.setClassifier(classifier);

		classy.buildClassifier(training);

		Evaluation evaluation = new Evaluation(training);
		evaluation.evaluateModel(classy, testing);

		// System.out.println(eval.toCumulativeMarginDistributionString());
		// System.out.println(eval.toSummaryString());
		// System.out.println(eval.toClassDetailsString());
//		System.out.println(evaluation.toMatrixString());

		return new PerformanceData(algorithm.name(classifier), selection, training.numInstances(), testing.numInstances(), (int)evaluation.numTruePositives(0), (int)evaluation.numFalsePositives(0),
				evaluation.recall(1));
	}

	private static Instances getData(String filename, Integer posClass) throws IOException, URISyntaxException {
		File file = new File(filename);
		BufferedReader inputReader = new BufferedReader(new FileReader(file));
		Instances data = new Instances(inputReader);
		data.setClass(data.attribute("finalEvaluate"));

		return data;
	}

	public static Instances selectFeaturesWithFilter(Instances data) throws Exception {
		// weka.filters.supervised.attribute.AttributeSelection -E
		// "weka.attributeSelection.CfsSubsetEval -P 1 -E 1" -S
		// "weka.attributeSelection.BestFirst -D 1 -N 5"
		weka.filters.supervised.attribute.AttributeSelection filter = new weka.filters.supervised.attribute.AttributeSelection();
		CfsSubsetEval eval = new CfsSubsetEval();
		eval.setOptions(Utils.splitOptions("-P 1 -E 1"));
		BestFirst search = new BestFirst();
		search.setOptions(Utils.splitOptions("-D 1 -N 5"));
		filter.setEvaluator(eval);
		filter.setSearch(search);
		try {
			filter.setInputFormat(data);
			Instances newData = Filter.useFilter(data, filter);
			return (newData);
		} catch (Exception e) {
		}

		return null;
	}

}
