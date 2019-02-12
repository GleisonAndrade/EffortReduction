/**
 * 
 */
package br.ufpi.easii.effortreduction.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;

import br.ufpi.easii.effortreduction.teste.WekaJ48Demo;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;

/**
 * @author gleys
 *
 */
public class Main {
	public static void main(String[] args) throws Exception {
		Instances training = getData("/treinamento.arff", 1);
		System.out.println("Treinamento: " + training.numInstances());
		Instances testing = getData("/teste.arff", 1);
		System.out.println("Teste: " + testing.numInstances());
		
		Algorithm algorithm = new Algorithm();
		Classifier classy = algorithm.getJ48();//algorithm.getInputMappedClassifier();
//		classy.setClassifier(algorithm.getJ48());
		
		classy.buildClassifier(training);
		System.out.println(classy);

		Evaluation eval = new Evaluation(training);		
		eval.evaluateModel(classy, testing);
		
        System.out.println(eval.toSummaryString());
        System.out.println(eval.toMatrixString());
	}
	
	  /** Liest aus einer ARFF Datei Daten mit Attribut- und Datenbeschreibungen
    *
    * @param filename Pfad und Name der Datei
    * @param posClass 1-basierter Index der Klassendefinition vom Ende der Attributliste aus gesehen.
    * @return Ein Instances Objekt
    */
   private static Instances getData( String filename, Integer posClass ) throws IOException, URISyntaxException {
       // Einlesen der Daten
       File file = new File(WekaJ48Demo.class.getResource( filename ).toURI());
       BufferedReader inputReader = new BufferedReader(new FileReader(file));
       // Erstelle einen Datensatz der Klasse Instances
       Instances data = new Instances(inputReader);
       // Bestimme letztes Attribut als Zielklasse
       data.setClassIndex(data.numAttributes() - posClass);

       return data;
   }
}
