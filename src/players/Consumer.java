package players;

import input.InputLoader;

public final
class Consumer implements Players {

    private
    final
    double penalty = 1.2;
    private
    long id;
    private
    int initialBudget;
    private
    int monthlyIncome;
    private
    Contract currentContract;
    private
    Contract oldContract;
    private
    int debt = 0;
    private
    boolean isBankrupt = false;

    public
    boolean isBankrupt() {
        return isBankrupt; }

    public
    void setBankrupt(final boolean bankrupt) {
        isBankrupt = bankrupt; }

    public
    long getId() {
        return id; }

    public
    void setId(final long id) {
        this.id = id; }

    public
    int getInitialBudget() {
        return initialBudget; }

    public
    void setInitialBudget(final int initialBudget) {
        this.initialBudget = initialBudget;
    }

    public
    int getMonthlyIncome() {
        return monthlyIncome; }

    public
    void setMonthlyIncome(final int monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public
    Contract getCurrentContract() {
        return currentContract; }

    public
    void setCurrentContract(final Contract currentContract) {
        this.currentContract = currentContract;
    }

    /**
     *
     * @param inputLoader input
     */
    public
    void playerPay(final InputLoader inputLoader) {
        if (!isBankrupt) {
            if (debt != 0) {
                if (oldContract != null) {
                    if (!oldContract.getDistributor().isBankrupt()) {
                        if (debt + currentContract.getPrice() > initialBudget) {
                            if (debt > initialBudget) {
                                setBankrupt(true);
                            } else {
                                if (currentContract.getDistributor().getId()
                                        != oldContract.getDistributor().getId()) {
                                    oldContract.getDistributor().setInitialBudget(
                                            oldContract.getDistributor().getInitialBudget() + debt);
                                    oldContract = null;
                                    initialBudget -= debt;
                                    debt = (int) Math.round(Math.floor(penalty
                                            * currentContract.getPrice()));
                                } else {
                                    setBankrupt(true);
                                }
                            }
                        } else {
                            oldContract.getDistributor().setInitialBudget(
                                    oldContract.getDistributor().getInitialBudget() + debt);
                            initialBudget = initialBudget - debt;
                            debt = 0;
                            oldContract = null;
                            currentContract.getDistributor().setInitialBudget(
                                    currentContract.getDistributor().getInitialBudget()
                                            + currentContract.getPrice());
                            initialBudget = initialBudget - currentContract.getPrice();
                        }
                        return;
                    } else {
                        oldContract = null;
                        debt = 0;
                    }
                }

                if (debt + currentContract.getPrice() > initialBudget) {
                    setBankrupt(true);
                } else {
                    currentContract.getDistributor().setInitialBudget(
                            currentContract.getDistributor().getInitialBudget()
                                    + currentContract.getPrice() + debt);
                    initialBudget = initialBudget - currentContract.getPrice() - debt;
                    debt = 0;
                }
            } else {
                if (initialBudget < currentContract.getPrice()) {
                    debt = (int) Math.round(Math.floor(penalty * currentContract.getPrice()));
                } else {
                    currentContract.getDistributor().setInitialBudget(
                            currentContract.getDistributor().getInitialBudget()
                                    + currentContract.getPrice());
                    initialBudget = initialBudget - currentContract.getPrice();
                }
            }
        }
    }

    /**
     *
     * @param inputLoader input
     */
    public
    void chooseContract(final InputLoader inputLoader) {
        for (int i = 0; i < inputLoader.getInitialData().getDistributors().size();
             i++) {
            Distributor distributor =
                    inputLoader.getInitialData().getDistributors().get(i);
            if (!distributor.isBankrupt()) {
                Contract contract = new Contract();
                if (currentContract == null) {
                    oldContract = currentContract;
                    contract.setConsumer(this);
                    contract.setDistributor(distributor);
                    contract.setValability(distributor.getContractLength());
                    contract.setPrice(distributor.getContractPrice());
                    currentContract = contract;
                    distributor.getContracts().add(contract);
                } else {
                    if (currentContract.getValability() == 0) {
                        if (debt != 0) {
                            oldContract = currentContract;
                        }
                        contract.setConsumer(this);
                        contract.setDistributor(distributor);
                        contract.setValability(distributor.getContractLength());
                        contract.setPrice(distributor.getContractPrice());
                        currentContract = contract;
                        distributor.getContracts().add(contract);
                    }
                }
            }
        }
    }
}
