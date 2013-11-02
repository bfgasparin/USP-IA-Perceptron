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
	 * @param double[]  pattern		Uma unidade de entrada
	 * @param double[]  target		A unidade de saída esperada para a unidade de entrada
	 * @return Um array com as respostas (sinais) dos neurônios da camada saída
	 */
	public double[] train(double[] pattern, double[] target);

	/**
	 * Executa a rede com com a unidade de entrada (pattern).  
	 * 
	 * @param double[]  pattern		Uma unidade de entrada
	 * @return Um array com as respostas (sinais) dos neurônios da camada saída
	 */
	public double[] execute(double[] pattern);
}