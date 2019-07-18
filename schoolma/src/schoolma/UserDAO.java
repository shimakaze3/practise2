package schoolma;

import java.util.ArrayList;

import schoolma.UserVO;

/**
 * 文件或数据库的读写操作
 * @author Administrator
 *
 */
public class UserDAO {
	
	
	private static ArrayList<UserVO> userlist;
	
	public UserDAO()
	{
	    userlist=new ArrayList<>();
	    userlist.add(new UserVO("a1","张三","1111"));
	    userlist.add(new UserVO("a2","李四","2222"));	    
	}
	
	
	public UserVO CheckExist(UserVO vo)
	{
		for(UserVO uvo:userlist)
		{
			if(uvo.getUserId().equals(vo.getUserId()))
			{
				return uvo;
			}
		}
		return null;
	}
	
	public void UpdateUser(UserVO vo)
	{
	     for(UserVO uvo:userlist)
	     {
	    	 if(uvo.getUserId().equals(vo.getUserId()))
	    	 {
	    		 uvo.setLastLogin(vo.getLastLogin());
	    	 }
	     }
	}
	
	

}
