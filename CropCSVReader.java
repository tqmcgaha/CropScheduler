import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;

public class CropCSVReader{
	private static final int NUM_CROP_FIELDS = 6;

	// TODO: bug reading in multi word crop name
	public static List<Crop> ReadCropCSV(String fileName) throws FileNotFoundException {
		List<Crop> crops = new ArrayList<Crop>();
		File csv = new File(fileName);
		Scanner csvReader = new Scanner(csv);
		while(csvReader.hasNextLine()){
			String line = csvReader.nextLine();
			//System.out.println(line);
			String[] tokens = line.split(",");
			if(tokens.length != NUM_CROP_FIELDS){
				continue;
			}
			for(int i = 0; i < tokens.length; i++){
				tokens[i] = tokens[i].trim();
				//System.out.println(tokens[i]);
			}
			String name = tokens[0];
			int growthTime = Integer.parseInt(tokens[1]);
			int regrowthTime = Integer.parseInt(tokens[2]);
			int seedCost = Integer.parseInt(tokens[3]);
			int sellPrice = Integer.parseInt(tokens[4]);
			boolean repeats = Boolean.parseBoolean(tokens[5]);
			Crop crop = new Crop(name, growthTime, regrowthTime, seedCost, sellPrice, repeats);
			crops.add(crop);
		}
		return crops;
	}

	public static Map<String, Integer> readWants(String fileName) throws FileNotFoundException {
		Map<String, Integer> wants = new HashMap<String, Integer>();
		File csv = new File(fileName);
		Scanner csvReader = new Scanner(csv);
		while(csvReader.hasNextLine()){
			String line = csvReader.nextLine();
			String[] tokens = line.split(",");
			if(tokens.length != 2){
				continue;
			}
			for(int i = 0; i < tokens.length; i++){
				tokens[i] = tokens[i].trim();
			}
			String name = tokens[0];
			int count = Integer.parseInt(tokens[1]);
			int oldCount = 0;
			if(wants.containsKey(name)){
				oldCount = wants.get(name);
			}
			wants.put(name, count+oldCount);
		}
		return wants;

	}
}
