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
	
	protected String fileName;

	public static final double TRAINING_DATA_AMOUNT = 1;

	/**
	 * Construtor
	 *
	 * @param String fileName    O nome do arquivo 
	 */
	public NeuralNetFileDataHandler(String fileName) 
	{
		this.fileName = fileName;
		
		List<String> lines = this.extractData();

		int trainingEndIndex = (int)Math.round(lines.size()*NeuralNetFileDataHandler.TRAINING_DATA_AMOUNT);
		int validationEndIndex = trainingEndIndex + (int)((lines.size() - trainingEndIndex)/2);

		trainingData = lines.subList(0, trainingEndIndex);
		validationData = lines.subList(trainingEndIndex, validationEndIndex);
		testData = lines.subList(validationEndIndex, lines.size());
	}

	protected List<String> extractData() 
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
	public abstract double[][] getTrainingSet();

	/**
	 * @inheritDoc
	 */
	public abstract int[][] getTrainingTargetsSet();

	/**
	 * @inheritDoc
	 */
	public abstract double[][] getValidationSet();

	/**
	 * @inheritDoc
	 */
	public abstract int[][] getValidationTargetsSet();

	/**
	 * @inheritDoc
	 */
	public abstract double[][] getTestSet();		

	/**
	 * @inheritDoc
	 */
	public abstract int[][] getTestTargetsSet();	


    private void log(Object msg)
    {
    	System.out.println(String.valueOf(msg));
    }
}