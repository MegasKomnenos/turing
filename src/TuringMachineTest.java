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

        machine.add_instruction(' ', 's', ' ', 's', true);

        assert(machine.get_instructions().get(0).symbol_after == 's');

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

        machine.add_instruction(' ', ' ', ' ', 's', true);

        machine.run_once();

        assert(machine.get_head() == 2);
        assert(machine.get(1) == 's');
    }

    @DisplayName("Run busy beaver on a turing machine")
    @Test
    void testBusyBeaver() {
        var tape = new TuringMachineTape();
        var machine = new TuringMachineInternals(tape);

        machine.add_instruction('A', ' ', 'B', '1', true);
        machine.add_instruction('A', '1', 'C', '1', false);
        machine.add_instruction('B', ' ', 'A', '1', false);
        machine.add_instruction('B', '1', 'B', '1', true);
        machine.add_instruction('C', ' ', 'B', '1', false);
        machine.add_instruction('C', '1', 'H', '1', true);

        machine.set_state('A');

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
        assert(machine.get_state() == 'H');
    }
}