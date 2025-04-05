package ProxyPattern;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class ImageViewerApp {
    public static void main(String[] args) {
        ProtectedImageUploader uploader = new ProtectedImageUploader();

        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField userField = new JTextField(15);
        JPasswordField passField = new JPasswordField(15);
        gbc.gridx = 0; gbc.gridy = 0;
        loginPanel.add(new JLabel("Логин:"), gbc);
        gbc.gridx = 1;
        loginPanel.add(userField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        loginPanel.add(new JLabel("Пароль:"), gbc);
        gbc.gridx = 1;
        loginPanel.add(passField, gbc);

        int result;
        do {
            result = JOptionPane.showConfirmDialog(
                    null,
                    loginPanel,
                    "🔐 Авторизация",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
                JOptionPane.showMessageDialog(null, "Авторизация отменена. До свидания!");
                System.exit(0);
            }

            String username = userField.getText().trim();
            String password = new String(passField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Пожалуйста, введите логин и пароль.");
                continue;
            }

            if (!uploader.login(username, password)) {
                JOptionPane.showMessageDialog(null, "Неверный логин или пароль. Попробуйте снова. LOG:Zhandos PASS:Crush");
            } else {
                JOptionPane.showMessageDialog(null, "Добро пожаловать!");
                break;
            }
        } while (true);

        JFrame frame = new JFrame("Photo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JLabel fullImageLabel = new JLabel("Выберите изображение", SwingConstants.CENTER);
        fullImageLabel.setPreferredSize(new Dimension(800, 400));
        fullImageLabel.setHorizontalAlignment(JLabel.CENTER);
        fullImageLabel.setVerticalAlignment(JLabel.CENTER);

        JPanel thumbnailPanel = new JPanel(new FlowLayout());
        JPanel controlPanel = new JPanel();

        String[] imageFilenames = {
                "C:/Users/Жандос/IdeaProjects/homework 5/src/ProxyPattern/U.jpg",
                "C:/Users/Жандос/IdeaProjects/homework 5/src/ProxyPattern/U.jpg",
                "C:/Users/Жандос/IdeaProjects/homework 5/src/ProxyPattern/U.jpg"
        };
        ProxyImage[] proxyImages = new ProxyImage[imageFilenames.length];

        JButton clearButton = new JButton("Скрыть фото");
        JButton uploadButton = new JButton("Загрузить");

        clearButton.addActionListener(e -> {
            fullImageLabel.setIcon(null);
            fullImageLabel.setText("Выберите изображение");
        });

        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(frame);

            if (option == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String imagePath = selectedFile.getAbsolutePath();

                ProxyImage newProxyImage = new ProxyImage(imagePath);

                JLabel newThumb = new JLabel("" + (thumbnailPanel.getComponentCount() + 1), SwingConstants.CENTER);
                newThumb.setVerticalTextPosition(JLabel.BOTTOM);
                newThumb.setHorizontalTextPosition(JLabel.CENTER);
                newThumb.setFont(new Font("SansSerif", Font.BOLD, 13));
                newThumb.setForeground(new Color(30, 30, 30));
                newThumb.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));
                newThumb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                newProxyImage.displayThumbnail(newThumb);
                thumbnailPanel.add(newThumb);
                thumbnailPanel.revalidate();
                thumbnailPanel.repaint();

                newThumb.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e2) {
                        newProxyImage.displayFullImage(fullImageLabel);
                    }
                });
            }
        });

        controlPanel.add(clearButton);
        controlPanel.add(uploadButton);

        frame.setLayout(new BorderLayout());
        frame.add(thumbnailPanel, BorderLayout.NORTH);
        frame.add(fullImageLabel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
        frame.getContentPane().setBackground(Color.WHITE);

        fullImageLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        fullImageLabel.setForeground(new Color(50, 50, 50));
        fullImageLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        thumbnailPanel.setBackground(new Color(245, 245, 245));
        thumbnailPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int i = 0; i < imageFilenames.length; i++) {
            proxyImages[i] = new ProxyImage(imageFilenames[i]);

            JLabel thumbLabel = new JLabel("" + (i + 1), SwingConstants.CENTER);
            thumbLabel.setVerticalTextPosition(JLabel.BOTTOM);
            thumbLabel.setHorizontalTextPosition(JLabel.CENTER);
            thumbLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
            thumbLabel.setForeground(new Color(30, 30, 30));
            thumbLabel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));

            proxyImages[i].displayThumbnail(thumbLabel);
            thumbnailPanel.add(thumbLabel);

            int finalI = i;
            thumbLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            thumbLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    proxyImages[finalI].displayFullImage(fullImageLabel);
                }
            });
        }

        clearButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        clearButton.setForeground(Color.WHITE);
        clearButton.setBackground(new Color(30, 144, 255));
        clearButton.setFocusPainted(false);
        clearButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        clearButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        uploadButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        uploadButton.setForeground(Color.WHITE);
        uploadButton.setBackground(new Color(34, 139, 34));
        uploadButton.setFocusPainted(false);
        uploadButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        uploadButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}
