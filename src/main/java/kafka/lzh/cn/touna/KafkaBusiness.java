package kafka.lzh.cn.touna;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
/**
 * kafka业务层
 * @author yourname
 *
 * @param <T>
 * @param <K>
 * @param <V>
 */
public interface KafkaBusiness<T,K,V> {
	public T dealConsumer(ConsumerRecord<K, V> record);
	public T dealConsumer(ConsumerRecords<K, V> records);
	public T dealProducer();
}
