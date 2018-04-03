package in.wynk.spark.config;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationConfiguration {

    @Autowired
    private Environment env;

    @Value("${app.name}")
    private String appName;

    @Value("${spark.home}")
    private String sparkHome;

    @Value("${master.uri:local}")
    private String masterUri;

    @Value("${app.word}")
    private String inputString;

    @Value("${input.file}")
    private String inputFile;

    @Bean public SparkConf sparkConf() {
        SparkConf sparkConf = new SparkConf().setAppName(appName).setSparkHome(sparkHome).setMaster(masterUri)
                .set("spark.streaming.kafka.maxRatePerPartition", "100");

        return sparkConf;
    }

    @Bean public JavaSparkContext javaSparkContext() {
        return new JavaSparkContext(sparkConf());
    }

    @Bean public JavaStreamingContext javaStreamingContext(JavaSparkContext javaSparkContext) {
        return new JavaStreamingContext(javaSparkContext, new Duration(10000));
    }

    @Bean public SparkSession sparkSession() {
        return SparkSession.builder().sparkContext(javaSparkContext().sc()).appName("Java Spark").getOrCreate();
    }

    @Bean public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public String getInputString() {
        return inputString;
    }

    public String getInputFile() {
        return inputFile;
    }
}
