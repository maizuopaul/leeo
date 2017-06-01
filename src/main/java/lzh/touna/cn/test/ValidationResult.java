package lzh.touna.cn.test;

import java.util.Map;
import java.util.Set;

/**
 * 验证结果bean
 * @author leeo
 *
 */
public class ValidationResult {
	/**
	 * 校验结果是否有错
	 */
	private boolean hasErrors;
		
	/**
	 * 校验错误信息
	 */
	private Map<String,String> errorMsg;

	public boolean hasErrors() {
		return hasErrors;
	}

	public void setHasErrors(boolean hasErrors) {
		this.hasErrors = hasErrors;
	}


	public void setErrorMsg(Map<String, String> errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public String getErrorMsg(){
		if(null == errorMsg || errorMsg.isEmpty()){
			return "";
		}
		StringBuffer msg = new StringBuffer();
		Set<String> keySet = errorMsg.keySet();
		
		for(String key : keySet){
			msg.append(key).append(errorMsg.get(key)).append(",");
		}
		return msg.substring(0, msg.length()-1);
	}
		
	@Override
	public String toString() {
		return "ValidationResult [hasErrors=" + hasErrors + ", errorMsg="
				+ getErrorMsg() + "]";
	}
	
}
