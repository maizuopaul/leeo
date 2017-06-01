package lzh.touna.cn.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

/**
 * java bean 验证器工具类
 * @author leeo
 *
 */
public class ValidationUtils {
private static Validator validator =  Validation.buildDefaultValidatorFactory().getValidator();
	
	/**
	 * 对待校验对象obj进行校验. 当待校验的对象未通过hibernate-validator验证时,hasErrors属性为true,错误信息会封装在ValidationResult对象中的errorMsg中;否则hasErrors为false,
	 * @param obj 待校验对象
	 * @return 校验后的结果对象@see ValidationResult
	 */
	public static <T> ValidationResult validateEntity(T obj){
		ValidationResult result = new ValidationResult();
		 Set<ConstraintViolation<T>> set = validator.validate(obj,Default.class);
		 if(null != set && !set.isEmpty() ){
			 result.setHasErrors(true);
			 Map<String,String> errorMsg = new HashMap<String,String>();
			 for(ConstraintViolation<T> cv : set){
				 errorMsg.put(cv.getPropertyPath().toString(), cv.getMessage());
			 }
			 result.setErrorMsg(errorMsg);
		 }else{
			 result.setHasErrors(false);
		 }
		 return result;
	}
	
	/**
	 * 校验java bean并抛出异常.当待校验的对象未通过hibernate-validator验证时,会抛出包含有校验的错误信息运行时异常
	 * @param obj 待校验对象
	 */
	public static <T>  void validateEntityThrowable(T obj){
		Set<ConstraintViolation<T>> set = validator.validate(obj,Default.class);
		if(null != set && !set.isEmpty() ){
			StringBuffer errorMsg = new StringBuffer();
			for(ConstraintViolation<T> cv : set){
				errorMsg.append(",").append(cv.getPropertyPath().toString()).append(cv.getMessage());
			}
			throw new RuntimeException(errorMsg.substring(1));
		}
	}
	
	
	/**
	 * 对待校验的对象 属性进行校验
	 * @param obj 带校验对象
	 * @param propertyName 带校验属性
	 * @return 校验后的结果
	 */
	public static <T> ValidationResult validateProperty(T obj,String propertyName){
		ValidationResult result = new ValidationResult();
		 Set<ConstraintViolation<T>> set = validator.validateProperty(obj,propertyName,Default.class);
		 if(null != set && !set.isEmpty() ){
			 result.setHasErrors(true);
			 Map<String,String> errorMsg = new HashMap<String,String>();
			 for(ConstraintViolation<T> cv : set){
				 errorMsg.put(propertyName, cv.getMessage());
			 }
			 result.setErrorMsg(errorMsg);
		 }
		 return result;
	}
	
	/**校验java bean并抛出异常
	 * 对待校验的对象属性进行校验. 当待校验的对象属性未通过hibernate-validator验证时,会抛出包含有校验的错误信息运行时异常
	 * @param obj 带校验对象
	 * @param propertyName 带校验属性
	 * @return 校验后的结果
	 */
	public static <T> void validatePropertyThrowable(T obj,String propertyName){
		Set<ConstraintViolation<T>> set = validator.validateProperty(obj,propertyName,Default.class);
		if(null != set && !set.isEmpty() ){
			StringBuffer errorMsg = new StringBuffer();
			for(ConstraintViolation<T> cv : set){
				errorMsg.append(",").append(propertyName).append(cv.getMessage());
			}
			throw new RuntimeException(errorMsg.substring(1));
		}
	}
}
