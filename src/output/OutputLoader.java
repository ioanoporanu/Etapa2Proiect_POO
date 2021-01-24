package output;

import input.InputLoader;
import players.Consumer;
import players.Distributor;
import players.Producer;
import playersout.PlayersOut;
import playersout.PlayersOutFactory;

public final
class OutputLoader {

    private OutputLoader() {

    }
    /**
     *
     * @param output output
     * @param input input
     */
    public static
    void makeOutput(final Output output, final InputLoader input) {
        for (Consumer consumer : input.getInitialData().getConsumers()) {
            PlayersOut consumerOut = PlayersOutFactory.makePlayersOut(
                    PlayersOutFactory.Player.valueOf("consumer"), consumer);

            output.getConsumers().add((consumerOut));
        }

        for (Distributor distributor : input.getInitialData().getDistributors()) {
            PlayersOut distributorsOut =
                     PlayersOutFactory.makePlayersOut(
                            PlayersOutFactory.Player.valueOf("distributor"), distributor);
            output.getDistributors().add(distributorsOut);
        }

        for (Producer producer : input.getInitialData().getProducers()) {
            PlayersOut producerOut = PlayersOutFactory.makePlayersOut(
                    PlayersOutFactory.Player.valueOf("producer"), producer);
            output.getEnergyProducers().add(producerOut);
        }
    }
}
