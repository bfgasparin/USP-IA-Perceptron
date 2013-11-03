USP-IA-Perceptron
=================

Implementação do Perceptron para o EP da disciplina IA

**Obs:**
  Todos os pesos dos axiomas do perceptron começam com peso 0, incluindo o peso da bias.
  A Bias sempre tem valor 1.

**Obs2:** 
  Todo o código foi escrito em ingles para melhor leitura do mesmo e por ser considerado boa prática. Ele está todo cometado em português por se tratar de um trabalho acadêmico com objetivo de ser avaliado pela docente. 

Radando o perceptron:
------------------------

  1. Compile o arquivo src/Main.java
  2. Rode o arquivo compilado

A class Main receve dois parâmetros:
  * arg[0] Caminho e nome do arquivo de dados de trainamento. Extensão .txt 
  * arg[1] Caminho e nome do arquivo de dados de validação Extensão .txt 
  * arg[2] Caminho e nome do arquivo de dados de teste Extensão .txt
  * arg[3] A taxa de aprendizado inicial
  * arg[4] Número de neurônios da camada escondida TODO
  * arg[5] Inicialização de pesos: 0 para iniciar com todos os pesos com 0, 1 para iniciar todos os pesos com números aleatórios
  * arg[6] Tipo do Problema: 0 para problema binário (Breast Cancer Wisconsin), 1 para problema multi-classe (Optical Recognition of 
  Handwritten Digits)
  * arg[7] Randomicidade dos dados de Trainamento. 1 para que os dados de treinamento seja randomizados em cada época. 0 para utilizar a mesma ordem dos dados de treinamento em todas as épocas

Exemplo:
  `$ java -cp bin/ Perceptron Resources/trainData.txt Resources/valData.txt Resources/testData.txt 0.4 5 0 0 1`
  
No exemplo acima, o perceptron irá rodar com a taxa de aprendizado inicial 0.4, com 5 neurônios na camada escondida, com os pesos dos axiomas 0, resolvendo o problema Breast Cancer Wisconsin e com dados de treinamento randômicos para cada época.

O tipo de problema é necessário para o algoritmo saber como será a leitura dos arquivos de entradas e como será a classificação dos dados. 
No caso do problema Breast Cancer Wisconsin a classificação é binária, já no Optical Recognition of Handwritten Digits a 
classificação é multi-classe.

