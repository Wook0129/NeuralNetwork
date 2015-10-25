package main;

import ds.Matrix;
import util.Activations;

public class TestMain {
	public static void main(String[] args) throws Exception{
		double[] a = {1,2,3,4,5};
		Matrix v = new Matrix(a);
		double[] a2 = {1,2,3,4,5};
		Matrix v2 = new Matrix(a2);
		
		System.out.println(v.element_multiply(v2));
		double[][] d = {{1,2,3},
						{3,4,5},
						{6,7,8}};
		double[][] d2 = {{1,0,0},
						{1,3,2},
						{2,3,1}};
		double[][] d3 = {{1,2},
						{3,4}};
		Matrix m = new Matrix(d);
		Matrix m2 = new Matrix(d2);
		Matrix m3 = new Matrix(d3);
		System.out.println(m.multiply(m2));
		System.out.println(m.multiply(m2).T());
		System.out.println(Activations.softmax(m3));
	}
}

