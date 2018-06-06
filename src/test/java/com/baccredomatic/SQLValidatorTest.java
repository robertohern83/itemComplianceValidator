package com.baccredomatic;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.junit.Test;


public class SQLValidatorTest {

	@Test (expected=IllegalArgumentException.class)
	public void testReadUnexistentFileAndFailWithFileNotFoundException() throws InvalidSQLException {
		new SQLValidator().validate(null);
	}
	
	@Test
	public void testReadExistentFileWithInvalidCreateConstant() {

		trySLQValidationFailedWith(
				new ByteArrayInputStream(
						Charset.forName("UTF-8").encode("Select * from wwa703;\nCreate table")
						.array()), 
				"El SQL incluye la constante CREATE");
		trySLQValidationFailedWith(
				new ByteArrayInputStream(
						Charset.forName("UTF-8").encode("Select * from wwa703;\nCreAte table")
						.array()), 
				"El SQL incluye la constante CREATE");		
		trySLQValidationFailedWith(
				new ByteArrayInputStream(
						Charset.forName("UTF-8").encode("Select * from wwa703;\nCREATE table")
						.array()), 
				"El SQL incluye la constante CREATE");
		
		
	}

	private void trySLQValidationFailedWith(InputStream inputStream, String expectedMessage) {
		new SQLValidator().validate(inputStream).stream().forEach(s -> assertEquals(expectedMessage, s));
		
	}
	
	@Test
	public void testReadExistentFileWithInvalidAlterConstant() {
		trySLQValidationFailedWith(
				new ByteArrayInputStream(
						Charset.forName("UTF-8").encode("Select * from wwa703;\nAlter table")
						.array()), 
				"El SQL incluye la constante ALTER");
		trySLQValidationFailedWith(
				new ByteArrayInputStream(
						Charset.forName("UTF-8").encode("Select * from wwa703;\nAlTer table")
						.array()), 
				"El SQL incluye la constante ALTER");		
		trySLQValidationFailedWith(
				new ByteArrayInputStream(
						Charset.forName("UTF-8").encode("Select * from wwa703;\nALTER table")
						.array()), 
				"El SQL incluye la constante ALTER");
	}
	
	@Test
	public void testReadExistentFileWithInvalidDropConstant() {
		trySLQValidationFailedWith(
				new ByteArrayInputStream(
						Charset.forName("UTF-8").encode("Select * from wwa703;\nDrop table")
						.array()), 
				"El SQL incluye la constante DROP");
		trySLQValidationFailedWith(
				new ByteArrayInputStream(
						Charset.forName("UTF-8").encode("Select * from wwa703;\nDrOp table")
						.array()), 
				"El SQL incluye la constante DROP");		
		trySLQValidationFailedWith(
				new ByteArrayInputStream(
						Charset.forName("UTF-8").encode("Select * from wwa703;\nDROP table")
						.array()), 
				"El SQL incluye la constante DROP");
	}
	
	@Test
	public void testReadExistentFileWithDeletesWithouthWhereClause() {
		
	}
	
	@Test
	public void testReadExistentFileWithValidSintaxForChanges() throws InvalidSQLException {
		new SQLValidator().validate(
				new ByteArrayInputStream(
						Charset.forName("UTF-8").encode("UPDATE wwa703 set idt703 = CURRENT_TIMESTAMP where idt703 > 5")
						.array()));
	}
	
	@Test
	public void testReadExistentFileWithInvalidSintaxNoSemiColons() {
		
	}

}
