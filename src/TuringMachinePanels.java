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
        int index;

        public TuringMachineHeadButtonActionListener(TuringMachineInternals m, int i) {
            machine = m;
            index = i;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }

    void add(SimpleEntry<TuringMachineInternals, ImageIcon> entry, int i) {
        for(var b : buttons) {
            if(!b.isVisible()) {
                b.setIcon(entry.getValue());
                b.setVisible(true);
                b.addActionListener(new TuringMachineHeadButtonActionListener(entry.getKey(), i));
                return;
            }
        }

        var button = new JButton();
        button.setIcon(entry.getValue());
        button.setPreferredSize(new Dimension(size, size));
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setBackground(Color.WHITE);
        button.addActionListener(new TuringMachineHeadButtonActionListener(entry.getKey(), i));
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
    int width, height, size;

    public RowPanel(int w, int h, int s, int num) {
        setPreferredSize(new Dimension(width, height));
        width = w;
        height = h;
        size = s;

        panels = Stream
                .generate(() -> genPanel())
                .limit(num)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    E get(int i) { return panels.get(i); }

    abstract E genPanel();
}

class ItemRowPanel extends RowPanel<SimpleEntry<JPanel, JLabel>> {
    public ItemRowPanel(int width, int height, int size, int num) {
        super(width, height, size, num);
    }

    SimpleEntry<JPanel, JLabel> genPanel() {
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

    HeadPanel genPanel() {
        var p = new HeadPanel(width, height, size);
        add(p);
        return p;
    }
}