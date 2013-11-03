import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
/**
 * 
 * * Interface para manipulação de dados para a rede neural
 */
interface NeuralNetDataHandlerInterface 
{
	/**
	 *
	 * Retorna o conjunto de dados de treinamento para a Rede Neural
	 * @param boolean random     True if the Training Set must be retrieve randomTrainingly	 
	 */	
	public double[][] getTrainingSet(boolean ramdom);

	/**
	 *
	 * Retorna o conjunto de dados que representam as respostas experadas dos
	 * dos dados de trainamento. A ordem das respostas dependem da ordem dos dados de 
	 * treinamento (@see NeuralNetDataHandlerInterface.getTrainData)
	 */	
	public double[][] getTrainingTargetsSet();

	/**
	 *
	 * Retorna o conjunto de dados para validção da Rede Neural
	 */	
	public double[][] getValidationSet();

	/**
	 *
	 * Retorna o conjunto de dados que representam as respostas experadas dos
	 * dos dados de validação. A ordem das respostas dependem da ordem dos dados de 
	 * validação (@see NeuralNetDataHandlerInterface.getValidationData)
	 */	
	public double[][] getValidationTargetsSet();


	/**
	 *
	 * Retorna o conjunto de dados de entrada para Teste da para a Rede Neural
	 */	
	public double[][] getTestSet();		

	/**
	 *
	 * Retorna o conjunto de dados que representam as respostas experadas dos
	 * dos dados de teste. A ordem das respostas dependem da ordem dos dados de 
	 * teste (@see NeuralNetDataHandlerInterface.getTestData)
	 */	
	public double[][] getTestTargetsSet();


}