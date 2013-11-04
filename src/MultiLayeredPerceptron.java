import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.lang.Math;
import java.util.Random;

/**
 * 
 * Este Classe é a implementação de um Mult-Layered Perceptron com camada escondida de um nível
 * (apenas uma camada escondida)
 *
 */
public class MultiLayeredPerceptron implements PerceptronInterface
{
	public int nbrInputNeurons; // número de neurônios na camada de entrada
	public int nbrOutputNeurons; // número de neurônios na camada de saída
	public int nbrHiddenNeurons; // número de neurônios na camada escondida
	//int nbrInstancces = instances.length; // número de instancias apresentados em cada época

	public double[][] outputWeights; // Matriz de pesos ligados aos neurônios da camada de saída
	public double[][] hiddenWeights; // Matriz de pesos ligados aos neurônios da camada escondida

	public double[] hiddenBiasWeights; // Os pesos das bias dos neurônios da camada escondida
	public double[] outputBiasWeights; // Os pesos das bias dos neurônios da camada de saída

	public double learningFactor; // Taxa de aprendizado dos neurônios da rede

	/**
	  * Esta é a matriz de dados de entrada. O conjunto de dados de treinamento que 
	  */
	public double[][] patterns;

	/**
	  * Esta é a matriz da resposta esperada.
	  */
	public double[][] targets;

	public boolean random;

	/**
	 * Resultado (sinais) dos reurônio da camada escondida 
	 */
	protected double[] hiddenResult; 

	public void randomWeights()
	{
		this.randomWeights(this.hiddenWeights);
		this.randomWeights(this.outputWeights);
	}

	/**
	 * Construtor
	 *
	 * @param int      nbrInputNeurons		Número de neorônios na camada de entrada
	 * @param int      nbrOuputNeurons		Número de neorônios na camada de saída
	 * @param double   learningFactor 		A taxa de aprendizado
	 * @param double   nbrHiddenNeurons 	Número de neurônios na camada escondida 
	 */
	public MultiLayeredPerceptron(int nbrInputNeurons, int nbrOutputNeurons, double learningFactor, int nbrHiddenNeurons) 
	{
		this.nbrInputNeurons = nbrInputNeurons;
		this.nbrOutputNeurons = nbrOutputNeurons; // O numero de neorônios na camada de saída é igual ao tamanho do conjunto de saídas esperadas
		this.nbrHiddenNeurons = nbrHiddenNeurons;

		/**
		 * Constroi a matriz de pesos entre a camada de entrada e a camada escondida
		 * É necessário uma matriz carteziano pois cada neurônio de entrada 
		 * se ligará com cada neurônio da camada escondida, e cada ligação terá um peso
		 * que deverá ser corrigido durante a etapa de treinamento
		 */
	    this.hiddenWeights = new double[nbrHiddenNeurons][nbrInputNeurons];
	    
		/**
		 * Constroi a matriz de pesos entre a camada escondida e a camada de saída
		 * É necessário uma matriz carteziano pois cada neurônio da camada escondida
		 * se ligará com cada neurônio da camada de saída, e cada ligação terá um peso
		 * que deverá ser corrigido durante a etapa de treinamento
		 */
	    this.outputWeights = new double[nbrOutputNeurons][nbrHiddenNeurons];
	    
		/**
		 * Constroi a matriz de pesos da bias da camada escondida
		 */
	    this.hiddenBiasWeights = new double[nbrHiddenNeurons];
		/**
		 * Constroi a matriz de pesos da bias da camada de saída
		 */
	    this.outputBiasWeights = new double[nbrOutputNeurons];
	    
	    this.learningFactor = learningFactor; //Taxa de aprendizado
	}

	protected void randomWeights(double[][] weights){
		double start = -0.5;
		double end = 0.5;
		Random random = new Random();

		for (int i = 0; i < weights.length; i++) {
	 		for (int j = 0; j < weights[i].length; j++) {
				double result = start + (random.nextDouble() * (end - start));
				weights[i][j] = result;
	 		}
	 	}
	}

	/**
	 * @inheritDoc
	 */	
	public double[] train(double[] pattern, double[] target)
	{
		 //executa a fase feedForward
		 double[] result = this.feedForward(pattern);
		 this.backPropagation(pattern, this.hiddenResult, result, target);	

		 return result;
	}

	/**
	 * @inheritDoc
	 */	
	public double[] execute(double[] pattern)
	{
		 //executa a fase feedForward
		 return this.feedForward(pattern);
	}


