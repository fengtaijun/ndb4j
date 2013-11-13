package org.pinae.ndb.operate;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;


/**
 * ndb 定位器
 * 
 * @author Huiyugeng
 *
 *
 */
public abstract class Locator {
	/**
	 * 定位对象
	 * 
	 * 查询语句表示方法为：A->B->C:Value
	 * 如果首次开始，A为当前项，B->C:Value为子查询，如果当前项=子查询，则证明已经进入最后一个查询
	 * 
	 * @param query 查询语句
	 * 
	 * @param ndb 需要查询的ndb对象
	 * 
	 */
	protected void locate(String query, Object ndb){
		
		if(query == null || query.equals("")){
			return;
		}
		
		String queryKey = query; //当前项
		String subQuery = query; //子查询
		
		if(ndb instanceof List){
			List configList = (List)ndb;
			for(Object configItem : configList){
				locate(subQuery, configItem);
			}
			
		}else if(ndb instanceof Map){
			if(query.indexOf("->") > 0){
				queryKey = StringUtils.substringBefore(query, "->").trim();
				subQuery = StringUtils.substringAfter(query, "->").trim();
			}
			
			Map<String, Object> ndbMap = (Map<String, Object>)ndb;
			
			if(! subQuery.equals(queryKey)){
				locate(subQuery, ndbMap.get(queryKey));
			}else{
				if(queryKey.indexOf(":") > 0){ //根据值进行查询
					String items[] = queryKey.split(":");
					if(items.length == 2){
						String key = items[0];
						
						Object value = ndbMap.get(key);
						if(items[1].startsWith("/") && items[1].endsWith("/")){ //正则表达式判断
							String regex = items[1].substring(1, items[1].length()-1);
							if(value!=null && value instanceof String && ((String)value).matches(regex)){ // 正则表达式匹配
								doAction(ndbMap, key);
							}
						}else if(items[1].startsWith("[") && items[1].endsWith("]") && items[1].indexOf(",") > 0){ //值域判断
							String region[] = items[1].substring(1, items[1].length()-1).split(",");
							if(value !=null && value instanceof String && region.length == 2){
								try{
									int min = Integer.parseInt(region[0]); //值域中最小值
									int max = Integer.parseInt(region[1]); //值域中最大值
									int intValue = Integer.parseInt((String)value);
									if(intValue >= min && intValue <= max){ //值域匹配
										doAction(ndbMap, key);
									}
								}catch(NumberFormatException e){
									
								}
							}
						}else{
							if(value != null && value.equals(items[1])){
								doAction(ndbMap, key);
							}
						}
					}
				}else{
					Object result = ndbMap.get(queryKey);
					if (result instanceof List) {
						List list = (List)result;
						for (Object item : list){
							doAction(item, null);
						}
					}else{
						doAction(result, null);
					}
					
				}
			}
		}
		
	}
	
	protected abstract void doAction(Object item, String key);
	
}
