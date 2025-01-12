package org.example;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.util.Arrays;

public class LogPreprocessor {
    public INDArray preprocess(String logMessage) {
        // Example: tokenize and encode each token as a hash
        String[] tokens = logMessage.split("\\s+");
        double[] vector = Arrays.stream(tokens)
                .mapToDouble(String::hashCode)
                .toArray();
        return Nd4j.create(vector);
    }
}

