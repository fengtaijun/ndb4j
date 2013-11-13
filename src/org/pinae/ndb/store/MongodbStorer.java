package org.pinae.ndb.store;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;

/**
 * ndb存储器（基于MongoDB）
 * 
 * @author Huiyugeng
 *
 *
 */
public class MongodbStorer {
	private static Logger logger = Logger.getLogger(MongodbStorer.class.getName());
	
	private Mongo mongodbAdapter; //Mongodb适配器

	private DB db; //Mongodb的数据库
	
	private DBCollection collection; //Mongodb的数据集合
	
	public MongodbStorer(MongodbConfig config){
		int port = config.getPort();
		String host = config.getServer();
		
		try {
			mongodbAdapter = new Mongo(host, port);
		} catch (UnknownHostException e) {
			logger.error("Exceprion:" + e.getMessage());
		} catch (MongoException e) {
			logger.error("Exceprion:" + e.getMessage());
		}
		
		String database = config.getDatabase();
		if(database!=null && !database.equals("")){
			db = mongodbAdapter.getDB(database);
		}else{
			logger.error("Database is NULL.");
		}
		
		String user = config.getUser();
		String password = config.getPassword();
		if(user!=null && password!=null){
			boolean auth = db.authenticate(user, password.toCharArray());
			if(!auth){
				logger.error("Authenticate fail.");
			}
		}
		
		String collectionName = config.getCollection();
		if(collectionName!=null && !collectionName.equals("")){
			collection = db.getCollection(collectionName);
		}else{
			logger.error("Collection is NULL.");
		}
	}
	
	/**
	 * 配置存储至MongoDB
	 * 
	 * @param ndb 配置信息
	 * 
	 * @return 保存结果
	 */
	public int save(Map<String, Object> ndb){
		BasicDBObject dbObject = new BasicDBObject(ndb);
		WriteResult result = collection.save(dbObject);
		return result.getN();
	}
	
	/**
	 * 从MongoDB中读取配置列表
	 * 
	 * @param field 检索的字段
	 * @param key 检索字段的值
	 * 
	 * @return 配置信息列表
	 */
	public List<Object> findList(String field, String key) {	
		List<Object> value = new ArrayList<Object>();
		DBCursor cursor = collection.find(new BasicDBObject(field, key));
		while(cursor.hasNext()){
			value.add(cursor.next().toMap());
		}
		return value;
	}
	
	/**
	 * 从MongoDB中读取配置
	 * 
	 * @param field 检索的字段
	 * @param key 检索字段的值
	 * 
	 * @return 配置信息
	 */
	public Object findOne(String field, String key) {	
		List<Object> value = new ArrayList<Object>();
		DBObject dboject = collection.findOne(new BasicDBObject(field, key));
		return dboject.toMap();
	}
	
}
