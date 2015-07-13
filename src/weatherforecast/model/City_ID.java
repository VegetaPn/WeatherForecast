package weatherforecast.model;
/*
 *城市对应的实体类 */

public class City_ID {
private int id;
private String nameen;
private String namecn;
private String districten;
private String districtcn;
private String proven;
public City_ID(int id, String nameen, String namecn, String districten,
		String districtcn, String proven, String provcn) {
	super();
	this.id = id;
	this.nameen = nameen;
	this.namecn = namecn;
	this.districten = districten;
	this.districtcn = districtcn;
	this.proven = proven;
	this.provcn = provcn;
}
private String provcn;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getNameen() {
	return nameen;
}
public void setNameen(String nameen) {
	this.nameen = nameen;
}
public String getNamecn() {
	return namecn;
}
public void setNamecn(String namecn) {
	this.namecn = namecn;
}
public String getDistricten() {
	return districten;
}
public void setDistricten(String districten) {
	this.districten = districten;
}
public String getDistrictcn() {
	return districtcn;
}
public void setDistrictcn(String districtcn) {
	this.districtcn = districtcn;
}
public String getProven() {
	return proven;
}
public void setProven(String proven) {
	this.proven = proven;
}
public String getProvcn() {
	return provcn;
}
public void setProvcn(String provcn) {
	this.provcn = provcn;
}

}
