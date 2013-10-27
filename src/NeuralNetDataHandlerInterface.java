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
	 */	
	public int[][] getTrainingSet();

	/**
	 *
	 * Retorna o conjunto de dados que representam as respostas experadas dos
	 * dos dados de trainamento. A ordem das respostas dependem da ordem dos dados de 
	 * treinamento (@see NeuralNetDataHandlerInterface.getTrainData)
	 */	
	public int[][] getTrainingTargetsSet();

	/**
	 *
	 * Retorna o conjunto de dados para validção da Rede Neural
	 */	
	public int[][] getValidationSet();

	/**
	 *
	 * Retorna o conjunto de dados que representam as respostas experadas dos
	 * dos dados de validação. A ordem das respostas dependem da ordem dos dados de 
	 * validação (@see NeuralNetDataHandlerInterface.getValidationData)
	 */	
	public int[][] getValidationTargetsSet();


	/**
	 *
	 * Retorna o conjunto de dados de entrada para Teste da para a Rede Neural
	 */	
	public int[][] getTestSet();		

	/**
	 *
	 * Retorna o conjunto de dados que representam as respostas experadas dos
	 * dos dados de teste. A ordem das respostas dependem da ordem dos dados de 
	 * teste (@see NeuralNetDataHandlerInterface.getTestData)
	 */	
	public int[][] getTestTargetsSet();


}