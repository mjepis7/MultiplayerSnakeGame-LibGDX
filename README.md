<h1 align="center"> Multiplayer Snake Game </h1>

<h4 align="center">Grupo 8</h4>
<p align="center">
Larisse Ferreira Alzeri | NUSP 12703080  <br/>
Maria Eduarda Iwashita e Silva | NUSP: 13823979 <br/>
Vileno Cunha Cavalcante | NUSP: 12559249 <br/>
</p>

<br>

Este projeto desenvolve uma versão clássico Snake Game desenvolvido usando a biblioteca LibGDX para Java. O jogo suporta dois jogadores, cada um controlando uma cobra, e oferece mecânicas de invulnerabilidade temporária e bônus.

## Requisitos

- JDK 8 ou superior
- Gradle 6.0 ou superior
- LibGDX

## Estrutura do Projeto

- `core/src/com/mygdx/snakegame/`
  - `SnakeGame.java` (Classe principal do jogo)
  - `Snake.java` (Classe que representa a cobra)
  - `Food.java` (Classe que representa a comida)
  - `BonusItem.java` (Classe que representa o item bônus)
  - `GameRenderer.java` (Classe que renderiza os elementos do jogo)
- `assets/`
  - `snake.png` (Imagem da cobra)
  - `invulnerable_snake.png` (Imagem da cobra invulnerável)
  - `food.png` (Imagem da comida)
  - `bonus.png` (Imagem do item bônus)
  - `crunch.wav` (Som de quando a cobra come a comida)

## Instruções para Rodar o Jogo

1. **Clonar o Repositório**

   ```bash
   git clone https://github.com/mjepis7/MultiplayerSnakeGame-LibGDX.git
   cd <nome do diretório>
   ```

2. **Configurar o Ambiente**

   - Certifique-se de ter o JDK e o Gradle instalados.
   - Adicione as bibliotecas do LibGDX ao seu projeto. Você pode usar o Gradle para gerenciar as dependências.

3. **Estrutura de Arquivos**

   - Certifique-se de que sua estrutura de arquivos esteja assim:
     ```
     SnakeGame/
     ├── core/
     │   └── src/
     │       └── com/
     │           └── mygdx/
     │               └── snakegame/
     │                   ├── SnakeGame.java
     │                   ├── Snake.java
     │                   ├── Food.java
     │                   ├── BonusItem.java
     │                   └── GameRenderer.java
     ├── assets/
     │   ├── snake.png
     │   ├── invulnerable_snake.png
     │   ├── food.png
     │   ├── bonus.png
     │   └── crunch.wav
     └── build.gradle
     ```

4. **Configurar o build.gradle**

   - No arquivo `build.gradle`, adicione as dependências do LibGDX:

     ```groovy
     plugins {
         id 'java'
     }

     sourceCompatibility = 1.8

     repositories {
         mavenCentral()
     }

     dependencies {
         implementation 'com.badlogicgames.gdx:gdx:1.9.10'
         implementation 'com.badlogicgames.gdx:gdx-backend-lwjgl:1.9.10'
         implementation 'com.badlogicgames.gdx:gdx-platform:1.9.10:natives-desktop'
     }
     ```

5. **Compilar e Executar o Jogo**
   - No terminal, navegue até o diretório do projeto e execute:
     ```bash
     gradle build
     gradle run
     ```

## Como Jogar

- **Iniciar o Jogo:**
  - Pressione qualquer tecla para iniciar o jogo.
- **Controles do Jogador 1 (Cobra 1):**

  - Seta para cima: Move para cima
  - Seta para baixo: Move para baixo
  - Seta para a esquerda: Move para a esquerda
  - Seta para a direita: Move para a direita

- **Controles do Jogador 2 (Cobra 2):**

  - W: Move para cima
  - S: Move para baixo
  - A: Move para a esquerda
  - D: Move para a direita

- **Objetivo:**

  - Coma a comida para ganhar pontos.
  - Evite colidir com as paredes, com o próprio corpo e com a outra cobra.

- **Bônus:**
  - O item bônus aparece periodicamente e concede pontos extras e invulnerabilidade temporária.

## Planos de teste

<p>Os testes manuais realizados cobrem todos os principais aspectos do jogo, garantindo que as funcionalidades implementadas estejam funcionando conforme o esperado. </p>

- **Movimentação da Cobra**: Verificação de resposta às entradas do teclado.
- **Crescimento da Cobra**: Verificação do aumento de comprimento ao consumir comida.
- **Velocidade da Cobra**: Verificação do aumento gradual da velocidade.
- **Comportamento das Paredes**: Verificação da colissão de paredes.
- **Controle de Duas Cobras**: Teste de controle simultâneo por dois jogadores.
- **Colisão Entre Cobras**: Teste de término de jogo ao colidir.
- **Pontuação**: Verificação do rastreamento e exibição de pontuação.
- **Efeitos Sonoros**: Teste de reprodução de sons.
- **Item bônus**: Teste da funcionalidade do item bônus.

## Resultado dos testes

- **Movimentação da Cobra**: Resposta instantânea às entradas do teclado.
- **Crescimento da Cobra**: Cobra aumentou de comprimento ao consumir comida.
- **Velocidade da Cobra**: Velocidade aumentou ligeiramente após consumir comida.
- **Comportamento das Paredes**: Jogo terminou corretamente ao colidir, detectando colisões com as paredes.
- **Controle de Duas Cobras**: Dois jogadores puderam controlar suas cobras simultaneamente sem problemas.
- **Colisão Entre Cobras**: Jogo terminou corretamente ao colidir, detectando colisões entre as cobras.
- **Pontuação**: Pontuação foi rastreada e exibida corretamente em tempo real.
- **Efeitos Sonoros**: Sons foram reproduzidos ao consumir a comida.
- **Item bônus**: O item bônus aparece periodicamente e concede habilidades especiais, como pontuação extra e invunerabilidade.
#
