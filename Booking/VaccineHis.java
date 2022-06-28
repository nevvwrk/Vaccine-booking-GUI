package Booking;

public class VaccineHis {
	private String lastDate;
    private String needle;
    private String location;
    private String addre;
    private String sex;
    public VaccineHis(String lastDate,String needle,String location,String addre,String sex) {
    	this.lastDate = lastDate;
    	this.needle = needle;
    	this.location = location;
    	this.addre = addre;
    	this.sex = sex;
    }
	public String getLastDate() {
        return this.lastDate;
    }
	public String getNeedle() {
        return this.needle;
    }
	public String getLocation() {
        return this.location;
    }
	public String getAddre() {
        return this.addre;
    }
	public String getSex() {
        return this.sex;
    }
}
