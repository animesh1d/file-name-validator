package com.test.filenamevalidator;

import com.test.filenamevalidator.service.FileNameValidatorService;
import com.test.filenamevalidator.service.exception.FileValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class FilenamevalidatorApplicationTests {

	@Autowired
	private FileNameValidatorService fileNameValidatorService;

	@Test
	public void testValidateFileNotExist() {
		File file = new File("src/test/resources/testfiles/abc.csv");
		assertThrows(FileValidationException.class, () -> fileNameValidatorService.validateFileName(file));
	}

	@Test
	public void testValidateFileNameBlank() {
		File file = new File("src/test/resources/testfiles/.txt");
		Exception exception = assertThrows(FileValidationException.class, () -> fileNameValidatorService.validateFileName(file));
		assertEquals("File '.txt' failed validation\n" +
				"File format should be ‘Test_<portfoliocode>_<ddmmyyyy>_<2digit-sequencenumber>.csv’", exception.getMessage());
	}

	@Test
	public void testValidateFileNameFormatError() {
		File file = new File("src/test/resources/testfiles/Test.txt");
		Exception exception = assertThrows(FileValidationException.class, () -> fileNameValidatorService.validateFileName(file));
		assertEquals("File 'Test.txt' failed validation\n" +
				"File format should be ‘Test_<portfoliocode>_<ddmmyyyy>_<2digit-sequencenumber>.csv’", exception.getMessage());
	}

	@Test
	public void testValidateFileNameFormatSeqNumMissing() {
		File file = new File("src/test/resources/testfiles/Test_A_07121987.csv");
		Exception exception = assertThrows(FileValidationException.class, () -> fileNameValidatorService.validateFileName(file));
		assertEquals("File 'Test_A_07121987.csv' failed validation\n" +
				"File format should be ‘Test_<portfoliocode>_<ddmmyyyy>_<2digit-sequencenumber>.csv’", exception.getMessage());
	}

	@Test
	public void testValidateFileNamePortfolioCodeError() {
		File file = new File("src/test/resources/testfiles/Test_E_07121987_89.csv");
		Exception exception = assertThrows(FileValidationException.class, () -> fileNameValidatorService.validateFileName(file));
		assertEquals("File 'Test_E_07121987_89.csv' failed validation\n" +
				"PortfolioCode should be A/B/C found E.", exception.getMessage());
	}

	@Test
	public void testValidateFileNamePrefixError() {
		File file = new File("src/test/resources/testfiles/Hello_A_07121987_89.csv");
		Exception exception = assertThrows(FileValidationException.class, () -> fileNameValidatorService.validateFileName(file));
		assertEquals("File 'Hello_A_07121987_89.csv' failed validation\n" +
				"Prefix for the file should be ‘Test’ found Hello", exception.getMessage());
	}

	@Test
	public void testValidateFileNameExtensionError() {
		File file = new File("src/test/resources/testfiles/Test_A_07121987_12.txt");
		Exception exception = assertThrows(FileValidationException.class, () -> fileNameValidatorService.validateFileName(file));
		assertEquals("File 'Test_A_07121987_12.txt' failed validation\n" +
				"Invalid File format.Expected ‘csv’ found txt", exception.getMessage());
	}

	@Test
	public void testValidateFileNamSeqNumWrongError() {
		File file = new File("src/test/resources/testfiles/Test_A_07121987_291.csv");
		Exception exception = assertThrows(FileValidationException.class, () -> fileNameValidatorService.validateFileName(file));
		assertEquals("File 'Test_A_07121987_291.csv' failed validation\n" +
				"Sequence number is not a valid two digit sequence number.", exception.getMessage());
	}
}
