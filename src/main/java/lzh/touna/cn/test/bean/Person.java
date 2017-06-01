package lzh.touna.cn.test.bean;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

public class Person {
	
	//不能为空,纯空格组成的也是blank
	@NotBlank(message="不能为blank")
	private String username;
	
	//数字范围
	@Range(min=20,max=130)
	private int age;
	
	//不能为空,空格符不算empty
	@NotEmpty(message="不能为empty")
	private String address;
	
	//字符串长度介于3-5
	@Length(min=3,max=5)
	private String enjoy;
	
	//正则表达式,四个数字开头+3个字符+cat字符串
	@Pattern(regexp="^\\d{4}\\w{3}cat")
	@NotNull
	private String pattern;
	
	//小数部分不大于2位,整数部分不大于3位
	@Digits(integer=3,fraction=2)
	
	private String number;
	
	@Email(message="不符合邮件地址格式")
	private String email;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEnjoy() {
		return enjoy;
	}
	public void setEnjoy(String enjoy) {
		this.enjoy = enjoy;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
}
