/**
 * 
 */
package br.ufpi.easii.effortreduction.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * @author Gleison Andrade
 *
 */
public class FilesUtil {
	public static final String MAIN_PATH = "E:\\Documentos\\reviews-cut";// "C:\\Users\\gleys\\OneDrive\\Documentos\\maps-all";//"
	public static final String resultFileName = "result10";																// D:\\Google Drive\\Testes Mestrado\\maps";

	public static String[][] paths = {
			{ "code-familiarity", "28", "56", "84", "112", "141", "169", "197", "225", "253" },
			{"oracle-teste", "50", "100", "150", "201", "251", "301", "352", "402", "452"},
			{"tools-ic", "59", "118", "178", "237", "297", "356", "415", "475", "534"},
			{"thasciano", "141", "282", "423", "564", "705", "846", "986", "1128", "1269"},
			{"martony", "320", "640", "960", "1281", "1601", "1921", "2242", "2562", "2882"},
			{"Kitchenham", "170", "340", "511", "681", "852", "1022", "1192", "1363", "1533"},
			{"Radjenovic", "600", "1200", "1800", "2400", "3000", "3600", "4200", "4800", "5400"},
			{"Wahono", "700", "1400", "2100", "2800", "3501", "4201", "4901", "5601", "6301"},
			{"Hall", "891", "1782", "2673", "3564", "4455", "5346", "6237", "7128", "8019"}
	};

	//private static String[] c = {"80", "81", "82", "83", "84", "85", "86", "87","88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99"};//{ "10", "20", "30", "40", "50", "60", "70", "80", "90"};
	//private static String[] c2 = { "1", "2", "3", "4", "5", "6", "7", "8", "9"};
	public static String[] secundaryPaths = generateCuts();
	
	public static ArrayList<Double> generateVector(Double first, Double end, Double increment) {
		ArrayList<Double> cuts = new ArrayList<>();
		DecimalFormat df = new DecimalFormat("#.00");
		
		Double value = Double.valueOf(df.format(first).replaceAll(",", "."));
		
		while (value <= end) {
			cuts.add(value);
			value = Double.valueOf(df.format(value + increment).replaceAll(",", "."));
		}		
		
		return cuts;
	}

	public static String[] generateCuts() {
		int pos = 0;
		
		ArrayList<Double> freq_top = generateVector(0.01, 1.0, 0.05);
		ArrayList<Double> freq_down = generateVector(0.01, 1.0, 0.05);
		int size_cuts = freq_top.size() * freq_down.size();
		String[] cuts = new String[size_cuts];

		for (int freq_max = 0; freq_max < freq_top.size(); freq_max++) {
			for (int freq_min = 0; freq_min < freq_down.size(); freq_min++) {
				if (freq_min >= freq_max) {
					cuts[pos++] = ("0-0");
					continue;
				}
				
				String top = ""+Math.round(freq_top.get(freq_max) * 100);
				String down = ""+Math.round(freq_down.get(freq_min) * 100);
				cuts[pos++] = (top + "-" + down);
			}
		}

		return cuts;
	}

	public static void generateFile(String name, String data) {
		FileWriter writer;
		try {
			writer = new FileWriter(name);
			writer.append(data);

			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void concatFiles(String mapName) {
		String data = "";
		// result-mapName-Percent-Cut.csv
		String pathMap = MAIN_PATH + "\\" + mapName;
		int map = findMapIndice(mapName);
		// Para cada percentual de treinamento 10~90%
		for (int percent = 1; percent < paths[map].length; percent++) {
			String mapPercent = paths[map][percent];

			// Para cada corte superior e inferior
			for (int cut = 0; cut < secundaryPaths.length; cut++) {
				String fileCSV = pathMap + "\\"+resultFileName+"-" + mapName + "-" + mapPercent + "-" + secundaryPaths[cut]+".csv";
				File file = new File(fileCSV);
				
				if(file.exists()) {
					try{
						data = data.concat(new String(Files.readAllBytes(file.toPath())));
					}catch(java.io.IOException e){ 
						/*faz qualquer coisa com o erro */
					}
					
					file.delete();
				}

			}
		}
		
		generateFile(pathMap + "\\"+resultFileName+"-"+mapName+".csv", data);

	}

	private static int findMapIndice(String mapName) {
		for (int i = 0; i < paths.length; i++) {
			if (paths[i][0].equals(mapName)) {
				return i;
			}
		}

		return 999;
	}
}