package ca.gforcesoftware.util;

import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

import ca.gforcesoftware.fixes.CSVFile;

public class SQLCreator {
	
	private static  StringBuffer  SQL_STR  = new StringBuffer(); ;
	private static final String EMP = " " ;
	private static final String BEGINDQOUTE = " \"";
	private static final String ENDDQOUTE = "\" ,";
	private static final String COMMA = ",";
	private static final String BEGIN_VAL = " VALUES(";
	private static final String END_VAL = ")";
	private static final String SET = " SET ";
	private static final String INSERT = "INSERT INTO ";
	private static final String UPDATE = "UPDATE ";
	private static final String WHERE = " WHERE ";
	

	
	
	private static UnaryOperator<String> embraceQuote = x-> {
		return " \'" + x + "\' ";
	};
	
	
	public static BiFunction <String, CSVFile, String> insertEmployeeFunction = (tableName, empl) -> {
		SQL_STR = new StringBuffer();
		return  SQL_STR
			   .append(INSERT)
			   .append(tableName)
			   .append(" (FIRSTNAME, LAT , JOBNAME )" )
			   .append(BEGIN_VAL)
			   .append(embraceQuote.apply(empl.getFirstName()))
			   .append(COMMA)
			   .append(embraceQuote.apply(empl.getLastName()))
			   .append(COMMA)
			   .append(embraceQuote.apply(empl.getJobName()))
			   .append(END_VAL)
			   .toString();
	};
	
	public static BiFunction <String, CSVFile, String> UpdateFunction= (tableName, empl) -> {
		return  SQL_STR
			   .append(UPDATE)
			   .append(tableName)
			   .append(SET)
			   .append(" LASTNAME=")
			   .append(embraceQuote.apply(empl.getLastName()))
			   .append(COMMA)
			   .append("JOBNAME  = ")
			   .append(embraceQuote.apply(empl.getJobName()))
			   .append(WHERE)
			   .append("FIRSTNAME = ")
			   .append(embraceQuote.apply(empl.getFirstName()))
			   .toString();
	};

}
