package org.pinae.ndb.operate;

import java.util.Map;

import org.pinae.ndb.action.OperationAction;

/**
 * ndb 节点删除
 * 
 * @author Huiyugeng
 *
 */
public class Delete extends Locator {
	
	private boolean clear = false; //是否全部清除
	
	private String columns[] = null; //需要清除的字段名称
	
	private OperationAction action = null; //需要执行的行为
	
	/**
	 * 
	 * 
	 * @param path
	 * @param ndb
	 * @param column
	 * @return
	 */
	public Object delete (String path, Map<String, Object> ndb, String column){
		if (column != null){
			if (column.startsWith("[") && column.endsWith("]")){
				column = column.trim().substring(1, column.length() -1 );
				this.columns = column.split(",");
			}else if(column.equalsIgnoreCase("block")){
				clear = true;
			}
		}

		locate(path, ndb);
		
		return ndb;
	}
	
	public Object delete (String path, Map<String, Object> ndb, OperationAction action){
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
				if (clear){
					map.clear();
				}else{
					if (columns != null && columns.length > 0){
						for (String column : columns){
							map.remove(column.trim());
						}
					}else{
						map.remove(key);
					}
				}
			}
		}
	}


}
