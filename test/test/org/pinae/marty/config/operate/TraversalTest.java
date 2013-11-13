package test.org.pinae.marty.config.operate;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;
import org.pinae.ndb.Parser;
import org.pinae.ndb.action.TraversalAction;
import org.pinae.ndb.operate.Traversal;

public class TraversalTest {
	
	@Test
	public void testTraversal(){
		Parser parser = new Parser();
		
		Traversal traversal = new Traversal();
		
		try {
			
			Map<String, Object> configMap = parser.parse("config.txt");
			
			Map<String, Object> map = traversal.traversal(configMap, new TraversalActionTest());
			
			
			
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	public static class TraversalActionTest implements TraversalAction {

		@Override
		public String handleKey(String key) {
			String newKey = "";

			if (key.indexOf("-") > -1) {
				String keys[] = key.split("-");
				for (String _key : keys){
					newKey += _key;
				}
			} else {
				newKey = key;
			}
			return newKey;
		}

		@Override
		public Object handleValue(Object value) {
			return value;
		}


	}
	
}
