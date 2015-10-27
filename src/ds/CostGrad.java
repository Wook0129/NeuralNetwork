package ds;

public class CostGrad {
	
	public double cost;
	public Matrix grad;
	
	public CostGrad(double cost, Matrix grad){
		this.cost = cost;
		this.grad = grad;
	}
}
