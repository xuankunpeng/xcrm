package com.xcrm.system.user.servlet;

import javax.servlet.http.HttpServlet;

public class AddServlet extends HttpServlet {/*

	*//**
	 * 
	 *//*
	private static final long serialVersionUID = 1L;

	 //doPost����
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		    request.setCharacterEncoding("UTF-8");
   		    response.setCharacterEncoding("UTF-8");
			String methodName=request.getParameter("methodName");
			int method=Integer.parseInt(methodName);
		try {  
			switch(method)
		       {
		    	case 0:
					insert(request,response);
		        case 1:
                    difpage(request,response);
			        break;    
		    	case 2:
  				    delete(request,response);
  			        break;       
		        case 3:
  				    update(request,response);
  				    break;
		        case 4:
		        	update1(request,response);
			        break;
		        case 5:
		        	dispatch(request,response);
			        break;
		       }
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	}
	//doGet����
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
       doPost(request,response);
    }
	
	
    //���ݿ����ӷ���
	public Connection connect() throws ClassNotFoundException, SQLException{
    	Connection conn=null; 
	    Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://192.168.1.189:13306/test?useUnicode=true&amp;characterEncoding=UTF-8"; 
	    String user="root"; 
		String password="HanTian2014"; 
		conn=DriverManager.getConnection(url,user,password); 
		return conn;
	}
	//�ر����ݿ���Դ
	public void close(Statement stat,Connection conn) throws SQLException{
		if(stat!=null){
	    	   stat.close();
	    }
	    if(conn!=null){
	    	   conn.close();
	    }
	}
	//���뷽��
	public void insert(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException{
    	Connection conn=null;
    	Statement stat=null;
		String id=request.getParameter("id");
        String name=request.getParameter("name");
        String age=request.getParameter("age");
        String gender=request.getParameter("gender");
        String major=request.getParameter("major");
		conn=connect();
		stat=conn.createStatement();
    	stat.execute("insert into student(id,name,age,gender,major) values("+id+",'"+name+"',"+age+",'"+gender+"','"+major+"')"); 
    	close(stat,conn);
    }
    //��ѯ����
    public ArrayList<StudentInfo> select(String id,String name) throws ClassNotFoundException, SQLException{
    	Connection conn=null;
    	Statement stat=null;
	    ResultSet rs=null;
	    conn=connect();
		stat=conn.createStatement();
    	ArrayList<StudentInfo> result=new ArrayList<StudentInfo>();
    	if(id==""&&name==""){
    	     rs=stat.executeQuery("select * from student"); 
    	}
    	if(id!=""&&name==""){
   	        rs=stat.executeQuery("select * from student where id="+id+""); 
     	}
    	if(id==""&&name!=""){
   	        rs=stat.executeQuery("select * from student where name='"+name+"'"); 
   	    }
    	if(id!=""&&name!=""){
      	    rs=stat.executeQuery("select * from student where id="+id+" and name='"+name+"'"); 
      	}
    	while(rs.next())
        {
        	StudentInfo st=new StudentInfo();
        	st.setId(rs.getInt("id"));
        	st.setName(rs.getString("name"));
        	st.setAge(rs.getInt("age"));
        	st.setGender(rs.getString("gender"));
        	st.setMajor(rs.getString("major")); 
        	result.add(st);	
        }
	    if(rs!=null){
	    	  rs.close();
	       }
	    close(stat,conn);
    	return result;
    }
    //������ѯ��ת
    public void dispatch(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException{
    	String id5=request.getParameter("id");
    	String name5=request.getParameter("name");  
     if(select(id5,name5).isEmpty()){
        	request.getRequestDispatcher("../selectnothing.jsp").forward(request, response);
        }
       else{
    		request.setAttribute("result", select(id5,name5));
            request.getRequestDispatcher("../idnameselect.jsp").forward(request, response);	
        }
    }
    //���÷�ҳ��ز�������
	public Page setpage(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException{
		String crd=request.getParameter("currentRecord");
		//String id=request.getParameter("id");
      //  String name=request.getParameter("name");
    	ArrayList<StudentInfo> result=select("","");
    	Page pager=new Page();
    	pager.setTotalRecord(result.size()); 
    	pager.setTotalPage(result.size(),pager.getPageSize());
    	if(crd!=null)
        {
    		int currentRecord=Integer.parseInt(crd);
            pager.setCurrentRecord(currentRecord);
            pager.setCurrentPage(currentRecord,pager.getPageSize());
        }
    	return pager;
	}
	//��÷�ҳ��ʾ���Ӽ�
	 public void difpage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException{
		// String id=request.getParameter("id");
	 //    String name=request.getParameter("name");
		 ArrayList<StudentInfo> result=select("","");
		 Page pager=new Page();
		 pager=setpage(request,response);
  	     List<StudentInfo> subResult=null;
  	     int currentRecord=pager.getCurrentRecord();
         if(currentRecord==0){
         	if(pager.getTotalRecord()<8){
         		subResult=(List<StudentInfo>) result.subList(0,pager.getTotalRecord());
         	}
         	else{
         		subResult=(List<StudentInfo>) result.subList(0,pager.getPageSize());
         	}         
         }
         else if(pager.getCurrentRecord()+pager.getPageSize()<result.size())
         {
               subResult=(List<StudentInfo>) result.subList(pager.getCurrentRecord(),pager.getCurrentRecord()+pager.getPageSize());
         }
         else
         {
              subResult=(List<StudentInfo>) result.subList(pager.getCurrentRecord(),result.size());
         }
         request.setAttribute("pager", pager);
	     request.setAttribute("subResult", subResult);
		 request.getRequestDispatcher("../layout.jsp").forward(request, response);
     }
    //��Ϣɾ������
    public void delete(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException{
    	Connection conn=null;
    	Statement stat=null;
    	conn=connect();
 		stat=conn.createStatement();
 		String id2=request.getParameter("id");
		stat.execute("delete from student where id="+id2+"");
		request.getRequestDispatcher("../delete.jsp").forward(request, response);
    } 
    //��Ϣ�޸ķ���
    public void update1(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException{
    	String id4=request.getParameter("id");
	    request.setAttribute("result", select(id4,""));
        request.getRequestDispatcher("../update1.jsp").forward(request, response);
    }   
    public void update(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException{
    	Connection conn=null;
    	Statement stat=null;
        String id3=request.getParameter("id");
        String name3=request.getParameter("name");
        String age3=request.getParameter("age");
        String gender3=request.getParameter("gender");
        String major3=request.getParameter("major");
    	conn=connect();
 		stat=conn.createStatement();
		stat.execute("update student set id="+id3+",name='"+name3+"',age="+age3+",gender='"+gender3+"',major='"+major3+"' where id="+id3+"");
		request.setAttribute("result", select(id3,""));    
	    request.getRequestDispatcher("../update.jsp").forward(request, response); 
    } 
   
*/}
