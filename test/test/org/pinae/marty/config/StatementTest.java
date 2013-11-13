package test.org.pinae.marty.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.pinae.ndb.Parser;
import org.pinae.ndb.Statement;

import test.org.pinae.marty.config.action.TraversalActionTest;
import test.org.pinae.marty.config.action.OperationActionTest;

public class StatementTest {
	
	private Statement operate = new Statement();
	
	private Map<String, Object> ndb = null;
	
	@Before
	public void setUp(){
		Parser parser = new Parser();
		try{
			ndb = parser.parse("example_1.txt");
		}catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testSelect(){
		
		Object result = null;
		List resultList= null;
			
		//Select：查询测试
		result = operate.execute("select:firewall->interface->name:dmz", ndb);
		assertTrue(result instanceof List);
		resultList = (List)result;
		assertEquals(resultList.size(), 1);
		assertEquals(((Map)resultList.get(0)).get("ip"), "192.168.12.2");
			
		result = operate.execute("select:firewall->interface", ndb);
		assertTrue(result instanceof List);
		resultList = (List)result;
		assertEquals(resultList.size(), 3);
		
		result = operate.execute("select:firewall->interface->mask:255.255.255.0", ndb);
		assertTrue(result instanceof List);
		resultList = (List)result;
		assertEquals(resultList.size(), 3);
		
		result = operate.execute("select:firewall->access-list->action:/permit|deny/", ndb);
		assertTrue(result instanceof List);
		resultList = (List)result;
		assertEquals(resultList.size(), 11);
		
		result = operate.execute("select:firewall->interface->security:[50,100]", ndb);
		assertTrue(result instanceof List);
		resultList = (List)result;
		assertEquals(resultList.size(), 2);

	}
	
	@Test
	public void testDelete(){
		
		Object result = null;
		List resultList= null;
		
		//Delete：删除测试
		result = operate.execute("delete:firewall->interface->name:inside && [mask, security]", ndb);
		result = operate.execute("select:firewall->interface->name:inside", (Map<String, Object>)result);
		assertTrue(result instanceof List);
		resultList = (List)result;
		assertEquals(resultList.size(), 1);
		assertEquals(((Map)resultList.get(0)).get("mask"), null);
		assertEquals(((Map)resultList.get(0)).get("security"), null);
		
		result = operate.execute("delete:firewall->interface->name:outside && block", ndb);
		result = operate.execute("select:firewall->interface->name:outside", (Map<String, Object>)result);
		assertTrue(result instanceof List);
		resultList = (List)result;
		assertEquals(resultList.size(), 0);
	}
	
	@Test
	public void testUpdate(){
		
		Object result = null;
		List resultList= null;
		
		//Update：更新测试
		result = operate.execute("update:firewall->interface->name:dmz && name=dmz2,mask=255.255.255.32", ndb);
		result = operate.execute("select:firewall->interface->name:dmz2", (Map<String, Object>)result);
		assertTrue(result instanceof List);
		resultList = (List)result;
		assertEquals(resultList.size(), 1);
		assertEquals(((Map)resultList.get(0)).get("ip"), "192.168.12.2");
		
		result = operate.execute("update:firewall", ndb, new OperationActionTest());
		result = operate.execute("select:firewall->flag:pinae", (Map<String, Object>)result);
		resultList = (List)result;
		assertEquals(resultList.size(), 1);
	}
	
	@Test
	public void testTravel(){
		
		Object result = null;
		List resultList= null;
		
		//Travel: 遍历
		result = operate.execute("select:firewall->objectgroup", (Map<String, Object>)result);
		assertTrue(result instanceof List);
		assertTrue(((List)result).size() == 0);
		
		result = operate.execute("travel", ndb, new TraversalActionTest());
		assertTrue(result instanceof Map);
		
		result = operate.execute("select:firewall->objectgroup", (Map<String, Object>)result);
		assertTrue(result instanceof List);
		assertTrue(((List)result).size() > 0);
	}
	
}
