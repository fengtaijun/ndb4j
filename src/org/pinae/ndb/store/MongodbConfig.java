package org.pinae.ndb.store;


/**
 * Mongodb信息配置信息
 * 
 * @author Huiyugeng
 *
 */
public class MongodbConfig {
	private String server = "localhost"; //Mongodb服务器地址
	
	private int port = 27017; //Mongodb服务器端口
	
	private String user; //Mongodb认证用户
	
	private String password; //Mongodb认证密码
	
	private String database = "fimas"; //数据库
	
	private String collection = "firewall"; //数据集合
	
	
	/**
	 * 构造函数
	 */
	public MongodbConfig(){
		super();
	}
	
	/**
	 * 返回Mongodb服务器地址
	 * 
	 * @return Mongodb服务器地址
	 */
	public String getServer() {
		return server;
	}
	
	/**
	 * 设置Mongodb服务器地址
	 * 
	 * @param server Mongodb服务器地址
	 */
	public void setServer(String server) {
		this.server = server;
	}
	
	/**
	 * 返回Mongodb服务器端口
	 * 
	 * @return Mongodb服务器端口
	 */
	public int getPort() {
		return port;
	}
	
	/**
	 * 设置Mongodb服务器端口
	 * 
	 * @param port Mongodb服务器端口
	 */
	public void setPort(int port) {
		this.port = port;
	}
	
	/**
	 * 返回Mongodb数据库
	 * 
	 * @return Mongodb数据库
	 */
	public String getDatabase() {
		return database;
	}

	/**
	 * 设置Mongodb数据库
	 * 
	 * @param database Mongodb数据库
	 */
	public void setDatabase(String database) {
		this.database = database;
	}

	/**
	 * 返回数据库用户名
	 * 
	 * @return 数据库用户名
	 */
	public String getUser() {
		return user;
	}

	/**
	 * 设置数据库用户名
	 * 
	 * @param user 数据库用户名
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * 返回数据库密码
	 * 
	 * @return 数据库密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置数据库密码
	 * 
	 * @param password 数据库密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 返回数据集合
	 * 
	 * @return 数据集合
	 */
	public String getCollection() {
		return collection;
	}

	/**
	 * 设置数据集合
	 * 
	 * @param collection 数据集合
	 */
	public void setCollection(String collection) {
		this.collection = collection;
	}

}
