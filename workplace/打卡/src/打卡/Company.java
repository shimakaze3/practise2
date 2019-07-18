package 打卡;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Company {
	// 存放员工的容器
	public List<Employee> list = new ArrayList<Employee>();
	// 存放考勤日期及打卡信息的容器
	public Map<String,List<DakaInfo>> map = new HashMap<String,List<DakaInfo>>();
	
	// 新增员工
	public void addEmp(Employee e){
		list.add(e);
		System.out.println("添加信息成功");
	}
	// 离职员工
	public void delEmp(int id){
		int index = -1;
		int i = 0;
		for(Employee e:list){
			index = i;
			if(e.getId() == id){
				list.remove(index);
				break;
			}
			i++;
		}
	}
	
	// 查找员工
	public Employee findEmp(int id){
		for(int i=0;i<list.size();i++) {
			if(id == list.get(i).getId()) {
				return list.get(i);
			}
		}
		return null;
	}
	
	// 显示员工信息
	public void printEmp(){
		for(Employee e:list){
			System.out.println(e);
		}
	}
}
