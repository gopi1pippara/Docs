package org.example;

import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.lossfunctions.LossFunctions;

public class LogAnalyzerModel {
    public MultiLayerNetwork createModel(int inputSize) {
        return new MultiLayerNetwork(new NeuralNetConfiguration.Builder()
                .updater(new org.nd4j.linalg.learning.config.Adam(0.01))
                .list()
                .layer(new DenseLayer.Builder()
                        .nIn(inputSize)
                        .nOut(50)
                        .activation(Activation.RELU)
                        .build())
                .layer(new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                        .nIn(50)
                        .nOut(1)
                        .activation(Activation.SIGMOID)
                        .build())
                .build());
    }
}

