/**
 * 
 */
package br.ufpi.easii.effortreduction.main;

import java.io.File;

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
	private Instances trainingSelected;
	private Instances testing;
	private CSVToArff csvToArff = new CSVToArff();

	public PerformanceData buildClassifier(Classifier classifier, Algorithm algorithm, boolean selection, Integer freq_max, Integer freq_min)
			throws Exception {

//		if (selection)
//			training = selectFeaturesWithFilter(training);

//		Instances testing = getData(testFile, 1);

		InputMappedClassifier classy = algorithm.getInputMappedClassifier();
		classy.setClassifier(classifier);
		classy.setSuppressMappingReport(true);
		classy.buildClassifier(selection ? trainingSelected : training);

		Evaluation evaluation = new Evaluation(selection ? trainingSelected : training);
		evaluation.evaluateModel(classy, testing);

		// System.out.println(eval.toCumulativeMarginDistributionString());
		// System.out.println(eval.toSummaryString());
		// System.out.println(eval.toClassDetailsString());
//		System.out.println(evaluation.toMatrixString());

		return new PerformanceData(algorithm.name(classifier), selection,
				(selection ? trainingSelected : training).numInstances(), testing.numInstances(),
				(int) evaluation.numTrueNegatives(0), (int) evaluation.numFalseNegatives(0),
				(int) evaluation.numTruePositives(0), (int) evaluation.numFalsePositives(0), evaluation.recall(0),
				(selection ? trainingSelected : training).numAttributes(), freq_max, freq_min);
	}

	private String generateArffFile(String pathFile, String mapName) throws Exception {
		String path = "";
		String name = "";
		
		String pathReplace = pathFile.replace("csv", "arff");
		
		boolean fileFind = fileFind(pathReplace);
		
		if (!fileFind) {
			if (!fileFind(pathFile))
				throw new Exception("File "+pathFile+" not found!");

			path = pathFile.substring(0, pathFile.lastIndexOf("\\"));
			name = pathFile.substring(pathFile.lastIndexOf("\\") + 1, pathFile.length() - 4);

			csvToArff.generateArffFile(path, name, ",", name, mapName);
			
			deleteFileCSV(pathFile);		
		}else {
			//Deletar .csv se ainda existir
			if(pathFile.contains(".csv") && fileFind(pathFile)) {
				deleteFileCSV(pathFile);
			}
		}
		
		return pathReplace;
	}

	private void deleteFileCSV(String pathFile) {
		File file = new File(pathFile);
		
		if(file.exists()) {
			file.delete();
		}
		
	}

	public void loadFiles(String pathTrainFile, String pathTestFile, String mapName) throws Exception {
		//Se existir os arquivos arff e não existir o csv é porque foi calculado no result4-v2, assim eu não preciso calcular novamente
		String pathReplaceTrain = pathTrainFile.replace("csv", "arff");
		String pathReplaceTest = pathTestFile.replace("csv", "arff");
		
		boolean existeTrainCSV = fileFind(pathTrainFile);
		boolean existeTrainARFF = fileFind(pathReplaceTrain);
		
		boolean existeTestCSV = fileFind(pathTestFile);
		boolean existeTestARFF = fileFind(pathReplaceTest);
		
		if(existeTrainARFF && existeTestARFF && !existeTrainCSV && !existeTestCSV) {
			throw new Exception("Files "+pathReplaceTrain+", "+pathReplaceTest+" já foram avaliados!");
		}		
		
		pathTrainFile = generateArffFile(pathTrainFile, mapName);
		pathTestFile = generateArffFile(pathTestFile, mapName);
		
		this.training = getData(pathTrainFile);

		try {
			this.trainingSelected = selectFeaturesWithFilter(this.training);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		this.testing = getData(pathTestFile);
	}

	private boolean fileFind(String path) {
		File file = new File(path);

		return file.exists();
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
