package util;

import ds.Matrix;

public interface Activation {

	Matrix activate(Matrix m) throws Exception;
	
	public class Softmax implements Activation{
		@Override
		public Matrix activate(Matrix m) throws Exception {
			Matrix e = (m.subtract(m.max(1))).exp();
			return e.divide(e.sum(1));
		}
	}
	public class Sigmoid implements Activation{
		@Override
		public Matrix activate(Matrix m) throws Exception {
		Matrix ones = new Matrix("1", m.row_num, m.col_num);
		return ones.divide(ones.add(m.multiply(-1).exp()));		
		}
	}
	public class Sigmoid_grad implements Activation{
		@Override
		public Matrix activate(Matrix m) throws Exception {
			Matrix ones = new Matrix("1", m.row_num, m.col_num);
			Matrix sigmoid = new Sigmoid().activate(m);
			return sigmoid.element_multiply(ones.subtract(sigmoid));
		}
	}
	public class HyperTangent implements Activation{
		@Override
		public Matrix activate(Matrix m) throws Exception {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
