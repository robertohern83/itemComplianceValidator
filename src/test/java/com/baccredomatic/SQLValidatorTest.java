package com.baccredomatic;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;

import org.junit.Test;


public class SQLValidatorTest {

	@Test (expected=IllegalArgumentException.class)
	public void testReadUnexistentFileAndFailWithFileNotFoundException() {
		new SQLValidator().validate(null);
	}
	
	@Test
	public void testReadExistentFileWithInvalidCreateConstant() {

		trySLQValidationFailedWith(
				new ValidationInput(
						new ByteArrayInputStream(
								Charset.forName("UTF-8").encode("Select * from wwa703;\nCreate table")
								.array()), 
						"TestFile"), 
				"El SQL dentro del archivo TestFile incluye la constante CREATE");
		trySLQValidationFailedWith(
				new ValidationInput(
						new ByteArrayInputStream(
								Charset.forName("UTF-8").encode("Select * from wwa703;\nCreAte table")
								.array()),
						"TestFile"), 
				"El SQL dentro del archivo TestFile incluye la constante CREATE");		
		trySLQValidationFailedWith(
				new ValidationInput(
						new ByteArrayInputStream(
								Charset.forName("UTF-8").encode("Select * from wwa703;\nCREATE table")
								.array()),
						"TestFile"), 
				"El SQL dentro del archivo TestFile incluye la constante CREATE");
		
		
	}

	private void trySLQValidationFailedWith(ValidationInput input, String expectedMessage) {
		new SQLValidator().validate(input).stream().forEach(s -> assertEquals(expectedMessage, s));
		
	}
	
	@Test
	public void testReadExistentFileWithInvalidAlterConstant() {
		trySLQValidationFailedWith(
				new ValidationInput(
						new ByteArrayInputStream(
								Charset.forName("UTF-8").encode("Select * from wwa703;\nAlter table")
								.array()), 
						"TestFile"), 
					"El SQL dentro del archivo TestFile incluye la constante ALTER");
				
		trySLQValidationFailedWith(
				new ValidationInput(
							new ByteArrayInputStream(
									Charset.forName("UTF-8").encode("Select * from wwa703;\nAlTer table")
									.array()), 
							"TestFile"), 
					"El SQL dentro del archivo TestFile incluye la constante ALTER");		
		trySLQValidationFailedWith(
				new ValidationInput(
						new ByteArrayInputStream(
								Charset.forName("UTF-8").encode("Select * from wwa703;\nALTER table")
								.array()), 
						"TestFile"), 
					"El SQL dentro del archivo TestFile incluye la constante ALTER");
	}
	
	@Test
	public void testReadExistentFileWithInvalidDropConstant() {
		trySLQValidationFailedWith(
				new ValidationInput(
						new ByteArrayInputStream(
								Charset.forName("UTF-8").encode("Select * from wwa703;\nDrop table")
								.array()), 
						"TestFile"), 
					"El SQL dentro del archivo TestFile incluye la constante DROP");
		trySLQValidationFailedWith(
				new ValidationInput(
						new ByteArrayInputStream(
								Charset.forName("UTF-8").encode("Select * from wwa703;\nDrOp table")
								.array()), 
						"TestFile"), 
					"El SQL dentro del archivo TestFile incluye la constante DROP");		
		trySLQValidationFailedWith(
				new ValidationInput(
						new ByteArrayInputStream(
								Charset.forName("UTF-8").encode("Select * from wwa703;\nDROP table")
								.array()), 
						"TestFile"), 
					"El SQL dentro del archivo TestFile incluye la constante DROP");
	}
	
	@Test
	public void testReadExistentFileWithDeletesWithouthWhereClause() {
		
	}
	
	@Test
	public void testReadExistentFileWithValidSintaxForChanges() {
		new SQLValidator().validate(
				new ValidationInput(
				new ByteArrayInputStream(
						Charset.forName("UTF-8").encode("UPDATE wwa703 set idt703 = CURRENT_TIMESTAMP where idt703 > 5")
						.array()), "TestFile"));
	}
	
	@Test
	public void testReadExistentFileWithInvalidSintaxNoSemiColons() {
		
	}

}
