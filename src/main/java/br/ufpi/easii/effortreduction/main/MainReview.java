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
public class MainReview {
	
	public static void main(String[] args) {
		Algorithm algorithm = new Algorithm();
		Integer freq_min = 1;
		Integer freq_max = 100;
		
		//Para cada mapeamento
		for (int map = 0; map < FilesUtil.paths.length; map++) {
			boolean first = true;
			String mapName = FilesUtil.paths[map][0];
			String pathMap = FilesUtil.MAIN_PATH + "\\" + mapName;
			
			//Para cada percentual de treinamento 10~90%
			for (int percent = 1; percent < FilesUtil.paths[map].length; percent++) {
				String mapPercent = FilesUtil.paths[map][percent];
				String pathPercent = pathMap + "\\" + mapPercent;
	
				String fileCSV = pathMap + "\\"+FilesUtil.resultFileName+"-" + mapName + "-" + mapPercent;
					
				CSVUtil csvUtil = new CSVUtil();
				csvUtil.setFileName(fileCSV);
				
				//Obtem arquivo de teste e treinamento
				EvaluationAlgorithm evaluationAlgorithm = new EvaluationAlgorithm();
				
				String pathTrainFile = pathPercent + "\\train.csv";
				String pathTestFile = pathPercent + "\\test.csv";
				
				try {
					evaluationAlgorithm.loadFiles(pathTrainFile, pathTestFile, mapName);
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
					continue;
				}
				
				//Aplica todos os algoritmos
				for (Classifier classifier : algorithm.getAlgorithms()) {
					System.out.println( pathPercent + " -> " + algorithm.name(classifier));
					try {
						PerformanceData data = null;
						//Sem seleÃ§Ã£o de atributos
						data = evaluationAlgorithm.buildClassifier(classifier, algorithm, false, freq_max, freq_min);
						csvUtil.addData(data);
						
						//Com seleÃ§Ã£o de atributos
						data = evaluationAlgorithm.buildClassifier(classifier, algorithm, true, freq_max, freq_min);
						csvUtil.addData(data);
					} catch (Exception e) {
						System.out.println("Error: " + pathPercent + ":" + e.getMessage());
					}
				}
				
				//Gera arquivo com resultados
				System.out.println("Gerando resultands de " + mapName);
				//				System.out.println("O arquivo possui um total de " + csvUtil.getListData().size() );
				csvUtil.generateCSV(first);
				first = false;
			}
			
			//Unindo todos os arquivos de resultados
			FilesUtil.concatFiles(mapName);			
		}
	}
}
