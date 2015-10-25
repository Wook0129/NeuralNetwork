package ds;

public class Matrix {

	int row_num;
	int col_num;
	double[][] values;

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

	public Matrix(String arg, int dim) throws Exception{
		this.row_num = dim;
		this.col_num = dim;
		this.values = new double[dim][dim];
		if(arg.equals("I")){ //Identity Matrix
			for(int i = 0; i<dim; i++){
				values[i][i] = 1;
			}
		}
		else if(arg.equals("1")){ //All one Matrix
			for(int i = 0; i<dim; i++){
				for(int j = 0; j<dim; j++){
					values[i][j] = 1;
				}
			}
		}
		else{
			throw new Exception("Invalid Argument");
		}
	}

	public Matrix multiply(Matrix m) throws Exception{
		if(this.col_num == m.row_num){
			Matrix result = new Matrix(this.row_num, m.col_num);
			for(int i = 0; i<this.row_num; i++){
				for(int j = 0; j<m.col_num; j++){
					for(int k = 0; k<m.row_num; k++){
						result.values[i][j] += this.values[i][k] * m.values[k][j];
					}
				}
			}
			result.col_num = m.col_num;
			result.row_num = this.row_num;

			return result;
		}
		else{
			throw new Exception("Infeasible Operation");
		}	
	}

	public Matrix multiply(double d) throws Exception{

		for(int i = 0; i<this.row_num; i++){
			for(int j = 0; j<this.col_num; j++){
				this.values[i][j] *= d;
			}
		}
		return this;
	}

	public Matrix divide(double d) throws Exception{

		if(d == 0) throw new Exception("Cannot Divide by 0");

		for(int i = 0; i<this.row_num; i++){
			for(int j = 0; j<this.col_num; j++){
				this.values[i][j] /= d;
			}
		}
		return this;
	}

	public Matrix divide(Matrix m) throws Exception{

		if(this.row_num == m.row_num && this.col_num == m.col_num){
			for(int i = 0; i<this.row_num; i++)
				for(int j = 0; j<this.col_num; j++)
					this.values[i][j] /= m.values[i][j];
		}
		else if(this.row_num - 1 > 0 || this.col_num - 1 == 0){ //Column vector
			for(int i = 0; i<this.row_num; i++)
				for(int j = 0; j<this.col_num; j++)
					this.values[i][j] /= m.values[i][0];
		}
		else throw new Exception("Infeasible Operation");

		return this;
	}

	public Matrix element_multiply(Matrix m) throws Exception{
		if(this.row_num == m.row_num && this.col_num == m.col_num){
			for(int i = 0; i<row_num; i++){
				for(int j = 0; j<col_num; j++){
					this.values[i][j] *= m.values[i][j];
				}
			}
		}
		else{
			throw new Exception("Infeasible Operation");
		}
		return this;
	}

	public Matrix add(Matrix m) throws Exception{
		if(this.row_num == m.row_num && this.col_num == m.col_num){
			for(int i = 0; i<row_num; i++){
				for(int j = 0; j<col_num; j++){
					this.values[i][j] += m.values[i][j];
				}
			}
		}
		else{
			throw new Exception("Infeasible Operation");
		}
		return this;
	}

	public Matrix subtract(Matrix m) throws Exception{
		if(this.row_num == m.row_num && this.col_num == m.col_num)
			for(int i = 0; i<row_num; i++)
				for(int j = 0; j<col_num; j++)
					this.values[i][j] -= m.values[i][j];
		else if(this.row_num == m.row_num && m.col_num == 1){ //Subtract by each Column
			for(int i = 0; i<row_num; i++)
				for(int j = 0; j<col_num; j++)
					this.values[i][j] -= m.values[i][0];
		}
		else throw new Exception("Infeasible Operation");
		return this;
	}

	public Matrix T(){

		int temp = this.row_num;
		this.row_num = this.col_num;
		this.col_num = temp;
		double[][] values = new double[row_num][col_num];
		for(int i = 0; i<row_num; i++){
			for(int j = 0; j<col_num; j++){
				values[i][j] = this.values[j][i];
			}
		}
		this.values = values;
		return this;
	}

	public Matrix exp(){
		for(int i = 0; i<row_num; i++)
			for(int j = 0; j<col_num; j++)
				this.values[i][j] = Math.exp(values[i][j]);
		return this;
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
		else{
			throw new Exception("Invalid Argument");
		}
		return result;
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
