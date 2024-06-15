package com.mygdx.snakegame;

import java.util.List;

/**
 * Represents a bonus item in the game.
 */
public class BonusItem {
    private int[] position; // Coordinates of the bonus item
    private int gridSizeX;  // Grid size on the X-axis
    private int gridSizeY; // Grid size on the Y-axis
    private boolean active; // Indicates whether the bonus item is active

    /**
     * Creates a new instance of bonus item, initially inactive.
     *
     * @param gridSizeX Size of the grid on the X-axis.
     * @param gridSizeY Size of the grid on the Y-axis.
     * @param snake      Snake instance to avoid the bonus item appearing on it.
     */
    public BonusItem(int gridSizeX, int gridSizeY, Snake snake) {
        this.gridSizeX = gridSizeX;
        this.gridSizeY = gridSizeY;
        active = false; // Initially inactive
    }

    /**
     * Positions the bonus item randomly on the grid, avoiding it appearing on the snake.
     *
     * @param snake Snake instance to avoid the bonus item appearing on it.
     */
    public void spawn(Snake snake) {
        int[] newPosition;
        boolean onSnake;
        do {
            newPosition = new int[]{(int) (Math.random() * gridSizeX), (int) (Math.random() * gridSizeY)};
            onSnake = false;
            // Check if the new position of the bonus item is on the snake
            for (int[] part : snake.getBody()) {
                if (newPosition[0] == part[0] && newPosition[1] == part[1]) {
                    onSnake = true;
                    break;
                }
            }
        } while (onSnake); // Repeat until finding a valid position
        position = newPosition; // Set the bonus item position
        active = true; // Activate the bonus item
    }

    /**
     * Deactivates the bonus item.
     */
    public void deactivate() {
        active = false;
    }

    /**
     * Gets the current coordinates of the bonus item.
     *
     * @return Coordinates of the bonus item.
     */
    public int[] getPosition() {
        return position;
    }

    /**
     * Checks if the bonus item is active.
     *
     * @return True only if the bonus item is active.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Checks if the bonus item is eaten by the snake.
     *
     * @param snake Snake instance.
     * @return True if the snake has eaten the bonus item.
     */
    public boolean isEatenBySnake(Snake snake) {
        return snake.checkCollision(position);
    }

}
