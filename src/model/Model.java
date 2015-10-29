package model;

import ds.CostGrad;
import ds.Matrix;
import util.Optimizer;
import util.StochasticGradientDescent;

public abstract class Model {
	
	private Matrix data;
	private Matrix label;
	private Matrix params; //Parameters Of Model, Saved as Flatten Format
	private Optimizer optMethod = new StochasticGradientDescent(); //Method to Optimize Model. Default method is SGD
	
	public abstract CostGrad cost_grad_at_givenParams(Matrix params) throws Exception;
	//Input : Parameters
	//Output : 1)Cost of Objective Function and 2)Gradient of Parameters, at Given Parameters
	
	public void setData(Matrix data){
		this.data = data;
	}
	public void setLabel(Matrix label){
		this.label = label;
	}
	public Matrix getData(){
		return data;
	}
	public Matrix getLabel(){
		return label;
	}
	public void setParams(Matrix params){ //Used when 1)Initializing Parameters or 2)Gradient Descent
		this.params = params;
	}
	public Matrix getParams(){
		return params;
	}
	
	public void setOptimizer(Optimizer optMethod){ //Change Optimizer
		this.optMethod = optMethod;
	}
	public Optimizer getOptimizer(){
		return optMethod;
	}

}