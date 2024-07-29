# ApiFut

## Visão Geral

ApiFut é um aplicativo Android desenvolvido para buscar e exibir informações de jogadores de futebol do modo Ultimate Team do jogo EA Sports FC24, a partir de uma API externa. O projeto utiliza Retrofit para fazer requisições HTTP, Moshi para a serialização/deserialização de JSON, e Coil para o carregamento de imagens.

## Funcionalidades

- 📋 Listagem de jogadores com informações básicas (nome e rating.)
- 🖼️ Carregamento de imagens dos jogadores a partir de uma URL dinâmica
- 🖼️ Tela de informações adicionais de cada jogador (Estrelas de perna ruim, Estrelas de Habilidade e outros Atributos)

## Tecnologias Utilizadas

- 🛠️ [Kotlin](https://kotlinlang.org/)
- 🛠️ [Retrofit](https://square.github.io/retrofit/)
- 🛠️ [Moshi](https://github.com/square/moshi)
- 🛠️ [Coil](https://coil-kt.github.io/coil/)
- 🛠️ [Jetpack Compose](https://developer.android.com/jetpack/compose)

## Este projeto foi criado a partir de um projeto solicitado na disciplina de Programação para Dispositivos Móveis com os seguintes objetivos e critérios de avaliação:
## Objetivos
Desenvolvimento de Aplicativo que consuma API

## Descrição:
O objetivo principal deste projeto é desenvolver um aplicativo para dispositivos móveis utilizando Kotlin e Jetpack Compose, aplicando boas práticas de programação e seguindo o padrão de projeto Model-View-ViewModel (MVVM). O aplicativo terá como propósito acessar uma API selecionada pelo aluno e exibir os dados obtidos de forma organizada e intuitiva na tela do dispositivo.

## Implementar a conexão com uma API:
O aplicativo deverá ser capaz de realizar a conexão com a API escolhida pelo aluno, utilizando os recursos adequados para fazer requisições HTTP, como a biblioteca Retrofit.
## Recuperar e processar os dados da API:
O aplicativo deve ser capaz de recuperar os dados da API por meio de chamadas assíncronas, utilizando corrotinas.
## Exibir os dados na interface:
Os dados obtidos da API devem ser exibidos na tela do aplicativo de forma organizada, utilizando um layout de lazy grid ou lazy column para melhorar a performance e a experiência do usuário. Os componentes visuais devem ser configurados adequadamente, considerando espaçamentos, cores e fontes para garantir uma boa visualização e legibilidade do conteúdo.
## Navegação entre telas: 
O aplicativo deve permitir que o usuário clique em um item específico para visualizar mais detalhes em uma tela separada.
## Utilizar boas práticas de programação: 
O desenvolvimento do aplicativo deve seguir boas práticas de programação, incluindo a separação de responsabilidades em classes e funções, o uso de nomes significativos para variáveis e métodos, e a organização do código em pacotes coerentes.
## Critérios de Avaliação:
- Requisitos Funcionais (40%): O aplicativo deve cumprir todos os requisitos funcionais estabelecidos no enunciado do projeto.
- Padrões de Projetos e Componentes (40%): O desenvolvimento do aplicativo deve seguir os padrões de projetos e utilizar corretamente os componentes exigidos, como Kotlin, Jetpack Compose e o padrão MVVM.
- Interface do Aplicativo (20%): A interface do aplicativo deve apresentar uma estrutura bem organizada, com espaçamentos adequados, sem sobreposição de elementos visuais, cores e fontes que garantam uma boa experiência de visualização do conteúdo.
