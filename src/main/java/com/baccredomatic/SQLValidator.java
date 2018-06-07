package com.baccredomatic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

/**
 * Validador estándar de SQLs para cumplimiento base
 * @author rhernandezm
 *
 */
@Service
public class SQLValidator implements ComplianceValidator {

	public List<String> validate(ValidationInput input) {
		if(null == input || null == input.getInputStream()){
			throw new IllegalArgumentException("No input data available");
		}
		
		List<String> errors = new ArrayList<String>(10);
		BufferedReader reader = new BufferedReader(new InputStreamReader(input.getInputStream()));
		try {
			while(reader.ready())
			{
			     String line = Optional.ofNullable(reader.readLine()).orElseThrow(() -> new IllegalArgumentException("Null data read, not valid input"));
			     if (line.matches(".*[cC][rR][eE][aA][tT][eE].*"))
			    	 errors.add(String.format("El SQL dentro del archivo %s incluye la constante CREATE", input.getInputId()));
			     if (line.matches(".*[aA][lL][tT][eE][rR].*"))
			    	 errors.add(String.format("El SQL dentro del archivo %s incluye la constante ALTER", input.getInputId()));
			     if (line.matches(".*[dD][rR][oO][pP].*"))
			    	 errors.add(String.format("El SQL dentro del archivo %s incluye la constante DROP", input.getInputId()));
			}
		} catch (IOException e) {
			Logger.getLogger(SQLValidator.class.getName()).log(Level.SEVERE, "Error while reading input", e);
		}
		
		return errors;
		
	}

}
