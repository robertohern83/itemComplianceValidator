package com.baccredomatic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SQLValidator {

	public static void validate(InputStream data) throws InvalidSQLException {
		if(null == data){
			throw new IllegalArgumentException("No input data available");
		}
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(data));
		try {
			while(reader.ready())
			{
			     String line = Optional.ofNullable(reader.readLine()).orElseThrow(() -> new IllegalArgumentException("Null data read, not valid input"));
			     if (line.matches(".*[cC][rR][eE][aA][tT][eE].*"))
			    	 throw new InvalidSQLException("El SQL incluye la constante CREATE");
			     if (line.matches(".*[aA][lL][tT][eE][rR].*"))
			    	 throw new InvalidSQLException("El SQL incluye la constante ALTER");
			     if (line.matches(".*[dD][rR][oO][pP].*"))
			    	 throw new InvalidSQLException("El SQL incluye la constante DROP");
			}
		} catch (IOException e) {
			Logger.getLogger(SQLValidator.class.getName()).log(Level.SEVERE, "Error while reading input", e);
		}
		
	}

}
