package ds;

import java.util.ArrayList;
import java.util.Iterator;

public class Matrix {

	public int row_num;
	public int col_num;
	public double[][] values;

	public Matrix(int row_num, int col_num){
		this.row_num = row_num;
		this.col_num = col_num;
		this.values = new double[row_num][col_num]; //Initialize as Zero matrix
	}

	public Matrix(double[] values){
		this.values = new double[values.length][1];
		for(int i = 0; i<values.length; i++) this.values[i][0] = values[i];
		this.row_num = values.length;
		this.col_num = 1;
	}

	public Matrix(double[][] values){
		this.row_num = values.length;
		this.col_num = values[0].length;
		this.values = values;
	}

	public Matrix(String arg, int row_num, int col_num) throws Exception{
		this.row_num = row_num;
		this.col_num = col_num;
		this.values = new double[row_num][col_num];
		if(arg.equals("I")){ //Identity Matrix
			if(row_num != col_num) throw new Exception("Invalid Argument");
			else for(int i = 0; i<row_num; i++) values[i][i] = 1;
		}
		else if(arg.equals("1")) //All one Matrix
			for(int i = 0; i<row_num; i++)
				for(int j = 0; j<col_num; j++)
					values[i][j] = 1;
		else if(arg.equals("R")) //Random Number Matrix
			for(int i = 0; i<row_num; i++)
				for(int j = 0; j<col_num; j++)
					values[i][j] = Math.random();
		else throw new Exception("Invalid Argument");
	}

	public Matrix multiply(Matrix m) throws Exception{
		if(this.col_num == m.row_num){
			Matrix result = new Matrix(this.row_num, m.col_num);
			for(int i = 0; i<this.row_num; i++)
				for(int j = 0; j<m.col_num; j++)
					for(int k = 0; k<m.row_num; k++)
						result.values[i][j] += this.values[i][k] * m.values[k][j];
			result.col_num = m.col_num;
			result.row_num = this.row_num;
			return result;
		}
		else throw new Exception("Infeasible Operation");	
	}

	public Matrix multiply(double d) throws Exception{
		Matrix result = new Matrix(this.row_num, this.col_num);
		for(int i = 0; i<this.row_num; i++)
			for(int j = 0; j<this.col_num; j++)
				result.values[i][j] = d * this.values[i][j];
		return result;
	}

	public Matrix divide(double d) throws Exception{
		Matrix result = new Matrix(this.row_num, this.col_num);
		if(d == 0) throw new Exception("Cannot Divide by 0");
		for(int i = 0; i<this.row_num; i++)
			for(int j = 0; j<this.col_num; j++)
				result.values[i][j] = this.values[i][j] / d;
		return result;
	}

	public Matrix divide(Matrix m) throws Exception{
		Matrix result = new Matrix(this.row_num, this.col_num);
		if(this.row_num == m.row_num && this.col_num == m.col_num)
			for(int i = 0; i<this.row_num; i++)
				for(int j = 0; j<this.col_num; j++)
					result.values[i][j] = this.values[i][j] / m.values[i][j];

		else if(this.row_num - 1 > 0 || this.col_num - 1 == 0) //Column vector
			for(int i = 0; i<this.row_num; i++)
				for(int j = 0; j<this.col_num; j++)
					result.values[i][j] = this.values[i][j] / m.values[i][0];

		else throw new Exception("Infeasible Operation");

		return result;
	}

	public Matrix element_multiply(Matrix m) throws Exception{
		Matrix result = new Matrix(this.row_num, this.col_num);
		if(this.row_num == m.row_num && this.col_num == m.col_num)
			for(int i = 0; i<row_num; i++)
				for(int j = 0; j<col_num; j++)
					result.values[i][j] = this.values[i][j] * m.values[i][j];

		else throw new Exception("Infeasible Operation");
		return result;
	}

	public Matrix add(Matrix m) throws Exception{
		Matrix result = new Matrix(this.row_num, this.col_num);
		if(this.row_num == m.row_num && this.col_num == m.col_num)
			for(int i = 0; i<row_num; i++)
				for(int j = 0; j<col_num; j++)
					result.values[i][j] = this.values[i][j] + m.values[i][j];

		else if(this.row_num == m.row_num && m.col_num == 1) //Add by each Column
			for(int i = 0; i<row_num; i++)
				for(int j = 0; j<col_num; j++)
					result.values[i][j] = this.values[i][j] + m.values[i][0];

		else if(this.col_num == m.col_num && m.row_num == 1) //Add by each Row
			for(int i = 0; i<row_num; i++)
				for(int j = 0; j<col_num; j++)
					result.values[i][j] = this.values[i][j] + m.values[0][j];

		else throw new Exception("Infeasible Operation");
		return result;
	}

	public Matrix subtract(Matrix m) throws Exception{
		Matrix result = new Matrix(this.row_num, this.col_num);
		if(this.row_num == m.row_num && this.col_num == m.col_num)
			for(int i = 0; i<row_num; i++)
				for(int j = 0; j<col_num; j++)
					result.values[i][j] = this.values[i][j] - m.values[i][j];
		else if(this.row_num == m.row_num && m.col_num == 1) //Subtract by each Column
			for(int i = 0; i<row_num; i++)
				for(int j = 0; j<col_num; j++)
					result.values[i][j] = this.values[i][j] - m.values[i][0];

		else if(this.col_num == m.col_num && m.row_num == 1) //Subtract by each Row
			for(int i = 0; i<row_num; i++)
				for(int j = 0; j<col_num; j++)
					result.values[i][j] = this.values[i][j] - m.values[0][j];

		else throw new Exception("Infeasible Operation");
		return result;
	}

