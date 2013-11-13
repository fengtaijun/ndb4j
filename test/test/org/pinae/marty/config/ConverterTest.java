package test.org.pinae.marty.config;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;
import org.pinae.ndb.Converter;
import org.pinae.ndb.Parser;

public class ConverterTest {
	@Test
	public void testConvertToJSON(){
		Parser parser = new Parser();
		
		Converter converter = new Converter();
		
		try {
			Map<String, Object> configMap = parser.parse("example_1.txt");
			
			String result = converter.convertToJSON(configMap);
			
			assertTrue(result.length()>0);
		
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testConvertToXML(){
		Parser parser = new Parser();
		
		Converter converter = new Converter();
		
		try {
			Map<String, Object> configMap = parser.parse("example_1.txt");
			
			String result = converter.convertToXML(configMap);
			
			assertTrue(result.length()>0);
		
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
}
