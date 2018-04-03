package in.wynk.spark.job;

import com.google.common.collect.Lists;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.api.java.*;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import kafka.serializer.StringDecoder;
import scala.Tuple2;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author vaibhav
 */
@Component
public class KafkaExampleJob {

    private static final Pattern SPACE = Pattern.compile(" ");

    @Autowired
    private JavaStreamingContext javaStreamingContext;

    public void kafkaStreamTest() throws InterruptedException {
        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", "localhost:9092");
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", StringDeserializer.class);
        kafkaParams.put("group.id", "kafka_demo_group");
        kafkaParams.put("auto.offset.reset", "earliest");
        kafkaParams.put("enable.auto.commit", true);

        Collection<String> topics = Arrays.asList("text");

        final JavaInputDStream<ConsumerRecord<String, String>> stream = KafkaUtils
                .createDirectStream(javaStreamingContext, LocationStrategies.PreferConsistent(), ConsumerStrategies.<String, String>Subscribe(topics, kafkaParams));

        JavaPairDStream<String, String> pairDStream = stream.mapToPair(record -> new Tuple2<>(record.key(), record.value()));

        JavaPairDStream<String, Integer> wordCounts = pairDStream.map(a -> a._2).flatMap((FlatMapFunction<String, String>) s -> Lists.newArrayList(SPACE.split(s)).iterator())
                .mapToPair((PairFunction<String, String, Integer>) s -> new Tuple2<>(s, 1)).reduceByKey((Function2<Integer, Integer, Integer>) (integer, integer2) -> integer + integer2);
        wordCounts.print();

        javaStreamingContext.start();
        javaStreamingContext.awaitTermination();
    }

}
