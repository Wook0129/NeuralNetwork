package model;

import ds.CostGrad;
import ds.Matrix;

public abstract class Model {
	public Matrix params;
	public abstract CostGrad forward_backward_prop(Matrix data, Matrix label, Matrix flattenedParams);
}
