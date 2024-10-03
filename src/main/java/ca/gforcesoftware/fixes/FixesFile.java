package ca.gforcesoftware.fixes;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class FixesFile {
	private List<CSVFile> employeeList = new ArrayList<>();
	
	
	public List<CSVFile> getEmployeeList() {
		return employeeList;
	}


	public void setEmployeeList(List<CSVFile> icaoList) {
		this.employeeList = icaoList;
	}
	
	public CSVFile getEnroute(String icao) {
		Optional<CSVFile> optionICAO = employeeList.stream()
				.filter(i -> i.getFirstName().equals(icao))
				.findFirst();
		if (optionICAO.isPresent()) {
			return optionICAO.get();
		} 
		return null; 
	}


	public FixesFile() {
		try {
			System.out.println("Reading file from resource folder");
			List<String> allLines = Files.readAllLines(Paths.get("src/main/resources/employeeList.csv"),StandardCharsets.UTF_8);
			for (String line : allLines) {
				if (!line.isEmpty() ) {
					String[] splitedLine = line.replace(",,",",").split(",");
					if (splitedLine[0].equals("FIST_NAME")) {
						continue;
					}else {
						CSVFile csvFile = new CSVFile();
						csvFile.setFirstName(splitedLine[4]);
						csvFile.setLastName(splitedLine[7]);
						csvFile.setJobName(splitedLine[8]);
						employeeList.add(csvFile);
					}
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void checkEmployeeFile(String firstName) {
		System.out.println ("\n\nLength of the list is:" + employeeList.size());
		String name = firstName;
		Optional<CSVFile> optionEmploy = employeeList.stream()
			.filter(i -> i.getFirstName().equals(name))
			.findFirst();
		if (optionEmploy.isPresent()) {
			System.out.println("The name of \"" + optionEmploy.get().getFirstName() + "\" is:");
			System.out.println("\t\tLastName: " + optionEmploy.get().getLastName() + "\n\t\tJob: " +optionEmploy.get().getJobName());
		} else {
			System.out.println("The name of \"" + name + "\" does not exist in employee file.");
		}
		
	}
}
