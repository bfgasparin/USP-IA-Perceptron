import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
/**
 * 
 * Esta Classe é um esqueleto para extrair dados da rede reural de um arquivo fisíco
 */
public abstract class NeuralNetFileDataHandler implements NeuralNetDataHandlerInterface
{
	protected List<String> trainingData;
	protected List<String> validationData;
	protected List<String> testData;
	
	protected String trainFilaName;
	protected String validationFilaName;
	protected String testFilaName;

	/**
	 * Construtor
	 *
	 * @param String trainFilaName         O nome do arquivo de treinamento
	 * @param String validationFilaName    O nome do arquivo de validação
	 * @param String testFilaName          O nome do arquivo de teste
	 */
	public NeuralNetFileDataHandler(String trainFilaName, String validationFilaName, String testFilaName) 
	{
		this.trainFilaName = trainFilaName;
		this.validationFilaName = validationFilaName;
		this.testFilaName = testFilaName;
		
		trainingData = this.extractData(this.trainFilaName);
		validationData = this.extractData(this.validationFilaName);
		testData = this.extractData(this.testFilaName);
	}

	protected List<String> extractData(String fileName) 
	{
        File file = new File(fileName);
        List<String> lines = new ArrayList<String>();
        
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return lines;
	}

	/**
	 * @inheritDoc
	 */
	public abstract double[][] getTrainingSet(boolean random);

	/**
	 * @inheritDoc
	 */
	public abstract double[][] getTrainingTargetsSet();

	/**
	 * @inheritDoc
	 */
	public abstract double[][] getValidationSet();

	/**
	 * @inheritDoc
	 */
	public abstract double[][] getValidationTargetsSet();

	/**
	 * @inheritDoc
	 */
	public abstract double[][] getTestSet();		

	/**
	 * @inheritDoc
	 */
	public abstract double[][] getTestTargetsSet();	


    private void log(Object msg)
    {
    	System.out.println(String.valueOf(msg));
    }
}