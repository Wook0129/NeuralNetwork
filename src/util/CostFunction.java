package util;

import ds.Matrix;

public interface CostFunction {
	
	Double cost(Matrix y, Matrix label) throws Exception;
	Matrix grad(Matrix y, Matrix label) throws Exception;
	
	public class CrossEntropyCost implements CostFunction{

		@Override
		public Double cost(Matrix y, Matrix label) throws Exception {
			return - label.element_multiply(y.log()).sum_all();
		}
		@Override
		public Matrix grad(Matrix y, Matrix label) throws Exception {
			Matrix grad_y = label.element_divide(y).multiply(-1);
			return grad_y;
		}
	}
	
	public class SquareLoss implements CostFunction{

		@Override
		public Double cost(Matrix y, Matrix label) throws Exception {
			return y.subtract(label).element_multiply(y.subtract(label)).sum_all();
		}
		@Override
		public Matrix grad(Matrix y, Matrix label) throws Exception {
			Matrix grad_y = y.subtract(label).multiply(2);
			return grad_y;
		}
	}
}