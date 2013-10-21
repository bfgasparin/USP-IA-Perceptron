USP-IA-Perceptron
=================

Implementação do Perceptron para o EP da disciplina IA

**Obs:**
  Todos os pesos dos axiomas do perceptron começam com peso 0, incluindo o peso da bias.
  A Bias sempre tem valor 1.

Para rodar o perceptron:
------------------------

  1. Compile o arquivo src/Perceptron.java
  2. Rode o arquivo compilado

A class Perceptron receve dois parâmetros, e que são capturados a partir do método main da mesma:
  * arg[0] A taxa de aprendizado
  * arg[1] O limiar de ativação

Exemplo:
  `$ java bin/Perceptron 1 0.2`
  
No exemplo acima, o perceptron irá rodar com a taxa de aprendizado 1 e limiar de ativação 0.2.

Para alterar os dados de entrada e as respostas que o rede deve aprender, será necessário, por hora,
alterar os atributos inputData e expectedOutput, respectivamente, da classe.     
