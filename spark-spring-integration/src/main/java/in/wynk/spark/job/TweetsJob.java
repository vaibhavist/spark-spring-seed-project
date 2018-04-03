package in.wynk.spark.job;

import org.springframework.stereotype.Component;

/**
 * @author vaibhav
 */
@Component
public class TweetsJob {

//    @Autowired
//    private SparkSession sparkSession;
//
//    @Autowired
//    private JavaStreamingContext javaStreamingContext;
//
//    @Autowired
//    ApplicationConfiguration applicationConfiguration;
//
//    public void getTweets(String keyword) {
//        final String consumerKey = "sPx9MUc9QR3kvRQMD25KfF5im";
//        final String consumerSecret = "BPm6144vbVDtDibqgPiLpHeE3uRgBUQkDJxmgs3peFNdV76hTK";
//        final String accessToken = "60664555-K8Z4QNRp7QBcUAkwpfuEMwpaltXq8zuqSEi5Ki7F6";
//        final String accessTokenSecret = "OzQAWR2YOp7bBzXkgj3uqYYdIsi8sLxVq67vokxTtvAWc";
//
//        System.setProperty("twitter4j.oauth.consumerKey", consumerKey);
//        System.setProperty("twitter4j.oauth.consumerSecret", consumerSecret);
//        System.setProperty("twitter4j.oauth.accessToken", accessToken);
//        System.setProperty("twitter4j.oauth.accessTokenSecret", accessTokenSecret);
//
//        JavaReceiverInputDStream<Status> twitterStream = TwitterUtils.createStream(javaStreamingContext);
//
//        JavaDStream<String> statuses = twitterStream.map(a -> a.getText());
//        statuses.print();
//        javaStreamingContext.start();
//    }
}
