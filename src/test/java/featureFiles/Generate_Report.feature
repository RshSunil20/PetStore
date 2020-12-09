Feature: Test the file upload and report generate feature



  @Smoketest
   Scenario: TC_01 User is able to select the files and generate the reports

      Given user has access to the report generator application interface
      When user inputs the Start and End date as today
      And user inputs the input file location with correct text file and click Generate button
      Then Success message is displayed and Report is generated in the Report table
      And log file is generated in the Log folder


  @Smoketest
  Scenario: TC_02 User is able to validate the correct data in the report table

     Given user has validate credentials for Report database
     When user enters valid connection details to connect to the Report DB
     Then user sucessfully connects to the report DB
     When user submit the query with date range to get records on the given date range
     Then user gets the response report data for the given date range

