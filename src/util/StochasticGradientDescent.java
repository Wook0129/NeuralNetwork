package util;

import ds.CostGrad;
import ds.Matrix;
import model.Model;

public class StochasticGradientDescent {
	public static CostGrad optimize(Model model, Matrix data, Matrix label, double step, int num_iter) throws Exception{

		for(int iter = 0; iter<num_iter; iter++){
			CostGrad cost_grad = model.forward_backward_prop(data, label, model.params);
			model.params = model.params.subtract(cost_grad.grad.multiply(step));
			if(iter % 100 == 0) System.out.println(cost_grad.cost);
		}
		return null;
	}
}
