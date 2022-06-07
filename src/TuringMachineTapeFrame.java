import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

class TuringMachineTapeFrame extends JFrame {
    int head, width;
    JPanel mainPanel;
    HeadRowPanel headPanel;
    TwoLabelRowPanel tapePanel;
    ButtonRowPanel buttonPanel;
    TuringMachineTape tape;
    ArrayList<SimpleEntry<TuringMachineInternals, ImageIcon>> machines;

    public TuringMachineTapeFrame() {
        tape = new TuringMachineTape();
        machines = new ArrayList();

        add_machine();

        setTitle("Turing Machine Tape");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1080, 220);

        head = 0;
        width = 10;

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        buttonPanel = new ButtonRowPanel(20, 20, 16, 5);
        buttonPanel.setPreferredSize(new Dimension(20 * 5, 20));
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        buttonPanel.set(0, new ImageIcon("resources/icons/reset.png"));
        buttonPanel.set(1, new ImageIcon("resources/icons/start.png"));
        buttonPanel.set(2, new ImageIcon("resources/icons/play.png"));
        buttonPanel.set(3, new ImageIcon("resources/icons/left.png"));
        buttonPanel.set(4, new ImageIcon("resources/icons/right.png"));
        buttonPanel.register(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tape.clear();
                head = 0;
                machines.get(0).getKey().set_state(' ');
                machines.get(0).getKey().set_head(0);
                refresh_tape_labels();
            }
        });
        buttonPanel.register(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                machines.get(0).getKey().run_once();
                refresh_tape_labels();
            }
        });
        buttonPanel.register(2, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                machines.get(0).getKey().run(100);
                refresh_tape_labels();
            }
        });
        buttonPanel.register(3, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                --head;
                refresh_tape_labels();
            }
        });
        buttonPanel.register(4, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ++head;
                refresh_tape_labels();
            }
        });
        mainPanel.add(buttonPanel);

        headPanel = new HeadRowPanel(100, 20, 16, width);
        tapePanel = new TwoLabelRowPanel(100, 100, 20, 60, width);

        headPanel.setPreferredSize(new Dimension(100 * width, 20));
        tapePanel.setPreferredSize(new Dimension(100 * width, 100));

        mainPanel.add(headPanel);
        mainPanel.add(tapePanel);

        add(mainPanel);

        refresh_tape_labels();

        setResizable(false);
        setVisible(true);
    }

    public void add_machine() {
        machines.add(new SimpleEntry(new TuringMachineInternals(tape), new ImageIcon("resources/icons/head.png")));
    }

    void refresh_tape_labels() {
        int i = head - width / 2;

        for(int j = 0; j < width; ++j) {
            var t = headPanel.get(j);

            t.clear();

            for(var m : machines) {
                if(m.getKey().get_head() == i) {
                    t.add(m, machines.indexOf(m));
                }
            }

            tapePanel.set(Integer.toString(i), Character.toString(tape.get(i)), j);

            ++i;
        }
    }
}