package util;

import ds.CostGrad;
import ds.Matrix;
import model.Model;

public class StochasticGradientDescent extends Optimizer{
	
	public StochasticGradientDescent(){
		super.learning_rate = 0.01; //Default Option
		super.num_iter = 10000;
	}
	public Matrix optimize(Model model, Matrix data, Matrix label) throws Exception{
		
		if(model == null) throw new Exception("No model to Optimize");
		for(int iter = 0; iter<super.num_iter; iter++){
			CostGrad cost_grad = model.cost_grad_at_givenParams(model.getParams());
			model.setParams(model.getParams().subtract(cost_grad.grad.multiply(learning_rate)));
			if(iter % 1000 == 0) System.out.println("Cost : "+cost_grad.cost);
		}
		return model.getParams();
	}
}