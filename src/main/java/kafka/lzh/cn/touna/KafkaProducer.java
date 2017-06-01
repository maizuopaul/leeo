package kafka.lzh.cn.touna;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * kafka producer to ptoducer message to the kafka's cluster
 * @author leeo
 *
 * @param <K> the key's data type
 * @param <V> the value's data type
 */
public class KafkaProducer<K, V>{
	private static final Properties properties = new Properties();
	private org.apache.kafka.clients.producer.KafkaProducer<K, V> producer = null;
	private final static Logger	logger	= LoggerFactory.getLogger(KafkaConsumer.class);	
	public KafkaProducer(){
		init();
	}
	
	/**
	 * init the configuration
	 */
	private void init(){
		String conf ="/kafka-producer.properties";
		InputStream inputStream = null;
		logger.info("begin to init the kafka producer configuration...");
		try {
			inputStream =  KafkaConsumer.class.getResourceAsStream(conf);
			if(null == inputStream){
				throw new RuntimeException("init the kafka producer confituration fail! get the kafka-producer.properties file failed!");
			}
		} catch (Exception e) {
			logger.error("error",e);
			throw new RuntimeException("init the kafka producer confituration error!",e);
		}
		try {
			properties.load(inputStream);
			producer = new org.apache.kafka.clients.producer.KafkaProducer<K, V>(properties);
			logger.info("finished to init the kafka producer configuration...");
		} catch (IOException e) {
			logger.error("init the kafka producer confituration error!",e);
			throw new RuntimeException("init the kafka producer confituration error!",e);
		}
	}
	public void sendMsg(String topic,K key,V value){
		if(StringUtils.isEmpty(topic))return;
		ProducerRecord<K, V> record = new ProducerRecord<K, V>(topic, key, value);
		producer.send(record );
		producer.flush();
	}
	public void sendMsg(String topic,V value){
		if(StringUtils.isEmpty(topic))return;
		ProducerRecord<K, V> record = new ProducerRecord<K, V>(topic, value);
		producer.send(record);
		producer.flush();
	}
}
