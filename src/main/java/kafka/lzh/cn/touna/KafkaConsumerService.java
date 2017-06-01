package kafka.lzh.cn.touna;

/**
 * kafka's service
 * you can invoke this interface in your consumer's service code
 * @author leeo
 *
 * @param <T>	the return data type after deal message
 * @param <K>	kafka message key's data type
 * @param <V>	kafka message value's data type
 */
public interface KafkaConsumerService<T,K,V> {
	/**
	 * to deal the consumerRecord
	 * @param record
	 * @return
	 */
	public T dealConsumer(org.apache.kafka.clients.consumer.KafkaConsumer<K, V> consumer);
}
