Feature: Add book functionality
  focusing on add functionality
  
  Background:
    * url 'http://127.0.0.1:8000/book/rest/book'
    * def randomBook =
  		"""
  		function(arg) {
    		var Book = Java.type('gr.aegean.book.domain.Book');
    		var b = new Book();
    		b.setIsbn(arg);
    		var pubId = Math.floor(Math.random() * 10) + 1;
	    	b.setPublisher("Pub" + pubId);
	    	var titleId = Math.floor(Math.random() * 10) + 1;
	    	b.setTitle("Title" + titleId);
	    	var auth1 = Math.floor(Math.random() * 10) + 1;
	    	var auth2 = Math.floor(Math.random() * 10) + 1;
	    	var author1 = "Author" + auth1;
	    	var author2 = "Author" + auth2;
	    	const authors = [author1, author2];
	    	b.setAuthors(authors);
    		return karate.toJson(b,true);  
  		}
  		"""

  Scenario: Adding empty book
    * header Authorization = call read('classpath:basic-auth.js') { username: 'admin', password: 'admin' }
    Given path 'admin' 
    And request {}
    And header Content-Type = 'application/json'
    When method post
    Then status 400
    
  Scenario: Adding correct book with wrong content type
    * header Authorization = call read('classpath:basic-auth.js') { username: 'admin', password: 'admin' }
    * def cBook = call randomBook 'xxx' 
    Given path 'admin' 
    And request cBook
    And header Content-Type = 'text/html'
    When method post
    Then status 415
    
  Scenario: Adding correct book with no authorization
    * def cBook = call randomBook 'xxx' 
    Given path 'admin' 
    And request cBook
    And header Content-Type = 'application/json'
    When method post
    Then status 401
    
  Scenario: Adding correct book
    * header Authorization = call read('classpath:basic-auth.js') { username: 'admin', password: 'admin' }
    * def cBook = call randomBook 'xxx' 
    Given path 'admin' 
    And request cBook
    And header Content-Type = 'application/json'
    When method post
    Then status 200
    
  Scenario: Book added exists
    * header Authorization = call read('classpath:basic-auth.js') { username: 'admin', password: 'admin' }
    Given path 'xxx'
    And header Accept = 'application/json'
    When method get
    Then status 200
    And match header Content-Type == 'application/json'
    And match response.isbn == 'xxx'
    
  Scenario: Only one book exists
    Given header Accept = 'application/json'
    When method get
    Then status 200
    And match header Content-Type == 'application/json'
    And match response == '#[1]'
    And match response[0].isbn == 'xxx'
    
  Scenario: Book with isbn yyy does not exist
    Given path 'yyy'
    And header Accept = 'application/json'
    When method get
    Then status 404
    
  Scenario Outline: Adding 3 correct books
    * header Authorization = call read('classpath:basic-auth.js') { username: 'admin', password: 'admin' }
    * def cBook = call randomBook <isbn> 
    Given path 'admin' 
    And request cBook
    And header Content-Type = 'application/json'
    When method post
    Then status 200
    
    Examples:
    | isbn |
    | '1111' |
    | '2222' |
    | '3333' |
    
  Scenario: Get all added books
    Given header Accept = 'application/json'
    When method get
    Then status 200
    And match response == '#[4]'
    And match response[*].isbn contains only ['xxx','1111','2222','3333'] 
