package model;

import ds.CostGrad;
import ds.Matrix;
import util.Optimizer;
import util.StochasticGradientDescent;

public abstract class Model {
	
	private Matrix params; //Parameters Of Model
	private Optimizer optMethod = new StochasticGradientDescent(); //Method to Optimize Model. Default method is SGD
	
	public abstract CostGrad cost_grad_at_givenParams(Matrix data, Matrix label, Matrix params);
	
	public void setParams(Matrix params){
		this.params = params;
	}
	public Matrix getParams(){
		return params;
	}
	
	public void setOptimizer(Optimizer optMethod){
		this.optMethod = optMethod;
	}
	public Optimizer getOptimizer(){
		return optMethod;
	}

}