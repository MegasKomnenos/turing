class TuringMachineInstruction implements Comparable<TuringMachineInstruction> {
    char state_before, symbol_before, state_after, symbol_after;
    boolean right;

    public TuringMachineInstruction(char st_b, char sy_b, char st_a, char sy_a, boolean r) {
        state_before = st_b;
        symbol_before = sy_b;
        state_after = st_a;
        symbol_after = sy_a;
        right = r;
    }

    @Override
    public int compareTo(TuringMachineInstruction p) {
        if(state_before < p.state_before) {
            return -1;
        }
        else if(state_before > p.state_before) {
            return 1;
        }
        else {
            if(symbol_before < p.symbol_before) {
                return -1;
            }
            else if(symbol_before > p.symbol_after) {
                return 1;
            }
            else {
                return 0;
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || !obj.getClass().equals(this.getClass())) {
            return false;
        }

        var instruction = (TuringMachineInstruction) obj;

        return instruction.state_before == this.state_before && instruction.symbol_before == this.symbol_before;
    }
}