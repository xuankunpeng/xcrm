package com.xcrm.system.user.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.xcrm.system.user.dao.IUserDao;
import com.xcrm.system.user.entity.User;
import com.xcrm.system.user.utils.SQLHelper;

public class UserDaoImpl implements IUserDao{

	@Override
	public User findById(Object o) throws Exception {
		String strSQL="SELECT id,username,password,age,gender FROM user";
		if(o!=null && o.toString()!=""){
			strSQL+=MessageFormat.format(" WHERE id=''{0}''",o.toString());
			System.out.println("===="+strSQL);
			return getUser(SQLHelper.executeQuery(strSQL,null));
		}else
			return null;
	}
	/**
	 * 读取数据库数据,获取单个用户
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private User getUser(ResultSet rs) throws SQLException{
		User user=null;
		if(rs.next()){
			user=new User();
			user.setId(rs.getInt(1));
			user.setUsername(rs.getString(2));
			user.setPassword(rs.getString(3));
			user.setAge(rs.getInt(4));
			user.setGender(rs.getString(5));
		}
		//关闭连接
		SQLHelper.close(rs, SQLHelper.getStatement(), SQLHelper.getCon());
		return user;
	}
	
	@Override
	public List<User> list() throws Exception {
		List<User> userList=new ArrayList<User>();
		User user=null;
		ResultSet rs=SQLHelper.executeQuery("select * from user", null);
		while(rs.next()){
			user=new User();
			user.setId(rs.getInt("id"));
			user.setUsername(rs.getString("username"));
			user.setPassword(rs.getString("password"));
			user.setAge(rs.getInt("age"));
			user.setGender(rs.getString("gender"));
			userList.add(user);
		}
		//关闭连接
		SQLHelper.close(rs, SQLHelper.getStatement(), SQLHelper.getCon());
		return userList;
	}

	@Override
	public List<User> list(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int add(User user) throws Exception {
		StringBuffer strAdd=new StringBuffer("INSERT INTO user(");
		StringBuffer strVal=new StringBuffer(") VALUES(");
		if(user.getUsername()!=null){
			strAdd.append("username,");
			strVal.append("'"+user.getUsername()+"',");
		}
		if(user.getPassword()!=null){
			strAdd.append("password,");
			strVal.append("'"+user.getPassword()+"',");
		}
		
		if(user.getAge()>0){		
			strAdd.append("age,");
			strVal.append(""+user.getAge()+",");
		}
		
		if(user.getGender()!=null){		
			strAdd.append("gender,");
			strVal.append("'"+user.getGender()+"',");
		}
		
		String strSQL=strAdd.toString();
		String _strVal=strVal.toString();
		int returnVal=-1;
		if(!strSQL.toLowerCase().endsWith("user(")){
			strSQL=strSQL.substring(0,strSQL.length()-1)+_strVal.substring(0,_strVal.length()-1)+")";
			returnVal=SQLHelper.executeDML(strSQL, null);
		}
		return returnVal;
	}

	@Override
	public int delete(Object o) throws Exception {
		return SQLHelper.executeDML("DELETE FROM user WHERE id=?", new String[]{o.toString()});
	}

	@Override
	public int update(User user) throws Exception {
		StringBuffer strSQL=new StringBuffer("UPDATE user SET ");
		if(user.getUsername()!=null)
			strSQL.append("username='"+user.getUsername()+"',");
		if(user.getPassword()!=null)
			strSQL.append("password='"+user.getPassword()+"',");
		if(user.getAge()>0)
			strSQL.append("age="+user.getAge()+",");
		if(user.getGender()!=null)
			strSQL.append("gender='"+user.getGender()+"',");
		
		String strUpdate=strSQL.toString();
		int returnVal=-1;
		if(!strUpdate.toLowerCase().endsWith("set ")){
			strUpdate=strUpdate.substring(0, strUpdate.length()-1)
					+" where id='"+user.getId()+"'";
			returnVal=SQLHelper.executeDML(strUpdate, null);
		}
		return returnVal;
	}

}
