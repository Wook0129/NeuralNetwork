package util;

import ds.Matrix;

public class Activations {
	
	public static Matrix softmax(Matrix m) throws Exception{
		Matrix e = (m.subtract(m.max(1))).exp();
		return e.divide(e.sum(1));
	}
	
}
