import java.util.ArrayList;
import java.util.AbstractMap.SimpleImmutableEntry;

class TuringMachineTape {
    ArrayList<Character> positive, negative;

    public TuringMachineTape() {
        positive = new ArrayList();
        negative = new ArrayList();
    }

    char get(int i) {
        try {
            if(i < 0) {
                return negative.get(-1 - i);
            }
            else {
                return positive.get(i);
            }
        }
        catch(IndexOutOfBoundsException e) {
            return ' ';
        }
    }

    void add(int i, char c) {
        int j;
        ArrayList<Character> list;

        if(i < 0) {
            j = -1 - i;
            list = negative;
        }
        else {
            j = i;
            list = positive;
        }

        while(j >= list.size()) {
            list.add(' ');
        }

        list.set(j, c);
    }
}

class TuringMachineInternals {
    int head;
    char state;
    TuringMachineInstructionSet instructions;
    TuringMachineTape tape;

    public TuringMachineInternals(TuringMachineTape t) {
        head = 0;
        state = ' ';

        instructions = new TuringMachineInstructionSet();
        tape = t;
    }

    char get(int i) {
        return tape.get(i);
    }
    void add_symbol(int i, char c) {
        tape.add(i, c);
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
        var move = instructions.get(state, get(head));

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