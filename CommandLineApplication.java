import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class CommandLineApplication {
	public static void main(String[] args){
		List<Crop> crops = null;
		Map<String, Integer> wants = null;
		Scanner console = new Scanner(System.in);
		int capacity = 2;
		
		try {
			crops = CropCSVReader.ReadCropCSV(SchedulerConstants.SP_CROPS_PATH);
			wants = CropCSVReader.readWants("wants.csv");
		} catch (Exception e){
			System.out.println(e.getMessage());
			return;
		}

		FarmScheduler scheduler = new FarmScheduler(crops);
		Calendar schedule = scheduler.schedule(wants, capacity);
		//System.out.println(scheduler.getCrops());
		//List<Map<String, Integer>> schedule = scheduler.schedule(wants, capacity);
		//System.out.println(schedule);
	
		// TESTS START
		//Crop corn = new Crop("corn", 14, 4, 150, 50, true); 
		//System.out.println(corn.totalLifetime());
		//System.out.println(corn.totalProfit());
		//System.out.println(corn.profitPerDay());
		// TESTS END

		System.out.print("$ ");
		while(console.hasNextLine()) {
			String line = console.nextLine();
			if(line.equalsIgnoreCase("calendar")) {
				//System.out.println(scheduler.scheduleToString(schedule));
				System.out.println(schedule);
			} else if (line.equalsIgnoreCase("list") || line.equalsIgnoreCase("ls")) {
				System.out.println(scheduler.getCrops());
			} else if(line.equalsIgnoreCase("exit")) {
				break;
			} else if(line.startsWith("capacity ") || line.startsWith("cap ") || line.startsWith("spots ")){
				String[] tokens = line.split("[ \n\t]+");
				if(tokens.length < 2){
					System.out.println("Usage: $ capacity <capacity of farm>");
				}
				try {
					int cap = Integer.parseInt(tokens[1]);
					if(cap >= 0){
						capacity = cap;
						schedule = scheduler.schedule(wants, capacity);
					}
				} catch (NumberFormatException nfe){
					System.out.println("Invalid number input: " + tokens[2]);
				}
			}else if(line.startsWith("priority")) {
				String[] tokens = line.split("[ \n\t]+");
				if(tokens.length > 1 && !tokens[1].equalsIgnoreCase("help")){
					String command = tokens[1];
					// do something with the command
				} else {
					// print out things it can prioritize;
				}
			} else if(line.startsWith("info")){
				String[] tokens = line.split("[ \n\t]+");
				boolean found = false;
				if (tokens.length > 1){
					String name = tokens[1];
					for(int i = 2; i < tokens.length; i++){
						name += " " + tokens[i];
					}
					for(Crop c : crops){
						if(name.equalsIgnoreCase(c.name())){
							System.out.println(c.info());
							found = true;
							break;
						}
					}
				}
				if(!found){
					System.out.println("crop not found, type \"list\" to see available crops");
				}
			} else if(line.startsWith("load wants")){
				String[] tokens = line.split("[ \n\t]+");
				if(tokens.length < 3){
					System.out.println("usage: \"load wants <filename>\"");
				} else {
					try {
						wants = CropCSVReader.readWants(tokens[2]);
						schedule = scheduler.schedule(wants, capacity);
					} catch (FileNotFoundException fnfe){
						System.out.println("Error reading file: \"" + tokens[2] +"\"");
					}
				}
			} else if(line.startsWith("load crops")){
				String[] tokens = line.split("[ \n\t]+");
				if(tokens.length < 3){
					System.out.println("usage: \"load crops <filename>\"");
				} else {
					try {
						if(tokens[2].equalsIgnoreCase("SP")){
							tokens[2] = SchedulerConstants.SP_CROPS_PATH;
						} else if(tokens[2].equalsIgnoreCase("su")){
							tokens[2] = SchedulerConstants.SU_CROPS_PATH;
						} else if(tokens[2].equalsIgnoreCase("au")){
							tokens[2] = SchedulerConstants.AU_CROPS_PATH;
						}
						crops = CropCSVReader.ReadCropCSV(tokens[2]);
						scheduler = new FarmScheduler(crops);
						schedule = scheduler.schedule(wants, capacity);
					} catch (FileNotFoundException fnfe){
						System.out.println("Error reading file: \"" + tokens[2] +"\"");
					}
				}
			} else if(line.equalsIgnoreCase("help")){
				printHelp();
			} else {
				try {
					int day = Integer.parseInt(line);
					day--;
					if(day < 0 || day >= SchedulerConstants.DAYS_IN_SEASON){
						continue;
					}
					//System.out.println(schedule.get(day));
				} catch (Exception e) {
					System.out.println("invalid commands, input \"help\" to see available commands");
				}
			}
			//TODO: consider adding a method that lets you set the number of places on your farm

			// prompt for next input
			System.out.print("$ ");
		}
		System.out.println("shutting down...");
	}

	public static void printHelp(){
		System.out.println("exit \t\t\t\t exits the program");
		System.out.println("list \t\t\t\t lists out the current loaded crops");
		System.out.println("capcaity <integer> \t\t sets the number of spots you"
			+"have available on your farm to plant crops to the value of <integer>");
		System.out.println("calendar \t\t\t lists out the calendar for when you need"
			+"to plant things, given the current settings");
		System.out.println("info <crop_name> \t\t provides the information about a"
			+"crop with given name");
		System.out.println("load wants <csv_file_path> \t reads in the wants for"
			+"your season from a csv that has two columns, the first is the crop"
			+"name, second column is quantity");
		System.out.println("load crops <csv_file_path> \t reads in the a list of"
			+"crops from a given csv file");
		System.out.println("load crops sp \t\t\t loads in all spring crops");
		System.out.println("load crops su \t\t\t loads in all summer crops");
		System.out.println("load crops au \t\t\t loads in all autumn crops");
		System.out.println("help \t\t\t\t prints out all of the commands you can input");
	}
}