	public Matrix T(){
		Matrix result = new Matrix(this.col_num, this.row_num);
		for(int i = 0; i<col_num; i++)
			for(int j = 0; j<row_num; j++)
				result.values[i][j] = this.values[j][i];
		return result;
	}

	public Matrix exp(){
		Matrix result = new Matrix(this.row_num, this.col_num);
		for(int i = 0; i<row_num; i++)
			for(int j = 0; j<col_num; j++)
				if (values[i][j] > 30)
					result.values[i][j] = Math.exp(30.1);
				else if (values[i][j] < -30)
					result.values[i][j] = 0.00000000000000000000001;
				else
					result.values[i][j] = Math.exp(values[i][j]);
		return result;
	}
	
	public Matrix log(){
		Matrix result = new Matrix(this.row_num, this.col_num);
		for(int i = 0; i<row_num; i++)
			for(int j = 0; j<col_num; j++)
				result.values[i][j] = Math.log10(values[i][j]);
		return result;
	}

	public Matrix sum(int axis) throws Exception{
		Matrix result;
		if(axis == 0){
			result = new Matrix(1, this.col_num);
			for(int i = 0; i<col_num; i++){
				double row_sum = 0;
				for(int j = 0; j<row_num; j++)
					row_sum += this.values[j][i];
				result.values[0][i] = row_sum;
			}
		}
		else if(axis == 1){
			result = new Matrix(this.row_num, 1);
			for(int i = 0; i<row_num; i++){
				double col_sum = 0;
				for(int j = 0; j<col_num; j++)
					col_sum += this.values[i][j];
				result.values[i][0] = col_sum;
			}
		}
		else{
			throw new Exception("Invalid Argument");
		}
		return result;
	}

	public double sum_all(){
		double result = 0;
		for(int i = 0; i<row_num; i++)
			for(int j = 0; j<col_num; j++)
				result += this.values[i][j];
		return result;
	}

	public Matrix max(int axis) throws Exception{
		Matrix result;
		if(axis == 0){
			result = new Matrix(1, this.col_num);
			for(int i = 0; i<col_num; i++){
				double row_max = 0;
				for(int j = 0; j<row_num; j++)
					if(this.values[j][i] > row_max) row_max = this.values[j][i];
				result.values[0][i] = row_max;
			}
		}
		else if(axis == 1){
			result = new Matrix(this.row_num, 1);
			for(int i = 0; i<row_num; i++){
				double col_max = 0;
				for(int j = 0; j<col_num; j++)
					if(this.values[i][j] > col_max) col_max = this.values[i][j];
				result.values[i][0] = col_max;
			}
		}
		else throw new Exception("Invalid Argument");
		return result;
	}
	
	public Matrix reshape(int new_row_num, int new_col_num){
		Matrix result = new Matrix(new_row_num, new_col_num);
		ArrayList<Double> temp = new ArrayList<Double>();
		for(int i = 0; i<this.row_num; i++)
			for(int j = 0; j<this.col_num; j++)
				temp.add(this.values[i][j]);
		Iterator<Double> iter = temp.iterator();
		for(int i = 0; i<new_row_num; i++)
			for(int j = 0; j<new_col_num; j++)
				result.values[i][j] = iter.next();
		return result;
	}
	public static Matrix concat(ArrayList<Matrix> matrices) throws Exception{
		ArrayList<Double> temp = new ArrayList<Double>();
		int axis = 0;
		Matrix first_Matrix = matrices.get(0);
		Iterator<Matrix> matrix_iter = matrices.iterator();
		
		while(matrix_iter.hasNext()){
			Matrix matrix = matrix_iter.next();
			if(first_Matrix.col_num == 1 && matrix.col_num == 1)
				for(int j = 0; j<matrix.row_num; j++) temp.add(matrix.values[j][0]);
			else if(first_Matrix.row_num == 1 && matrix.row_num == 1){
				for(int j = 0; j<matrix.col_num; j++) temp.add(matrix.values[0][j]);
				axis = 1;
			}
			else throw new Exception("Infeasible Operation");
		}
		Matrix result;
		Iterator<Double> iter = temp.iterator();
		if(axis == 1) {
			result = new Matrix(1, temp.size());
			for(int i = 0; i<temp.size(); i++)
				result.values[0][i] = iter.next();
		}
		else {
			result = new Matrix(temp.size(), 1);
			for(int i = 0; i<temp.size(); i++)
				result.values[i][0] = iter.next();
		}
		return result;
	}
	public int[] dim(){
		return new int[]{row_num, col_num};
	}
	public Matrix flatten(){
		Matrix result;
		result = this.reshape(this.row_num * this.col_num, 1);
		return result;
	}
	public int total_params_num(){
		return row_num * col_num;
	}
	@Override
	public String toString(){
		String s = "";
		for(int i = 0; i<this.row_num; i++){
			s +=  "[";
			for(int j = 0;j<this.col_num; j++)
				s += values[i][j] + ",";
			s = s.substring(0, s.length() - 1) + "]\n";
		}
		return s;
	}
}
