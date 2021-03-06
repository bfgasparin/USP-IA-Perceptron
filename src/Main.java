import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.util.Date;

/**
 * 
 * Este Classe é a classe principal do projeto
 *
 */
public class Main 
{
	public static void main(String args[]) throws Exception
	{
		String logPath;

		try{
			 logPath = args[8];
		}catch(ArrayIndexOutOfBoundsException e){
			logPath = "";
		}
		boolean stop = false; 
		int epoca = 1;
		int problemType = Integer.parseInt((args[6]));
		double traTotalSquaredError; 
		double valTotalSquaredError; 
		double tesTotalSquaredError; 
		double[] result; 
		Main main = new Main();
		NeuralNetDataHandlerInterface handler;
		boolean randomTraining = !(0 == Integer.parseInt((args[7])));
		java.util.Date date = new java.util.Date();

		//Idenfitica qual é o problema para escolher qual Filehandler irá utilizar
		if(0 == problemType){
		 	handler = new BreastCancerWisconsinFileHandler(args[0], args[1], args[2]);
		}else if(1 == problemType){
			throw new Exception("O problema Optical Recognition of Handwritten Digits ainda não está inplementado");
		}else if(2 == problemType){
		 	handler = new XORFileHandler(args[0], args[1], args[2]);	
		}else if(3 == problemType){
		 	handler = new ANDFileHandler(args[0], args[1], args[2]);	
		}else{
			throw new Exception("Você deve passar 0 ou 1 no argumento de Tipo de Problema");
		}

		//Busca os dados de treinamento
		double[][] validationSet = handler.getValidationSet();
		double[][] validationTargets = handler.getValidationTargetsSet();
		double[][] testSet = handler.getTestSet();
		double[][] testTargets = handler.getTestTargetsSet();


		//Inicia a rede neural
		MultiLayeredPerceptron perceptron = new MultiLayeredPerceptron(validationSet[0].length, validationTargets[0].length, Double.parseDouble(args[3]), Integer.parseInt(args[4]));
		if(!(0 == Integer.parseInt((args[5])))){
			perceptron.randomWeights();
		}	
		
		System.out.println("Taxa de aprendizado: " + perceptron.learningFactor);
		System.out.println("Numero de neuronios camada de entrada: " + perceptron.nbrInputNeurons);
		System.out.println("Numero de neuronios camada escondida: " + perceptron.nbrHiddenNeurons);
		System.out.println("Numero de neuronios camada de saída: " + perceptron.nbrOutputNeurons);
		System.out.println();
        try{  
			while(!stop){
				traTotalSquaredError = 0;
				valTotalSquaredError = 0;
				System.out.println("-----------------------------");
				System.out.println("Época: "+epoca++ + " ...  ");
				System.out.print("   Treinamento...   ");
				double[][] trainingSet = handler.getTrainingSet(randomTraining);
				double[][] targets = handler.getTrainingTargetsSet();
				for (int i = 0; i < trainingSet.length; i++) {
					//Treina a rede reural				
					result = perceptron.train(trainingSet[i], targets[i]);	
					traTotalSquaredError += main.calculateSquaredError(result, targets[i]);
				}
				System.out.print("  Erro quadrado total: " + traTotalSquaredError);
				System.out.println("");
				System.out.print("   Validação...     ");
				for (int i = 0; i < validationSet.length; i++) {
					//Treina a rede reural				
					result = perceptron.execute(validationSet[i]);	
					valTotalSquaredError += main.calculateSquaredError(result, validationTargets[i]);
				}
				System.out.print("  Erro quadrado total: " + valTotalSquaredError);
				System.out.println("");
				System.out.println("");

				if(valTotalSquaredError < 0.05) stop = true;
				//if(epoca == 400) stop = true;
			}
            File file = new File(logPath + "problem_"+problemType+"_" + date.getTime() + ".csv"); 
	        FileOutputStream fOut = new FileOutputStream(file);
	        BufferedOutputStream out = new BufferedOutputStream(fOut);  
			tesTotalSquaredError = 0;
			double squareError = 9;
			System.out.println("");
			System.out.print("   Teste...         ");
			out.write(String.valueOf("Erro Quadrado;").getBytes());
    		out.write(13);
			for (int i = 0; i <testSet.length; i++) {
				//Treina a rede reural				
				result = perceptron.execute(testSet[i]);	
				squareError = main.calculateSquaredError(result, testTargets[i]);
				tesTotalSquaredError += main.calculateSquaredError(result, testTargets[i]);
		        out.write(String.valueOf(squareError+";").getBytes());
		        out.write(13);
			}
			System.out.print("  Erro quadrado total: " + tesTotalSquaredError);
			System.out.println("");
			System.out.println("");
			out.write(13);
			out.write(String.valueOf("Erro Quadrado Total;").getBytes());
			out.write(String.valueOf(tesTotalSquaredError+";").getBytes());
            out.close();  
        }catch(Exception e){  
            System.out.println("ERRO: " + e.getMessage());
            throw e;
        }  
	}

	public void printWeights(double[][] weights, String spaces){
		for (int i = 0; i < weights.length; i++) {
			System.out.print(spaces); 	
	 		for (int j = 0; j < weights[i].length; j++) {
	 			System.out.print(weights[i][j] + ", "); 	
			}
			System.out.println(""); 	
		}
	}


	/**
	 * Calcula o erro quadrado
	 *
	 * @param double[]  result		A resposta (sinal) da rede
	 * @param double[]  target		A unidade de saída esperada para a unidade de entrada
	 * @return O erro quadrado
	 */
	public double calculateSquaredError(double[] result, double[] target)
	{		
		double totalRrror = 0;
		double error = 0;
	 	for (int i = 0; i < result.length; i++) {
	 			error += target[i]-result[i];
	 			totalRrror += error*error;
		}

		return totalRrror;
	}
}