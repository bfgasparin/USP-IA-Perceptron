import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * 
 * Este Classe é a implementação do Single-Layered Perceptron 
 *
 * Obs: Todo o código foi escrito em ingles para melhor leitura  
 * do mesmo e por ser considerado boa prática. Os comentários
 * estão em português por se tratar de um trabalho acadêmico 
 * com objetivo de ser avaliado pela docente. 
 *
 */
public class Perceptron {
	

	public int nbrInputNeurons; // número de neurônios na camada de entrada
	public int nbrOutputNeurons; // número de neurônios na camada de saída
	//int nbrInstancces = instances.length; // número de instancias apresentados em cada época

	public double[][] weights;

	public double bias; // O bias do perceptron

	public double learningFactor; // Taxa de aprendizado dos neurônios da rede

	public double activationThreshold; //Limiar de ativação dos neurônios da rede

	/**
	  * Esta é a matriz de dados de entrada. O conjunto de dados de treinamento que 
	  */
	public double[][] patterns;

	/**
	  * Esta é a matriz da resposta esperada.
	  */
	public int[][] targets;


	/**
	 * Construtor
	 *
	 * @param int[][] O conjunto de dados de entrada
	 * @param int[][] O conjunto de saídas esperadas da associados aos dados de entrada
	 * @param double learningFactor A taxa de aprendizado
	 * @param double activationThreshold O limiar de ativação
	 */
	public Perceptron(double[][] patterns, int[][] targets, double learningFactor, double activationThreshold) 
	{
		this.patterns = patterns;
		this.targets = targets;
		nbrInputNeurons = this.patterns[0].length; 
		nbrOutputNeurons = this.targets[0].length; 

		/**
		 * Constroi a matriz de pesos do perceptron.
		 * É necessário uma matriz carteziano pois cada neurônio de entrada 
		 * se ligará com cada neurônio de saída, e cada ligação terá um peso
		 * que deverá ser corrigido durante a etapa de aprendizado
		 * Somamos mais um aos neuronios de entrada pois todo neurônio recebe a 
		 * bias de entrada também
		 */
	    this.weights = new double[nbrInputNeurons][nbrOutputNeurons];

	    this.bias = 0; // O peso da bias
	    this.learningFactor = learningFactor; //Taxa de aprendizado
	    this.activationThreshold = activationThreshold; //Limiar de ativação
	}

	/**
     * Mostra os pesos das conexões na tela 
     */
	protected void printWeights()
	{
		for (int i = 0; i < this.weights.length; i++) {
		    for (int j = 0; j < this.weights[i].length; j++) {
		        NumberFormat f = NumberFormat.getInstance();
		        if (f instanceof DecimalFormat) {
		            DecimalFormat decimalFormat = ((DecimalFormat) f);
		            decimalFormat.setMaximumFractionDigits(1);
		            decimalFormat.setMinimumFractionDigits(1);
		            System.out.print(" NE"+(i+1)+"  => NS"+(j+1)+": " + f.format(this.weights[i][j]));
		        }
		    }
		    System.out.println();
		}
		for (int j = 0; j < this.weights[0].length; j++) {
			System.out.print(" Bias => NS"+(j+1)+": " + this.bias);
		}

		System.out.println();
		// System.out.println();
		// System.out.println("Legenda:");
		// System.out.println("   NE = Neurônio de entrada");
		// System.out.println("   NE = Neurônio de saída");
	}	

	/**
	 * Função que le todos os dados de uma época e chama a função d atualização de peso
	 * das conexões
	 *
	 * @return True se todas as respostas da rede foram iguais as respostas esperadas. False caso contrário
	 */
	protected boolean train()
	{
		 double entry = 0;
		 boolean $allRight = true;

		 for (int i = 0; i < this.patterns.length; i++) {
		 		//cada registro de result representa o valor de cada reurônio de saída da rede
		 		int[] result = new int[nbrOutputNeurons]; 
		 	    
		 	    //para cada neurônio de saída, calcula qual o seu valor e armazena em result
		 	    for (int j = 0; j < nbrOutputNeurons; j++) {	
					entry = this.getEntryData(i, j);	
					result[j] = this.calculateActivationFunction(entry);
				}

				/**
				* Chama a função fixNet para alterar os pesos das
				* conexões caso o result não seja o esperado
				*/
		 		if(this.fixNet(result, i)) $allRight = false;
		 }
		 
		 return $allRight;
	}

