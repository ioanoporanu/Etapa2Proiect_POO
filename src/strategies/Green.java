package strategies;

import input.InputLoader;
import players.Distributor;
import players.Producer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public final
class Green implements Strategy {

    /**
     *
     * @param input input
     * @param distributor distributor
     */
    public void choose(InputLoader input, Distributor distributor) {

        distributor.getProducers().clear();

        ArrayList<Producer> sortedRenewable = (ArrayList<Producer>) input.getInitialData()
                .getProducers().stream().sorted(Comparator
                        .comparing(Producer::isRenewable, Comparator.reverseOrder())
                        .thenComparingDouble(Producer::getPriceKW)
                        .thenComparing(Producer::getEnergyPerDistributor, Comparator.reverseOrder())
                        .thenComparingLong(Producer::getId)).collect(Collectors.toList());

        int totalEnergy = 0;
        int i = 0;

        while (totalEnergy < distributor.getEnergyNeededKW()) {
            Producer producer = sortedRenewable.get(i);
            if (producer.getDistributors().size() < producer.getMaxDistributors()) {
                distributor.getProducers().add(producer);
                producer.getDistributors().add(distributor);
                totalEnergy += producer.getEnergyPerDistributor();
            }
            i++;
        }
    }
}
