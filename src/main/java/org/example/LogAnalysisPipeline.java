package org.example;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LogAnalysisPipeline {
    public static void main(String[] args) throws Exception {
        ElasticsearchClient esClient = new ElasticsearchClient();
        LogPreprocessor preprocessor = new LogPreprocessor();
        LogAnalyzerModel modelBuilder = new LogAnalyzerModel();

        // Fetch logs from Elasticsearch
        var response = esClient.fetchLogs("logs-index", 100);
        List<String> logs = Arrays.stream(response.getHits().getHits())
                .map(hit -> hit.getSourceAsMap().get("message"))
                .filter(Objects::nonNull) // Exclude null values
                .map(Object::toString)
                .collect(Collectors.toList());

        // Preprocess logs
        INDArray data = Nd4j.vstack(
                logs.stream()
                        .map(preprocessor::preprocess)
                        .toArray(INDArray[]::new)
        );

        // Build and train the model
        MultiLayerNetwork model = modelBuilder.createModel(data.columns());
        model.init();
        model.fit(data, data); // Train an autoencoder: input equals output

        // Test on a new log
        String newLog = "User failed login attempt";
        INDArray newLogVector = preprocessor.preprocess(newLog);
        double anomalyScore = model.output(newLogVector).sumNumber().doubleValue();

        System.out.println("Anomaly Score: " + anomalyScore);

        esClient.close();
    }
}


