import java.util.Iterator;
import java.util.Collections;
import java.util.List;

/**
 * 
 * Esta classe extrai os dados necessários para a rede neural resolver o
 * o problema Breast Cancer Wisconsin.
 */
public class BreastCancerWisconsinFileHandler extends NeuralNetFileDataHandler
{

	public static final int NUMBER_OF_ATTRIBUTES = 2;

	protected double[][] trainingSet;
	protected int[][] trainingTargetSet;
	protected double[][] validationSet;
	protected int[][] validationTargetSet;
	protected double[][] testSet;
	protected int[][] testTargetSet;
	protected boolean randomTraining;

	/**
	 * Construtor
	 *
	 * @param String fileName    O nome do arquivo 
	 * @param boolean randomTraining     True if the Training Set must be retrieve randomTrainingly
	 */
	public BreastCancerWisconsinFileHandler(String fileName, boolean randomTraining) 
	{
		super(fileName);
		this.randomTraining = randomTraining;
	}

	/**
	 * @inheritDoc
	 */	
	public double[][] getTrainingSet() 
	{
		if(this.trainingSet == null || this.randomTraining){
			if(this.randomTraining)
				Collections.shuffle(this.trainingData); 

			this.trainingSet = this.getPatternSetFrom(this.trainingData);
		}

		return this.trainingSet;
			
	}

	/**
	 * @inheritDoc
	 */	
	public int[][] getTrainingTargetsSet() 
	{
		if(this.trainingTargetSet == null || this.randomTraining){
			this.trainingTargetSet = this.getTargetsSetFrom(this.trainingData);
		}
		return this.trainingTargetSet;
			
	}

	/**
	 * @inheritDoc
	 */	
	public double[][] getValidationSet() 
	{
		if(this.validationSet == null){
			this.validationSet = this.getPatternSetFrom(this.validationData);
		}

		return this.validationSet;			
	}

	/**
	 * @inheritDoc
	 */	
	public int[][] getValidationTargetsSet() 
	{
		if(this.validationTargetSet == null){
			this.validationTargetSet = this.getTargetsSetFrom(this.validationData);
		}

		return this.validationTargetSet;
	}

	/**
	 * @inheritDoc
	 */	
	public double[][] getTestSet() 
	{
		if(this.testSet == null){
			this.testSet = this.getPatternSetFrom(this.testData);
		}

		return this.testSet;			
	}

	/**
	 * @inheritDoc
	 */	
	public int[][] getTestTargetsSet() 
	{
		if(this.testTargetSet == null){
			this.testTargetSet = this.getTargetsSetFrom(this.testData);
		}

		return this.testTargetSet;
	}

	protected int[][] getTargetsSetFrom(List<String> data) 
	{
		int[][] targetSet = new int[data.size()][2];

		Iterator<String> iterator = data.iterator();

		int inputIndex = 0;
		while (iterator.hasNext()) {
			
			String[] attributes = iterator.next().split(",");	
			int[] input = {Integer.parseInt(attributes[1])};
			targetSet[inputIndex] = input;
			inputIndex++;
		}
		
		return targetSet;
	}

	public double[][] getPatternSetFrom(List<String> data) 
	{

		double[][] patternSet = new double[data.size()][BreastCancerWisconsinFileHandler.NUMBER_OF_ATTRIBUTES];
		Iterator<String> iterator = data.iterator();

		int inputIndex = 0;
		while (iterator.hasNext()) {				
			String[] attributes = iterator.next().split(",");	
			double[] input = new double[BreastCancerWisconsinFileHandler.NUMBER_OF_ATTRIBUTES];

			for (int j = 2; j < attributes.length; j++) {
				input[j - 2] = Double.parseDouble(attributes[j]);

			}

			patternSet[inputIndex] = input;
			inputIndex++;
		}

		return patternSet;
			
	}

}