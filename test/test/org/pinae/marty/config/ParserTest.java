package test.org.pinae.marty.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;
import org.pinae.ndb.Parser;

public class ParserTest {
	@Test
	public void testParse(){
		Parser parser = new Parser();
		
		try {
			Map<String, Object> configMap = parser.parse("example_1.txt");
			
			assertEquals(configMap.size(), 1);
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testParse2(){
		Parser parser = new Parser();
		
		try {
			Map<String, Object> configMap = parser.parse("example_2.txt");
			
			assertEquals(configMap.size(), 1);
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
}
