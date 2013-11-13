package org.pinae.ndb.operate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.pinae.ndb.action.OperationAction;

/**
 * ndb 节点查询
 * 
 * @author Huiyugeng
 *
 */
public class Select extends Locator {
	private List<Object> resultList = null; //查询结果
	
	private OperationAction action = null; //需要执行的行为

	public Object select (String path, Map<String, Object> ndb){
		resultList = new ArrayList<Object>();
		
		locate(path, ndb);
		
		return resultList;
	}
	
	public Object select (String path, Map<String, Object> ndb, OperationAction action){
		this.action = action;
		
		return select (path, ndb);
	}

	@Override
	protected void doAction(Object item, String key) {
		if (action != null) {
			action.handle(item);
		}
		
		if (item != null){
			resultList.add(item);
		}
	}



}
