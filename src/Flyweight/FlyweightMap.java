import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

// Flyweight class
class MarkerType {
    private final String name;
    private final Color color;
    private final Icon icon;

    public MarkerType(String name, Color color, Icon icon) {
        this.name = name;
        this.color = color;
        this.icon = icon;
    }

    public void draw(Graphics g, int x, int y) {
        icon.paintIcon(null, g, x, y);
        g.setColor(color);
        g.drawString(name, x, y + 30);
    }
}

// Flyweight factory
class MarkerFactory {
    private static final Map<String, MarkerType> types = new HashMap<>();

    public static MarkerType getMarkerType(String name, Color color, Icon icon) {
        String key = name;
        if (!types.containsKey(key)) {
            types.put(key, new MarkerType(name, color, icon));
        }
        return types.get(key);
    }

    public static int getTotalTypes() {
        return types.size();
    }
}

// Marker using Flyweight
class Marker {
    private final int x;
    private final int y;
    private final MarkerType type;

    public Marker(int x, int y, MarkerType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public void draw(Graphics g) {
        type.draw(g, x, y);
    }
}

// Map Panel
class MapPanel extends JPanel {
    private final List<Marker> markers = new ArrayList<>();

    public void addMarker(int x, int y, String name, Color color, Icon icon) {
        MarkerType type = MarkerFactory.getMarkerType(name, color, icon);
        Marker marker = new Marker(x, y, type);
        markers.add(marker);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Marker marker : markers) {
            marker.draw(g);
        }
    }

    public int getTotalMarkers() {
        return markers.size();
    }
}

public class FlyweightMap {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Карта с маркерами (Flyweight)");
        MapPanel mapPanel = new MapPanel();

        Icon hospitalIcon = UIManager.getIcon("OptionPane.errorIcon");
        Icon restaurantIcon = UIManager.getIcon("OptionPane.informationIcon");
        Icon gasIcon = UIManager.getIcon("OptionPane.warningIcon");

        Random rand = new Random();
        for (int i = 0; i < 1000; i++) {
            int x = rand.nextInt(800);
            int y = rand.nextInt(600);
            int type = rand.nextInt(3);
            switch (type) {
                case 0 -> mapPanel.addMarker(x, y, "Больница", Color.RED, hospitalIcon);
                case 1 -> mapPanel.addMarker(x, y, "Ресторан", Color.BLUE, restaurantIcon);
                case 2 -> mapPanel.addMarker(x, y, "Заправка", Color.GREEN, gasIcon);
            }
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);
        frame.add(mapPanel);
        frame.setVisible(true);

        System.out.println("Всего маркеров: " + mapPanel.getTotalMarkers());
        System.out.println("Уникальных объектов стиля: " + MarkerFactory.getTotalTypes());
    }
}
