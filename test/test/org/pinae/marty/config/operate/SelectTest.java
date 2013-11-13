package test.org.pinae.marty.config.operate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.pinae.ndb.Parser;
import org.pinae.ndb.operate.Select;

public class SelectTest {
	
	@Test
	public void testSelect(){
		Parser parser = new Parser();
		
		Select select = new Select();
		
		Object result = null;
		try {
			
			Map<String, Object> configMap = parser.parse("config.txt");

			List resultList= null;
			
			result = select.select("firewall->interface->name:dmz", configMap);
			assertTrue(result instanceof List);
			resultList = (List)result;
			assertEquals(resultList.size(), 1);
			assertEquals(((Map)resultList.get(0)).get("ip"), "192.168.12.2");
			
			result = select.select("firewall->interface", configMap);
			assertTrue(result instanceof List);
			resultList = (List)result;
			assertEquals(resultList.size(), 3);
			
			result = select.select("firewall->interface->mask:255.255.255.0", configMap);
			assertTrue(result instanceof List);
			resultList = (List)result;
			assertEquals(resultList.size(), 3);
			
			result = select.select("firewall->access-list->action:/permit|deny/", configMap);
			assertTrue(result instanceof List);
			resultList = (List)result;
			assertEquals(resultList.size(), 11);
			
			result = select.select("firewall->interface->security:[50,100]", configMap);
			assertTrue(result instanceof List);
			resultList = (List)result;
			assertEquals(resultList.size(), 2);
			
			result = select.select("firewall->access-group", configMap);
			assertTrue(result instanceof List);
			
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
}
