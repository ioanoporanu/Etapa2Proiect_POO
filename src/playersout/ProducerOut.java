package playersout;

import players.MonthlyStats;
import players.Producer;

import java.util.ArrayList;

public final
class ProducerOut implements PlayersOut {
    private long id;
    private int maxDistributors;
    private double priceKW;
    private String energyType;
    private int energyPerDistributor;
    private ArrayList<MonthlyStats> monthlyStats;

    public ProducerOut(Producer producer) {
        this.id = producer.getId();
        this.energyType = producer.getEnergyType();
        this.maxDistributors = producer.getMaxDistributors();
        this.priceKW = producer.getPriceKW();
        this.energyPerDistributor = (int) producer.getEnergyPerDistributor();
        this.monthlyStats = producer.getMonthlyStats();
    }

    public ArrayList<MonthlyStats> getMonthlyStats() {
        return monthlyStats;
    }

    public void setMonthlyStats(ArrayList<MonthlyStats> monthlyStats) {
        this.monthlyStats = monthlyStats;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEnergyType() {
        return energyType;
    }

    public void setEnergyType(String energyType) {
        this.energyType = energyType;
    }

    public int getMaxDistributors() {
        return maxDistributors;
    }

    public void setMaxDistributors(int maxDistributors) {
        this.maxDistributors = maxDistributors;
    }

    public double getPriceKW() {
        return priceKW;
    }

    public void setPriceKW(double priceKW) {
        this.priceKW = priceKW;
    }

    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public void setEnergyPerDistributor(double energyPerDistributor) {
        this.energyPerDistributor = (int) energyPerDistributor;
    }
}
