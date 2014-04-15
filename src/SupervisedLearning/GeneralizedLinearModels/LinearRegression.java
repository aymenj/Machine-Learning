package SupervisedLearning.GeneralizedLinearModels;

import java.util.Arrays;
import java.util.Random;

public class LinearRegression {
	
	public static double learning_rate;
	public static double error_lim;
	
	
	/**
	 * Constructor
	 */
	public LinearRegression(double learning_rate,double error_lim) {
		this.learning_rate = learning_rate;
		this.error_lim = error_lim;
	}
	
	/**
	 * Constructor without initialization of the learning rate
	 */
	public LinearRegression() {
		this.learning_rate = 0.0001;
		this.error_lim = 0.001;
	}
	
	/**
	 * Fit the Parameters using the Least-Square Method.
	 * @param 
	 */
	public static void fit(Double[][] X, Double[] Y){
		int Nfeatures = X[0].length;
		int Nobservations = X.length;
		
		Double[] betas  = new Double[Nfeatures+1];
		Double[] gradient = new Double[Nfeatures+1];
		Double[] residuals = new Double[Nobservations];
		
		//Initialize Betas Randomly
		Random random = new Random();
		for(int l=0;l<betas.length;++l)
			betas[l] = random.nextDouble();
		
		//Apply Gradient Descent
		double SSE = 1.0;
		double prev_SSE = 100.0;
		double e = Math.abs((SSE-prev_SSE)/prev_SSE);
		System.out.println("First error"+e);
		int counter = 1;
		while(e>error_lim){
			//Evaluate residuals
			
			for(int i=0;i<Nobservations;++i){
				double temp = betas[0] - Y[i];
				for(int j=0;j<Nfeatures;++j)
					temp += betas[j+1]*X[i][j];
				residuals[i] = temp;
			}
			
			//Calculate Gradient 
			
			for(int l=0;l<gradient.length;++l){
				double val = 0.0;
				for(int i=0;i<Nobservations;++i){
					if(l==0)
						val+= residuals[i];
					else
						val+= residuals[i]*X[i][l-1];
				}
				gradient[l] = val;
			}
			
			//Update betas
			for (int b=0;b<betas.length;++b)
				betas[b] -= learning_rate*gradient[b];
			
			//Calculate new SSE
			prev_SSE = SSE;
			SSE = 0.0;
			for(int i=0;i<Nobservations;++i){
				double temp = betas[0] - Y[i];
				for(int j=0;j<Nfeatures;++j)
					temp += betas[j+1]*X[i][j];
				SSE += Math.pow(temp,2);
			}
			
			e = Math.abs((SSE-prev_SSE)/prev_SSE);
			System.out.println("SSE at loop "+counter+" = "+SSE);
			System.out.println("Relative Error at loop "+counter+" = "+e);
			++counter;
		}
		System.out.println("===============================");
		System.out.println("Least Square Regression Results");
		System.out.println("Coefficients Values = "+ Arrays.toString(betas));
		System.out.println("Final SSE = "+SSE);
		
	}
	
}
