package kafka.lzh.cn.touna;

import java.util.Iterator;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;


public class KafkaConsumerTest {
	
	public static void main(String[] args) {
		testConsumer();
	}
	
	public static void testConsumer(){
		KafkaConsumer<String,String> consumer = new KafkaConsumer<String,String>(new KafkaConsumerService<Object, String, String>() {
			@Override
			public Object dealConsumer(
					org.apache.kafka.clients.consumer.KafkaConsumer<String, String> consumer) {
				try{
					while(true){
						ConsumerRecords<String, String> poll = consumer.poll(100);
						Iterator<ConsumerRecord<String, String>> iterator = poll.iterator();
						while(iterator.hasNext()){
							ConsumerRecord<String, String> next = iterator.next();
							System.out.println(next.key() + "="+next.value());
						}
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}finally{
					if(null != consumer){
						consumer.close();
					}
				}
				return null;
			}
		});
		Thread thread = new Thread(consumer);
		thread.start();
	}
}
