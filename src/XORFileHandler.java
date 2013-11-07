import java.util.Iterator;
import java.util.Collections;
import java.util.List;

/**
 * 
 * Esta classe extrai os dados necessários para a rede neural resolver o
 * o problema do XOE
 */
public class XORFileHandler extends NeuralNetFileDataHandler
{

	public static final int NUMBER_OF_ATTRIBUTES = 2;

	protected double[][] trainingSet;
	protected double[][] trainingTargetSet;
	protected double[][] validationSet;
	protected double[][] validationTargetSet;
	protected double[][] testSet;
	protected double[][] testTargetSet;
	protected boolean    randomTraining;

	/**
	 * Construtor
	 *
	 * @param String fileName    O nome do arquivo 
	 * @param String validationFilaName    O nome do arquivo de validação
	 * @param String testFilaName          O nome do arquivo de teste	 
	 */
	public XORFileHandler(String trainFilaName, String validationFilaName, String testFilaName) 
	{
		super(trainFilaName, validationFilaName, testFilaName);
	}

	/**
	 * @inheritDoc
	 */	
	public double[][] getTrainingSet(boolean random) 
	{
		this.randomTraining = random;
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
	public double[][] getTrainingTargetsSet() 
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
	public double[][] getValidationTargetsSet() 
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
	public double[][] getTestTargetsSet() 
	{
		if(this.testTargetSet == null){
			this.testTargetSet = this.getTargetsSetFrom(this.testData);
		}

		return this.testTargetSet;
	}

	protected double[][] getTargetsSetFrom(List<String> data) 
	{
		double[][] targetSet = new double[data.size()][2];

		Iterator<String> iterator = data.iterator();

		int targetIndex = 0;
		while (iterator.hasNext()) {
			
			String[] attributes = iterator.next().split(",");	
			double[] target = {Double.parseDouble(attributes[0])};
			targetSet[targetIndex] = target;
			targetIndex++;
		}
		
		return targetSet;
	}

	public double[][] getPatternSetFrom(List<String> data) 
	{

		double[][] patternSet = new double[data.size()][XORFileHandler.NUMBER_OF_ATTRIBUTES];
		Iterator<String> iterator = data.iterator();

		int inputIndex = 0;
		while (iterator.hasNext()) {				
			String[] attributes = iterator.next().split(",");	
			double[] input = new double[XORFileHandler.NUMBER_OF_ATTRIBUTES];

			for (int j = 1; j < attributes.length; j++) {
				input[j - 1] = Double.parseDouble(attributes[j]);

			}
			
			patternSet[inputIndex] = input;
			inputIndex++;
		}

		return patternSet;
			
	}

}