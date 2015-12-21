package main;

import ds.Matrix;
import model.NeuralNetwork;

public class TestMain {
	public static void main(String[] args) throws Exception{
		
		Matrix data = new Matrix("R", 50, 10);
		Matrix label = new Matrix("R", 50, 10);
		for(int i = 0; i<50; i++)
			for(int j = 0; j<10; j++)
				if(label.values[i][j] > 0.5) label.values[i][j] = 1;
				else label.values[i][j] = 0;
			
		NeuralNetwork nn = new NeuralNetwork();
//		nn.setCostFunction("SquareLoss");
		nn.layer_Setting(new int[]{10,5,5,10}, new String[]{"none","none","sigmoid"});	
		nn.train(data, label);
		Matrix p = nn.predict(new Matrix(1,10));
		System.out.println(p);
	}
}

