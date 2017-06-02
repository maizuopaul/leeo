package kafka.lzh.cn.touna;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class KafkaProducerTest {
	
	@Test
	public void testSend(){
		KafkaProducer<String, String> producer = new KafkaProducer<String, String>();
		List<String> keys = new ArrayList<String>();
		List<String> values = new ArrayList<String>();
		for(int i =0; i<100;i++){
			keys.add(i+"");
			values.add(i+"a");
		}
		producer.sendMsg("lizehua_test", "fff", "cffff");
		producer.sendMsg("lizehua_test", values);
		producer.sendMsg("lizehua_test", keys, values);
	}
}
