Feature: Remove book functionality
  focusing on remove functionality
  
  Background:
    * url 'http://127.0.0.1:8000/book/rest/book'

  Scenario: Delete non-existing book
    * header Authorization = call read('classpath:basic-auth.js') { username: 'admin', password: 'admin' }
    Given path 'admin','111' 
    And request {}
    When method delete
    Then status 404
    
  Scenario: Delete existing book with no authorization
    Given path 'admin','xxx' 
    When method delete
    Then status 401
    
  Scenario: Correctly delete existing book
    * header Authorization = call read('classpath:basic-auth.js') { username: 'admin', password: 'admin' }
    Given path 'admin','xxx' 
    When method delete
    Then status 200
    
  Scenario: Get removed book 
    Given path 'xxx' 
    And header Accept = 'application/json'
    When method get
    Then status 404
    
  Scenario: Get remaining books
    Given header Accept = 'application/json'
    When method get
    Then status 200
    And match response == '#[3]'
    And match response[*].isbn contains only ['1111','2222','3333'] 
    
  Scenario Outline: Correctly delete all existing books
    * header Authorization = call read('classpath:basic-auth.js') { username: 'admin', password: 'admin' }
    Given path 'admin',<isbn> 
    When method delete
    Then status 200
    
    Examples:
    | isbn |
    | '1111' |
    | '2222' |
    | '3333' |
    
  Scenario: No remaining book
    Given header Accept = 'application/json'
    When method get
    Then match [200,204] contains responseStatus
    And match response == '' 
