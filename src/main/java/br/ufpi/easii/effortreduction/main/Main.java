/**
 * 
 */
package br.ufpi.easii.effortreduction.main;

import br.ufpi.easii.effortreduction.PerformanceData;
import br.ufpi.easii.effortreduction.file.CSVUtil;
import br.ufpi.easii.effortreduction.file.FilesUtil;
import weka.classifiers.Classifier;

/**
 * @author Gleison Andrade
 *
 */
public class Main {
//	private static FilesUtil filesUtil;
//	private EvaluationAlgorithm evaluationAlgorithm;
	
	public static void main(String[] args) {
		Algorithm algorithm = new Algorithm();
		
		//Para cada mapeamento
		for (int map = 0; map < 5; map++) {
			String mapName = FilesUtil.getPaths()[map][0];
			
			//Para cada percentual de treinamento 10~90%
			for (int percent = 1; percent < 10; percent++) {
				CSVUtil csvUtil = new CSVUtil();
				String mapPercent = FilesUtil.getPaths()[map][percent];
				csvUtil.setFileName(FilesUtil.MAIN_PATH + "\\" + mapName + "\\result-"+mapName+"-"+mapPercent);
				
				//Obtem arquivo de teste e treinamento
				EvaluationAlgorithm evaluationAlgorithm = new EvaluationAlgorithm();
				
				String pathTrainFile = FilesUtil.MAIN_PATH + "\\" + mapName + "\\" + mapPercent + "\\train.csv";
				String pathTestFile = FilesUtil.MAIN_PATH + "\\" + mapName + "\\" + mapPercent + "\\test.csv";
				evaluationAlgorithm.loadFiles(pathTrainFile, pathTestFile);
				
				//Aplica todos os algoritmos
				for (Classifier classifier : algorithm.getAlgorithms()) {
					System.out.println(mapName+": " + mapPercent + " -> " + algorithm.name(classifier));
					try {
						PerformanceData data = null;
						//Sem seleção de atributos
						data = evaluationAlgorithm.buildClassifier(classifier, algorithm, false);
						csvUtil.addData(data);
						
						//Com seleção de atributos
						data = evaluationAlgorithm.buildClassifier(classifier, algorithm, true);
						csvUtil.addData(data);
					} catch (Exception e) {
						System.out.println("Error: " + mapName + " - " + mapPercent + ":" + e.getMessage());
					}
				}
				
				//Gera arquivo com resultados
				System.out.println("Gerando resultands de " + mapName);
				System.out.println("O arquivo possui um total de " + csvUtil.getListData().size() );
				csvUtil.generateCSV();
			}
			
			
		}
	}

}
