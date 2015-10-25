package main;

import ds.CostGrad;
import ds.Matrix;
import model.NeuralNetwork;
import util.Activations;
import util.StochasticGradientDescent;

public class TestMain {
	public static void main(String[] args) throws Exception{
//		double[] a = {1,2,3,4,5};
//		Matrix v = new Matrix(a);
//		double[] a2 = {1,2,3,4,5};
//		Matrix v2 = new Matrix(a2);
//		
//		double[][] d = {{1,2,3},
//						{3,4,5},
//						{6,7,8}};
//		double[][] d2 = {{1,0,0},
//						{1,3,2},
//						{2,3,1}};
//		double[][] d3 = {{1,2},
//						{-1,-2}};
//		Matrix m3 = new Matrix(d3);
//		System.out.println(Activations.softmax(m3));
//		System.out.println(Activations.sigmoid(m3));
//		System.out.println(Activations.sigmoid_grad(m3));
		
		Matrix data = new Matrix("R", 20, 10);
		Matrix label = new Matrix("1",20,10);
		Matrix flattenedParams = new Matrix("R", 55+60, 1);
		NeuralNetwork nn = new NeuralNetwork();
		nn.params = new Matrix("R", 55+60, 1); //Parameter Initialize
		CostGrad cg = nn.forward_backward_prop(data, label, flattenedParams);
		StochasticGradientDescent.optimize(nn, data, label, 0.01, 100000);
	}
}

