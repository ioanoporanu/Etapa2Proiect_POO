package players;

import input.InputLoader;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public final
class Distributor implements Players, Observer {

    private
    final
    double penalty = 0.2;
    private
    final
    int number = 10;
    private
    long id;
    private
    int contractLength;
    private
    int initialBudget;
    private
    int initialInfrastructureCost;
    private
    int initialProductionCost;
    private
    int contractPrice;
    private
    int profit;
    private boolean mustApplyStrategy;
    private
    ArrayList<Contract> contracts = new ArrayList<>();
    private
    int energyNeededKW;
    private
    String producerStrategy;

    public boolean isMustApplyStrategy() {
        return mustApplyStrategy;
    }

    public void setMustApplyStrategy(boolean mustApplyStrategy) {
        this.mustApplyStrategy = mustApplyStrategy;
    }

    private ArrayList<Producer> producers = new ArrayList<>();

    public ArrayList<Producer> getProducers() {
        return producers;
    }

    public void setProducers(ArrayList<Producer> producers) {
        this.producers = producers;
    }

    public int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public String getProducerStrategy() {
        return producerStrategy;
    }

    public void setProducerStrategy(String producerStrategy) {
        this.producerStrategy = producerStrategy;
    }

    public void setEnergyNeededKW(int energyNeededKW) {
        this.energyNeededKW = energyNeededKW;
    }

    public
    boolean isBankrupt() {
        return isBankrupt; }

    private
    boolean isBankrupt = false;

    public
    long getId() {
        return id; }

    public
    void setId(final long id) {
        this.id = id; }

    public
    int getContractLength() {
        return contractLength; }

    public
    void setContractLength(final int contractLength) {
        this.contractLength = contractLength;
    }

    public
    int getInitialBudget() {
        return initialBudget; }

    public
    void setInitialBudget(final int initialBudget) {
        this.initialBudget = initialBudget;
    }

    public
    int getInitialInfrastructureCost() {
        return initialInfrastructureCost; }

    public
    void setInitialInfrastructureCost(final int initialInfrastructureCost) {
        this.initialInfrastructureCost = initialInfrastructureCost;
    }

    public
    int getContractPrice() {
        return contractPrice; }

    public
    void setContractPrice(final int contractPrice) {
        this.contractPrice = contractPrice;
    }

    public
    void setProfit(final int profit) {
        this.profit = profit; }

    public
    ArrayList<Contract> getContracts() {
        return contracts; }

    public
    void setContracts(final ArrayList<Contract> contracts) {
        this.contracts = contracts;
    }

    /**
     *
     */
    public
    void computeProfit() {
        setProfit((int) Math.round(Math.floor(penalty * initialProductionCost)));
    }

    /**
     *
     */

    public void productionCost() {
        double cost = 0;
        for (Producer producer : producers) {
            cost += producer.getEnergyPerDistributor() * producer.getPriceKW();
        }
        initialProductionCost = (int) Math.round(Math.floor(cost / number));
    }

    /**
     *
     */
    public
    void computeContractPrice() {
        if (contracts == null) {
            setContractPrice(initialInfrastructureCost
                    + initialProductionCost
                    + profit);
        } else {
            if (contracts.size() == 0) {
                setContractPrice(initialInfrastructureCost
                        + initialProductionCost
                        + profit);
            } else {
                setContractPrice((int) Math.round(
                        Math.floor(initialInfrastructureCost / contracts.size())
                                + initialProductionCost
                                + profit));
            }
        }
    }

    /**
     *
     * @return cost
     */
    public
    int totalCosts() {
        if (contracts != null) {
            return initialInfrastructureCost
                    + initialProductionCost * contracts.size();
        } else {
            return initialInfrastructureCost;
        }
    }

    /**
     *
     */
    public
    void removeContractBankrupt() {
        contracts.removeIf(contract -> contract.getConsumer().isBankrupt());
    }

    /**
     *
     */
    public
    void removeContractFinished() {
        contracts.removeIf(contract -> contract.getValability() == 0);
    }

    /**
     *
     * @param inputLoader input
     */
    public
    void playerPay(final InputLoader inputLoader) {
        initialBudget = initialBudget - totalCosts();
        if (initialBudget < 0) {
            if (contracts.size() != 0) {
                isBankrupt = true;
                for (Contract contract : contracts) {
                    contract.getConsumer().setCurrentContract(null);
                }
                this.contracts = new ArrayList<>();
            } else {
                isBankrupt = true;
            }
        }
    }

    /**
     * makes update
     * @param o o
     * @param arg arg
     */
    public void update(Observable o, Object arg) {
        mustApplyStrategy = true;
    }
}
