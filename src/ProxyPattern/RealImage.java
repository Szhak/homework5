package ProxyPattern;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class RealImage implements RealEstateImage {
    private final String filename;

    public RealImage(String filename) {
        this.filename = filename;
        loadFromDisk();
    }

    private void loadFromDisk() {
        try {
            java.awt.Image img = ImageIO.read(new File(filename));
            ImageIcon fullImage = new ImageIcon(img);
            System.out.println("Загружено полное изображение: " + filename);
        } catch (IOException e) {
            System.out.println("Ошибка загрузки изображения: " + e.getMessage());
        }
    }

    @Override
    public void displayThumbnail(JLabel label) {
    }

    @Override
    public void displayFullImage(JLabel label) {
        try {
            java.awt.Image img = ImageIO.read(new File(filename));
            if (img != null) {
                int labelWidth = label.getWidth();
                int labelHeight = label.getHeight();

                int imgWidth = img.getWidth(null);
                int imgHeight = img.getHeight(null);

                double widthRatio = (double) labelWidth / imgWidth;
                double heightRatio = (double) labelHeight / imgHeight;
                double scale = Math.min(widthRatio, heightRatio);

                int newWidth = (int) (imgWidth * scale);
                int newHeight = (int) (imgHeight * scale);

                java.awt.Image scaled = img.getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(scaled));
                label.setText("");
            } else {
                label.setText("Ошибка: изображение не найдено.");
            }
        } catch (IOException e) {
            label.setText("Ошибка загрузки изображения: " + e.getMessage());
        }
    }
}
