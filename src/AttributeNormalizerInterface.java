import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.lang.Math;
/**
 * 
 * Interface para normalizaçao de dados para rodar na rede neural
 *
 */
interface AttributeNormalizerInterface {
	
	/**
	 * Normaliza os dados de entrada atrvés de desvio padrão
	 * 
	 * @param int  		maximumInterval		Valor máximo
	 * @param int  		minimumInterval		Valor mínimo
	 * @param double[]  pattern	 			Unidade de dado a ser normalizada
	 * @return A unidade de dado normalizada
	 */
	public double[] normalizeByStandardDeviation(int maximumInterval, int minimumInterval, double[]inputs);
}