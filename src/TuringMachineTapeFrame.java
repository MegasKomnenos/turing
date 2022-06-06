import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class TuringMachineTapeFrame extends JFrame {
    int head, width;
    JPanel mainPanel;
    ArrayList<TapePanel> panels;
    JButton rightButton, leftButton;
    TuringMachineTape tape;
    ArrayList<SimpleEntry<TuringMachineInternals, ImageIcon>> machines;

    public TuringMachineTapeFrame(TuringMachineTape t, ArrayList<SimpleEntry<TuringMachineInternals, ImageIcon>> m) {
        tape = t;
        machines = m;

        setTitle("Turing Machine Tape");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 320);
        setLayout(null);

        head = 0;
        width = 10;

        mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mainPanel.setSize(1100, 260);
        mainPanel.setLocation(100, 10);

        panels = Stream
                .generate(() -> {
                    var p = new TapePanel(100, 240, 16);
                    mainPanel.add(p);
                    return p;
                })
                .limit(width)
                .collect(Collectors.toCollection(ArrayList::new));

        var path = Paths.get(Paths.get(System.getProperty("user.dir")).toString(),"resources", "icon").toString();

        rightButton = new JButton();
        leftButton = new JButton();

        rightButton.setSize(70, 70);
        leftButton.setSize(70, 70);

        rightButton.setIcon(new ImageIcon("resources/icons/arrow_right.png"));
        rightButton.setPressedIcon(new ImageIcon("resources/icons/arrow_right_clicked.png"));
        leftButton.setIcon(new ImageIcon("resources/icons/arrow_left.png"));
        leftButton.setPressedIcon(new ImageIcon("resources/icons/arrow_left_clicked.png"));

        rightButton.setBackground(Color.WHITE);
        leftButton.setBackground(Color.WHITE);

        rightButton.setLocation(1226, 130);
        leftButton.setLocation(10, 130);

        rightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ++head;
                refresh_tape_labels();
            }
        });
        leftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                --head;
                refresh_tape_labels();
            }
        });

        add(mainPanel);
        add(rightButton);
        add(leftButton);

        setResizable(false);
        setVisible(true);
    }

    void refresh_tape_labels() {
        int i = head - width / 2;

        for(var p : panels) {
            p.set_index(Integer.toString(i));
            p.set_value(Character.toString(tape.get(i)));
            p.clear_icon();

            for(var m : machines) {
                if(m.getKey().get_head() == i) {
                    p.add_icon(m.getValue());
                }
            }

            ++i;
        }
    }
}