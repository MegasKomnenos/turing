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
            new TuringMachineInspectorFrame(machine);
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

class ButtonRowPanel extends RowPanel<JButton> {
    public ButtonRowPanel(int w, int h, int s, int num) {
        super(w, h, s, num);
    }

    void set(int i, ImageIcon icon) {
        var button = panels.get(i);

        button.setVisible(true);
        button.setIcon(icon);
    }
    void register(int i, ActionListener l) {
        var button = panels.get(i);

        button.addActionListener(l);
    }

    void unset(int i) {
        var button = panels.get(i);

        if(button.isVisible()) {
            button.setVisible(false);
            button.setIcon(null);
            button.removeActionListener(button.getActionListeners()[0]);
            button.revalidate();
        }
    }

    JButton genPanel() {
        var button = new JButton();
        button.setPreferredSize(new Dimension(size, size));
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setBackground(Color.WHITE);
        button.setVisible(false);
        add(button);

        return button;
    }
}

class MachineRowPanel extends RowPanel<SimpleEntry<JPanel, JTextField>> {
    TuringMachineInternals machine;

    public MachineRowPanel(int w, int h, int s, TuringMachineInternals m) {
        super(w, h, s, 3);
        machine = m;

        panels.get(0).getValue().setText(Integer.toString(machine.get_head()));
        panels.get(1).getValue().setText(Character.toString(machine.get_state()));
        panels.get(2).getValue().setText(Character.toString(machine.get_halt()));

        panels.get(0).getValue().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var t = panels.get(0).getValue();
                var s = t.getText().strip();

                if(s != null && s.length() > 0) {
                    try {
                        int i = Integer.parseInt(s);
                        t.setText(s);
                        machine.set_head(i);
                    } catch(NumberFormatException ex) {
                        t.setText(Integer.toString(machine.get_head()));
                    }
                }
                else {
                    t.setText(Integer.toString(machine.get_head()));
                }

                Main.getMainFrame().refresh_tape_labels();
            }
        });
        panels.get(1).getValue().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var t = panels.get(1).getValue();
                var s = t.getText().strip();

                if(s == null || s.length() == 0) {
                    t.setText(" ");
                    machine.set_state(' ');
                }
                else {
                    t.setText(Character.toString(s.charAt(0)));
                    machine.set_state(s.charAt(0));
                }
            }
        });
        panels.get(2).getValue().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var t = panels.get(2).getValue();
                var s = t.getText().strip();

                if(s == null || s.length() == 0) {
                    t.setText(" ");
                    machine.set_halt(' ');
                }
                else {
                    t.setText(Character.toString(s.charAt(0)));
                    machine.set_halt(s.charAt(0));
                }
            }
        });
    }

    SimpleEntry<JPanel, JTextField> genPanel() {
        var p = new JPanel();
        var t = new JTextField();

        t.setPreferredSize(new Dimension(width - 8, height - 8));
        t.setFont(new Font(Font.SERIF, Font.PLAIN, size));
        p.setPreferredSize(new Dimension(width, height));
        p.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        p.add(t);

        add(p);

        return new SimpleEntry<>(p, t);
    }
}

class InstructionRowPanel extends RowPanel<SimpleEntry<JPanel, JTextField>> {
    TuringMachineInternals machine;
    TuringMachineInstruction instruction;
    int row;

    public InstructionRowPanel(int w, int h, int s, int num, TuringMachineInternals m) {
        super(w, h, s, num);
        machine = m;

        setRow(0);

        int i = 0;
        for(var p : panels) {
            p.getValue().addActionListener(new InstructionTextFieldActionListener(i++));
        }
    }

    void setRow(int r) {
        row = r;

        instruction = machine.get_instruction(row);

        int i = 0;
        for(var p : panels) {
            p.getValue().setText(instruction == null ? " " : Character.toString(instruction.data[i++]));
        }
    }

    class InstructionTextFieldActionListener implements ActionListener {
        int index;

        public InstructionTextFieldActionListener(int i) {
            index = i;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            var t = panels.get(index).getValue();
            var s = t.getText().strip();

            if(s == null || s.length() == 0) {
                if(instruction != null) {
                    instruction.data[index] = ' ';

                    if(instruction.data[0] == ' ' && instruction.data[1] == ' ' && instruction.data[2] == ' ' && instruction.data[3] == ' ' && instruction.data[4] == ' ') {
                        instruction = null;
                        machine.remove_instruction(row);
                    }
                }

                t.setText(" ");
            }
            else {
                if(instruction == null) {
                    machine.add_instruction(row, ' ', ' ', ' ', ' ', ' ');
                    instruction = machine.get_instruction(row);
                }

                if(index == 4) {
                    instruction.data[index] = s.charAt(0) == 'R' ? 'R' : 'L';
                }
                else {
                    instruction.data[index] = s.charAt(0);
                }

                t.setText(Character.toString(instruction.data[index]));
            }
        }
    }


    SimpleEntry<JPanel, JTextField> genPanel() {
        var p = new JPanel();
        var t = new JTextField();

        t.setPreferredSize(new Dimension(width - 8, height - 8));
        t.setFont(new Font(Font.SERIF, Font.PLAIN, size));
        p.setPreferredSize(new Dimension(width, height));
        p.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        p.add(t);

        add(p);

        return new SimpleEntry<>(p, t);
    }
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
        l.setPreferredSize(new Dimension(size, size));
        ll.setPreferredSize(new Dimension(size_other, size_other));

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