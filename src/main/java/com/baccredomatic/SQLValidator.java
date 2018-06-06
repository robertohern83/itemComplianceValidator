package com.baccredomatic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

@Service
public class SQLValidator implements ComplianceValidator {

	public List<String> validate(InputStream data) {
		if(null == data){
			throw new IllegalArgumentException("No input data available");
		}
		
		List<String> errors = new ArrayList<String>(10);
		BufferedReader reader = new BufferedReader(new InputStreamReader(data));
		try {
			while(reader.ready())
			{
			     String line = Optional.ofNullable(reader.readLine()).orElseThrow(() -> new IllegalArgumentException("Null data read, not valid input"));
			     if (line.matches(".*[cC][rR][eE][aA][tT][eE].*"))
			    	 errors.add("El SQL incluye la constante CREATE");
			     if (line.matches(".*[aA][lL][tT][eE][rR].*"))
			    	 errors.add("El SQL incluye la constante ALTER");
			     if (line.matches(".*[dD][rR][oO][pP].*"))
			    	 errors.add("El SQL incluye la constante DROP");
			}
		} catch (IOException e) {
			Logger.getLogger(SQLValidator.class.getName()).log(Level.SEVERE, "Error while reading input", e);
		}
		
		return errors;
		
	}

}
