import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class TapePanel extends JPanel {
    HeadPanel head;
    JPanel indexPanel, valuePanel;
    JLabel indexLabel, valueLabel;

    public TapePanel(int width, int height, int size) {
        setPreferredSize(new Dimension(width, height));

        head = new HeadPanel(width, height / 6, size);
        indexPanel = new JPanel();
        valuePanel = new JPanel();
        indexLabel = new JLabel("0");
        valueLabel = new JLabel("0");

        indexLabel.setFont(new Font(Font.SERIF, Font.PLAIN, height / 8));
        indexLabel.setHorizontalAlignment(JLabel.CENTER);
        indexLabel.setVerticalAlignment(JLabel.CENTER);
        indexPanel.add(indexLabel);
        indexPanel.setPreferredSize(new Dimension(width, height / 5));
        indexPanel.setBackground(Color.LIGHT_GRAY);
        indexPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        valueLabel.setFont(new Font(Font.SERIF, Font.PLAIN, height / 4));
        valueLabel.setHorizontalAlignment(JLabel.CENTER);
        valueLabel.setVerticalAlignment(JLabel.CENTER);
        valuePanel.add(valueLabel);
        valuePanel.setPreferredSize(new Dimension(width, height / 3));
        valuePanel.setBackground(Color.LIGHT_GRAY);
        valuePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(head);
        add(indexPanel);
        add(valuePanel);
    }

    void set_index(String s) {
        indexLabel.setText(s);
    }
    void set_value(String s) {
        valueLabel.setText(s);
    }
    void add_icon(ImageIcon icon) {
        head.add(icon);
    }
    void clear_icon() {
        head.clear();
    }
}

class HeadPanel extends JPanel {
    int size;
    ArrayList<JLabel> labels;

    public HeadPanel(int width, int height, int s) {
        size = s;
        setPreferredSize(new Dimension(width, height));
        labels = new ArrayList();
    }

    void add(ImageIcon icon) {
        for(var l : labels) {
            if(!l.isVisible()) {
                l.setIcon(icon);
                l.setVisible(true);
                return;
            }
        }

        var label = new JLabel();
        label.setIcon(icon);
        label.setPreferredSize(new Dimension(size, size));
        labels.add(label);
        add(label);
    }

    void clear() {
        for(var l : labels) {
            l.setIcon(null);
            l.setVisible(false);
            l.revalidate();
        }
    }
}