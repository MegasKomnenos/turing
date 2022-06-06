import javax.swing.*;
import java.awt.*;

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