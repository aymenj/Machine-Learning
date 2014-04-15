import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import SupervisedLearning.GeneralizedLinearModels.LinearRegression;;

public class Main {
	
	public static Function<String, Double> stringToDouble = new Function<String, Double>(){
        @Override
        public Double apply(String from) {
            return Double.parseDouble(from);
        }
    };
    
    public static Double[] selectColumnOnIndex(Double[][] X, int index){
    	Double[] res = new Double[X.length];
    	for(int i=0;i<X.length;++i){
    		res[i] = X[i][index];
    	}
    	
    	return res;
    }
    
    public static Double[][] selectColumnsOnIndices(Double[][] X, int[] indices){
    	assert(indices.length>1);
    	Double[][] res = new Double[X.length][indices.length];
    	for(int i=0;i<X.length;++i){
    		for(int j=0;j<indices.length;++j)
    			res[i][j] = X[i][indices[j]];
    	}
    	return res;
    }

    public static Double min(Double[][]A, int index){
    	if(A.length==0) return Double.NaN;
    	Double min=Double.POSITIVE_INFINITY;
    	for(int i=0;i<A.length;++i){
    		if(A[i][index]<min)
    			min = A[i][index];
    	}
    	return min;	
    }
    
    public static Double max(Double[][]A, int index){
    	if(A.length==0) return Double.NaN;
    	Double max=Double.NEGATIVE_INFINITY;
    	for(int i=0;i<A.length;++i){
    		if(A[i][index]>max)
    			max = A[i][index];
    	}
    	return max;	
    }
    
    public static Double mean(Double[][]A, int index){
    	if(A.length==0) return Double.NaN;
    	Double sum=0.0;
    	for(int i=0;i<A.length;++i){
    		sum += A[i][index];
    	}
    	return sum/A.length;
    }
    
    public static Double var(Double[][]A, int index){
    	if(A.length==0) return Double.NaN;
    	Double avg=mean(A,index);
    	Double sum = 0.0;
    	for(int i=0;i<A.length;++i){
    		sum += (A[i][index]-avg)*(A[i][index]-avg);
    	}
    	return sum/(A.length-1);
    }
    
    public static Double std(Double[][]A, int index){
    	return Math.sqrt(var(A,index));
    }
    
    public static Double[][] normalize(Double[][] A){
    	Double[][] Res = new Double[A.length][A[0].length];
    	for(int j=0;j<A[0].length;++j){
    		Double mean = mean(A, j);
    		Double sd = std(A, j);
    		for(int i=0;i<A.length;++i)
    			Res[i][j] = (A[i][j]-mean)/sd;
    	}
    	return Res;
    }
    
	public static void main(String[] args) throws FileNotFoundException {
		
		
		
		
		Scanner sc = new Scanner(new File("C://Users/Aymen/workspace/MachineLearning/src/houses.txt"));
		List<List<Double>> lines = new ArrayList<List<Double>>();
		while (sc.hasNextLine()) {
			String[] st = sc.nextLine().split(",");
			List<String> l  = Arrays.asList(st);
			List<Double> res  = Lists.transform(l, stringToDouble);
			lines.add(res);
		  
		}
		sc.close();
		
		Double[][] array = new Double[lines.size()][];
		Double[] blankArray = new Double[0];
		for(int i=0;i<lines.size();++i)
			array[i] = lines.get(i).toArray(blankArray);
		
		//System.out.println(Arrays.deepToString(array));
		
		LinearRegression lr = new LinearRegression();
		
		//Input and Target Data
		int[] indices = {0,1};
		Double[][] X = selectColumnsOnIndices(array, indices);
		Double[] Y = selectColumnOnIndex(array, 2);
		
		X = normalize(X);
		
		System.out.println(Arrays.deepToString(X));
		
		lr.fit(X, Y);
		

	}

}
