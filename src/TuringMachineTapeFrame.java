import javax.swing.*;
import java.awt.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class TuringMachineTapeFrame extends JFrame {
    int head, width;
    JPanel mainPanel;
    ArrayList<TapePanel> panels;

    public TuringMachineTapeFrame() {
        setTitle("Turing Machine Tape");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 300);

        head = 0;
        width = 10;

        mainPanel = new JPanel();

        panels = Stream
                .generate(() -> {
                    var p = new TapePanel(100, 240, 16);
                    mainPanel.add(p);
                    return p;
                })
                .limit(width)
                .collect(Collectors.toCollection(ArrayList::new));

        add(mainPanel);

        setResizable(false);
        setVisible(true);
    }

    void refresh_tape_labels(ArrayList<SimpleEntry<TuringMachineInternals, ImageIcon>> machines, TuringMachineTape tape) {
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