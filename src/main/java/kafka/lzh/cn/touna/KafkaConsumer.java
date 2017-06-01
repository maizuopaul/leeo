package kafka.lzh.cn.touna;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class KafkaConsumer<K,V> implements Runnable{
	private final static Logger	logger	= LoggerFactory.getLogger(KafkaConsumer.class);
	private boolean poll = true;
	private KafkaBusiness<Object,K,V> kafkaBusiness;
	public KafkaConsumer(KafkaBusiness<Object,K,V> kafkaBusiness){
		this.kafkaBusiness = kafkaBusiness;
	}
	
	private static final Properties properties = new Properties();
	static{
		String conf ="/kafka-consumer.properties";
		InputStream inputStream = null;
		try {
			inputStream =  KafkaConsumer.class.getResourceAsStream(conf);
		} catch (Exception e) {
			inputStream = KafkaConsumer.class.getResourceAsStream("/conf/kafka-consumer.properties");
			logger.error("error",e);
		}
		try {
			properties.load(inputStream);
			
		} catch (IOException e) {
			logger.error("error",e);
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
		Collection<String> coll = Arrays.asList(properties.getProperty("topics", "TableSingleRowGet"));
		consumer.subscribe(coll);
		return consumer;
	}
	
	public void run() {
		org.apache.kafka.clients.consumer.KafkaConsumer<K, V> consumer = this.createConsumer();
		try{
			while(isPoll()){
				ConsumerRecords<K, V> records = consumer.poll(1000L);
				Iterator<ConsumerRecord<K, V>> iterator = records.iterator();
				while(iterator.hasNext()){
					ConsumerRecord<K, V> next = iterator.next();
					kafkaBusiness.dealConsumer(next);
				}
			}
		}catch(Exception ex){
			logger.error("error:"+ex);
		}finally{
			if(null != consumer){
				consumer.close();
			}
		}
		
	}
}
