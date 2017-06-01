package kafka.lzh.cn.touna;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

public class KafkaBusinessImpl<T, K, V> implements KafkaBusiness<T, K, V> {

	public T dealConsumer(ConsumerRecord<K, V> record) {
		System.out.println(record.key() + "="+record.value());
		return null;
	}
	public T dealConsumer(ConsumerRecords<K, V> records) {
		// TODO Auto-generated method stub
		return null;
	}
	public T dealProducer() {
		return null;
	}
}
