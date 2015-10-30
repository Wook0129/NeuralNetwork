package main;

import ds.Matrix;
import model.NeuralNetwork;

public class TestMain {
	public static void main(String[] args) throws Exception{
		
		Matrix data = new Matrix("R", 50, 10);
		Matrix label = new Matrix("R", 50, 10);
		NeuralNetwork nn = new NeuralNetwork();
		nn.layer_Setting(new int[]{10,5,5,10}, new String[]{"none","none","sigmoid"});	
		nn.train(data, label);
	}
}

