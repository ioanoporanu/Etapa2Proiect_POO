package playersout;

import players.Contract;
import players.Distributor;

import java.util.ArrayList;

public final
class DistributorOut implements PlayersOut {

    private
    long id;
    private int energyNeededKW;
    private int contractCost;
    private
    int budget;
    private String producerStrategy;
    private
    boolean isBankrupt;
    private
    ArrayList<ContractOut> contracts;

    public boolean getIsBankrupt() {
        return isBankrupt;
    }

    public void setIsBankrupt(final boolean isBankrupt) {
        this.isBankrupt = isBankrupt;
    }

    public int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public void setEnergyNeededKW(int energyNeededKW) {
        this.energyNeededKW = energyNeededKW;
    }

    public int getContractCost() {
        return contractCost;
    }

    public void setContractCost(int contractCost) {
        this.contractCost = contractCost;
    }

    public String getProducerStrategy() {
        return producerStrategy;
    }

    public void setProducerStrategy(String producerStrategy) {
        this.producerStrategy = producerStrategy;
    }


    public DistributorOut(final Distributor distributor) {
        this.id = distributor.getId();
        this.budget = distributor.getInitialBudget();
        this.isBankrupt = distributor.isBankrupt();
        this.energyNeededKW = distributor.getEnergyNeededKW();
        this.producerStrategy = distributor.getProducerStrategy();
        this.contractCost = distributor.getContractPrice();
        contracts = new ArrayList<>();

        for (Contract contract : distributor.getContracts()) {
            ContractOut contractsOut = new ContractOut();
            contractsOut.setConsumerId(contract.getConsumer().getId());
            contractsOut.setPrice(contract.getPrice());
            contractsOut.setRemainedContractMonths(contract.getValability());
            contracts.add(contractsOut);
        }
    }

    public
    long getId() {
        return id; }

    public
    void setId(final long id) {
        this.id = id; }

    public
    int getBudget() {
        return budget; }

    public
    void setBudget(final int budget) {
        this.budget = budget; }

    public
    ArrayList<ContractOut> getContracts() {
        return contracts; }

    public
    void setContracts(final ArrayList<ContractOut> contracts) {
        this.contracts = contracts;
    }
}
