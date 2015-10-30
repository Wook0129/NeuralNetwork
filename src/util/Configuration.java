package util;

import util.CostFunction.CrossEntropyCost;
import util.Optimizer.GradientDescent;
import util.Optimizer.OptMethod;

public class Configuration {
	
	private OptMethod optimizer;
	private CostFunction costFunction;
	
	public Configuration(){
		this.optimizer = new GradientDescent(); //Default Optimizer
		this.costFunction =  new CrossEntropyCost(); //Default Cost Function;
	}

	public OptMethod getOptimizer() {
		return optimizer;
	}

	public void setOptimizer(String optimizer) throws Exception {
		switch(optimizer){
			case "SGD":
				this.optimizer = new GradientDescent();
				break;
			default :
				throw new Exception("No Such Optimization Method");
		}
	}

	public void setCostFunction(String function) throws Exception {
		switch(function){
			case "CrossEntropy":
				this.costFunction = new CrossEntropyCost();
				break;
			default :
				throw new Exception("No Such Cost Function");
		}
	}
	
	public CostFunction getCostFunction(){
		return costFunction;
	}
}
