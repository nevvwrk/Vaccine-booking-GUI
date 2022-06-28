package Booking;

public class Pepole {
	private String pid;
    private String name;
	private String age;
    private String sex;
	private String addr;
    private String phone;
    
    public Pepole(String pid,String name,String age,String sex,String addr,String phone)
    {
    	this.pid = pid;
    	this.name = name;
    	this.age = age;
    	this.sex = sex;
    	this.addr = addr;
    	this.phone = phone;
    }
	public String getPID() {
        return this.pid;
    }
	public String getName() {
        return this.name;
    }
	public String getAge() {
        return this.age;
    }
	public String getSex() {
        return this.sex;
    }
	public String getAddr() {
        return this.addr;
    }
	public String getPhone() {
        return this.phone;
    }
}
