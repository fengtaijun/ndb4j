package org.pinae.ndb.operate;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.pinae.ndb.action.OperationAction;

/**
 * ndb 节点更新
 * 
 * @author Huiyugeng
 *
 */
public class Update extends Locator {
	private Map<String, String> updateMap = null; //需要更新的键值映射
	
	private OperationAction action = null; //需要执行的行为
	
	public Object update (String path, Map<String, Object> ndb, String updateValue){
		updateMap = convertValueMap(updateValue);
		
		locate(path, ndb);
		
		return ndb;
	}
	
	public Object update (String path, Map<String, Object> ndb, OperationAction action){
		this.action = action;
		
		locate(path, ndb);
		
		return ndb;
	}
	

	@Override
	protected void doAction(Object item, String key) {
		if (action != null){
			action.handle(item);
		} else {
			if (item != null && item instanceof Map){
				Map map = (Map)item;
				
				Set<Map.Entry<String, String>> entrySet = (Set<Entry<String, String>>)updateMap.entrySet();
				for(Entry<String, String> entry : entrySet){
					map.put(entry.getKey(), entry.getValue());
				}
			}
		}
	}
	
	private Map<String, String> convertValueMap(String updateValue){
		Map<String, String> updateValueMap = new HashMap<String, String>();
		String values[] = updateValue.split(",");
		for (String tempValue : values){
			String valuePair[] = tempValue.split("=");
			if (valuePair.length == 2){
				updateValueMap.put(valuePair[0].trim(), valuePair[1].trim());
			}
		}
		return updateValueMap;
	}


}
