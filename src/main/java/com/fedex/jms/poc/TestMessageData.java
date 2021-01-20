package com.fedex.jms.poc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.dataformat.csv.*;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

public class TestMessageData {
	
	public TestMessageData() {
		
	}
	
	public static final List<Map<?, ?>> covertCSVTestDataToJSON() {
		
	      try {
	    	  URL path = TestMessageData.class.getClassLoader().getResource("com/fedex/jms/poc/SampleCSVFile.csv");
	    	  File input = new File(path.toURI());
	    	  CsvSchema csv = CsvSchema.emptySchema().withHeader();
	          CsvMapper csvMapper = new CsvMapper();
	         MappingIterator<Map<?, ?>> mappingIterator =  csvMapper.reader().forType(Map.class).with(csv).readValues(input);
	         List<Map<?, ?>> list = mappingIterator.readAll();
	         System.out.println("******************* List are *********** "+list);
	         
	         
	         return list;
	      } catch(Exception e) {
	         e.printStackTrace();
	      }
	      return null;
	}
	
	public static final void readCSVTestData() {

		URL path = TestMessageData.class.getClassLoader().getResource("com/fedex/jms/poc/SampleCSVFile.csv");
		
		
			CSVReader reader;
			try {
				File f = new File(path.toURI());
				reader = new CSVReader(new FileReader(f), ',');

			
				/*ColumnPositionMappingStrategy<MaterialComposition> beanStrategy = new ColumnPositionMappingStrategy<MaterialComposition>();
				beanStrategy.setType(MaterialComposition.class);
				
				 beanStrategy.setColumnMapping(new String[]
				 {"fixed_acidity","volatile_acidity","citric_acid","residual_sugar",
				 "chlorides","free_sulfurdioxide","total_sulfurdioxide","density","pH",
				 "sulphates","alcohol","quality"});
				 
				
				CsvToBean<MaterialComposition> csvToBean = new CsvToBean<MaterialComposition>();
				
				List<MaterialComposition> materials = csvToBean.parse(beanStrategy, reader);
				*/
				HeaderColumnNameMappingStrategy<MaterialComposition> beanStrategy = new HeaderColumnNameMappingStrategy<MaterialComposition>();
				beanStrategy.setType(MaterialComposition.class);
				
				CsvToBean<MaterialComposition> csvToBean = new CsvToBean<MaterialComposition>();
				List<MaterialComposition> materials = csvToBean.parse(beanStrategy, reader);
				System.out.println(materials);
			} catch (FileNotFoundException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

}
