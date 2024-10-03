package ca.gforcesoftware.fixes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import ca.gforcesoftware.dao.DBTable;
import ca.gforcesoftware.util.SQLCreator;

/**
 * @author Gavin Hashemi
 * @version 1.0
 * 
 *
 * This is a utility tool application to read a table and a file and compare the value accordingly.
 * 
 * In this tool the table is DB_TABLE which is a reference table for keeping property of each employee
 * The application reads them and it compare each object with the value in .csv file.
 */
public class RunCompareEmployeeList {

	private static SessionFactory factory;

	public static void main(String[] args) {
		FixesFile FixesFile = new FixesFile();
		FixesFile.getEmployeeList()
			.stream()
			.forEach(x ->System.out.println(SQLCreator.insertEmployeeFunction.apply("DB_TABLE", x)));
		/** 
		 * The predicate interface compares all value of each object and if it completely matches it returns true.
		 */
		Predicate<DBTable> matchAllIcao = (x) -> {
			Optional<CSVFile> optionCSV =FixesFile
										.getEmployeeList()
										.stream()
										.filter ((i) -> {
											if (i.compareEmpl(x.getInfo(), i) == 0 ) {
												return true;
											} else {
												return false;
											}
										})
										.findAny();
			if(optionCSV.isPresent()) {
				return true;
			}else {
				return false;
			}
		};
		
		/** 
		 * The predicate interface compares all value of each route and if it partially matches it returns true.
		 */
		Predicate<DBTable> matchPartialList = (x) -> {
			Optional<CSVFile> optionEmpl =FixesFile
										.getEmployeeList()
										.stream()
										.filter ((i) -> {
											if (i.compareEmpl(x.getInfo(), i) > 0 ) {
												return true;
											} else {
												return false;
											}
										})
										.findAny();
			if(optionEmpl.isPresent()) {
				return true;
			}else {
				return false;
			}
		};
		

		/** 
		 * The function interface compares all value of each route and if it completely matches it returns Empl object.
		 */	
		Function<DBTable, CSVFile> matchAllEmployeeValue = (x) -> {
			Optional<CSVFile> optionEmpl =FixesFile
										.getEmployeeList()
										.stream()
										.filter ((i) -> {
											if (i.compareEmpl(x.getInfo(), i) == 0 ) {
												return true;
											
											}else {
												return false;
											}
										})
										.findAny();
			if(optionEmpl.isPresent()) {
				return x.getInfo();
			}else {
				return null;
			}
		};
		
		/** 
		 * The function interface compares all value of each route and if it partially matches it returns employee object.
		 */
		
		Function<DBTable, CSVFile> partialMatchedEmployeeValue = (x) -> {
			Optional<CSVFile> optionEmpl =FixesFile
										.getEmployeeList()
										.stream()
										.filter ((i) -> {
											if (i.compareEmpl(i,x.getInfo()) > 0 ) {
												return true;
											
											}else {
												return false;
											}
										})
										.findFirst();
			if(optionEmpl.isPresent()) {
				return optionEmpl.get();
			}else {
				return null;
			}
		};
		
		try {
			factory = new Configuration().configure().addAnnotatedClass(DBTable.class).buildSessionFactory();

			Session session = factory.openSession();
			Query<DBTable> queryList = session
					.createQuery("SELECT firstName,jobName FROM DBTable ORDER BY firstName", DBTable.class);
			ArrayList<DBTable> DBTableList = (ArrayList<DBTable>) queryList.getResultList();
			List<DBTable> AllmatchedIcaoList = DBTableList
				.stream()
				.filter(matchAllIcao)
				.collect(Collectors.toList());
			System.out.println("ICAOs with fully Matched Completely\n");
			System.out.println("Empl\tjobName \\DMS jobName \tLong\\DMS Long");
			AllmatchedIcaoList.forEach(x -> {
				CSVFile icaoValue = matchAllEmployeeValue.apply(x);
				System.out.println( x.getFirstName() + "\t" + x.getLastName() +"\\" +icaoValue.getLastName()+ "\t" + x.getJobName() +  "\\" + icaoValue.getJobName() );
			});
			
			List<DBTable> partialMatchedIcaoList = DBTableList
					.stream()
					.filter(matchPartialList)
					.collect(Collectors.toList());
				System.out.println("\n\n\nEmpl Name was found in  file. However, either latitude or longitude or both are different.\n");
				System.out.println("Empl\tjobName \\DMS jobName \tLong\\DMS Long");
				partialMatchedIcaoList.forEach(x -> {
					CSVFile icaoValue = partialMatchedEmployeeValue.apply(x);
					System.out.println( x.getFirstName() + "\t" + x.getLastName() +"\\" +icaoValue.getLastName()+ "\t" + x.getJobName() +  "\\" + icaoValue.getJobName() );
				});
				
				
				List<DBTable> noMatchedIcaoList = DBTableList;
				noMatchedIcaoList.removeAll(AllmatchedIcaoList);
				noMatchedIcaoList.removeAll(partialMatchedIcaoList);

					System.out.println("\n\n\nEmpl Name was not found in file.\n");
					System.out.println("Empl\tjobName \tLong");
					noMatchedIcaoList.forEach(x -> {
						System.out.println( x.getFirstName() + "\t" + x.getLastName() + "\t" + x.getJobName()  );
					});
		} catch (Exception ex) {
			System.err.println("Darn : " + ex.getMessage());
		}
	}

}
