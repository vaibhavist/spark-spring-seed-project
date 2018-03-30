package in.wynk.spark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PocApplication /*implements CommandLineRunner*/ {

/*    @Autowired
    private WordCountJob wordCountJob;*/
    
	public static void main(String[] args) {
		SpringApplication.run(PocApplication.class, args);
	}

/*    @Override
    public void run(String... args) throws Exception {
    	wordCountJob.count();
    } */
}
