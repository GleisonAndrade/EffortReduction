/**
 * 
 */
package br.ufpi.easii.effortreduction.teste;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import br.ufpi.easii.effortreduction.file.FilesUtil;
import br.ufpi.easii.effortreduction.main.Algorithm;
import weka.attributeSelection.BestFirst;
import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.GreedyStepwise;
import weka.classifiers.Evaluation;
import weka.classifiers.misc.InputMappedClassifier;
import weka.core.Instances;
import weka.core.Utils;
import weka.filters.Filter;

/**
 * @author gleys
 *
 */
public class Main {
	
	public static void main(String[] args) {
		ArrayList<Double> freq_top = FilesUtil.generateVector(0.4, 1.0, 0.01);
		
		for (Double d : freq_top) {
			System.out.print(d + " ");
		}
		
		System.out.println();
		
		ArrayList<Double> freq_down = FilesUtil.generateVector(0.01, 0.39, 0.01);
		
		for (Double d : freq_down) {
			System.out.print(d + " ");
		}
		
		System.out.println();
	}
	
	
	public static void mainnnnnn(String[] args) throws Exception {
		Instances training = getData("/treinamento.arff", 1);
		System.out.println("Treinamento: " + training.numInstances());
		training=selectFeaturesWithFilter(training);
//		System.out.println("Treinamento: " + training.numInstances());
		
		Instances testing = getData("/teste.arff", 1);
		System.out.println("Teste: " + testing.numInstances());
		
		Algorithm algorithm = new Algorithm();
		InputMappedClassifier classy = algorithm.getInputMappedClassifier();//algorithm.getRandomForest();
		classy.setClassifier(algorithm.getJ48());
		
		classy.buildClassifier(training);
//		System.out.println(classy);

		Evaluation eval = new Evaluation(training);	
		eval.evaluateModel(classy, testing);
		
//		System.out.println(eval.toCumulativeMarginDistributionString());
//        System.out.println(eval.toSummaryString());
//        System.out.println(eval.toClassDetailsString());
        System.out.println(eval.toMatrixString());
        System.out.println(eval.confusionMatrix());
        
        
//		System.out.println(eval.numTrueNegatives(0));
//		System.out.println(eval.numFalseNegatives(0));
		
		System.out.println(eval.numTruePositives(0));
		System.out.println(eval.numFalsePositives(0));
		
		System.out.println(eval.recall(1));

	}
	
   private static Instances getData( String filename, Integer posClass ) throws IOException, URISyntaxException {
       File file = new File(WekaJ48Demo.class.getResource( filename ).toURI());
       BufferedReader inputReader = new BufferedReader(new FileReader(file));
       Instances data = new Instances(inputReader);
       data.setClass(data.attribute("finalEvaluate"));

       return data;
   }
   
   /**
    * uses the filter
    */
   protected static Instances useFilter(Instances data) throws Exception {
       System.out.println("\n2. Filter");
       weka.filters.supervised.attribute.AttributeSelection filter = new weka.filters.supervised.attribute.AttributeSelection();
       CfsSubsetEval eval = new CfsSubsetEval();
       GreedyStepwise search = new GreedyStepwise();
       search.setSearchBackwards(true);
       filter.setEvaluator(eval);
       System.out.println("Set the evaluator : " + eval.toString());
       filter.setSearch(search);
       System.out.println("Set the search : " + search.toString());
       filter.setInputFormat(data);
//       System.out.println("Set the input format : " + data.toString());
       Instances newData = Filter.useFilter(data, filter);
       System.out.println("Results of Filter:\n" + newData);
       
       return newData;
   }
   
   public static Instances selectFeaturesWithFilter(Instances data) throws Exception{
//	   weka.filters.supervised.attribute.AttributeSelection -E "weka.attributeSelection.CfsSubsetEval -P 1 -E 1" -S "weka.attributeSelection.BestFirst -D 1 -N 5"
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
			return(newData);
		} catch (Exception e) {
		}
	    
	    return null;
	}
   
   public Instances filterCFS_BestFirst(Instances data) throws Exception {
	    weka.filters.supervised.attribute.AttributeSelection filter = new weka.filters.supervised.attribute.AttributeSelection();
	    CfsSubsetEval eval = new CfsSubsetEval();
	    BestFirst search = new BestFirst();
	    filter.setEvaluator(eval);
	    filter.setSearch(search);
	    filter.setInputFormat(data);
	    Instances newData = Filter.useFilter(data, filter);
	    return newData;
	}
}
