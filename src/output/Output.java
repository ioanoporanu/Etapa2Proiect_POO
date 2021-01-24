package output;

import playersout.PlayersOut;

import java.util.ArrayList;

public final
class Output {

    private
    ArrayList<PlayersOut> consumers = new ArrayList<>();
    private
    ArrayList<PlayersOut> distributors = new ArrayList<>();
    private ArrayList<PlayersOut> energyProducers = new ArrayList<>();

    public ArrayList<PlayersOut> getEnergyProducers() {
        return energyProducers;
    }

    public void setEnergyProducers(ArrayList<PlayersOut> energyProducers) {
        this.energyProducers = energyProducers;
    }

    public
    ArrayList<PlayersOut> getConsumers() {
        return consumers; }

    public
    void setConsumers(final ArrayList<PlayersOut> consumers) {
        this.consumers = consumers;
    }

    public
    ArrayList<PlayersOut> getDistributors() {
        return distributors; }

    public
    void setDistributors(final ArrayList<PlayersOut> distributors) {
        this.distributors = distributors;
    }
}
