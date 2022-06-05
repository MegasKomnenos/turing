import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TuringMachineTest {
    @DisplayName("Test basic functionalities of a turing machine")
    @Test
    void testTuringMachine() {
        var tape = new TuringMachineTape();
        var machine = new TuringMachineInternals(tape);

        assert(machine.get_symbol(0) == '\0');
        assert(machine.get_symbol(-1) == '\0');
        assert(machine.get_symbol(1) == '\0');
        assert(machine.get_symbol(-2) == '\0');
        assert(machine.get_symbol(2) == '\0');
        assert(machine.get_symbol(-3) == '\0');

        machine.add_instruction('\0', 's', '\0', 's', true);

        assert(machine.get_instructions().get(0).symbol_after == 's');

        machine.run_once();
        machine.run_once();

        assert(machine.get_symbol(0) == '\0');
        assert(machine.get_symbol(-1) == '\0');
        assert(machine.get_symbol(1) == '\0');

        machine.add_symbol(0, 's');

        assert(machine.get_symbol(0) == 's');

        machine.run_once();

        assert(machine.get_head() == 1);

        machine.run_once();

        assert(machine.get_head() == 1);

        machine.add_instruction('\0', '\0', '\0', 's', true);

        machine.run_once();

        assert(machine.get_head() == 2);
        assert(machine.get_symbol(1) == 's');
    }

    @DisplayName("Run busy beaver on a turing machine")
    @Test
    void testBusyBeaver() {
        var tape = new TuringMachineTape();
        var machine = new TuringMachineInternals(tape);

        machine.add_instruction('A', '\0', 'B', '1', true);
        machine.add_instruction('A', '1', 'C', '1', false);
        machine.add_instruction('B', '\0', 'A', '1', false);
        machine.add_instruction('B', '1', 'B', '1', true);
        machine.add_instruction('C', '\0', 'B', '1', false);
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