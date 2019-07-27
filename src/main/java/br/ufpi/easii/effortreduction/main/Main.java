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
	
	public static void main(String[] args) {
		Algorithm algorithm = new Algorithm();
		
		//Para cada mapeamento
		for (int map = 0; map < FilesUtil.paths.length; map++) {
			boolean first = true;
			String mapName = FilesUtil.paths[map][0];
			String pathMap = FilesUtil.MAIN_PATH + "\\" + mapName;
			
			//Para cada percentual de treinamento 10~90%
			for (int percent = 1; percent < FilesUtil.paths[map].length; percent++) {
				String mapPercent = FilesUtil.paths[map][percent];
				String pathPercent = pathMap + "\\" + mapPercent;
				//Para cada corte superior e inferior
				for (int cut = 0; cut < FilesUtil.secundaryPaths.length; cut++) {
					String freqs[] = FilesUtil.secundaryPaths[cut].split("-");
					Integer freq_max = Integer.parseInt(freqs[0]);
					Integer freq_min = Integer.parseInt(freqs[1]);
					
					if(freq_max == 0 && freq_max.equals(freq_min)) {
						continue;
					}
					
					String fullPath = pathPercent + "\\" + FilesUtil.secundaryPaths[cut];
					
					CSVUtil csvUtil = new CSVUtil();
					csvUtil.setFileName(pathMap + "\\"+FilesUtil.resultFileName+"-"+mapName+"-"+mapPercent+"-"+FilesUtil.secundaryPaths[cut]);
					
					//Obtem arquivo de teste e treinamento
					EvaluationAlgorithm evaluationAlgorithm = new EvaluationAlgorithm();
					
					String pathTrainFile = fullPath + "\\train.csv";
					String pathTestFile = fullPath + "\\test.csv";
					
					try {
						evaluationAlgorithm.loadFiles(pathTrainFile, pathTestFile, mapName);
					} catch (Exception e1) {
						System.out.println(e1.getMessage());
						continue;
					}
					
					//Aplica todos os algoritmos
					for (Classifier classifier : algorithm.getAlgorithms()) {
						System.out.println( fullPath + " -> " + algorithm.name(classifier));
						try {
							PerformanceData data = null;
							//Sem seleÃ§Ã£o de atributos
							data = evaluationAlgorithm.buildClassifier(classifier, algorithm, false, freq_max, freq_min);
							csvUtil.addData(data);
							
							//Com seleÃ§Ã£o de atributos
							data = evaluationAlgorithm.buildClassifier(classifier, algorithm, true, freq_max, freq_min);
							csvUtil.addData(data);
						} catch (Exception e) {
							System.out.println("Error: " + fullPath + ":" + e.getMessage());
						}
					}
					
					//Gera arquivo com resultados
					System.out.println("Gerando resultands de " + mapName);
					//				System.out.println("O arquivo possui um total de " + csvUtil.getListData().size() );
					csvUtil.generateCSV(first);
					first = false;
				}
			}
			
			//Unindo todos os arquivos de resultados
			FilesUtil.concatFiles(mapName);			
		}
	}
}
