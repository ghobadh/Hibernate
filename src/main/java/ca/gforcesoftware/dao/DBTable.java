package ca.gforcesoftware.dao;

import ca.gforcesoftware.fixes.CSVFile;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;



@Entity
@Table(name = "DBTABLE")
public class DBTable {
	// You need to have  an id no matter you used or not
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	@Column(name= "Name")
	private String firstName;
	
	@Column(name= "lastName")
	private String lastName;
	
	@Column(name= "jobname")
	private String jobName;
	
	//This is the constructor which is called in "SELECT firstname,lastname, objectname FROM DBTable ORDER BY lastName"
	public DBTable(String firstName, String lastName , String jobName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.jobName = jobName;
	}

	public DBTable() {

	}

	public CSVFile getInfo() {
		return new CSVFile(firstName,lastName, jobName);
		
	}
	

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String icaoName) {
		this.firstName = icaoName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public String getJobName() {
		return jobName;
	}



	public void setJobName(String s) {
		this.jobName = s;
	}
	
	

}
