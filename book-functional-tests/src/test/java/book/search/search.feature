Feature: Book search functionality
  focusing on book search functionality
  
  Background:
    * url 'http://127.0.0.1:8000/book/rest/book'
    * def randomBook =
  		"""
  		function(isbn,pubId,titleId,auth1,auth2) {
    		var Book = Java.type('gr.aegean.book.domain.Book');
    		var b = new Book();
    		b.setIsbn(isbn);
	    	b.setPublisher("Pub" + pubId);
	    	b.setTitle("Title" + titleId);
	    	var author1 = "Author" + auth1;
	    	var author2 = "Author" + auth2;
	    	const authors = [author1, author2];
	    	b.setAuthors(authors);
    		return karate.toJson(b,true);  
  		}
  		"""
    
  Scenario Outline: Adding 5 correct books
    * header Authorization = call read('classpath:basic-auth.js') { username: 'admin', password: 'admin' }
    * def cBook = randomBook(<isbn>,<pubId>,<titleId>,<auth1>,<auth2>) 
    Given path 'admin' 
    And request cBook
    And header Content-Type = 'application/json'
    When method post
    Then status 200
    
    Examples:
    | isbn | pubId | titleId | auth1 | auth2 |
    | '1111' | '1' | '1' | '1' | '2' | 
    | '2222' | '1' | '2' | '2' | '3' |
    | '3333' | '2' | '3' | '3' | '4' |
    | '4444' | '2' | '4' | '4' | '1' |
    | '5555' | '3' | '5' | '5' | '2' |
    
  Scenario: Get all added books
    Given header Content-Type = 'application/json'
    When method get
    And header Accept = 'application/json'
    Then status 200
    And match response == '#[5]'
    And match response[*].isbn contains only ['1111','2222','3333','4444','5555']
    
  Scenario Outline: Search books based on publisher 
  	Given header Accept = 'application/json'
  	And param publisher = <pub>
    When method get
    Then status 200
    And match response == <num>
    And match response[*].isbn contains only <isbn>
    
    Examples:
    | pub | num | isbn |
    | 'Pub1' | '#[2]' | ['1111','2222'] |
    | 'Pub2' | '#[2]' | ['3333','4444'] |
    | 'Pub3' | '#[1]' | ['5555'] |
    
  Scenario Outline: Search books based on author 
  	Given header Accept = 'application/json'
  	And param author = <auth>
    When method get
    Then status 200
    And match response == <num>
    And match response[*].isbn contains only <isbn>
    
    Examples:
    | auth | num | isbn |
    | 'Author1' | '#[2]' | ['1111','4444'] |
    | 'Author2' | '#[3]' | ['1111','2222','5555'] |
    | 'Author3' | '#[2]' | ['2222','3333'] |
    | 'Author4' | '#[2]' | ['3333','4444'] |
    | 'Author5' | '#[1]' | ['5555'] |
