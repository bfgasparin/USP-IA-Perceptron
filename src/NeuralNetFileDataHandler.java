import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
/**
 * 
 * Esta Classe é um esqueleto para extrair dados da rede reural de um arquivo fisíco
 */
public abstract class NeuralNetFileDataHandler implements NeuralNetDataHandlerInterface, AttributeNormalizerInterface
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

   public double[] normalizeByStandardDeviation(int maximumInterval, int minimumInterval, double[] pattern)
   {
        double standardDeviation = this.getStandardDeviation(pattern);

        double averageValue = (maximumInterval + maximumInterval)/2;

        double[] output = new double[pattern.length];
        
        for (int index = 0; index < pattern.length; index++)
        {
            double value = pattern[index];

            output[index] = (value - averageValue) / standardDeviation;
        }

        return output;
    }

   protected double getStandardDeviation(double[] inputs)
    {
        int count = 0;
        double sum = 0;
        double average = this.getAverage(inputs);

        for (int column = 0; column < inputs.length; column++)
        {
            double result = inputs[column] - average;
            sum = sum + (result * result);
            count++;
        }

        return Math.sqrt(((double)1 / (double)count) * sum);
    }
   protected double getAverage(double[] inputs)
    {
        int count = 0;
        double sum = 0;

        for (int column = 0; column < inputs.length; column++)
        {
            sum += inputs[column];
            count++;
        }

        return sum / (double)count;
    }

    

}