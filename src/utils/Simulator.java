package utils;

import entities.EnergyType;
import input.InputLoader;
import players.MonthlyStats;
import players.Consumer;
import players.Distributor;
import players.Producer;
import strategies.StrategyFactory;
import updates.DistributorChanges;
import updates.MonthlyUpdates;
import updates.ProducerChanges;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public
final class Simulator {

    private static Simulator singleInstance = null;

    private Simulator() {

    }

    /**
     *
     * @return singleton
     */
    public static Simulator getSimulator() {
        if (Simulator.singleInstance == null) {
            singleInstance = new Simulator();

            return singleInstance;
        }

        return singleInstance;
    }

    /**
     *
     */
    public
    void computeContractsPrice(final InputLoader inputLoader) {
        for (Distributor distributor
                : inputLoader.getInitialData().getDistributors()) {
            distributor.computeProfit();
            distributor.computeContractPrice();
        }

        inputLoader.getInitialData().getDistributors().sort(
                Comparator.comparingLong(Distributor::getId));
        inputLoader.getInitialData().getDistributors().sort(
                Comparator.comparingInt(Distributor::getContractPrice));
    }

    /**
     *
     */
    public
    void consumerPays(final InputLoader inputLoader) {
        for (Consumer consumer
                : inputLoader.getInitialData().getConsumers()) {
            if (!consumer.isBankrupt()) {
                consumer.setInitialBudget(consumer.getInitialBudget()
                        + consumer.getMonthlyIncome());
                consumer.chooseContract(inputLoader);
                consumer.playerPay(inputLoader);
            }
        }
    }

    /**
     *
     */
    public
    void chooseInitialProducers(final InputLoader inputLoader) {

        for (Distributor distributor
                : inputLoader.getInitialData().getDistributors()) {
            if (!distributor.isBankrupt()) {
                StrategyFactory
                        .makeStrategy(StrategyFactory.StrategyType.valueOf(
                                distributor.getProducerStrategy()))
                        .choose(inputLoader, distributor);
            }
        }

        for (Producer producer
                : inputLoader.getInitialData().getProducers()) {
            for (Distributor distributor
                    : producer.getDistributors()) {
                producer.addObserver(distributor);
            }
        }
    }

    /**
     *
     * @param i i
     */
    public
    void setMonthlyStats(final InputLoader inputLoader, int i) {
        for (Producer producer
                : inputLoader.getInitialData().getProducers()) {
            MonthlyStats monthlyStats = new MonthlyStats();
            monthlyStats.setMonth(i);

            for (Distributor distributor
                    : producer.getDistributors()) {
                monthlyStats.getDistributorsIds().add(distributor.getId());
            }

            producer.getMonthlyStats().add(monthlyStats);
        }
    }

    /**
     * @param monthlyUpdates updates
     */
    public
    void monthlyUpdates(final InputLoader inputLoader, final MonthlyUpdates monthlyUpdates) {
        for (Consumer consumer
                : monthlyUpdates.getNewConsumers()) {
            inputLoader.getInitialData().getConsumers().add(consumer);
        }

        for (DistributorChanges changes
                : monthlyUpdates.getDistributorChanges()) {
            for (Distributor distributor
                    : inputLoader.getInitialData().getDistributors()) {
                if (distributor.getId() == changes.getId()) {
                    distributor.setInitialInfrastructureCost(
                            changes.getInfrastructureCost());
                }
            }
        }
    }

    /**
     *
     * @param monthlyUpdates producers
     */
    public
    void
    monthlyUpdatesProducers(final InputLoader inputLoader, final MonthlyUpdates monthlyUpdates) {

        for (ProducerChanges changes
                : monthlyUpdates.getProducerChanges()) {
            for (Producer producer
                    : inputLoader.getInitialData().getProducers()) {
                if (producer.getId() == changes.getId()) {
                    producer.setEnergyPerDistributor(changes.getEnergyPerDistributor());
                }
            }
        }
    }

    /**
     *
     */
    public
    void distributorPays(final InputLoader inputLoader) {
        for (Distributor distributor
                : inputLoader.getInitialData().getDistributors()) {
            if (!distributor.isBankrupt()) {
                distributor.playerPay(inputLoader);
            }
        }
    }

    /**
     *
     */
    public
    void setRenewable(final InputLoader inputLoader) {
        for (Producer producer
                : inputLoader.getInitialData().getProducers()) {
            producer.setRenewable(
                    EnergyType.valueOf(producer.getEnergyType()).isRenewable());
        }
    }

