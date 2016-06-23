package com.xcrm.system.user.utils;

import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class SQLHelper {
	private static String DRIVER;

	private static String URL;

	private static String USER;

	private static String PASSWORD;

	private static Connection connection = null; // 得到连接的属性

	private static Statement statement = null;// 得到编译通道的属性

	private static PreparedStatement preparedStatement = null;// 得到预编译通道的属性

	private static CallableStatement callableStatement = null; // 调用存储过程的属性

	private static ResultSet resultSet = null; // 得到结果集的属性

	private static InputStream is = null;

	static {
		try {
			Properties properties = new Properties();
			is = SQLHelper.class.getResourceAsStream("/dbInfo.properties");
			properties.load(is);
			DRIVER = properties.getProperty("DRIVER");
			URL = properties.getProperty("URL");
			USER = properties.getProperty("USER");
			PASSWORD = properties.getProperty("PASSWORD");
			Class.forName(DRIVER);
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		} finally {
			try {
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (Exception ex2) {
				ex2.printStackTrace();
				throw new RuntimeException(ex2.getMessage());
			}
		}
	}
	
	/*
	 * 返回与数据库连接的方法
	 */
	public static Connection getConnection() throws Exception{
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return connection;
	}

	/*
	 * 这是一个查询的方法
	 * @String sql 要执行的sql语句
	 * @String[] parameters 要执行的参数列表
	 *   先得到连接 connection
	 *   创建预编译通道 preparedStatement 它本身是一个接口，它指向了它的实现类
	 *   当传进来的参数不为空时，才将参数parameters 放入预编译通道中
	 *   最后将执行到的结果放入/返回结果集ResultSet。
	 *   最后关闭资源的方法应该在调用这个方法的时候执行
	 */
	public static ResultSet executeQuery(String sql,String[] parameters) throws Exception{
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			preparedStatement = connection.prepareStatement(sql);
			//根据实际情况对sql语句赋值
			if(parameters != null&&!"".equals(parameters)){
				for (int i = 0; i < parameters.length; i++) {
					preparedStatement.setString(i+1, parameters[i]);
				}
			}
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}finally{
			//close(resultSet,preparedStatement,connection);
		}
		return resultSet;
	}

	
	/*
	 * 统一关闭资源的一个方法
	 * 这里用Statement 不用PreparedStatement 
	 *    因为PreparedStatement子接口继承Statement接口，Statement可以关闭通道，范围包括PreparedStatement
	 *    最后 将三个参数置null， 显示的垃圾回收
	 */
	public static void close(ResultSet resultSet,Statement statement,Connection connection){
		
		try {
			if(resultSet!=null){
				resultSet.close();
				resultSet = null;
			}
			if(statement!=null){
				statement.close();
				statement = null;
			}
			if(connection!=null){
				connection.close();
				connection=null;
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 这是一个统一的增删改(insert,update,delete)的方法
	 * DML语句调用的是executeUpdate()方法
	 * 如果没有参数传递，可以传null值
	 */
	public static int executeDML(String sql,String[] parameters) throws Exception{
		
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			preparedStatement = connection.prepareStatement(sql);
			if(parameters != null&&!"".equals(parameters)){
				for (int i = 0; i < parameters.length; i++) {
					preparedStatement.setString(i+1, parameters[i]);
				}
			}
			return preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}finally{
			close(resultSet,preparedStatement,connection);
		}
	}

	/*
	 * 这是在第一个方法的基础上增加对事务的控制
	 *  多个事务的提交与回滚，保持事务的一致性
	 *    1、得到连接后将自动提交设为false
	 *    2、循环的对sql数组进行创建预编译通道
	 *    3、根据参数数组进行设置
	 *    4、最后执行每条语句
	 *    5、进行统一提交，如果有异常，就统一回滚
	 */
	public static void executeDML2(String[] sql,String[][] parameters) throws Exception{
		
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			connection.setAutoCommit(false);
			
			if(sql != null && !"".equals(sql)){
				for(int i=0;i<sql.length;i++){
					preparedStatement = connection.prepareStatement(sql[i]);
				}
				if(parameters != null&&!"".equals(parameters)){
					for (int i = 0; i < parameters.length; i++) {
						preparedStatement.setString(i+1, parameters[i][i]);
					}
				}
			}

			preparedStatement.executeUpdate();
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new Exception(e1.getMessage());
			}
		}finally{
			close(resultSet,preparedStatement,connection);
		}
	}
	
	/*
	 * 统一的提供一个存储过程的方法（无返回值）
	 * CallableStatement 是PreparedStatement的子接口，PreparedStatement 又是statement的子接口
	 */
	public static void executeProcedure(String sql,String[] parameters) throws Exception{
		
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			callableStatement = connection.prepareCall(sql);
			if(parameters != null&&!"".equals(parameters)){
				for (int i = 0; i < parameters.length; i++) {
					callableStatement.setString(i+1, parameters[i]);
				}
			}
			callableStatement.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}finally{
			close(resultSet,callableStatement,connection);
		}
	}
	
	/*
	 * 统一的提供一个存储过程的方法（返回一个resultSet）
	 * CallableStatement 是PreparedStatement的子接口，PreparedStatement 又是statement的子接口
	 */
	public static ResultSet executeProcedure2(String sql,String[] parameters) throws Exception{
		
		try {
			connection = DriverManager.getConnection(URL,USER,PASSWORD);
			callableStatement = connection.prepareCall(sql);
			if(parameters != null&&!"".equals(parameters)){
				for (int i = 0; i < parameters.length; i++) {
					callableStatement.setString(i+1, parameters[i]);
				}
			}
			resultSet = callableStatement.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}finally{
			close(resultSet,callableStatement,connection);
		}
		return resultSet;
	}

	//暴露一组getter方法给调用者
 	public static Connection getCon() {
 		return connection;
 	}

	public static Statement getStatement() {
		return statement;
	}

	public static PreparedStatement getPreparedStatement() {
		return preparedStatement;
	}

	public static ResultSet getResultSet() {
		return resultSet;
	}	

}
