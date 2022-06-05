import java.util.ArrayList;
import java.util.Collections;

class TuringMachineInstructionSet {
    private ArrayList<TuringMachineInstruction> data;

    public TuringMachineInstructionSet() {
        data = new ArrayList();
    }

    public void add_instruction(char state_before, char symbol_before, char state_after, char symbol_after, boolean right) {
        TuringMachineInstruction instruction = new TuringMachineInstruction(state_before, symbol_before, state_after, symbol_after, right);

        var i = data.indexOf(instruction);

        if(i >= 0) {
            data.set(i, instruction);
        }
        else {
            data.add(instruction);

            Collections.sort(data);
        }
    }
    public void remove_instruction(char state, char symbol) {
        data.remove(new TuringMachineInstruction(state, symbol, state, symbol, true));
    }
    public TuringMachineInstruction get(char state, char symbol) {
        TuringMachineInstruction instruction = new TuringMachineInstruction(state, symbol, state, symbol, true);

        var i = data.indexOf(instruction);

        if(i >= 0) {
            return data.get(i);
        }
        else {
            return null;
        }
    }
    public ArrayList<TuringMachineInstruction> get_instructions() {
        return data;
    }
}