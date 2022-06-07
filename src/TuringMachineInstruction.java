class TuringMachineInstruction implements Comparable<TuringMachineInstruction> {
    char[] data;

    public TuringMachineInstruction(char st_b, char sy_b, char st_a, char sy_a, char r) {
        data = new char[5];
        data[0] = st_b;
        data[1] = sy_b;
        data[2] = st_a;
        data[3] = sy_a;
        data[4] = r == 'R' ? 'R' : 'L';
    }

    @Override
    public int compareTo(TuringMachineInstruction p) {
        if(data[0] < p.data[0]) {
            return -1;
        }
        else if(data[0] > p.data[0]) {
            return 1;
        }
        else {
            if(data[1] < p.data[1]) {
                return -1;
            }
            else if(data[1] > p.data[1]) {
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

        return instruction.data[0] == this.data[0] && instruction.data[1] == this.data[1];
    }
}