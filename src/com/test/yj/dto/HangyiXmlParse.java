package com.test.yj.dto;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.Locator;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;
import java.io.IOException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.*;

class HangyiXmlParse extends DefaultHandler{
	ArrayList list = new ArrayList();
	HashMap listItem = null;
    HashMap itemListItem = null;
    private String currentElement = null; 
    private String currentValue = null;   
    	
	public HangyiXmlParse(){
		super(); 
	}

	public void setDocumentLocator(Locator locator){

	}
	
    public void setList(HashMap table) {
        this.listItem = listItem;
    } 

    public ArrayList getList() {
        return list;
    } 
    
	public void startDocument() throws SAXException {
	}

	public void endDocument() throws SAXException{
	}

	public void startPrefixMapping( String prefix, String uri ) {
	}

	public void endPrefixMapping( String prefix ){
	}

	public void processingInstruction( String target, String instruction )throws SAXException{
	}

	public void ignorableWhitespace( char[] chars, int start, int length ) throws SAXException {
	}

	public void skippedEntity( String name ) throws SAXException {
	}

	public void startElement(String namespaceURI,String localName,String qName,Attributes atts) {
		if(qName!=null){
			currentElement=qName;
			if(qName.equals("Errors")==false&&qName!=null){
				if(qName.equals("PolicySchedule")) 
					listItem=new HashMap();
				itemListItem = new HashMap();	
				for(int i=0;i<atts.getLength();i++){
					itemListItem.put(atts.getQName(i),atts.getValue(i));
					if(i==atts.getLength()-1){
						listItem.put(qName+"attributes",itemListItem);
					}
				}
			}
        }
	}

	public void endElement(String namespaceURI,String localName,String fullName )throws SAXException{
		if(currentElement!=null){
			if(!currentElement.equals("Errors")){
				if (currentElement.equals(fullName))
				   if(currentElement.equals("FlightDate")){
				   	  
				   }
				   if(currentElement.equals("OperateTime")){
				   	   
				   }
				if(listItem.get(currentElement)==null)
				listItem.put(currentElement, currentValue);
		        if(fullName.equals("PolicySchedule"))
		            list.add(listItem); 
		    }  
		}
		       
	}

	public void characters( char[] chars, int start, int length )throws SAXException{
		if(currentElement!=null){
			if(!currentElement.equals("Errors")){
				currentValue=new String(chars,start,length);
				
			}
		}				
		
	}


	
}



 