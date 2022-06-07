import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class HeadPanel extends JPanel {
    int size;
    ArrayList<JButton> buttons;

    public HeadPanel(int width, int height, int s) {
        size = s;
        setPreferredSize(new Dimension(width, height));
        buttons = new ArrayList();
    }

    class TuringMachineHeadButtonActionListener implements ActionListener {
        TuringMachineInternals machine;

        public TuringMachineHeadButtonActionListener(TuringMachineInternals m) {
            machine = m;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }

    void add(SimpleEntry<TuringMachineInternals, ImageIcon> entry) {
        for(var b : buttons) {
            if(!b.isVisible()) {
                b.setIcon(entry.getValue());
                b.setVisible(true);
                b.addActionListener(new TuringMachineHeadButtonActionListener(entry.getKey()));
                return;
            }
        }

        var button = new JButton();
        button.setIcon(entry.getValue());
        button.setPreferredSize(new Dimension(size, size));
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setBackground(Color.WHITE);
        button.addActionListener(new TuringMachineHeadButtonActionListener(entry.getKey()));
        buttons.add(button);
        add(button);
    }

    void clear() {
        for(var b : buttons) {
            if(b.isVisible()) {
                b.setIcon(null);
                b.setVisible(false);
                b.revalidate();
                b.removeActionListener(b.getActionListeners()[0]);
            }
        }
    }
}

abstract class RowPanel<E> extends JPanel {
    ArrayList<E> panels;

    public RowPanel(int width, int height, int size, int num) {
        setPreferredSize(new Dimension(width, height));

        panels = Stream
                .generate(() -> genPanel(width, height, size))
                .limit(num)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    E get(int i) { return panels.get(i); }

    abstract E genPanel(int width, int height, int size);
}

class ItemRowPanel extends RowPanel<SimpleEntry<JPanel, JLabel>> {
    public ItemRowPanel(int width, int height, int size, int num) {
        super(width, height, size, num);
    }

    SimpleEntry<JPanel, JLabel> genPanel(int width, int height, int size) {
        var p = new JPanel();
        var l = new JLabel("0");

        l.setFont(new Font(Font.SERIF, Font.PLAIN, size));
        l.setHorizontalAlignment(JLabel.CENTER);
        l.setVerticalAlignment(JLabel.CENTER);

        p.add(l);
        p.setPreferredSize(new Dimension(width, height));
        p.setBackground(Color.LIGHT_GRAY);
        p.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        add(p);

        return new SimpleEntry<>(p, l);
    }
}

class HeadRowPanel extends RowPanel<HeadPanel> {
    public HeadRowPanel(int width, int height, int size, int num) {
        super(width, height, size, num);
    }

    HeadPanel genPanel(int width, int height, int size) {
        var p = new HeadPanel(width, height, size);
        add(p);
        return p;
    }
}