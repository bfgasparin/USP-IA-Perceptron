/**
 * 
 * Esta classe extrai os dados necessários para a rede neural resolver o
 * o problema do AND
 */
public class ANDFileHandler extends XORFileHandler
{
	/**
	 * Construtor
	 *
	 * @param String fileName    O nome do arquivo 
	 * @param String validationFilaName    O nome do arquivo de validação
	 * @param String testFilaName          O nome do arquivo de teste	 
	 */
	public ANDFileHandler(String trainFilaName, String validationFilaName, String testFilaName) 
	{
		super(trainFilaName, validationFilaName, testFilaName);
	}
}