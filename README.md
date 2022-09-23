# file-name-validator

1. Clone https://github.com/animesh1d/file-name-validator.git
2. Checkout master Branch (Don't checkout main)
3. The service checks the file name validation.
4. FileNameValidatorService is the main class that contains the logic for file name validation check.
5. FileValidationException is the custom exception class for handling and throwing File Validation exception.
6. FilenamevalidatorApplicationTests is the Junit class that has several test methods for checking all the validation. 
7. The resource folder in test package has few files which are used as an input to check the logic for checking file name validation.

- I have tried to handle as many scenarios or edge cases possible.
- As per the document Test_A_07121987.csv passed valdation but the 2 digit sequence number is missing, then it should have failed which has failed in the testcases as per the business logic.
- Tried with a regex to handle complete file name instead of using Split method but the regex didn't work.
