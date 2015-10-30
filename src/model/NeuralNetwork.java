package model;

import java.util.ArrayList;
import java.util.HashMap;

import ds.CostGrad;
import ds.Matrix;
import util.Activation.*;

public class NeuralNetwork extends Model{

	private HashMap<Integer, Matrix> weight_Matrices = new HashMap<Integer, Matrix>();
	private HashMap<Integer, Matrix> biases = new HashMap<Integer, Matrix>();
	private HashMap<Integer, String> h_layer_types = new HashMap<Integer, String>(); // Save name of Activation Functions for each Hidden Layer
	
	
	public void layer_Setting(int[] dimensions, String[] layer_types) throws Exception{ //First dimension : Dim of Input, Last Dimension : Dim of Output
		
		if(dimensions.length != layer_types.length + 1) throw new Exception("Error in Layer Setting");
		
		int total_params_num = 0;
		
		for(int i = 0; i<dimensions.length - 1; i++){ // Allocate Memory for Weight matrices and Biases according to dimensions
			weight_Matrices.put((i+1),new Matrix(dimensions[i], dimensions[i+1]));// Index Start from 1
			biases.put((i+1), new Matrix(1, dimensions[i+1])); // Index Start from 1, Row Vector
			total_params_num += (dimensions[i] + 1) * dimensions[i+1];
		}
		for(int i = 0; i<layer_types.length; i++) h_layer_types.put((i+1), layer_types[i]);

		super.setParams(new Matrix("R", total_params_num, 1)); //Random Initialization
	}
	
	private void unpack_Params(HashMap<Integer, Matrix> weight_Matrices, HashMap<Integer, Matrix> biases, Matrix flattenedParams){
		//Unpack Flatten Parameters to Weight Matrices and Biases According to their Dimensions
		int t = 0;
		for(int i = 1; i<=weight_Matrices.size(); i++){
			
			//Unpacking Weight Matrix
			Matrix w = weight_Matrices.get(i);
			int[] dim = w.dim();
			Matrix temp = new Matrix(w.total_params_num(), 1);
			for(int j = t; j< t + w.total_params_num(); j++)
				temp.values[j-t][0] = flattenedParams.values[j][0];
			weight_Matrices.put(i, temp.reshape(dim[0], dim[1]));
			t += w.total_params_num();
			
			//Unpacking Bias
			Matrix b = biases.get(i);
			for(int j = t; j< t + b.total_params_num(); j++)
				b.values[0][j-t] = flattenedParams.values[j][0];
			biases.put(i, b);
			t += b.total_params_num();
		}
	}
	
	private CostGrad forward_backward_Propagation() throws Exception{
		
		//Forward Propagation
		Matrix h = super.getData();
		HashMap<Integer, Matrix> hidden_list = new HashMap<Integer, Matrix>(); // Index Start from 0 (h_0 = data, h_n+1 = y)
		HashMap<Integer, Matrix> hidden_input_list = new HashMap<Integer, Matrix>();
		hidden_list.put(0, h); // View X as First Hidden Layer
		for(int i = 1; i <= weight_Matrices.size(); i++){ // If #hidden = n then #weight_matrices = #biases = n+1	
					
			Matrix z = hidden_list.get(i-1).multiply(weight_Matrices.get(i)).add(biases.get(i)); // h_i, W_i+1, b_i+1
			hidden_input_list.put(i, z);
			
			//Activation by Functions
			String activation = h_layer_types.get(i);
			switch(activation){
				case "sigmoid":
					h = new Sigmoid().activate(z); 
					break;
				case "tanh":
					h = new HyperTangent().activate(z);
					break;
				case "softmax":
					h = new Softmax().activate(z);
					break;
				case "relu":
					h = new ReLU().activate(z);
					break;
				case "none":
					h = z;
					break;
				default:
					throw new Exception("Invalid Activation Function");
			}			
			hidden_list.put(i, h); // Save h_i+1
		}
		Matrix y = h; //h equals y at this point
		double cost = super.getCostFunction().cost(y, super.getLabel());
		Matrix grad_y = super.getCostFunction().grad(y, super.getLabel());
		
		//Backward Propagation
		HashMap<Integer, Matrix> grad_Z_list = new HashMap<Integer, Matrix>();
		HashMap<Integer, Matrix> grad_W_list = new HashMap<Integer, Matrix>();
		HashMap<Integer, Matrix> grad_b_list = new HashMap<Integer, Matrix>();
		
		int weight_matrix_num = weight_Matrices.size();
		for(int i = weight_matrix_num; i >= 1; i--){
			if(i == weight_matrix_num){ // i = n + 1
				
				String activation = h_layer_types.get(i);
				Matrix z = hidden_input_list.get(weight_matrix_num);
				Matrix grad_Z;
				switch(activation){
					case "sigmoid":
						grad_Z = new Sigmoid().grad(z).element_multiply(grad_y); 
						break;
					case "tanh":
						grad_Z = new HyperTangent().grad(z).element_multiply(grad_y);
						break;
					case "softmax":
						grad_Z = new Softmax().grad(z).element_multiply(grad_y);
						break;
					case "relu":
						grad_Z = new ReLU().grad(z).element_multiply(grad_y);
						break;
					case "none":
						grad_Z = grad_y;
						break;
					default:
						throw new Exception("Invalid Activation Function");
				}	
				grad_Z_list.put(i, grad_Z);
				grad_W_list.put(i, hidden_list.get(i-1).T().multiply(grad_Z));
				grad_b_list.put(i, grad_Z.sum(0));
			}
			else{
				Matrix z_i = hidden_input_list.get(i);
				Matrix h_i_prev = hidden_list.get(i-1);
				Matrix grad_h = grad_Z_list.get(i+1).multiply(weight_Matrices.get(i+1).T());
				
				//Gradient by Functions
				Matrix grad_Z;
				String activation = h_layer_types.get(i);
				switch(activation){
					case "sigmoid":
						grad_Z = new Sigmoid().grad(z_i).element_multiply(grad_h); 
						break;
					case "tanh":
						grad_Z = new HyperTangent().grad(z_i).element_multiply(grad_h);
						break;
					case "softmax":
						grad_Z = new Softmax().grad(z_i).element_multiply(grad_h);
						break;
					case "relu":
						grad_Z = new ReLU().grad(z_i).element_multiply(grad_h);
						break;
					case "none":
						grad_Z = grad_h;
						break;
					default:
						throw new Exception("Invalid Activation Function");
				}
				
				Matrix grad_W = h_i_prev.T().multiply(grad_Z);
				Matrix grad_b = grad_Z.sum(0);
				grad_Z_list.put(i, grad_Z);
				grad_W_list.put(i, grad_W);
				grad_b_list.put(i, grad_b);
			}
		}
		
		//Stack Gradients
		ArrayList<Matrix> grads = new ArrayList<Matrix>();
		for(int i = 1; i <= weight_matrix_num; i++){
			grads.add(grad_W_list.get(i).flatten());
			grads.add(grad_b_list.get(i).flatten());
		}
		return new CostGrad(cost, Matrix.concat(grads));
	}

	@Override
	public CostGrad cost_grad_at_givenParams(Matrix flattenedParams) throws Exception{
		unpack_Params(weight_Matrices, biases, flattenedParams);
		return forward_backward_Propagation();
	}
}
