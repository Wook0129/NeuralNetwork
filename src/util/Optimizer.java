package util;

import ds.CostGrad;
import ds.Matrix;
import model.Model;

public interface Optimizer {

	public abstract class OptMethod{	
		double learning_rate;
		int num_iter;
		public abstract Matrix optimize(Model model, Matrix data, Matrix label) throws Exception; //Input : Model, Data, Label, Output : Optimal Params
		public void setOption(double learning_rate, int num_iter){ //Change Default Learning rate & Iteration Number
			this.learning_rate = learning_rate;
			this.num_iter = num_iter;
		} 
	}
	
	public class GradientDescent extends OptMethod implements Optimizer{

		public GradientDescent(){
			this.learning_rate = 0.01; //Default Option
			this.num_iter = 10000;
		}
		@Override
		public Matrix optimize(Model model, Matrix data, Matrix label) throws Exception{		
			if(model == null) throw new Exception("No model to Optimize");
			for(int iter = 0; iter<num_iter; iter++){
				CostGrad cost_grad = model.cost_grad_at_givenParams(model.getParams());
				model.setParams(model.getParams().subtract(cost_grad.grad.multiply(learning_rate)));
				if(iter % 1000 == 0) System.out.println("Cost : "+cost_grad.cost);
			}
			return model.getParams();
		}
	}
}
