package in.wynk.spark.job;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.initcap;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.RelationalGroupedDataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.wynk.spark.beans.Count;
import in.wynk.spark.beans.Word;
import in.wynk.spark.config.ApplicationConfiguration;

@Component
public class WordCountJob {

    @Autowired
    private SparkSession sparkSession;

    @Autowired
    ApplicationConfiguration applicationConfiguration;

    public List<Count> count() {
        //String input = "hello world hello hello hello";
        String inputFilePath = applicationConfiguration.getInputFile();
        String input;
        try {
            input = getContentFromFile(inputFilePath);
        }
        catch (IOException e) {
            input = "";
        }
        String[] _words = input.split(" ");
        List<Word> words = Arrays.stream(_words).map(Word::new).collect(Collectors.toList());
        Dataset<Row> dataFrame = sparkSession.createDataFrame(words, Word.class);
        dataFrame.show();
        //StructType structType = dataFrame.schema();

        RelationalGroupedDataset groupedDataset = dataFrame.groupBy(col("word"));
        groupedDataset.count().show();
        List<Row> rows = groupedDataset.count().collectAsList();//JavaConversions.asScalaBuffer(words)).count();
        return rows.stream().map(new Function<Row, Count>() {

            @Override public Count apply(Row row) {
                return new Count(row.getString(0), row.getLong(1));
            }
        }).collect(Collectors.toList());
    }

    private String getContentFromFile(String filepath) throws IOException {
        FileReader fileReader = new FileReader(filepath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = null;
        String content = "";
        while((line = bufferedReader.readLine()) != null) {
            content += line;
        }
        return content;
    }
}
