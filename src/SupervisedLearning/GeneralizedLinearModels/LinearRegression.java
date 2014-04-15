package SupervisedLearning.GeneralizedLinearModels;

import java.util.Arrays;
import java.util.Random;

public class LinearRegression {
	
	public static double learning_rate;
	
	
	/**
	 * Constructor
	 */
	public LinearRegression(double learning_rate) {
		this.learning_rate = learning_rate;
	}
	
	/**
	 * Fit the Parameters using the Least-Square Method.
	 * @param <T>
	 */
	public static void fit(double[][] X, double[] Y){
		int Nfeatures = X[0].length;
		int Nobservations = X.length;
		
		double[] betas  = new double[Nfeatures+1];
		double[] gradient = new double[Nfeatures+1];
		double[] residuals = new double[Nobservations];
		
		//Initialize Betas Randomly
		Random random = new Random();
		for(int l=0;l<betas.length;++l)
			betas[l] = random.nextDouble();
		
		//Apply Gradient Descent
		double error_lim = 0.001;
		double SSE = 1.0;
		double prev_SSE = 100.0;
		double e = Math.abs((SSE-prev_SSE)/prev_SSE);
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
					if(i==0)
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
		}
		System.out.println("Final Sum of Squared Error = "+SSE);
		System.out.println(Arrays.toString(betas));
	}
	
}
