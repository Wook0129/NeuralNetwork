package util;

import ds.Matrix;

public interface Activation {

	Matrix activate(Matrix m) throws Exception;
	Matrix grad(Matrix m) throws Exception;
	
	public class Sigmoid implements Activation{
		@Override
		public Matrix activate(Matrix m) throws Exception {
		Matrix ones = new Matrix("1", m.row_num, m.col_num);
		return ones.divide(ones.add(m.multiply(-1).exp()));		
		}
		@Override
		public Matrix grad(Matrix m) throws Exception {
			Matrix ones = new Matrix("1", m.row_num, m.col_num);
			Matrix sigmoid = new Sigmoid().activate(m);
			return sigmoid.element_multiply(ones.subtract(sigmoid));
		}
	}
	
	public class Softmax implements Activation{
		@Override
		public Matrix activate(Matrix m) throws Exception {			
			if(m.col_num == 1){
				Matrix e = m.subtract(m.max(0)).exp();
				return e.divide(e.sum(0));
			}
			else{
				Matrix e = (m.subtract(m.max(1))).exp();
				return e.divide(e.sum(1));
			}
		}

		@Override
		public Matrix grad(Matrix m) throws Exception {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	public class HyperTangent implements Activation{
		@Override
		public Matrix activate(Matrix m) throws Exception {
			Matrix z2 = m.multiply(2).exp();
			return z2.subtract(new Matrix("1", z2.row_num, z2.col_num)).divide(z2.add(new Matrix("1", z2.row_num, z2.col_num)));
		}
		@Override
		public Matrix grad(Matrix m) throws Exception {
			Matrix z2 = m.multiply(2).exp();
			Matrix z_minus2 = m.multiply(-2).exp();
			return new Matrix("1", m.row_num, m.col_num).multiply(4).divide(z2.add(z_minus2).add(new Matrix("1", m.row_num, m.col_num).multiply(2)));
		}
	}
	
	public class ReLU implements Activation{
		@Override
		public Matrix activate(Matrix m) throws Exception {
			Matrix result = new Matrix(m.row_num, m.col_num);
			for(int i = 0; i< m.row_num; i++)
				for(int j = 0; j< m.col_num; j++)
					if(m.values[i][j] < 0) result.values[i][j] = 0;
					else result.values[i][j] = m.values[i][j];
			return result;
		}
		@Override
		public Matrix grad(Matrix m) throws Exception {
			Matrix result = new Matrix(m.row_num, m.col_num);
			for(int i = 0; i< m.row_num; i++)
				for(int j = 0; j< m.col_num; j++)
					if(m.values[i][j] > 0) result.values[i][j] = 1;
					else result.values[i][j] = 0;
			return result;
		}
	}
	
}
