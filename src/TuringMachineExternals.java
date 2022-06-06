import javax.swing.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

class TuringMachineExternals {
    TuringMachineTape tape;
    ArrayList<SimpleEntry<TuringMachineInternals, ImageIcon>> machines;
    int head, width;
    TuringMachineTapeFrame tapeFrame;

    public TuringMachineExternals() {
        tape = new TuringMachineTape();
        machines = new ArrayList();
        tapeFrame = new TuringMachineTapeFrame(tape, machines);

        add_machine();
        add_machine();
        add_machine();

        tapeFrame.refresh_tape_labels();
    }

    public void add_machine() {
        machines.add(new SimpleEntry(new TuringMachineInternals(tape), new ImageIcon(String.format("resources/icons/%d.png", machines.size() % 12))));
    }
}