	/**
	 * Retorna o valor de entrada do neurónio de acordo o dados de entrada 
	 * da linha patterns[input] 
	 * para o respectivo neurônio de saida output
	 *
	 * @return O valor de entrada
	 */
	protected double getEntryData(int input, int output)
	{
		double yin = 0;
		//recupera os dados da epoca
		double[] trainingData = this.patterns[input];
		
		//Calcula o valor de entrada (somatória)
		for (int i = 0; i < trainingData.length ;i++) {
			double[] weights = this.weights[i];
			// (yin é o valor de entrada do neurônio, de acrodo no material de estudos da professora)
			yin += trainingData[i]*weights[output];
		}
		return yin + 1*this.bias;//adiciona o peso da bios
	}

	/**
	 * calcula da função de ativação 
	 */
	protected int calculateActivationFunction(double entry)
	{
			if (entry > this.activationThreshold){
		        return 1;
		    }else if (entry >= -(this.activationThreshold) && entry <= this.activationThreshold){
		        return 0;
		    }else{
		    	return -1;
		    }
	}

	/**
	 * Altera os pesos de todas as conexões caso o 
	 * result não seja o esperado. O input serve para saber 
	 * em qual patterns está sendo trabalhado
	 * @return retorna true se algum peso foi alterado. False caso contrário
	 */
	protected boolean fixNet(int[] result, int input)
	{
		boolean fixed = false;
		    // para cada neurónio de saída, verifica se o resultado era o esperado
			for (int i = 0; i < result.length ; i++) {
				//verificação do resultado
				if(result[i] != this.targets[input][i]){// se for diferente corrige os pesos

					for (int j = 0; j < this.weights.length ; j++) {
							fixed = true;
							this.weights[j][i] = this.weights[j][i] + learningFactor*targets[input][i]*patterns[input][j];
					}
					//Não pode esquecer de atualizar o peso da bias
					this.bias = this.bias + learningFactor*targets[input][i];
				}
			}				
		return fixed;
	}

	public static void main(String args[]) throws Exception
	{
		
		boolean stop = false; 
		int epoca = 1;
		int problemType = Integer.parseInt((args[6]));

		NeuralNetDataHandlerInterface handler;

		//Idenfitica qual é o problema para escolher qual Filehandler irá utilizar
		if(0 == problemType){
		 	handler = new BreastCancerWisconsinFileHandler(args[0], !(0 == Integer.parseInt((args[7]))));
		}else if(1 == problemType){
			throw new Exception("O problema Optical Recognition of Handwritten Digits ainda não está inplementado");
		}else{
			throw new Exception("Você deve passar 0 ou 1 no argumento de Tipo de Problema");
		}

		//Busca os dados de treinamento
		double[][] trainingSet = handler.getTrainingSet();
		int[][] targets = handler.getTrainingTargetsSet();

		//Inicia a rede neural
		Perceptron perceptron = new Perceptron(trainingSet, targets, Double.parseDouble(args[3]), 0.5);
		
		System.out.println("Taxa de aprendizado: " + perceptron.learningFactor);
		System.out.println("Limiar de ativação: " + perceptron.activationThreshold);
		System.out.println();
		System.out.println("Pesos dos axiomas antes do treinamento: ");

		perceptron.printWeights();

		while(!stop){
			//Treina a rede reural
			stop = perceptron.train();	
			System.out.println();
			System.out.println("Pesos dos axiomas depois da epoca: "+epoca++);
			perceptron.printWeights();
		}
		
	}



}