    /**
     *
     */
    public
    void removeFinishedContracts(final InputLoader inputLoader) {
        for (Distributor distributor
                : inputLoader.getInitialData().getDistributors()) {
            if (!distributor.isBankrupt()) {
                distributor.removeContractFinished();
            }
        }
    }

    /**
     *
     */
    public
    void removeBankruptContracts(final InputLoader inputLoader) {
        for (Consumer consumer
                : inputLoader.getInitialData().getConsumers()) {
            if (consumer.isBankrupt()) {
                consumer.getCurrentContract().getDistributor().removeContractBankrupt();
            }
        }
    }

    /**
     *
     */
    public
    void decreaseContractsMonths(final InputLoader inputLoader) {
        for (Consumer consumer
                : inputLoader.getInitialData().getConsumers()) {
            if (consumer.getCurrentContract() != null && !consumer.isBankrupt()) {
                consumer.getCurrentContract().setValability(
                        consumer.getCurrentContract().getValability() - 1);
            }
        }
    }

    /**
     *
     */
    public
    void computeProductionCost(final InputLoader inputLoader) {
        for (Distributor distributor
                : inputLoader.getInitialData().getDistributors()) {
            distributor.productionCost();
        }
    }

    /**
     *
     * @param i i
     */
    public
    void chooseProducers(final InputLoader inputLoader, int i) {
        for (Distributor distributor
                : inputLoader.getInitialData().getDistributors()) {
            distributor.setMustApplyStrategy(false);
        }

        for (ProducerChanges producerChanges
                : inputLoader.getMonthlyUpdates().get(i).getProducerChanges()) {
            for (Producer producer
                    : inputLoader.getInitialData().getProducers()) {
                if (producerChanges.getId() == producer.getId()) {
                    producer.playerPay(inputLoader);
                }
            }
        }

        ArrayList<Distributor> sortedDistributors =
                (ArrayList<Distributor>) inputLoader.getInitialData()
                        .getDistributors()
                        .stream()
                        .sorted(Comparator
                                .comparing(Distributor::isMustApplyStrategy,
                                        Comparator.reverseOrder())
                                .thenComparing(Distributor::getId))
                        .collect(Collectors.toList());

        for (Distributor distributor
                : sortedDistributors) {
            if (distributor.isMustApplyStrategy() && !distributor.isBankrupt()) {
                for (Producer producer
                        : distributor.getProducers()) {
                    producer.getDistributors().remove(distributor);
                    producer.deleteObserver(distributor);
                }

                StrategyFactory
                        .makeStrategy(StrategyFactory.StrategyType.valueOf(
                                distributor.getProducerStrategy()))
                        .choose(inputLoader, distributor);
            }
        }

        for (Producer producer
                : inputLoader.getInitialData().getProducers()) {
            producer.getDistributors().sort(
                    Comparator.comparingLong(Distributor::getId));

            for (Distributor distributor
                    : producer.getDistributors()) {
                producer.addObserver(distributor);
            }
        }
    }

    /**
     *
     */
    public
    void runAllTurns(final InputLoader inputLoader) {

        for (int i = 0; i < inputLoader.getNumberOfTurns() + 1; i++) {

            if (i == 0) {
                setRenewable(inputLoader);

                chooseInitialProducers(inputLoader);

                computeProductionCost(inputLoader);

                computeContractsPrice(inputLoader);

                removeFinishedContracts(inputLoader);

                consumerPays(inputLoader);

                distributorPays(inputLoader);

                removeBankruptContracts(inputLoader);

            } else {

                monthlyUpdates(inputLoader, inputLoader.getMonthlyUpdates().get(i - 1));

                computeContractsPrice(inputLoader);

                removeFinishedContracts(inputLoader);

                consumerPays(inputLoader);

                distributorPays(inputLoader);

                removeBankruptContracts(inputLoader);

                monthlyUpdatesProducers(inputLoader, inputLoader.getMonthlyUpdates().get(i - 1));

                chooseProducers(inputLoader, i - 1);

                computeProductionCost(inputLoader);

                setMonthlyStats(inputLoader, i);
            }

            decreaseContractsMonths(inputLoader);
        }

        inputLoader.getInitialData().getDistributors().sort(
                Comparator.comparingLong(Distributor::getId));
    }
}