	/**
	 * Executa a fase feedForward do Perceptron 
	 *
	 * @param double[]  pattern		Uma unidade de entrada
	 * @return Um array com as respostas (sinal) dos neurônios da camada saída
	 */
	protected double[] feedForward(double[] pattern)
	{
		double entry = 0;

		//cada registro de this.hiddenResult representa o resultado (sinal) de cada reurônio da camada escondida
		this.hiddenResult = new double[this.nbrHiddenNeurons]; 
 		//cada registro de result representa o o resultado (sinal) de cada reurônio da camada de saída
 		double[] outputResult = new double[this.nbrOutputNeurons]; 
 	    
		/** 
		 * Para cada neurônio da camada escondida, calcula qual o seu valor de entrada, 
		 * aplica a função de ativação e armazena seu resultado (sinal) em this.hiddenResult
		 */
 	    for (int i = 0; i < nbrHiddenNeurons; i++) {	
			entry = this.getEntryData(pattern, hiddenWeights[i], this.hiddenBiasWeights[i]);	
			this.hiddenResult[i] = this.calculateActivationFunction(entry);
		} 	   

		entry = 0;

		/** 
		 * Para cada neurônio da camada de saída, calcula qual o seu valor de entrada 
		 * (untilizando os sinais da camada escondida), aplica a função de ativação e 
		 * armazerna seu resultado (sinal) em outputResult
		 */
 	    for (int i = 0; i < nbrOutputNeurons; i++) {	
			entry = this.getEntryData(this.hiddenResult, outputWeights[i], this.outputBiasWeights[i]);	
			outputResult[i] = this.calculateActivationFunction(entry);
		}

		return outputResult;
	}

	/**
	 * Retorna um valor de entrada de acordo a unidade de entrada 
	 * e os pesos passados
	 *
	 * @param double[]  pattern		 Uma unidade de entrada
	 * @param double[]  weigths		 Os pesos usados para calcular o valor de entrada
	 * @param double    biasWeigth	 O peso da bias usado para calcular o valor de entrada. 
	 * 								 If null, ignore bias weigth on operation
	 * @return O valor de entrada
	 */
	protected double getEntryData(double[] pattern, double[] weights, double biasWeight)
	{
		double yin = 0;
		
		yin = this.getEntryData(pattern, weights);
		
		//Adiciona o peso da bias do cálculo do valor de entrada	
		yin = yin+(1*biasWeight);
		

		return yin;
	}

	/**
	 * Retorna um valor de entrada de acordo a unidade de entrada 
	 * e os pesos passados
	 *
	 * @param double[]  pattern		 Uma unidade de entrada
	 * @param double[]  weigths		 Os pesos usados para calcular o valor de entrada
	 * @return O valor de entrada
	 */
	protected double getEntryData(double[] pattern, double[] weights)
	{
		double yin = 0;
		
		//Calcula o valor de entrada (somatória)
		for (int i = 0; i < pattern.length ;i++) {
			// (yin é o valor de entrada do neurônio, de acrodo no material de estudos da professora)
			yin += pattern[i]*weights[i];
		}

		return yin;
	}


