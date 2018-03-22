# Demo - Selenium, TestNG & report generation.

This framework is based on Selenium Webdriver & TestNG as hybrid framework. 
Test Data has been entering in form of XML file. Log4j has been entering the logs and screenshots for failed scenarios are collected at specified location.
Report is generated and mailed to the recipients. 

## Softwares used: 
 
1.Eclipse IDE 

2.Selenium                    version 3.10.0

3.Log4j                       version 1.2.17

4.Apache maven plugin         version 2.5.2

5.ChromeDriver                Version 65.0.3325.181 (Official Build)

6.TestNG

### Environment Setup

Clone or download source from project code. Clone repository to eclipse git repositories. 
Import project from cloned git repository. It is an maven project.

Install TestNG plugin to eclpise if it is not installed.

Open mail.properties file and update following properties value. This is used to send mail of execution test result. 

```
mail.smtp.from

mail.smtp.to

mail.smtp.password
```
## Running the tests via Eclipse

Open or navigate to DemoTest.java file.
```
Run DemoTest.java file as TestNG Test
```

## Running the tests via command line

Download sourcecode from git and compile it to jar file (via eclipse or mvn install)

Open commandline console. 

Navigate to folder which contains created jar file named as 'selenium-framework-0.0.1-SNAPSHOT.jar'

Execute following command.

```

```

### Project description 

Framework contains 3 main classes. 
```
1. Login 
2. Branch 
3. Staff
```

Login has test cases which checks the login functionality with authenticati.....

Branch class runs the basic functionality for adding , editing , viewing and deleting the branch. Along with that it checks for the validation messages corresponding to the maxlength/minlength/pattern of the name and code text.

Staff class runs the fucntionality as same for branch class , like adding , editing , viewing and deleting the staff. Along with that it checks for validation messages corresponding to the mandatory/maxlength/pattern of the name text.

