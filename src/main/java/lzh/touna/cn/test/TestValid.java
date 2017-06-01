package lzh.touna.cn.test;

import java.util.Date;

import lzh.touna.cn.test.bean.Person;

public class TestValid {
	public static void main(String[] args) {
		Person p = new Person();
		p.setAge(10);
		p.setUsername(" ");
		p.setEnjoy("123654");
		p.setAddress(" ");
		p.setEmail("adfafa");
		p.setPattern("145ccad");
		p.setNumber("223.523");
		
		
		//测试费抛异常的校验,所有校验信息在errorMsg字段
		ValidationResult validateEntity = ValidationUtils.validateEntity(p);
		System.out.println(validateEntity);
		System.out.println("=================");
		
		//测试单个属性age
		validateEntity = ValidationUtils.validateProperty(p, "age");
		System.out.println(validateEntity);
		System.out.println("-----------------"+ validateEntity.hasErrors() + "\t" +  validateEntity.getErrorMsg());
		
		
		//测试抛异常
		ValidationUtils.validateEntityThrowable(p);
	}
}
