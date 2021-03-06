USP-IA-Perceptron
=================

Implementação do Perceptron para o EP da disciplina IA

**Obs:** 
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
  Handwritten Digits), 2 para o problema XOR, 3 para o problema AND
  * arg[7] Randomicidade dos dados de Trainamento. 1 para que os dados de treinamento seja randomizados em cada época. 0 para utilizar 
  * arg[8] Caminho do diretório onde será salvo os arquivos de logs. If ommited, the log will be saved on current dir.

Exemplo:
  `$ java -cp bin/ Main Resources/trainData.txt Resources/valData.txt Resources/testData.txt 0.4 5 0 0 1` logs/
  
No exemplo acima, o perceptron irá rodar com a taxa de aprendizado inicial 0.4, com 5 neurônios na camada escondida, com os pesos dos axiomas 0, resolvendo o problema Breast Cancer Wisconsin, com dados de treinamento randômicos para cada época e irá gravar os arquivos de log no diretório logs (sendo a raiz o diretório corrente).

O tipo de problema é necessário para o algoritmo saber como será a leitura dos arquivos de entradas e como será a classificação dos dados. 
No caso do problema Breast Cancer Wisconsin a classificação é binária, já no Optical Recognition of Handwritten Digits a 
classificação é multi-classe.

**Obs:** 
  Os logs gerados são separados por problema (tipo 1, tipo 2, ...) e data. Cada log é gerado num arquivo de nome <tipo_problema>_<data_em_milissegundos>.txt. Assim você pode rever todos os seus logs anteriores. 