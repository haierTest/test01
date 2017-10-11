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

public class XmltoConnection{
	public  ArrayList excute(String xml){
		ArrayList list = new ArrayList();
		try{
			StringReader in=new StringReader(xml);
			SAXParserFactory sf = SAXParserFactory.newInstance();
			SAXParser sp = sf.newSAXParser();
			HangyiXmlParse testsax=new HangyiXmlParse();
			sp.parse(new InputSource(in),testsax);
			list=testsax.getList();
		}catch(Exception e){
			return new ArrayList();
		}
		return list;
	}
}