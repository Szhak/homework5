package ProxyPattern;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ProxyImage implements RealEstateImage {
    private final String filename;
    private RealImage realImage;
    private ImageIcon thumbnail;

    public ProxyImage(String filename) {
        this.filename = filename;
        loadThumbnail();
    }

    private void loadThumbnail() {
        try {
            java.awt.Image img = ImageIO.read(new File(filename));
            java.awt.Image thumb = img.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);
            thumbnail = new ImageIcon(thumb);
            System.out.println("Миниатюра загружена: " + filename);
        } catch (IOException e) {
            System.out.println("Ошибка загрузки миниатюры: " + e.getMessage());
        }
    }

    @Override
    public void displayThumbnail(JLabel label) {
        label.setIcon(thumbnail);
    }

    @Override
    public void displayFullImage(JLabel label) {
        if (realImage == null) {
            realImage = new RealImage(filename);
        }
        realImage.displayFullImage(label);
    }
}
