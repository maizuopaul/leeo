package kafka.lzh.cn.touna;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaProducer {
	
	private final static Logger	logger	= LoggerFactory.getLogger(KafkaConsumer.class);
	private static final Properties properties = new Properties();
	public KafkaProducer(){}
	
	static{
		init();
	}
	
	public static void init(){
		String conf ="/kafka-producer.properties";
		InputStream inputStream = null;
		try {
			inputStream =  KafkaConsumer.class.getResourceAsStream(conf);
		} catch (Exception e) {
			inputStream = KafkaConsumer.class.getResourceAsStream("/conf/kafka-producer.properties");
			logger.error("error",e);
		}
		try {
			properties.load(inputStream);
			
		} catch (IOException e) {
			logger.error("error",e);
		}
	}
	
	
	public void sendMsg(String topic,String key,String value){
		if(topic == null)topic=properties.getProperty("topic");
		if(topic == null)return;
		org.apache.kafka.clients.producer.KafkaProducer<String, String> producer = new org.apache.kafka.clients.producer.KafkaProducer<String, String>(properties);
		ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, key, value);
		producer.send(record );
		producer.close();
	}
	
	public static void main(String[] args) {
		KafkaProducer producer = new KafkaProducer();
		producer.sendMsg("TableSingleRowGet", "aa", "bb");
	}
}
