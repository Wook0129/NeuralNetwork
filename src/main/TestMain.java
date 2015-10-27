package main;

import ds.Matrix;
import model.NeuralNetwork;

public class TestMain {
	public static void main(String[] args) throws Exception{

		Matrix data = new Matrix("R", 20, 10);
		Matrix label = new Matrix("1",20,10);
		NeuralNetwork nn = new NeuralNetwork();
		nn.init();
		nn.train(data, label);
	}
}

