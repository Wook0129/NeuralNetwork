package main;

import ds.Matrix;
import model.NeuralNetwork;

public class TestMain {
	public static void main(String[] args) throws Exception{
		
		Matrix data = new Matrix("R", 500, 30);
		Matrix label = new Matrix("R",500, 30);
		NeuralNetwork nn = new NeuralNetwork();
		nn.layer_Setting(new int[]{30,10,10,10,10,30}, new String[]{"sigmoid","sigmoid","sigmoid","sigmoid","sigmoid"});

		nn.train(data, label);
	}
}

