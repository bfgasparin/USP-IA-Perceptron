import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.lang.Math;
/**
 * 
 * Esta é a Interface de um Perceptron
 *
 */
interface PerceptronInterface {
	
	/**
	 * Treina a rede com com a unidade de entrada (pattern). 
	 * 
	 * Usa a unidade de sáida (target) para 
	 * corrigir os pesos das conexões dos axionios 
	 *
	 * @param double[]  patterns		Uma unidade de entrada
	 * @param double[]  targets			A unidade de saída esperada para a unidade de entrada
	 * @return Um array com as respostas (sinais) dos neurônios da camada saída
	 */
	public double[] train(double[] patterns, double[] targets);

	/**
	 * Executa a rede com com a unidade de entrada (pattern).  
	 * 
	 * @param double[]  patterns		Uma unidade de entrada
	 * @param double[]  targets			A unidade de saída esperada para a unidade de entrada
	 * @return Um array com as respostas (sinais) dos neurônios da camada saída
	 */
	public double[] execute(double[] patterns);
}