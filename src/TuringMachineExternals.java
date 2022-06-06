import javax.swing.*;
import java.awt.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class TuringMachineExternals extends JFrame {
    TuringMachineTape tape;
    ArrayList<ImageIcon> machine_icons;
    ArrayList<TuringMachineInternals> machines;
    int head, width;
    JPanel tape_panel, index_panel, bottom_panel, head_panel;
    ArrayList<TapePanel> tape_panels, index_panels;
    ArrayList<HeadPanel> head_panels;

    public TuringMachineExternals() {
        setTitle("Turing Machine Tape");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 240);

        tape_panel = new JPanel();
        index_panel = new JPanel();
        head_panel = new JPanel();
        bottom_panel = new JPanel();

        tape_panel.add(new JLabel("Value"));
        index_panel.add(new JLabel("Index"));
        head_panel.add(new JLabel("Heads"));

        tape = new TuringMachineTape();
        machines = new ArrayList();
        machine_icons = new ArrayList();
        head = 0;
        width = 10;

        tape_panels = Stream
                .generate(() -> {
                    var p = new TapePanel(75, 75, 45);
                    tape_panel.add(p);
                    return p;
                })
                .limit(width)
                .collect(Collectors.toCollection(ArrayList::new));
        index_panels = Stream
                .generate(() -> {
                    var p = new TapePanel(75, 50, 27);
                    index_panel.add(p);
                    return p;
                })
                .limit(width)
                .collect(Collectors.toCollection(ArrayList::new));
        head_panels = Stream
                .generate(() -> {
                    var p = new HeadPanel(75, 30, 16);
                    head_panel.add(p);
                    return p;
                })
                .limit(width)
                .collect(Collectors.toCollection(ArrayList::new));

        setLayout(new BorderLayout());
        bottom_panel.setLayout(new BorderLayout(0, 10));
        bottom_panel.add(index_panel, BorderLayout.NORTH);
        bottom_panel.add(tape_panel, BorderLayout.SOUTH);
        add(head_panel, BorderLayout.NORTH);
        add(bottom_panel, BorderLayout.SOUTH);

        add_machine();
        add_machine();

        refresh_tape_labels();

        setResizable(false);
        setVisible(true);
    }

    public void add_machine() {
        machines.add(new TuringMachineInternals(tape));
        machine_icons.add(new ImageIcon(String.format("resources/icons/%d.png", machine_icons.size() % 12)));
    }

    private void refresh_tape_labels() {
        int i = head - width / 2, j = head + width / 2;

        head_panels.forEach(s -> s.clear());

        for(int k = 0; i + k < j; ++k) {
            tape_panels.get(k).set(Character.toString(tape.get(i + k)));
            index_panels.get(k).set(Integer.toString(i + k));

            for(int kk = 0; kk < machines.size(); ++kk) {
                if(machines.get(kk).get_head() == i + k) {
                    head_panels.get(k).add(machine_icons.get(kk));
                }
            }
        }
    }
}
