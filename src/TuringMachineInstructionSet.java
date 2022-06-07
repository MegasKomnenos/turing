import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

class TuringMachineInstructionSet {
    private HashMap<Integer, TuringMachineInstruction> data;

    public TuringMachineInstructionSet() {
        data = new HashMap<>();
    }

    public void add_instruction(int i, char state_before, char symbol_before, char state_after, char symbol_after, char right) {
        data.put(i, new TuringMachineInstruction(state_before, symbol_before, state_after, symbol_after, right));
    }
    public void remove_instruction(int i) {
        data.remove(i);
    }
    public TuringMachineInstruction get(int i) {
        return data.get(i);
    }
    public TuringMachineInstruction query(char state, char symbol) {
        TuringMachineInstruction instruction = new TuringMachineInstruction(state, symbol, state, symbol, 'R');

        for(var i : data.values()) {
            if(i.equals(instruction)) {
                return i;
            }
        }

        return null;
    }
}