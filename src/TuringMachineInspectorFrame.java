import javax.swing.*;
import java.awt.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

public class TuringMachineInspectorFrame extends JFrame {
    int index;
    TuringMachineInternals machine;
    JPanel mainPanel;
    TwoLabelRowPanel topPanel;
    ArrayList<TwoLabelRowPanel> instPanels;

    public TuringMachineInspectorFrame(TuringMachineInternals m, int i) {
        index = i;
        machine = m;

        setTitle("Turing Machine Inspector");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 800);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mainPanel.setSize(400, 600);
        mainPanel.setLocation(50, 10);

        topPanel = new TwoLabelRowPanel(100, 60, 10, 30, 3);
        topPanel.set("machine index", Integer.toString(index), 0);
        topPanel.set("current state", Character.toString(m.get_state()), 1);
        topPanel.set("current head", Integer.toString(m.get_head()), 2);
        topPanel.setLocation(10, 10);
        mainPanel.add(topPanel);
        add(mainPanel);

        setResizable(false);
        setVisible(true);
    }
}
