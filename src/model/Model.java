package model;

import java.util.HashMap;

import ds.CostGrad;
import ds.Matrix;
import util.Configuration;
import util.CostFunction;

public abstract class Model {
	
	private Matrix data;
	private Matrix label;
	private Matrix params; //Parameters Of Model, Saved as Flatten Format
	private Configuration config = new Configuration(); //Optimization Method & Cost Function
	
	public abstract CostGrad cost_grad_at_givenParams(Matrix flattenedparams) throws Exception;
	//Input : Parameters
	//Output : 1)Cost of Objective Function and 2)Gradient of Parameters, at Given Parameters
	
	public void train(Matrix data, Matrix label) throws Exception{
		
		if(params == null) throw new Exception("Model is Not Initialized");
		setData(data);
		setLabel(label);		

		long start = System.currentTimeMillis();
		Matrix optimal_params = config.getOptimizer().optimize(this, data, label);
		setParams(optimal_params);
		long elapsed = System.currentTimeMillis() - start;
		
		System.out.println("Training "+this.getClass().getName()+" Finished...");
		System.out.println("Elapsed Time : "+(elapsed/1000)+"s");
	}
	
	@SuppressWarnings("rawtypes")
	protected abstract HashMap[] getTrainedParams();
	
	public abstract Matrix predict(Matrix testData) throws Exception;
	
	public void setData(Matrix data){
		this.data = data;
	}
	protected Matrix getData(){
		return data;
	}
	public void setLabel(Matrix label){
		this.label = label;
	}
	protected Matrix getLabel(){
		return label;
	}
	public void setParams(Matrix params){ //Used when 1)Initializing Parameters or 2)Gradient Descent
		this.params = params;
	}
	public Matrix getParams(){
		return params;
	}
	public void setCostFunction(String function) throws Exception{
		this.config.setCostFunction(function);
	}
	protected CostFunction getCostFunction(){
		return config.getCostFunction();
	}
	public void setOptimizer(String optimizer) throws Exception{
		this.config.setOptimizer(optimizer);
	}

}