/**
 * 
 */
package br.ufpi.easii.effortreduction.main;

import br.ufpi.easii.effortreduction.PerformanceData;
import br.ufpi.easii.effortreduction.file.CSVToArff;
import weka.attributeSelection.BestFirst;
import weka.attributeSelection.CfsSubsetEval;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.misc.InputMappedClassifier;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;

/**
 * @author Gleison Andrade
 *
 */
public class EvaluationAlgorithm {
	private Instances training;
	private Instances testing;
	private CSVToArff csvToArff = new CSVToArff();

	public PerformanceData buildClassifier(Classifier classifier, Algorithm algorithm, boolean selection)
			throws Exception {
//		Instances training = getData(trainFile, 1);

		if (selection)
			training = selectFeaturesWithFilter(training);

//		Instances testing = getData(testFile, 1);

		InputMappedClassifier classy = algorithm.getInputMappedClassifier();
		classy.setClassifier(classifier);
		classy.setSuppressMappingReport(true);
		classy.buildClassifier(training);

		Evaluation evaluation = new Evaluation(training);
		evaluation.evaluateModel(classy, testing);

		// System.out.println(eval.toCumulativeMarginDistributionString());
		// System.out.println(eval.toSummaryString());
		// System.out.println(eval.toClassDetailsString());
//		System.out.println(evaluation.toMatrixString());

		return new PerformanceData(algorithm.name(classifier), selection, training.numInstances(),
				testing.numInstances(), (int) evaluation.numTruePositives(0), (int) evaluation.numFalsePositives(0),
				evaluation.recall(1));
	}

	public void loadFiles(String pathTrainFile, String pathTestFile) {
		String path = "";
		String name = "";
		
		if(pathTrainFile.endsWith(".csv")){
			path = pathTrainFile.substring(0, pathTrainFile.lastIndexOf("\\"));
			name = pathTrainFile.substring(pathTrainFile.lastIndexOf("\\")+1, pathTrainFile.length()-4);
			
			csvToArff.generateArffFile(path, name, ",", name);
		}
		
		if(pathTestFile.endsWith(".csv")){
			path = pathTestFile.substring(0, pathTestFile.lastIndexOf("\\"));
			name = pathTestFile.substring(pathTestFile.lastIndexOf("\\")+1, pathTestFile.length()-4);
			
			csvToArff.generateArffFile(path, name, ",", name);
		}
		
		this.training = getData(pathTestFile);		
		this.testing = getData(pathTestFile);
	}

	private Instances getData(String filename) {
//		File file = new File(filename);
//		BufferedReader inputReader;
		Instances data = null;

		try {
//			inputReader = new BufferedReader(new FileReader(file));
//			data = new Instances(inputReader);
			// Read all the instances in the file (ARFF, CSV, XRFF, ...)
			DataSource source = new DataSource(filename);
			data = source.getDataSet();
			data.setClass(data.attribute("finalEvaluate"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
	}

	public Instances selectFeaturesWithFilter(Instances data) throws Exception {
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
