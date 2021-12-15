Feature: Initial State
  focusing on search functionality with respect to initial state
  
  Background:
    * url 'http://127.0.0.1:8000'

  Scenario: Index Page Loads
    Given path 'book'
    When method get
    Then status 200
    
  Scenario: No book exists
    Given path 'book','rest','book'
    And header Accept = 'application/json'
    When method get
    Then match [200,204] contains responseStatus
    And match response == ''
    
  Scenario: Get non-existing book
    Given path 'book','rest','book','xxx'
    When method get
    Then status 404
    
  Scenario: Follow wrong path
  	Given path 'book','rest'
  	When method get
  	Then status 404
  	
  Scenario: Follow second wrong path
  	Given path 'book','rest','books','xxx'
  	When method get
  	Then status 404
