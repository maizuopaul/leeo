package lzh.touna.cn.test;


  
public class Test3 {
	
	public static void main(String[] args) throws Exception{
		/*try{
			while(true){
				System.out.println("while######");
				throw new RuntimeException("runtiome");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			System.out.println("finally......");
		}*/
		getInt();
	}
	
		
	private static int getInt(){
		try{
			System.out.println("before return");
			return 10;
		}finally{
			System.out.println("after finally");
		}
	}

}
