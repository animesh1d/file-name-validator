package com.test.filenamevalidator.service;

import com.test.filenamevalidator.service.exception.FileValidationException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FileNameValidatorService {

    private final static String TEST = "Test";
    private final static String PortFolioCodePattern = "[ABC]";
    private final static String FileExtensionPattern = "^[a-zA-Z0-9._ -]+\\.(csv)$";
    private final static String DateFormatPattern = "ddmmyyyy";
    private final static String twoDigitSequencePattern = "\\d{2}";
    private final static String PortfolioCodeError = "PortfolioCode should be A/B/C found E.";
    private final static String ValuationDateError = "Valuation Date is not a valid date format ‘ddmmyyyy’";
    private final static String PrefixError = "Prefix for the file should be ‘Test’ found ";
    private final static String TwoDigitSequenceError = "Sequence number is not a valid two digit sequence number.";
    private final static String InvalidFileFormatError = "Invalid File format.Expected ‘csv’ found ";
    private final static String FileFormatError = "File format should be ‘Test_<portfoliocode>_<ddmmyyyy>_<2digit-sequencenumber>.csv’";
    private final static String FailedValidation = "failed validation";
    private final static String PassedValidation= "passed validation";
    private final static String FileNotExistError= "File doesn't exist";

    public String validateFileName(File file) throws FileValidationException {

        checkFileExistOrNot(file);

        String fileName = file.getName();
        String message = "File " + "'" + fileName + "'" + " ";
        String[] fileSubNames = fileName.substring(0,fileName.indexOf(".")).split("_");

        checkFileFormat(fileName, message, fileSubNames);
        checkFileExtension(fileName, message);
        checkFileSubNamesFormat(fileSubNames, message);

        return message + PassedValidation;
    }

    private static void checkFileExistOrNot(File file) throws FileValidationException {
        if(!file.exists()){
            throw new FileValidationException(FileNotExistError);
        }
    }

    private static void checkFileFormat(String fileName, String message, String[] fileSubNames) throws FileValidationException {
        if (fileName.isBlank() || !fileName.contains("_") || fileSubNames.length < 4) {
            throw new FileValidationException(message + FailedValidation + "\n" + FileFormatError);
        }
    }

    private static void checkFileExtension(String fileName, String message) throws FileValidationException {
        if(!fileName.matches(FileExtensionPattern)){
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
            throw new FileValidationException(message + FailedValidation + "\n" + InvalidFileFormatError + fileExtension);
        }
    }

    private static void checkFileSubNamesFormat(String[] fileSubNames, String message) throws FileValidationException {
        int index =0;
        for(String subName : fileSubNames){
            if(index == 0 && !subName.equalsIgnoreCase(TEST)){
                throw new FileValidationException(message + FailedValidation + "\n" + PrefixError + subName);
            }

            else if(index == 1 && !patternMatcher(PortFolioCodePattern, subName)){
                throw new FileValidationException(message + FailedValidation + "\n" + PortfolioCodeError);
            }

            else if(index == 2 && !isDateValid(subName)){
                throw new FileValidationException(message + FailedValidation + "\n" + ValuationDateError);
            }

            else if(index == 3 && !patternMatcher(twoDigitSequencePattern, subName)){
                throw new FileValidationException(message + FailedValidation + "\n" + TwoDigitSequenceError);
            }
            index++;
        }
    }

    private static boolean patternMatcher(String patternString, String text){
        Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }

    private static boolean isDateValid(String date)
    {
        try {
            DateFormat df = new SimpleDateFormat(DateFormatPattern);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

   /* private static boolean isValid(final String date) {
        boolean valid;
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern(DateFormatPattern).withResolverStyle(ResolverStyle.STRICT));
            valid = true;

        } catch (DateTimeParseException e) {
            e.printStackTrace();
            valid = false;
        }
        return valid;
    }*/
}
