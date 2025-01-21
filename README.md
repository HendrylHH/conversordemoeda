
# Conversor de Moeda em Java


Realizado como Desafio para a conclusão do curso de Java e Orientação a Objetos G7 - ONE, ministrado pela Oracle e Alura, para desenvolvimento Back-End.

Este é um projeto simples de um Conversor de Moedas que permite realizar conversões entre diferentes moedas com base em taxas de câmbio dinâmicas fornecidas pela API ExchangeRate-API. O programa é executado via console e foi desenvolvido em Java.



## Funcionalidades

Seis tipos de conversão diferentes;
Busca de taxas de câmbio em tempo real por meio da API ExchangeRate-API;
Validação de entradas do usuário para evitar erros.

## Tecnologias Utilizadas

Java: Linguagem de programação principal.

Gson: Biblioteca para analisar e manipular respostas JSON da API.


## Como Configurar o Projeto

1) Obtenha a chave da API:
https://www.exchangerate-api.com

2) Clone este repositório;
3) Adicione a chave da API no código:

 private static final String API_KEY = "API";  


4) Baixe a biblioteca Gson:
5) Compile e utilize.


## Validações Implementadas

### Entradas inválidas no menu:

Se o usuário inserir um valor fora do intervalo de 1 a 7, o programa solicita uma entrada válida.

### Entrada inválida para valores numéricos:

Caso o valor a ser convertido não seja numérico, o programa pede para que seja inserido um número válido.
