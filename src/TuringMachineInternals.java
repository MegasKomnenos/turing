import java.util.ArrayList;
import java.util.AbstractMap.SimpleImmutableEntry;

class TuringMachineTape {
    ArrayList<Character> positive, negative;

    public TuringMachineTape() {
        positive = new ArrayList();
        negative = new ArrayList();
    }

    void clear() {
        positive.clear();
        negative.clear();
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
    char state, halt;
    TuringMachineInstructionSet instructions;
    TuringMachineTape tape;

    public TuringMachineInternals(TuringMachineTape t) {
        head = 0;
        state = ' ';
        halt = ' ';

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
    void set_halt(char h) { halt = h; }
    int get_head() { return head; }
    char get_state() { return state; }
    char get_halt() { return halt; }
    void add_instruction(int i, char state_before, char symbol_before, char state_after, char symbol_after, char right) {
        instructions.add_instruction(i, state_before, symbol_before, state_after, symbol_after, right);
    }
    void remove_instruction(int i) {
        instructions.remove_instruction(i);
    }
    TuringMachineInstruction get_instruction(int i) {
        return instructions.get(i);
    }

    void clear_tape() {
        tape.clear();
    }

    void run_once() {
        var move = instructions.query(state, get(head));

        if(move != null) {
            state = move.data[2];

            add_symbol(head, move.data[3]);

            if(move.data[4] == 'R') {
                ++head;
            }
            else {
                --head;
            }
        }
    }

    void run(int i) {
        while(i-- > 0 && state != halt) {
            run_once();
        }
    }
}