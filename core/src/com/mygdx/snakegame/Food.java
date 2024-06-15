package com.mygdx.snakegame;

import java.util.List;

/**
 * Represents the food in the game.
 * Food is positioned randomly on the grid, avoiding collision with the snake.
 */

public class Food {
    private int[] position; // Coordinates of the food
    private int gridSizeX; // Grid size on the X-axis
    private int gridSizeY; // Grid size on the Y-axis

    /**
     * Creates a new instance of Food and positions it randomly,
     * ensuring it does not appear on the snake.
     *
     * @param gridSizeX Size of the grid on the X-axis.
     * @param gridSizeY Size of the grid on the Y-axis.
     * @param snake      Snake instance to avoid food appearing on it.
     */
    public Food(int gridSizeX, int gridSizeY, Snake snake) {
        this.gridSizeX = gridSizeX;
        this.gridSizeY = gridSizeY;
        spawn(snake); // Position the food initially
    }

    /**
     * Positions the food randomly on the grid, ensuring it does not appear on the snake.
     *
     * @param snake Snake instance to avoid food appearing on it.
     */
    public void spawn(Snake snake) {
        int[] newPosition;
        boolean onSnake;
        do {
            newPosition = new int[]{(int) (Math.random() * gridSizeX), (int) (Math.random() * gridSizeY)};
            onSnake = false;
            // Check if the new food position is on the snake
            for (int[] part : snake.getBody()) {
                if (newPosition[0] == part[0] && newPosition[1] == part[1]) {
                    onSnake = true;
                    break;
                }
            }
        } while (onSnake); // Repeat until finding a valid position
        position = newPosition; // Set the food position
    }

    /**
     * Gets the current coordinates of the food.
     *
     * @return Coordinates of the food.
     */
    public int[] getPosition() {
        return position;
    }
}
