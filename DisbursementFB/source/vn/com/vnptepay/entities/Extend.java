package vn.com.vnptepay.entities;

public class Extend {
	String Phone;
	String Email;
	String Address;
	String CustomerId;
	
	public Extend(String phone, String email, String address, String customerId) {
		super();
		Phone = phone;
		Email = email;
		Address = address;
		CustomerId = customerId;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		this.Phone = phone;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		this.Email = email;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		this.Address = address;
	}

	public String getId() {
		return CustomerId;
	}

	public void setId(String id) {
		this.CustomerId = id;
	}
}
