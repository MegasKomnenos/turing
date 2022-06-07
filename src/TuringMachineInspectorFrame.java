import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TuringMachineInspectorFrame extends JFrame {
    int head;
    TuringMachineInternals machine;
    JPanel mainPanel;
    MachineRowPanel topPanel;
    ArrayList<InstructionRowPanel> instPanels;
    TwoLabelRowPanel topPanelExpl, instPanelExpl;
    ButtonRowPanel buttonPanel;

    public TuringMachineInspectorFrame(TuringMachineInternals m) {
        head = 0;
        machine = m;

        setTitle("Turing Machine Inspector");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 840);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mainPanel.setLocation(50, 10);

        topPanelExpl = new TwoLabelRowPanel(100, 20, 10, 0, 3);
        topPanelExpl.set("current head", " ", 0);
        topPanelExpl.set("current state", " ", 1);
        topPanelExpl.set("halt state", " ", 2);
        topPanelExpl.setPreferredSize(new Dimension(350, 30));
        topPanelExpl.setMaximumSize(new Dimension(350, 30));
        mainPanel.add(topPanelExpl);

        topPanel = new MachineRowPanel(100, 60, 30, machine);
        topPanel.setPreferredSize(new Dimension(350, 80));
        topPanel.setMaximumSize(new Dimension(350, 80));
        mainPanel.add(topPanel);

        instPanelExpl = new TwoLabelRowPanel(100, 20, 10, 0, 5);
        instPanelExpl.set("state", " ", 0);
        instPanelExpl.set("symbol", " ", 1);
        instPanelExpl.set("state after", " ", 2);
        instPanelExpl.set("symbol after", " ", 3);
        instPanelExpl.set("direction", " ", 4);
        instPanelExpl.setPreferredSize(new Dimension(550, 30));
        instPanelExpl.setMaximumSize(new Dimension(550, 30));
        mainPanel.add(instPanelExpl);

        instPanels = Stream
                .generate(() -> {
                    var p = new InstructionRowPanel(100, 80, 40, 5, machine);
                    p.setPreferredSize(new Dimension(550, 90));
                    p.setMaximumSize(new Dimension(550, 90));
                    mainPanel.add(p);
                    return p;
                })
                .limit(7)
                .collect(Collectors.toCollection(ArrayList::new));

        refresh_tape_labels();

        buttonPanel = new ButtonRowPanel(20, 20, 16, 2);
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        buttonPanel.set(0, new ImageIcon("resources/icons/up.png"));
        buttonPanel.set(1, new ImageIcon("resources/icons/down.png"));
        buttonPanel.register(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(head <= 0 ) {
                    head = 0;
                } else {
                    --head;
                    refresh_tape_labels();
                }
            }
        });
        buttonPanel.register(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ++head;
                refresh_tape_labels();
            }
        });
        mainPanel.add(buttonPanel);

        add(mainPanel);

        setResizable(false);
        setVisible(true);
    }

    void refresh_tape_labels() {
        int i = head;
        for(var p : instPanels) {
            p.setRow(i++);
        }
    }
}
