package ��;

/**
 * Ա����
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
		return "���ţ� "+this.id+" ������ "+this.name;
	}

}
