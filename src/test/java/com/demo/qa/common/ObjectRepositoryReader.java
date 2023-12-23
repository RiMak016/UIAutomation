package com.demo.qa.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

public class ObjectRepositoryReader {
	
	private CSVReader csvreader=null;
	public List<String[]> ObjectArray=null;
	
	public void readWebObjects(String module) throws IOException
	{
		FileInputStream FileInputStream = new FileInputStream(System.getProperty("user.dir")+File.separator+"Resources"+File.separator+module+File.separator+"ObjectRepository.csv");
		csvreader=new CSVReader(new InputStreamReader(FileInputStream));
		ObjectArray = csvreader.readAll();
	    csvreader.close();	
	    FileInputStream.close();
    }
	
	public  String[] getObjectArray(String ObjectName) {
		
		String Row[]=null;           
		for (Object object : ObjectArray){
		    Row = (String[]) object;		     
		    if(Row[0]!=null)
		    {
		    	if(Row[0].toString().equalsIgnoreCase(ObjectName))
		    		return Row;
		    }
		}
		return null;
	}
	
}
