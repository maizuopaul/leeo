package kafka.lzh.cn.touna;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class KafkaConsumer<K,V> implements Runnable{
	private final static Logger	logger	= LoggerFactory.getLogger(KafkaConsumer.class);
	private boolean poll = true;
	private KafkaConsumerService<?,K,V> kafkaBusiness;
	private String DEFAULTTOPICS = "DEFAULTTOPICS";	//默认的topics
	private final Properties properties = new Properties();
	
	public KafkaConsumer(KafkaConsumerService<?,K,V> kafkaBusiness){
		this.kafkaBusiness = kafkaBusiness;
		init();
	}
	
	private void init(){
		String conf ="/kafka-consumer.properties";
		InputStream inputStream = null;
		logger.info("begin to init the kafka consumer configuration...");
		try {
			inputStream =  KafkaConsumer.class.getResourceAsStream(conf);
		} catch (Exception e) {
			inputStream = KafkaConsumer.class.getResourceAsStream("/conf/kafka-consumer.properties");
			logger.error("error",e);
			throw new RuntimeException("init the kafka consumer confituration error!",e);
		}
		try {
			properties.load(inputStream);
			if(!properties.containsKey("topics")){
				throw new RuntimeException("init the kafka consumer confituration error! don't contains the topics");
			}
			logger.info("finished to init the kafka consumer configuration...");
		} catch (IOException e) {
			logger.error("error",e);
			throw new RuntimeException("init the kafka consumer confituration error!",e);
		}
	}
	
	
	public boolean isPoll() {
		return poll;
	}

	public void setPoll(boolean poll) {
		this.poll = poll;
	}

	private org.apache.kafka.clients.consumer.KafkaConsumer<K, V> createConsumer(){
		org.apache.kafka.clients.consumer.KafkaConsumer<K, V> consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<K, V>(properties);
		Collection<String> coll = Arrays.asList(properties.getProperty("topics", DEFAULTTOPICS ));
		consumer.subscribe(coll);
		return consumer;
	}
	
	public void run() {
		org.apache.kafka.clients.consumer.KafkaConsumer<K, V> consumer = this.createConsumer();
		kafkaBusiness.dealConsumer(consumer);
	}
}
