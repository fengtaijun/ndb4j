package org.pinae.ndb;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.pinae.ndb.action.Action;
import org.pinae.ndb.action.OperationAction;
import org.pinae.ndb.action.TraversalAction;
import org.pinae.ndb.operate.Delete;
import org.pinae.ndb.operate.Select;
import org.pinae.ndb.operate.Traversal;
import org.pinae.ndb.operate.Update;

/**
 * ndb语句执行器
 * 
 * @author Huiyugeng
 *
 *
 */
public class Statement {

	/**
	 * 执行ndb语句
	 * 
	 * @param query 需要执行的ndb语句
	 * @param ndb ndb信息
	 * 
	 * @return 执行结果
	 */
	public Object execute(String query, Map<String, Object> ndb) {
		return execute(query, ndb, null);
	}

	/**
	 * 执行ndb语句
	 * 
	 * @param query 需要执行的ndb语句
	 * @param ndb ndb信息
	 * @param action 自定义行为,如果使用自定义行为，则仅进行定位不执行值变更
	 * 
	 * @return 执行结果
	 */
	public Object execute(String query, Map<String, Object> ndb, Action action) {
		
		String command = query;
		if (query.indexOf(":") > -1 ) {
			command = StringUtils.substringBefore(query, ":").trim();
			query = StringUtils.substringAfter(query, ":").trim();
		}
		
		String queryItems[] = query.split("&&");

		if (queryItems != null) {
			String path = null, value = null;

			if (queryItems.length > 0) {
				path = queryItems[0].trim();
			}
			if (queryItems.length > 1) {
				value = queryItems[1].trim();
			}

			if (action != null) {
				return execute(ndb, command, path, null, action);
			} else {
				return execute(ndb, command, path, value, null);
			}

		}
		return ndb;
	}

	private Object execute(Map<String, Object> config, String command,
			String path, String value, Action action) {
		Object result = config;

		if (command != null) {

			if (command.equalsIgnoreCase("select")) {
				if (action != null) {
					result = new Select().select(path, config, (OperationAction)action);
				} else {
					result = new Select().select(path, config);
				}
			} else if (command.equalsIgnoreCase("delete")) {
				if (action != null) {
					result = new Delete().delete(path, config, (OperationAction)action);
				} else {
					result = new Delete().delete(path, config, value);
				}
			} else if (command.equalsIgnoreCase("update")) {
				if (action != null) {
					result = new Update().update(path, config, (OperationAction)action);
				} else {
					result = new Update().update(path, config, value);
				}
			} else if (command.equalsIgnoreCase("travel")) {
				if (action != null) {
					result = new Traversal().traversal(config, (TraversalAction)action);
				}
			}
		}
		return result;
	}

}
