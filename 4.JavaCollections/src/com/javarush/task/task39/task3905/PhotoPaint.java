package com.javarush.task.task39.task3905;

public class PhotoPaint {
    public boolean paintFill(Color[][] image, int x, int y, Color desiredColor) {
        if (x >= image.length || y >= image[0].length || desiredColor == null || desiredColor == image[y][x])
            return false;

        paintFill(image, y, x, desiredColor, image[y][x]);
        return true;
    }

    private void paintFill(Color[][] image, int y, int x, Color desiredColor, Color currentColor) {
        if (y < 0 || x < 0 || y >= image[0].length || x >= image.length || image[y][x] != currentColor)
            return;

        image[y][x] = desiredColor;
        paintFill(image, y - 1, x, desiredColor, currentColor);
        paintFill(image, y + 1 , x, desiredColor, currentColor);
        paintFill(image, y, x - 1, desiredColor, currentColor);
        paintFill(image, y, x + 1, desiredColor, currentColor);
    }
}
