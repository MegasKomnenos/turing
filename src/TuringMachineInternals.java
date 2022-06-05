import java.util.ArrayList;
import java.util.AbstractMap.SimpleImmutableEntry;

class TuringMachineTape {
    ArrayList<Character> positive, negative;

    public TuringMachineTape() {
        positive = new ArrayList();
        negative = new ArrayList();
    }

    SimpleImmutableEntry<ArrayList<Character>, Integer> get(int i) {
        if(i < 0) {
            return new SimpleImmutableEntry(negative, -1 - i);
        }
        else {
            return new SimpleImmutableEntry(positive, i);
        }
    }
}

class TuringMachineInternals {
    int head;
    char state;
    TuringMachineInstructionSet instructions;
    TuringMachineTape tape;

    public TuringMachineInternals(TuringMachineTape t) {
        head = 0;
        state = '\0';

        instructions = new TuringMachineInstructionSet();
        tape = t;
    }

    private char get_symbol(ArrayList<Character> list, int i) {
        if(i < list.size()) {
            return list.get(i);
        }
        else {
            return '\0';
        }
    }
    char get_symbol(int i) {
        var tmp = tape.get(i);

        return get_symbol(tmp.getKey(), tmp.getValue());
    }
    private void add_symbol(ArrayList<Character> list, int i, char c) {
        while(i >= list.size()) {
            list.add('\0');
        }

        list.set(i, c);
    }
    void add_symbol(int i, char c) {
        var tmp = tape.get(i);

        add_symbol(tmp.getKey(), tmp.getValue(), c);
    }
    void set_head(int h) {
        head = h;
    }
    void set_state(char s) { state = s; }
    int get_head() { return head; }
    char get_state() {
        return state;
    }
    public ArrayList<TuringMachineInstruction> get_instructions() {
        return instructions.get_instructions();
    }
    void add_instruction(char state_before, char symbol_before, char state_after, char symbol_after, boolean right) {
        instructions.add_instruction(state_before, symbol_before, state_after, symbol_after, right);
    }
    void remove_instruction(char state, char symbol) {
        instructions.remove_instruction(state, symbol);
    }

    void run_once() {
        var move = instructions.get(state, get_symbol(head));

        if(move != null) {
            state = move.state_after;

            add_symbol(head, move.symbol_after);

            if(move.right) {
                ++head;
            }
            else {
                --head;
            }
        }
    }
}