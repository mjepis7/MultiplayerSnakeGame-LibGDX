package com.mygdx.snakegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Renders the game graphics and UI elements.
 */
public class GameRenderer {
    private SpriteBatch batch;
    private Texture snakeTexture;
    private Texture foodTexture;
    private Texture bonusTexture;
    private BitmapFont font;
    private Texture invulnerableSnakeTexture;

    /**
     * Constructs a GameRenderer.
     *
     * @param batch                    SpriteBatch instance for rendering.
     * @param snakeTexture             Texture for rendering the snake.
     * @param foodTexture              Texture for rendering the food.
     * @param bonusTexture             Texture for rendering the bonus item.
     * @param font                     BitmapFont for rendering text.
     * @param invulnerableSnakeTexture Texture for rendering the invulnerable snake.
     */
    public GameRenderer(SpriteBatch batch, Texture snakeTexture, Texture foodTexture, Texture bonusTexture, BitmapFont font, Texture invulnerableSnakeTexture) {
        this.batch = batch;
        this.snakeTexture = snakeTexture;
        this.foodTexture = foodTexture;
        this.bonusTexture = bonusTexture;
        this.font = font;
        this.invulnerableSnakeTexture = invulnerableSnakeTexture;

        // Increase font size for score
        //this.font.getData().setScale(1.2f);
    }

    /**
     * Renders the game graphics and UI elements.
     *
     * @param snake1        First snake instance.
     * @param snake2        Second snake instance.
     * @param food          Food instance.
     * @param bonusItem     BonusItem instance.
     * @param score1        Score of player 1.
     * @param score2        Score of player 2.
     * @param gameStarted   Indicates whether the game has started.
     * @param invulnerable1 Indicates whether player 1 is invulnerable.
     * @param invulnerable2 Indicates whether player 2 is invulnerable.
     */
    public void render(Snake snake1, Snake snake2, Food food, BonusItem bonusItem, int score1, int score2, boolean gameStarted, boolean invulnerable1, boolean invulnerable2) {
        batch.begin();

        // Render the first snake
        for (int[] part : snake1.getBody()) {
            if (invulnerable1) {
                batch.draw(invulnerableSnakeTexture, part[0] * Snake.CELL_SIZE, part[1] * Snake.CELL_SIZE);
            } else {
                batch.draw(snakeTexture, part[0] * Snake.CELL_SIZE, part[1] * Snake.CELL_SIZE);
            }
        }

        // Render the second snake
        for (int[] part : snake2.getBody()) {
            if (invulnerable2) {
                batch.draw(invulnerableSnakeTexture, part[0] * Snake.CELL_SIZE, part[1] * Snake.CELL_SIZE);
            } else {
                batch.draw(snakeTexture, part[0] * Snake.CELL_SIZE, part[1] * Snake.CELL_SIZE);
            }
        }

        // Render the food
        int[] foodPosition = food.getPosition();
        batch.draw(foodTexture, foodPosition[0] * Snake.CELL_SIZE, foodPosition[1] * Snake.CELL_SIZE);

        // Render the bonus item if active
        if (bonusItem.isActive()) {
            int[] bonusPosition = bonusItem.getPosition();
            batch.draw(bonusTexture, bonusPosition[0] * Snake.CELL_SIZE, bonusPosition[1] * Snake.CELL_SIZE);
        }

        // Render player 1 score
        font.draw(batch, "Player 1: " + score1, 10, Gdx.graphics.getHeight() - 10);

        // Render player 2 score
        font.draw(batch, "Player 2: " + score2, Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 10);

        // Render initial message if the game has not started
        if (!gameStarted) {
            font.draw(batch, "Press any key to start", Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2);
        }

        batch.end();
    }
}
