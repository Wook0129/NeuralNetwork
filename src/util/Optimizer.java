package util;

import ds.Matrix;
import model.Model;

public abstract class Optimizer {
	
	double learning_rate;
	int num_iter;
	
	public void setOption(double learning_rate, int num_iter){ //Change Default Learning rate & Iteration Number
		this.learning_rate = learning_rate;
		this.num_iter = num_iter;
	}
	public abstract Matrix optimize(Model model, Matrix data, Matrix label) throws Exception;
	//Input : Model, Data, Label
	//Output : Optimal Parameters
}
