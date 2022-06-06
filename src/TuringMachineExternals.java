import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class TuringMachineExternals extends JFrame {
    TuringMachineTape tape;
    ArrayList<TuringMachineInternals> machines;
    int head, width;
    JPanel tape_panel, index_panel;
    ArrayList<TapePanel> tape_panels, index_panels;

    public TuringMachineExternals() {
        setTitle("Turing Machine Tape");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 145);
        setLayout(new BorderLayout(0, 10));

        tape_panel = new JPanel();
        index_panel = new JPanel();

        tape_panel.add(new JLabel("Value"));
        index_panel.add(new JLabel("Index"));

        tape = new TuringMachineTape();
        machines = new ArrayList();
        head = 0;
        width = 10;

        tape_panels = Stream
                .generate(() -> {
                    var p = new TapePanel(50, 50, 30);
                    tape_panel.add(p);
                    return p;
                })
                .limit(width)
                .collect(Collectors.toCollection(ArrayList::new));
        index_panels = Stream
                .generate(() -> {
                    var p = new TapePanel(50, 35, 18);
                    index_panel.add(p);
                    return p;
                })
                .limit(width)
                .collect(Collectors.toCollection(ArrayList::new));

        add(index_panel, BorderLayout.NORTH);
        add(tape_panel, BorderLayout.SOUTH);

        //refresh_tape_labels();

        setVisible(true);
    }

    private void refresh_tape_labels() {
        int i = head - width / 2;

        for(var panel : tape_panels) {
            panel.set(Character.toString(tape.get(i)));

            ++i;
        }

        i = head - width / 2;

        for(var panel : index_panels) {
            panel.set(Integer.toString(i));

            ++i;
        }
    }
}
