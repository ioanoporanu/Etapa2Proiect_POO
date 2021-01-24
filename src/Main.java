import com.fasterxml.jackson.databind.ObjectMapper;
import input.InputLoader;
import output.Output;
import output.OutputLoader;
import utils.Simulator;

import java.io.File;

/**
 * Entry point to the simulation
 */
public final class Main {

    private Main() { }

    /**
     * Main function which reads the input file and starts simulation
     *
     * @param args input and output files
     * @throws Exception might error when reading/writing/opening files, parsing JSON
     */
    public static void main(final String[] args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        InputLoader input =
                objectMapper.readValue(new File(args[0]), InputLoader.class);
        Simulator simulation = Simulator.getSimulator();

        simulation.runAllTurns(input);

        Output output = new Output();

        OutputLoader.makeOutput(output, input);

        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(args[1]),
                output);
    }
}
