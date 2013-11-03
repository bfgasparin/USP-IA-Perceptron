import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.lang.Math;
import java.util.Random;

/**
 * 
 * Este Classe é a classe principal do projeto
 *
 */
public class Main 
{
	public static void main(String args[]) throws Exception
	{
		boolean stop = false; 
		int epoca = 1;
		int problemType = Integer.parseInt((args[6]));
		double error = 0; 
		double[] result; 
		Main main = new Main();
		NeuralNetDataHandlerInterface handler;
		boolean randomTraining = !(0 == Integer.parseInt((args[7])));

		//Idenfitica qual é o problema para escolher qual Filehandler irá utilizar
		if(0 == problemType){
		 	handler = new BreastCancerWisconsinFileHandler(args[0], args[1], args[2]);
		}else if(1 == problemType){
			throw new Exception("O problema Optical Recognition of Handwritten Digits ainda não está inplementado");
		}else if(2 == problemType){
		 	handler = new XORFileHandler(args[0], args[1], args[2]);	
		}else{
			throw new Exception("Você deve passar 0 ou 1 no argumento de Tipo de Problema");
		}

		//Busca os dados de treinamento
		double[][] trainingSet = handler.getTrainingSet(randomTraining);
		double[][] targets = handler.getTrainingTargetsSet();

		//Inicia a rede neural
		MultiLayeredPerceptron perceptron = new MultiLayeredPerceptron(trainingSet[0].length, targets[0].length, Double.parseDouble(args[3]), Integer.parseInt(args[4]));
		if(!(0 == Integer.parseInt((args[5])))){
			perceptron.randomWeights();
		}	
		
		// System.out.println("Taxa de aprendizado: " + perceptron.learningFactor);
		// System.out.println("Numero de neuronios camada de entrada: " + perceptron.nbrInputNeurons);
		// System.out.println("Numero de neuronios camada escondida: " + perceptron.nbrHiddenNeurons);
		// System.out.println("Numero de neuronios camada de saída: " + perceptron.nbrOutputNeurons);
		// System.out.println();

		while(!stop){
			error = 0;
			System.out.print("Época: "+epoca++ + " ... ");
			for (int i = 0; i < trainingSet.length; i++) {
				// System.out.println("------------------------------------");
				// System.out.println("  Pesos camada saída: (antes do treinamento) ");
				// perceptron.printWeights(perceptron.outputWeights);

				//Treina a rede reural				

				result = perceptron.train(trainingSet[i], targets[i]);	
				error += main.calculateSquaredError(result, targets[i]);
				// System.out.println("  Pesos camada escondida: ");
				// perceptron.printWeights(perceptron.hiddenWeights);
				// System.out.println("  Pesos camada saída: (depois do treinamento)");
				// perceptron.printWeights(perceptron.outputWeights);
				// System.out.println("------------------------------------");
				//System.out.println("");
			}	
			System.out.print("  Erro quadrado total: " + error);
			System.out.println("");
			//System.out.println("");
			if(epoca == 3000) stop = true;
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