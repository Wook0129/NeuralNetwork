package util;

import ds.Matrix;

public class Activations {
	
	public static Matrix softmax(Matrix m) throws Exception{
		Matrix e = (m.subtract(m.max(1))).exp();
		return e.divide(e.sum(1));
	}
	
	public static Matrix sigmoid(Matrix m) throws Exception{
		Matrix ones = new Matrix("1", m.row_num, m.col_num);
		return ones.divide(ones.add(m.multiply(-1).exp()));		
	}
	
	public static Matrix sigmoid_grad(Matrix m) throws Exception{
		Matrix ones = new Matrix("1", m.row_num, m.col_num);
		return sigmoid(m).element_multiply(ones.subtract(sigmoid(m)));
	}
}
