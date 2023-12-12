package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class RandomDrawingGenerator extends JFrame {
    private int numShapes;
    private int width, height;
    private boolean showGrid;

    public RandomDrawingGenerator(int numShapes, int width, int height, boolean showGrid) {
        this.numShapes = numShapes;
        this.width = width;
        this.height = height;
        this.showGrid = showGrid;

        setTitle("Random Drawing Generator");
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DrawingPanel drawingPanel = new DrawingPanel(numShapes, width, height, showGrid);
        add(drawingPanel);

        setVisible(true);
    }

    private class DrawingPanel extends JPanel {
        private int numShapes;
        private int width, height;
        private boolean showGrid;

        public DrawingPanel(int numShapes, int width, int height, boolean showGrid) {
            this.numShapes = numShapes;
            this.width = width;
            this.height = height;
            this.showGrid = showGrid;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            // Рисование координатной сетки
            if (showGrid) {
                drawGrid(g2d);
            }

            // Генерация случайных фигур
            Random random = new Random();
            for (int i = 0; i < numShapes; i++) {
                drawRandomShape(g2d, random);
            }
        }

        private void drawGrid(Graphics2D g2d) {
            g2d.setColor(Color.LIGHT_GRAY);

            // Горизонтальные линии
            for (int i = 0; i <= height; i += 20) {
                g2d.drawLine(0, i, width, i);
            }

            // Вертикальные линии
            for (int i = 0; i <= width; i += 20) {
                g2d.drawLine(i, 0, i, height);
            }
        }

        private void drawRandomShape(Graphics2D g2d, Random random) {
            int shapeType = random.nextInt(6); // 0-линия, 1-окружность, 2-прямоугольник, 3-треугольник, 4-параболла, 5-трапеция

            int x = random.nextInt(width);
            int y = random.nextInt(height);

            int size = random.nextInt(50) + 20; // случайный размер фигуры

            // Устанавливаем толщину линии (в данном случае, 2 пикселя)
            g2d.setStroke(new BasicStroke(2.0f));

            switch (shapeType) {
                case 0:
                    g2d.draw(new Line2D.Double(x, y, x + size, y + size));
                    break;
                case 1:
                    g2d.draw(new Ellipse2D.Double(x, y, size, size));
                    break;
                case 2:
                    g2d.draw(new Rectangle2D.Double(x, y, size, size));
                    break;
                case 3:
                    int[] xPoints = {x, x + size, x + size / 2};
                    int[] yPoints = {y + size, y + size, y};
                    g2d.drawPolygon(xPoints, yPoints, 3);
                    break;
                case 4:
                    // Рисование параболы (пример)
                    QuadCurve2D curve = new QuadCurve2D.Float(x, y, x + size / 2, y + size, x + size, y);
                    g2d.draw(curve);
                    break;
                case 5:
                    // Рисование трапеции (пример)
                    int topWidth = size + random.nextInt(20);
                    int bottomWidth = size + random.nextInt(20);
                    int trapezeHeight = size + random.nextInt(20);

                    int[] xPointsTrapeze = {x, x + topWidth, x + bottomWidth, x - bottomWidth / 2};
                    int[] yPointsTrapeze = {y, y, y + trapezeHeight, y + trapezeHeight};
                    g2d.drawPolygon(xPointsTrapeze, yPointsTrapeze, 4);
                    break;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RandomDrawingGenerator(10, 800, 600, true); //10 - количество базовых фигур,800 - ширина окна, 600 - высота окна, true - указывает, нужно ли рисовать координатную сетку.
        });
    }
}
