package biz;


import java.util.ArrayList;
import java.util.List;
import com.yc.dao.DBHelper;
import bean.User;

public class UserBiz {
	
	
	
	public User login(String username,String userpwd) throws BizException   {
		 if(username == null || username.trim().isEmpty()) {
			 throw new BizException("����д�û�����");
		 }
		 if(userpwd == null || userpwd.trim().isEmpty()) {
			 throw new BizException("����д����!");
		 }
		String sql= "select *from user where account =? and pwd=?";
		return DBHelper.unique(sql, User.class,username,userpwd);
	}

	public List<User> findAll() {
		return DBHelper.select("select * from user",User. class);
	}

	public void add(User user) throws BizException {
		if(user.getName() ==null || user.getName().trim().isEmpty()) {
			throw new BizException("����д�û���");
		}
		if(user.getPwd() ==null || user.getPwd().trim().isEmpty()) {
			throw new BizException("����д����");
		}
		
		//��ʽ��Ϣ�ж�
		
	
		
		String sql = "insert into user(name,account,tel,pwd) values(?,?,?,?)";
		DBHelper.insert(sql, user.getName(),user.getAccount(),user.getTel(),user.getPwd());
		
	}

	public Object find(User user) {
		
		String sql ="select * from user where 1=1";
		ArrayList<Object> params =new ArrayList();
		if(user.getName() !=null && user.getName().trim().isEmpty() ==false) {
			sql +=" and name like ?";
			params.add("%" +user.getName()+"%");
		}
		if(user.getAccount() !=null && user.getAccount().trim().isEmpty() ==false) {
			sql +=" and account like concat('%' ,?,'%')";//��MySQLʹ��
			params.add(user.getAccount());
		}
		if(user.getTel() !=null && user.getTel().trim().isEmpty() ==false) {
			sql +=" and tel like ?";
			params.add("%" +user.getTel()+"%");//MySQL��oricleͨ��
		}
		return null;
	}
	public User findById(String id) {
		return DBHelper.unique("select * from user where id=?", User.class,id);
	}
}
