package in.wynk.spark.web.controller;

import java.util.List;

import in.wynk.spark.beans.Count;
import in.wynk.spark.job.WordCountJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("api")
@Controller
public class WorldCountController {

    @Autowired
    WordCountJob wordCountJob;

    @RequestMapping("wordcount")
    public ResponseEntity<List<Count>> words() {
        return new ResponseEntity<>(wordCountJob.count(), HttpStatus.OK);
    }
    
}
