package gr.aegean.book.domain;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Kyriakos Kritikos
 */

@XmlRootElement
public class ErrorItem {

    private Integer errorCode = 400;
    private String name = "bad_parameter";
    private String description = "A wrong parameter value was provided";
    
    ErrorItem() {}

    public ErrorItem(Integer errorCode, String name, String description) {
        this.errorCode = errorCode;
        this.name = name;
        this.description = description;
    }
    
    public Integer getErrorCode(){
    	return errorCode;
    }
    
    public void setErrorCode(Integer errorCode){
    	this.errorCode = errorCode;
    }
    
    public String getName(){
    	return name;
    }
    
    public void setName(String name){
    	this.name = name;
    }
    
    public String getDescription(){
    	return description;
    }
    
    public void setDescription(String description){
    	this.description = description;
    }
    
    public String toString(){
    	return name + " " + description;
    }
}
