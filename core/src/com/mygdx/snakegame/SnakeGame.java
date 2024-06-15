package com.mygdx.snakegame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;

/**
 * Main class of the Snake game.
 */
public class SnakeGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture snakeTexture;
    private Texture invulnerableSnakeTexture;
    private Texture foodTexture;
    private Texture bonusTexture;
    private BitmapFont font;
    private Sound eatSound;

    private Snake snake1;
    private Snake snake2;
    private Food food;
    private BonusItem bonusItem;
    private GameRenderer renderer;

    private int direction1; // Direction of movement for snake 1
    private int direction2; // Direction of movement for snake 2
    private int score1; // Player 1 score
    private int score2; // Player 2 score

    private boolean gameStarted; // Indicates whether the game has started
    private boolean bonusActive; // Indicates whether the bonus item is active
    private float bonusTimer; // Timer to control bonus item duration
    private final float BONUS_DURATION = 5f;  // Duration of bonus in seconds

    private boolean invulnerable1; // Indicates whether snake 1 is invulnerable
    private float invulnerabilityTimer1; // Timer to control invulnerability of snake 1
    private final float INVULNERABILITY_DURATION = 3f;  // Duration of invulnerability in seconds

    private boolean invulnerable2; // Indicates whether snake 2 is invulnerable
    private float invulnerabilityTimer2; // Timer to control invulnerability of snake 2

    private float bonusSpawnTimer; // Timer to control bonus spawn interval
    private final float BONUS_SPAWN_INTERVAL = 15f;  // Interval for bonus spawn in seconds

    private Logger logger; // Logger to display messages

    /**
     * Initializes the game resources and objects.
     */
    @Override
    public void create() {
        batch = new SpriteBatch();
        snakeTexture = new Texture("snake.png");
        invulnerableSnakeTexture = new Texture("invulnerable_snake.png"); // texture for invulnerable snake
        foodTexture = new Texture("food.png");
        bonusTexture = new Texture("bonus.png");
        font = new BitmapFont();
        eatSound = Gdx.audio.newSound(Gdx.files.internal("crunch.wav"));

        // Calculate grid size based on screen width and height
        int gridSizeX = Gdx.graphics.getWidth() / Snake.CELL_SIZE;
        int gridSizeY = Gdx.graphics.getHeight() / Snake.CELL_SIZE;

        // Create instances of game objects
        snake1 = new Snake(gridSizeX / 4, gridSizeY / 2, Input.Keys.RIGHT);
        snake2 = new Snake(3 * gridSizeX / 4, gridSizeY / 2, Input.Keys.LEFT);
        food = new Food(gridSizeX, gridSizeY, snake1);
        bonusItem = new BonusItem(gridSizeX, gridSizeY, snake1);

        renderer = new GameRenderer(batch, snakeTexture, foodTexture, bonusTexture, font, invulnerableSnakeTexture);

        // Initial settings
        direction1 = Input.Keys.RIGHT;
        direction2 = Input.Keys.LEFT;
        gameStarted = false;
        bonusActive = false;
        invulnerable1 = false;
        invulnerable2 = false;
        bonusSpawnTimer = BONUS_SPAWN_INTERVAL;

        logger = new Logger("SnakeGame", Logger.INFO); // Initialize logger
    }

    /**
     * Handles user input and updates the game state.
     */
    @Override
    public void render() {
        handleInput();
        if (gameStarted) {
            update(Gdx.graphics.getDeltaTime());
        }

        // Clear color buffer and draw game elements
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render(snake1, snake2, food, bonusItem, score1, score2, gameStarted, invulnerable1, invulnerable2);
    }

    /**
     * Handles input from players.
     */
    private void handleInput() {
        if (!gameStarted) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
                gameStarted = true;
            }
            return;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP) && direction1 != Input.Keys.DOWN) direction1 = Input.Keys.UP;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && direction1 != Input.Keys.UP) direction1 = Input.Keys.DOWN;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && direction1 != Input.Keys.RIGHT) direction1 = Input.Keys.LEFT;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && direction1 != Input.Keys.LEFT) direction1 = Input.Keys.RIGHT;

        if (Gdx.input.isKeyPressed(Input.Keys.W) && direction2 != Input.Keys.S) direction2 = Input.Keys.W;
        if (Gdx.input.isKeyPressed(Input.Keys.S) && direction2 != Input.Keys.W) direction2 = Input.Keys.S;
        if (Gdx.input.isKeyPressed(Input.Keys.A) && direction2 != Input.Keys.D) direction2 = Input.Keys.A;
        if (Gdx.input.isKeyPressed(Input.Keys.D) && direction2 != Input.Keys.A) direction2 = Input.Keys.D;
    }

    /**
     * Updates game state.
     *
     * @param delta Time since the last update.
     */
    private void update(float delta) {
        // Update snake 1
        if (snake1.update(delta, direction1, invulnerable1)) {
            if (snake1.checkCollision(food.getPosition())) {
                food.spawn(snake1);
                score1++;
                snake1.grow();
                snake1.increaseSpeed();
                eatSound.play();
            } else if (bonusActive && bonusItem.isEatenBySnake(snake1)) {
                score1 += 5;
                invulnerable1 = true;
                invulnerabilityTimer1 = INVULNERABILITY_DURATION;
                bonusItem.deactivate();
                bonusActive = false;
            }
        }

        // Update snake 2
        if (snake2.update(delta, direction2, invulnerable2)) {
            if (snake2.checkCollision(food.getPosition())) {
                food.spawn(snake2);
                score2++;
                snake2.grow();
                snake2.increaseSpeed();
                eatSound.play();
            } else if (bonusActive && bonusItem.isEatenBySnake(snake2)) {
                score2 += 5;
                invulnerable2 = true;
                invulnerabilityTimer2 = INVULNERABILITY_DURATION;
                bonusItem.deactivate();
                bonusActive = false;
            }
        }

        // Check collision between snakes
        for (int[] part : snake1.getBody()) {
            if (!invulnerable2 && snake2.checkCollision(part)) {
                logger.info("Game Over! Snakes collided."); // Log the message
                logWinner();
                resetGame();
                return;
            }
        }

        for (int[] part : snake2.getBody()) {
            if (!invulnerable1 && snake1.checkCollision(part)) {
                logger.info("Game Over! Snakes collided."); // Log the message
                logWinner();
                resetGame();
                return;
            }
        }

        // Check if snake 1 is invulnerable
        if (invulnerable1) {
            invulnerabilityTimer1 -= delta;
            if (invulnerabilityTimer1 <= 0) {
                invulnerable1 = false;
            }
        }

        // Check if snake 2 is invulnerable
        if (invulnerable2) {
            invulnerabilityTimer2 -= delta;
            if (invulnerabilityTimer2 <= 0) {
                invulnerable2 = false;
            }
        }

        // Update bonus spawn timer
        bonusSpawnTimer -= delta;
        if (bonusSpawnTimer <= 0 && !bonusActive) {
            bonusItem.spawn(snake1);
            bonusActive = true;
            bonusTimer = BONUS_DURATION;
            bonusSpawnTimer = BONUS_SPAWN_INTERVAL;
        }

        // Update active bonus timer
        if (bonusActive) {
            bonusTimer -= delta;
            if (bonusTimer <= 0) {
                bonusItem.deactivate();
                bonusActive = false;
            }
        }

        // Check if snakes hit the wall
        if (snake1.hasHitWall()) {
            logger.info("Game Over! Snake 1 hit the wall."); // Log the message
            logWinner("Snake 2"); // Snake 2 is the winner
            resetGame();
            return;
        }

        if (snake2.hasHitWall()) {
            logger.info("Game Over! Snake 2 hit the wall."); // Log the message
            logWinner("Snake 1"); // Snake 1 is the winner
            resetGame();
            return;
        }

        // Verifica colisão das cobras consigo mesmas
        if (!invulnerable1 && snake1.hasHitItself()) {
            logger.info("Game Over! Snake 1 hit itself."); // Log the message
            logWinner("Snake 2"); // Snake 2 is the winner
            resetGame();
            return;
        }

        if (!invulnerable2 && snake2.hasHitItself()) {
            logger.info("Game Over! Snake 2 hit itself."); // Log the message
            logWinner("Snake 1"); // Snake 1 is the winner
            resetGame();
            return;
        }
    }

    /**
     * Logs the winner of the game based on scores.
     */
    private void logWinner() {
        String winner;
        if (score1 > score2) {
            winner = "Snake 1";
        } else if (score2 > score1) {
            winner = "Snake 2";
        } else {
            winner = "Draw";
        }
        logger.info("Game Over! " + winner + " won!");
    }

    /**
     * Logs the specified winner of the game.
     *
     * @param winner The winner to log.
     */
    private void logWinner(String winner) {
        logger.info("Game Over! " + winner + " won!");
    }

    /**
     * Resets the game state to start a new game.
     */
    private void resetGame() {
        int gridSizeX = Gdx.graphics.getWidth() / Snake.CELL_SIZE;
        int gridSizeY = Gdx.graphics.getHeight() / Snake.CELL_SIZE;
        snake1.reset(gridSizeX / 4, gridSizeY / 2, direction1);
        snake2.reset(3 * gridSizeX / 4, gridSizeY / 2, direction2);
        food.spawn(snake1);
        bonusItem.deactivate(); /// Deactivate the bonus when resetting the game
        direction1 = Input.Keys.RIGHT;
        direction2 = Input.Keys.LEFT;
        score1 = 0;
        score2 = 0;
        gameStarted = false;
        bonusActive = false;
        invulnerable1 = false;
        invulnerable2 = false;
        bonusSpawnTimer = BONUS_SPAWN_INTERVAL;
    }

    /**
     * Releases the game resources.
     */
    @Override
    public void dispose() {
        batch.dispose();
        snakeTexture.dispose();
        invulnerableSnakeTexture.dispose();
        foodTexture.dispose();
        bonusTexture.dispose();
        font.dispose();
        eatSound.dispose();
    }
}