	/**
	 * Executa a fase backPropagation do Perceptron 
	 *
	 * @param double[][]  patterns	       O conjunto de dados de entrada
	 * @param double[]    hiddenResult     O resultado (sinais) da camada escondida
	 * @param double[]    result		   O resultado (sinais) alcançados
	 * @param double[]    target		   A unidade de saída esperada para a unidade de entrada
	 */
	protected void backPropagation(double[] pattern, double[] hiddenResult, double[] result, double[] target)
	{
		/**
		 * Cada registro de outputInformationErrorTerm representa um termo de erro de informação 
		 * respectivo a um registro em result da camada de saída
		 */
		double[] outputInformationErrorTerm =  new double[result.length];
		
		/**
		 * Cada registro de outputInformationErrorTerm representa um termo de correção de erro 
		 * respectivo a um axionio entre cada neurônio da camada escondida com cada neurônio da 
		 * de saída
		 */
		double[][] outputWeightAdjustmentTerm =  new double[result.length][nbrHiddenNeurons];

		/**
		 * Termo de correção da bias bias da camada de saída
		 */
		double outputBiasAdjustmentTerm = 0;

		/**
		 * Cada registro de hiddenInformationErrorTerm representa um termo de erro de informação 
		 * respectivo a um registro em hiddenResult da camada escondida
		 */
		double[] hiddenInformationErrorTerm =  new double[hiddenResult.length];
		
		/**
		 * Cada registro de hiddenInformationErrorTerm representa um termo de correção de erro 
		 * respectivo a um axionio entre cada neurônio de entrada e a camada de camada escondida
		 */
		double[][] hiddenWeightAdjustmentTerm =  new double[nbrHiddenNeurons][pattern.length];

		/**
		 * Termo de correção da bias bias da camada escondida
		 */
		double hiddenBiasAdjustmentTerm = 0;


		//-- Comeca o back propagation
		
		/** 
		 * Para cada result calcula o termo de erro de informação, o termo de 
		 * correção de pesos e o termo de correção de bias
		 */
 	    for (int i = 0; i < result.length; i++) {	
			outputInformationErrorTerm[i] = this.calculateInformationErrorTerm(result[i], (target[i] - result[i]));	
			// calcula o termo de correção de pessos de cada conexão com este neurôbio
			outputWeightAdjustmentTerm[i] = this.calculateWeightAdjustmentTerms(hiddenResult, outputInformationErrorTerm[i]); 
			// calcula o termo de correção de bias de cada coneção com este neurôbio
			outputBiasAdjustmentTerm = this.learningFactor*outputInformationErrorTerm[i]; 
		}

		/** 
		 * Para cada hiddenResult calcula o termo de erro de informação, o termo de 
		 * correção de pesos e o termo de correção de bias
		 */
 	    for (int i = 0; i < hiddenResult.length; i++) {	
 	    	
 	    	double[] weights = new double[outputWeights.length];
 	    	for (int j = 0; j < outputWeights.length; j++) {	
 	    		weights[j] = outputWeights[j][i];
 	    	}
			hiddenInformationErrorTerm[i] = this.calculateInformationErrorTerm(hiddenResult[i], this.getEntryData(outputInformationErrorTerm, weights));	
			// calcula o termo de correção de pessos de cada conexão com este neurôbio
			hiddenWeightAdjustmentTerm[i] = this.calculateWeightAdjustmentTerms(pattern, hiddenInformationErrorTerm[i]); 
			// calcula o termo de correção de bias de cada coneção com este neurôbio
			hiddenBiasAdjustmentTerm = this.learningFactor*hiddenInformationErrorTerm[i]; 
		}

		// -- comeca a corrigir os pesos

		/** 
		 * Corrigi os pesos dos axionios entra a camada escondida e a camada de saída
		 */
		 this.fixWeights(outputWeights, outputWeightAdjustmentTerm);
		
		/** 
		 * Corrigi os pesos da bias da camada de saída
		 */
		this.fixBiasWeights(outputBiasWeights, outputBiasAdjustmentTerm);

		/** 
		 * Corrigi os pesos dos axionios entra a camada de entrada e a camada escondida
		 */
		this.fixWeights(hiddenWeights, hiddenWeightAdjustmentTerm);

		/** 
		 * Corrigi os pesos da bias da camada de saída
		 */
		this.fixBiasWeights(hiddenBiasWeights, hiddenBiasAdjustmentTerm);
	}

	protected void fixBiasWeights(double[] biasWeights, double adjustmentTerm)
	{
		for (int i = 0; i < biasWeights.length; i++) {
	 		biasWeights[i] += adjustmentTerm;
		}
	}

	protected void fixWeights(double[][] weights, double[][] adjustmentTerm)
	{
		for (int i = 0; i < weights.length; i++) {
	 		for (int j = 0; j < weights[i].length; j++) {
	 			weights[i][j] += adjustmentTerm[i][j];	
			}
		}

	}
	/**
	 * Calcula o termo de erro de correção de pesos para cada unidade de entrada (pattern) 
	 *
	 * @param double[]  pattern		                A unidade de entrada
	 * @param double    informationErrorTerm		O termo de erro de informação a ser usado
	 * @return Um array com os termos de correção de peso
	 */
	protected double[] calculateWeightAdjustmentTerms(double[] pattern, double informationErrorTerm)
	{
		double[] adjustmentTerms = new double[pattern.length];

 		for (int i = 0; i < pattern.length; i++) {	
			adjustmentTerms[i] = this.learningFactor*informationErrorTerm*pattern[i]; 
		}

		return adjustmentTerms;
	}

	/**
	 * Calcula o termo de erro de informação de acordo com o resultado gerado, e uma classificão de entrada passada
	 *
	 * @param double  result		      O resultado (sinal) alcançado
	 * @param double  classification	  Classificação referente ao padrão de entrada
	 * @return O termo de erro de informação
	 */
	protected double calculateInformationErrorTerm(double result, double classification)
	{
		//calcula a derivada da função de ativação utilizada para calcular o resultado alcaçado (result)
		double derivative = result*(1-result);

		return classification*derivative;
	}

	/**
	 * calcula da função de ativação 
	 */
	protected double calculateActivationFunction(double entry)
	{
			return 1/(1+Math.exp(-entry));
	}

}