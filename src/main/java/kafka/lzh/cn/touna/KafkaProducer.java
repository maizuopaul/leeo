package kafka.lzh.cn.touna;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
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
			logger.info("finished to init the kafka producer configuration...");
		} catch (IOException e) {
			logger.error("init the kafka producer confituration error!",e);
			throw new RuntimeException("init the kafka producer confituration error!",e);
		}
	}
	
	/**
	 * send a key/value message to a specified topic. the topic,key,value should not be null
	 * @param topic the topic of message to send
	 * @param key the message of key
	 * @param value	the message of value
	 */
	public void sendMsg(String topic,K key,V value){
		if(StringUtils.isEmpty(topic))return;
		if(null == key || null == value)return;
		org.apache.kafka.clients.producer.KafkaProducer<K, V> producer = new org.apache.kafka.clients.producer.KafkaProducer<K, V>(properties);
		ProducerRecord<K, V> record = new ProducerRecord<K, V>(topic, key, value);
		producer.send(record );
		producer.close();
	}
	/**
	 * send a key/value message list to a specified topic. 
	 * the topic,keys,values should not be null. the keys size should be equal the values size
	 * @param topic the topic of message to send
	 * @param keys  the message of key list
	 * @param values the message of value list
	 */
	public void sendMsg(String topic,List<K> keys,List<V> values){
		if(StringUtils.isEmpty(topic))return;
		if(null == keys || null == values || keys.size()<=0 || keys.size()!=values.size())return;
		keys.remove(null);
		values.remove(null);
		if(keys.size()<=0 || keys.size()!=values.size())return;
		org.apache.kafka.clients.producer.KafkaProducer<K, V> producer = new org.apache.kafka.clients.producer.KafkaProducer<K, V>(properties);
		for(int i = 0;i < keys.size();i++){
			ProducerRecord<K, V> record = new ProducerRecord<K, V>(topic, keys.get(i), values.get(i));
			producer.send(record );
		}
		producer.close();
	}
	/**
	 * send a value message  to a specified topic. the topic,value should not be null
	 * @param topic the topic of message to send
	 * @param value	the message of value
	 */
	public void sendMsg(String topic,V value){
		if(StringUtils.isEmpty(topic))return;
		org.apache.kafka.clients.producer.KafkaProducer<K, V> producer = new org.apache.kafka.clients.producer.KafkaProducer<K, V>(properties);
		ProducerRecord<K, V> record = new ProducerRecord<K, V>(topic, value);
		producer.send(record);
		producer.close();
	}
	/**
	 * send a value message collection to a specified topic. the topic,value collection should not be null
	 * @param topic the topic of message to send
	 * @param values the message of value collection
	 */
	public void sendMsg(String topic,Collection<V> values){
		if(StringUtils.isEmpty(topic))return;
		if(null == values || values.size()<=0)return;
		values.remove(null);
		if(values.size()<=0)return;
		org.apache.kafka.clients.producer.KafkaProducer<K, V> producer = new org.apache.kafka.clients.producer.KafkaProducer<K, V>(properties);
		for (V v : values) {
			ProducerRecord<K, V> record = new ProducerRecord<K, V>(topic, v);
			producer.send(record);
		}
		producer.close();
	}
}
