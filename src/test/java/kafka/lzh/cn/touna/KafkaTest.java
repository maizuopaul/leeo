package kafka.lzh.cn.touna;


public class KafkaTest {
	
	public static void main(String[] args) {
		testConsumer();
	}
	
	public static void testConsumer(){
		KafkaConsumer<String,String> consumer = new KafkaConsumer<String,String>(new KafkaBusinessImpl<Object, String, String>());
		Thread thread = new Thread(consumer);
		thread.start();
	}
}
