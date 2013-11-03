import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.util.Date;

/**
 * 
 * Este Classe é a classe principal do projeto
 *
 */
public class Main 
{
	public static void main(String args[]) throws Exception
	{
		String logPath;

		try{
			 logPath = args[8];
		}catch(ArrayIndexOutOfBoundsException e){
			logPath = "";
		}
		boolean stop = false; 
		int epoca = 1;
		int problemType = Integer.parseInt((args[6]));
		double totalSquaredError; 
		double[] result; 
		Main main = new Main();
		NeuralNetDataHandlerInterface handler;
		boolean randomTraining = !(0 == Integer.parseInt((args[7])));
		java.util.Date date = new java.util.Date();

		//Idenfitica qual é o problema para escolher qual Filehandler irá utilizar
		if(0 == problemType){
		 	handler = new BreastCancerWisconsinFileHandler(args[0], args[1], args[2]);
		}else if(1 == problemType){
			throw new Exception("O problema Optical Recognition of Handwritten Digits ainda não está inplementado");
		}else if(2 == problemType){
		 	handler = new XORFileHandler(args[0], args[1], args[2]);	
		}else{
			throw new Exception("Você deve passar 0 ou 1 no argumento de Tipo de Problema");
		}

		//Busca os dados de treinamento
		double[][] trainingSet = handler.getTrainingSet(randomTraining);
		double[][] targets = handler.getTrainingTargetsSet();

		//Inicia a rede neural
		MultiLayeredPerceptron perceptron = new MultiLayeredPerceptron(trainingSet[0].length, targets[0].length, Double.parseDouble(args[3]), Integer.parseInt(args[4]));
		if(!(0 == Integer.parseInt((args[5])))){
			perceptron.randomWeights();
		}	
		
		// System.out.println("Taxa de aprendizado: " + perceptron.learningFactor);
		// System.out.println("Numero de neuronios camada de entrada: " + perceptron.nbrInputNeurons);
		// System.out.println("Numero de neuronios camada escondida: " + perceptron.nbrHiddenNeurons);
		// System.out.println("Numero de neuronios camada de saída: " + perceptron.nbrOutputNeurons);
		// System.out.println();
        try{  
            File file = new File(logPath + "problem_"+problemType+"_" + date.getTime() + ".txt"); 
	        FileOutputStream fOut = new FileOutputStream(file);
	        BufferedOutputStream out = new BufferedOutputStream(fOut);  
			while(!stop){
				totalSquaredError = 0;
				System.out.print("Época: "+epoca++ + " ... ");
				for (int i = 0; i < trainingSet.length; i++) {
					//Treina a rede reural				
					result = perceptron.train(trainingSet[i], targets[i]);	
					totalSquaredError += main.calculateSquaredError(result, targets[i]);
				}
				System.out.print("  Erro quadrado total: " + totalSquaredError);
				System.out.println("");
            	out.write(String.valueOf(totalSquaredError+";").getBytes());
            	out.write(13);
				//System.out.println("");
				if(totalSquaredError < 0.05) stop = true;
			}
            out.close();  

        }catch(Exception e){  
            System.out.println("ERRO: " + e.getMessage());
            throw e;
        }  
	}

	/**
	 * Calcula o erro quadrado
	 *
	 * @param double[]  result		A resposta (sinal) da rede
	 * @param double[]  target		A unidade de saída esperada para a unidade de entrada
	 * @return O erro quadrado
	 */
	public double calculateSquaredError(double[] result, double[] target)
	{		
		double totalRrror = 0;
		double error = 0;
	 	for (int i = 0; i < result.length; i++) {
	 			error += target[i]-result[i];
	 			totalRrror += error*error;
		}

		return totalRrror;
	}
	
	public static void print(BufferedOutputStream stream, String data) throws Exception{  

    }  
    
    // public static void print(File file, double[] neurons) throws Exception{  
    //     try{  
    //         FileOutputStream fOut = new FileOutputStream(file);
    //         BufferedOutputStream out = new BufferedOutputStream(fOut);  

    //         for(int index = 0; index < neurons.length; index++){
    //             out.write(String.valueOf(neurons[index]).getBytes());
    //         }  
    //         out.close();  

    //     }catch(Exception e){  
    //         System.out.println("ERRO: " + e.getMessage());
    //         throw e;  
    //     }  
    // }  
    
    // public static void print(FileOutputStream stream, double[] neurons) throws Exception{  
    //     try{  
    //         BufferedOutputStream out = new BufferedOutputStream(stream);  

    //         for(int index = 0; index < neurons.length; index++){
    //             out.write(String.valueOf(neurons[index]).getBytes());
    //         }  
    //         out.close();  

    //     }catch(Exception e){ 
    //         System.out.println("ERRO: " + e.getMessage()); 
    //         throw e;  
    //     }  
    // } 
    
    // public void print(BufferedOutputStream stream, double[] neurons) throws Exception{
    //     try{
    //         for(int index = 0; index < neurons.length; index++){
    //             stream.write(String.valueOf(neurons[index]).getBytes());
    //         }  
    //         stream.close();  

    //     }catch(Exception e){ 
    //         System.out.println("ERRO: " + e.getMessage()); 
    //         throw e; 
    //     }  
    // }


}