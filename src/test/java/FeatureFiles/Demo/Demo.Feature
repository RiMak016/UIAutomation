#Author: maksudmondal94@gmail.com

Feature: Verify online java compiler website Component

 @Sanity @CreateInn @Regression
  Scenario: Verify jdoddle website Component
    Given user navigates to jdoddle website	
    Then Verify Heading Text "Online Java Compiler IDE"
    Then Verify Menu Text "Products"	
    Then Verify Text1 "Pricing"
    Then Verify Text2 "External Libraries"
    Then Verify Text3 "Output"
    Then Verify Text4 "Font Size:"
    And Click on Font Size button
    Then Choose 18Px Resolution	
    And click on Execute Button
    Then Verify the Processing Text before showing the Result "JDoodle in Action.... Running the program..."
    Then Verify The output Result "Sum of x+y = 35"
    