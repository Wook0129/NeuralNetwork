package model;

import java.util.ArrayList;
import java.util.HashMap;

import ds.CostGrad;
import ds.Matrix;
import util.Activation.Sigmoid;

public class NeuralNetwork extends Model{ //Single Hidden Layer

	private int[] dimensions;
	private ArrayList<Matrix> weight_Matrices = new ArrayList<Matrix>();
	private ArrayList<Matrix> biases = new ArrayList<Matrix>();
	
	public NeuralNetwork(){
	}
	
	public void layer_Setting(int[] dimensions) throws Exception{ //First dimension : Dim of Input, Last Dimension : Dim of Output
		this.dimensions = dimensions;
		int total_params_num = 0;
		for(int i = 0; i<dimensions.length - 1; i++){ //Allocate Memory for Weight matrices and Biases according to dimensions
			weight_Matrices.add(new Matrix(dimensions[i], dimensions[i+1]));
			biases.add(new Matrix(1, dimensions[i+1])); //Row Vector
			total_params_num += (dimensions[i] + 1) * dimensions[i+1];
		}
		super.setParams(new Matrix("R", total_params_num, 1));
	}
	
	public void train(Matrix data, Matrix label) throws Exception{
		if(dimensions == null || dimensions.length < 3) throw new Exception("Invalid Layer Setting");
		
		long start = System.currentTimeMillis();
		Matrix optimal_params = super.getOptimizer().optimize(this, data, label);
		super.setParams(optimal_params);
		long elapsed = System.currentTimeMillis() - start;
		
		System.out.println("Training "+this.getClass().getName()+" Finished...");
		System.out.println("Elapsed Time : "+(elapsed/1000)+"s");
	}
	
	private void unpack_Params(ArrayList<Matrix> weight_Matrices, ArrayList<Matrix> biases, Matrix flattenedParams){
		//Unpack Flatten Parameters to Weight Matrices and Biases According to their Dimensions
		int t = 0;
		for(int i = 0; i<weight_Matrices.size(); i++){
			//Unpacking Weight Matrix
			Matrix w = weight_Matrices.get(i);
			int[] dim = w.dim();
			Matrix temp = new Matrix(w.total_params_num(), 1);
			for(int j = t; j< t + w.total_params_num(); j++)
				temp.values[j-t][0] = flattenedParams.values[j][0];
			weight_Matrices.set(i, temp.reshape(dim[0], dim[1]));
			t += w.total_params_num();
			
			//Unpacking Bias
			Matrix b = biases.get(i);
			for(int j = t; j< t + b.total_params_num(); j++)
				b.values[0][j-t] = flattenedParams.values[j][0];
			biases.set(i, b);
			t += b.total_params_num();
		}
	}
	
	private CostGrad forward_backward_Propagation(Matrix data, Matrix label) throws Exception{
		
		//Forward Propagation
		Matrix h = data;
		HashMap<Integer, Matrix> hidden_list = new HashMap<Integer, Matrix>();
		hidden_list.put(0, h); // View X as First Hidden Layer
		for(int i = 0; i < weight_Matrices.size(); i++){ // #hidden = n  -> #weight_matrices = n+1,  #biases = n+1	
			h = new Sigmoid().activate(hidden_list.get(i).multiply(weight_Matrices.get(i)).add(biases.get(i))); // h_i, W_i+1, b_i+1
			hidden_list.put((i+1), h); // Save h_i+1
		}
		double cost = - label.element_multiply(h.log()).sum_all();
		
		//Backward Propagation
		HashMap<Integer, Matrix> grad_Z_list = new HashMap<Integer, Matrix>();
		HashMap<Integer, Matrix> grad_W_list = new HashMap<Integer, Matrix>();
		HashMap<Integer, Matrix> grad_b_list = new HashMap<Integer, Matrix>();
		
		int weight_matrix_num = weight_Matrices.size();
		for(int i = weight_matrix_num; i >= 0; i--){
			if(i == weight_matrix_num){ // i = n + 1				
				Matrix grad_Z = label.element_multiply(h.subtract(new Matrix("1", h.row_num, h.col_num))); //h equals y in this point
				grad_Z_list.put(i, grad_Z);
				grad_W_list.put(i, hidden_list.get(i).T().multiply(grad_Z));
				grad_b_list.put(i, grad_Z.sum(0));
			}
			else{
				Matrix h_i = hidden_list.get(i);
				Matrix h_i_prev = hidden_list.get(i-1);
				Matrix grad_h = grad_Z_list.get(i+1).multiply(weight_Matrices.get(i));
				Matrix grad_Z = grad_h.element_multiply(h_i).element_multiply(h_i.subtract(new Matrix("1", h_i.row_num, h_i.col_num))).multiply(-1);
				Matrix grad_W = h_i_prev.T().multiply(grad_Z);
				Matrix grad_b = grad_Z.sum(0);
				grad_Z_list.put(i, grad_Z);
				grad_W_list.put(i, grad_W);
				grad_b_list.put(i, grad_b);
			}
		}
		
		//Stack Gradients
		ArrayList<Matrix> grads = new ArrayList<Matrix>();
		for(int i = 0; i < weight_matrix_num; i++){
			grads.add(grad_W_list.get(i).flatten());
			grads.add(grad_b_list.get(i).flatten());
		}
		return new CostGrad(cost, Matrix.concat(grads));
	}

	@Override
	public CostGrad cost_grad_at_givenParams(Matrix data, Matrix label, Matrix flattenedParams) throws Exception{
		unpack_Params(weight_Matrices, biases, flattenedParams);
		return forward_backward_Propagation(data, label);
	}
}
