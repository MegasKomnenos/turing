import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TuringMachineTest {
    @DisplayName("Test basic functionalities of a tape")
    @Test
    void testTape() {
        var tape = new TuringMachineTape();

        assert(tape.get(0) == ' ');
        assert(tape.get(-1) == ' ');
        
        tape.add(0, 'c');
        tape.add(-1, 'p');

        assert(tape.get(0) == 'c');
        assert(tape.get(-1) == 'p');
        assert(tape.get(1) == ' ');
        assert(tape.get(-2) == ' ');

        tape.add(10, 'c');
        tape.add(-10, 'p');

        assert(tape.get(10) == 'c');
        assert(tape.get(-10) == 'p');

        assert(tape.get(9) == ' ');
        assert(tape.get(8) == ' ');
        assert(tape.get(7) == ' ');
        assert(tape.get(6) == ' ');
        assert(tape.get(5) == ' ');
        assert(tape.get(4) == ' ');
        assert(tape.get(3) == ' ');
        assert(tape.get(2) == ' ');
        assert(tape.get(1) == ' ');

        assert(tape.get(-9) == ' ');
        assert(tape.get(-8) == ' ');
        assert(tape.get(-7) == ' ');
        assert(tape.get(-6) == ' ');
        assert(tape.get(-5) == ' ');
        assert(tape.get(-4) == ' ');
        assert(tape.get(-3) == ' ');
        assert(tape.get(-2) == ' ');

        tape.clear();

        assert(tape.get(0) == ' ');
        assert(tape.get(-1) == ' ');
    }
    @DisplayName("Test basic functionalities of a turing machine")
    @Test
    void testTuringMachine() {
        var tape = new TuringMachineTape();
        var machine = new TuringMachineInternals(tape);

        assert(machine.get(0) == ' ');
        assert(machine.get(-1) == ' ');
        assert(machine.get(1) == ' ');
        assert(machine.get(-2) == ' ');
        assert(machine.get(2) == ' ');
        assert(machine.get(-3) == ' ');

        machine.add_instruction(0, ' ', 's', ' ', 's', 'R');

        assert(machine.get_instruction(0).data[3] == 's');

        machine.run_once();
        machine.run_once();

        assert(machine.get(0) == ' ');
        assert(machine.get(-1) == ' ');
        assert(machine.get(1) == ' ');

        machine.add_symbol(0, 's');

        assert(machine.get(0) == 's');

        machine.run_once();

        assert(machine.get_head() == 1);

        machine.run_once();

        assert(machine.get_head() == 1);

        machine.add_instruction(1, ' ', ' ', ' ', 's', 'R');

        machine.run_once();

        assert(machine.get_head() == 2);
        assert(machine.get(1) == 's');
    }

    @DisplayName("Run busy beaver on a turing machine")
    @Test
    void testBusyBeaver() {
        var tape = new TuringMachineTape();
        var machine = new TuringMachineInternals(tape);

        machine.add_instruction(0, 'A', ' ', 'B', '1', 'R');
        machine.add_instruction(1, 'A', '1', 'C', '1', 'L');
        machine.add_instruction(2, 'B', ' ', 'A', '1', 'L');
        machine.add_instruction(3, 'B', '1', 'B', '1', 'R');
        machine.add_instruction(4, 'C', ' ', 'B', '1', 'L');
        machine.add_instruction(5, 'C', '1', 'H', '1', 'R');

        machine.set_state('A');
        machine.set_halt('H');

        machine.run_once();
        assert(machine.get_head() == 1);
        machine.run_once();
        assert(machine.get_head() == 0);
        machine.run_once();
        assert(machine.get_head() == -1);
        machine.run_once();
        assert(machine.get_head() == -2);
        machine.run_once();
        assert(machine.get_head() == -3);
        machine.run_once();
        assert(machine.get_head() == -2);
        machine.run_once();
        assert(machine.get_head() == -1);
        machine.run_once();
        assert(machine.get_head() == 0);
        machine.run_once();
        assert(machine.get_head() == 1);
        machine.run_once();
        assert(machine.get_head() == 2);
        machine.run_once();
        assert(machine.get_head() == 1);
        machine.run_once();
        assert(machine.get_head() == 0);
        machine.run_once();
        assert(machine.get_head() == 1);
        assert(machine.get_state() == machine.get_halt());

        machine.clear_tape();
        machine.set_state('A');

        machine.run(100);

        assert(machine.get_state() == machine.get_halt());
    }
}