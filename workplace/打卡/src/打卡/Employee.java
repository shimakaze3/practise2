package 打卡;

/**
 * 员工类
 * @author ttc
 *
 */
public class Employee {


	private int id;
	private String name;
	
	public Employee(){
		
	}

	public Employee(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "工号： "+this.id+" 姓名： "+this.name;
	}

}
