import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class TapePanel extends JPanel {
    JLabel label;

    public TapePanel(int width, int height, int label_size) {
        label = new JLabel("0");
        label.setFont(new Font(Font.SERIF, Font.PLAIN, label_size));
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label);
        setMinimumSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.LIGHT_GRAY);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    void set(String s) {
        label.setText(s);
    }
}

class HeadPanel extends JPanel {
    int size;
    ArrayList<JLabel> label;

    public HeadPanel(int width, int height, int s) {
        size = s;
        setMinimumSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
    }

    void add(ImageIcon icon) {
        var label = new JLabel();
        label.setIcon(icon);
        label.setMinimumSize(new Dimension(size, size));
        label.setPreferredSize(new Dimension(size, size));
        label.setMaximumSize(new Dimension(size, size));
        add(label);
    }

    void clear() {
        if(label != null) {
            label.clear();
        }
    }
}