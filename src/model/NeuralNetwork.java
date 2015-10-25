package model;

import ds.CostGrad;
import ds.Matrix;
import util.Activations;

public class NeuralNetwork extends Model{ //Single Hidden Layer

	static int[] dimensions = {10, 5, 10};
	
	public void train(Matrix data, Matrix label){
		CostGrad cost_grad = forward_backward_prop(data, label, super.params);
	}
	public CostGrad forward_backward_prop(Matrix data, Matrix label, Matrix flattenedParams){
		
		double cost = 0;
		Matrix grad = new Matrix(flattenedParams.row_num, 1);
		
		//Unpack Network Params
		int t = 0;
		Matrix w1 = new Matrix(dimensions[0] * dimensions[1], 1);
		for(int i = t; i< t + dimensions[0] * dimensions[1]; i++)
			w1.values[i][0] = flattenedParams.values[i][0];
		w1 = w1.reshape(dimensions[0], dimensions[1]);
		t += dimensions[0] * dimensions[1];
		
		Matrix b1 = new Matrix(dimensions[1], 1);
		for(int i = t; i< t + dimensions[1]; i++)
			b1.values[i-t][0] = flattenedParams.values[i][0];
		b1 = b1.reshape(1, dimensions[1]);
		t += dimensions[1];
		
		Matrix w2 = new Matrix(dimensions[1] * dimensions[2], 1);
		for(int i = t; i< t + dimensions[1] * dimensions[2]; i++)
			w2.values[i-t][0] = flattenedParams.values[i][0];
		w2 = w2.reshape(dimensions[1], dimensions[2]);
		t += dimensions[1] * dimensions[2];
		
		Matrix b2 = new Matrix(dimensions[2], 1);
		for(int i = t; i< t + dimensions[2]; i++)
			b2.values[i-t][0] = flattenedParams.values[i][0];
		b2 = b2.reshape(1, dimensions[2]);
		
		
		try {
		//Forward Propagation
			Matrix h = Activations.sigmoid(data.multiply(w1).add(b1));
			Matrix y = Activations.sigmoid(h.multiply(w2).add(b2));
			cost = - label.element_multiply(y.log()).sum_all();
		
		//Backward Propagation
			Matrix grad_Layer2Input = label.element_multiply(y.subtract(new Matrix("1", y.row_num, y.col_num)));
			Matrix grad_W2 = h.T().multiply(grad_Layer2Input);
			Matrix grad_b2 = grad_Layer2Input.sum(0);
			Matrix grad_h = grad_Layer2Input.multiply(w2.T());
			Matrix grad_Layer1Input = h.element_multiply(h.subtract(new Matrix("1", h.row_num, h.col_num)).multiply(-1)).element_multiply(grad_h);
			Matrix grad_W1 = data.T().multiply(grad_Layer1Input);
			Matrix grad_b1 = grad_Layer1Input.sum(0);
			
		//Stack Gradients
			
			grad_W1 = grad_W1.reshape(dimensions[0]*dimensions[1], 1);
			grad_b1 = grad_b1.reshape(dimensions[1], 1);
			grad_W2 = grad_W2.reshape(dimensions[1]*dimensions[2], 1);
			grad_b2 = grad_b2.reshape(dimensions[2], 1);
			Matrix[] grads = {grad_W1, grad_b1, grad_W2, grad_b2};
			grad = Matrix.concat(grads);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new CostGrad(cost, grad);
	}
}
