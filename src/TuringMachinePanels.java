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
            new TuringMachineInspectorFrame(machine, index);
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

class TwoLabelRowPanel extends RowPanel<SimpleEntry<JPanel, SimpleEntry<JLabel, JLabel>>> {
    int size_other;

    public TwoLabelRowPanel(int w, int h, int s, int ss, int num) {
        super(w, h, s, num);

        size_other = ss;

        for(var p : panels) {
            p.getValue().getValue().setFont(new Font(Font.SERIF, Font.PLAIN, size_other));
        }
    }

    void set(String s, String ss, int index) {
        assert(index >= 0 && index < panels.size());

        panels.get(index).getValue().getKey().setText(s);
        panels.get(index).getValue().getValue().setText(ss);
    }

    SimpleEntry<JPanel, SimpleEntry<JLabel, JLabel>> genPanel() {
        var p = new JPanel();
        var l = new JLabel("0");
        var ll = new JLabel("0");

        l.setFont(new Font(Font.SERIF, Font.PLAIN, size));
        ll.setFont(new Font(Font.SERIF, Font.PLAIN, size_other));

        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        ll.setAlignmentX(Component.CENTER_ALIGNMENT);

        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(l);
        p.add(ll);
        p.setPreferredSize(new Dimension(width, height));
        p.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        add(p);

        return new SimpleEntry<>(p, new SimpleEntry<>(l, ll));
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