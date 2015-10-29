package main;

import ds.Matrix;
import model.NeuralNetwork;

public class TestMain {
	public static void main(String[] args) throws Exception{
		
		Matrix data = new Matrix("R", 500, 10);
		Matrix label = new Matrix("R",500, 10);
		NeuralNetwork nn = new NeuralNetwork();
		nn.layer_Setting(new int[]{10,10,10,10,10,10}, new String[]{"tanh","sigmoid","sigmoid","relu","sigmoid"});

		nn.train(data, label);
	}
}

