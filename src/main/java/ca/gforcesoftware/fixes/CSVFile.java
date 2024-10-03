package ca.gforcesoftware.fixes;

import ca.gforcesoftware.util.NumberProcess;

public class
CSVFile {

	private String firstName;
	private String lastName;
	private String jobName;
	private final int FAMILYNAME_LENGTH = 4;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = NumberProcess.collectSubString.apply(lastName, FAMILYNAME_LENGTH);
	}

	public String getJobName() {
		if (jobName == null)
			System.out.println(getFirstName());
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = NumberProcess.collectSubString.apply(jobName, FAMILYNAME_LENGTH);
	}

	public CSVFile() {
	}

	public CSVFile(String i, String lastName, String jobName) {
		setFirstName(i);
		setLastName(lastName);
		setJobName(jobName);
	}

	public String get(String firstName) {
		return this.getFirstName();
	}

	public int compareEmpl(CSVFile i1, CSVFile i2) {
		if (i1.getFirstName().equals(i2.getFirstName())) {
			if (i1.getLastName().equals(i2.getLastName()) && i1.getJobName().equals(i2.getJobName())) {
				return 0;
			} else {
				return 1;
			}
		}
		return -1;
	}

	public String toString() {
		return "First Name: " + getFirstName() + "\tLastName: " + getLastName() + "\tJob: " + getJobName();
	}

}
