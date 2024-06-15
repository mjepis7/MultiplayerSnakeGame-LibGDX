package com.mygdx.snakegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents the snake in the game.
 */
public class Snake {
    /** The size of each cell in pixels. */
    public static final int CELL_SIZE = 20;

    private List<int[]> body; // The body segments of the snake
    private int direction; // Current direction of the snake
    private float speed; // Snake's speed
    private float timer; // Timer for movement control
    private final float UPDATE_INTERVAL = 0.2f; // Update interval in seconds
    private boolean growing; // Indicates whether the snake should grow

    /**
     * Constructs a new Snake instance.
     *
     * @param startX           Initial X position of the snake.
     * @param startY           Initial Y position of the snake.
     * @param initialDirection Initial direction of the snake.
     */
    public Snake(int startX, int startY, int initialDirection) {
        body = new LinkedList<>();
        body.add(new int[] { startX, startY });
        direction = initialDirection;
        speed = 1.0f;
        timer = 0;
        growing = false;
    }

    /**
     * Updates the snake's position based on elapsed time.
     *
     * @param delta        Time elapsed since the last update.
     * @param direction    Direction in which the snake should move.
     * @param invulnerable Indicates whether the snake is invulnerable.
     * @return True if the snake moved, false otherwise.
     */
    public boolean update(float delta, int direction, boolean invulnerable) {
        timer += delta * speed;
        // If the timer exceeds the update interval, update the snake's position
        if (timer >= UPDATE_INTERVAL) {
            this.direction = direction;
            int[] head = body.get(0);
            int[] newHead = new int[] { head[0], head[1] };

            // Update the new head position based on the direction
            switch (direction) {
                case Input.Keys.UP:
                    newHead[1]++;
                    break;
                case Input.Keys.DOWN:
                    newHead[1]--;
                    break;
                case Input.Keys.LEFT:
                    newHead[0]--;
                    break;
                case Input.Keys.RIGHT:
                    newHead[0]++;
                    break;
                case Input.Keys.W:
                    newHead[1]++;
                    break;
                case Input.Keys.S:
                    newHead[1]--;
                    break;
                case Input.Keys.A:
                    newHead[0]--;
                    break;
                case Input.Keys.D:
                    newHead[0]++;
                    break;
            }
            // Add the new head to the beginning of the body
            body.add(0, newHead);

            // If not growing, remove the last segment of the body
            if (!growing) {
                body.remove(body.size() - 1);
            } else {
                growing = false;
            }
            timer = 0;
            return true;
        }
        return false;
    }

    /**
     * Checks if the snake has collided with a certain position.
     *
     * @param position The position to check.
     * @return True if there was a collision, false otherwise.
     */
    public boolean checkCollision(int[] position) {
        for (int[] part : body) {
            if (part[0] == position[0] && part[1] == position[1]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Grows the snake by adding a new segment to its body.
     */
    public void grow() {
        growing = true;
    }

    /**
     * Increases the snake's speed.
     */
    public void increaseSpeed() {
        speed += 0.1f;
    }

    /**
     * Checks if the snake has hit the wall.
     *
     * @return True if there was a collision with the wall, false otherwise.
     */
    public boolean hasHitWall() {
        int[] head = body.get(0);
        return head[0] < 0 || head[0] >= Gdx.graphics.getWidth() / CELL_SIZE || head[1] < 0 || head[1] >= Gdx.graphics.getHeight() / CELL_SIZE;
    }

    /**
     * Checks if the snake has hit itself.
     *
     * @return True if there was a collision with itself, false otherwise.
     */
    public boolean hasHitItself() {
        int[] head = body.get(0);
        for (int i = 1; i < body.size(); i++) {
            int[] part = body.get(i);
            if (part[0] == head[0] && part[1] == head[1]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Resets the snake to its initial position.
     *
     * @param startX           Initial X position of the snake.
     * @param startY           Initial Y position of the snake.
     * @param initialDirection Initial direction of the snake.
     */
    public void reset(int startX, int startY, int initialDirection) {
        body.clear();
        body.add(new int[] { startX, startY });
        direction = initialDirection;
        speed = 1.0f;
        timer = 0;
        growing = false;
    }

    /**
     * Gets the snake's body.
     *
     * @return List of segments comprising the snake's body.
     */
    public List<int[]> getBody() {
        return body;
    }
}
