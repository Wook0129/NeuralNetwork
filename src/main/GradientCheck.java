package main;

import ds.Matrix;
import model.NeuralNetwork;
import util.CostFunction.CrossEntropyCost;
import util.CostFunction.SquareLoss;

public class GradientCheck {
	public static void main(String[] args) throws Exception{
		
		NeuralNetwork nn = new NeuralNetwork();
		nn.setCostFunction("SquareLoss");
		nn.layer_Setting(new int[]{5,5,2}, new String[]{"sigmoid","tanh"});
		Matrix data = new Matrix("R", 10, 5).multiply(100);
		Matrix label = new Matrix("1", 10, 2);
		nn.setData(data);
		nn.setLabel(label);
		//Analytical Gradient
		Matrix grad = nn.cost_grad_at_givenParams(nn.getParams()).grad;

		//Numerical Gradient
		final Matrix theta = nn.getParams();
		boolean[] pass_flag = new boolean[theta.row_num];
		boolean pass = true;
		double epsilon = 0.0001;
		
		for(int i = 0; i<theta.row_num; i++){
			Matrix h = new Matrix(theta.row_num, theta.col_num);
			h.values[i][0] = epsilon;
			Matrix theta_h = theta.add(h);
			Matrix theta_minus_h = theta.subtract(h);
			nn.setParams(theta_h);
			double f_theta_h = new SquareLoss().cost(nn.predict(data), label);
			nn.setParams(theta_minus_h);
			double f_theta_minus_h = new SquareLoss().cost(nn.predict(data), label);
			double num_grad = (f_theta_h - f_theta_minus_h) / (2*epsilon);
			System.out.println((i+1)+" : "+grad.values[i][0]+","+num_grad);
			if(Math.abs(grad.values[i][0] - num_grad) < 0.00001) pass_flag[i] = true;
		}
		for(boolean p : pass_flag) {
			if(p == false) {
				pass = false;
				System.out.println("Gradient Check Failed!");
				break;
			}
		}
		if(pass == true) System.out.println("Gradient Check Passed!");
	}
}
