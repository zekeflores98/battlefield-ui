package io.map.battleship.ui.utils;

import java.awt.Color;

public class ColorUtils {
    public static Color makeTransparent(Color color, int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }
}
