package playersout;

import players.Consumer;

public final
class ConsumerOut implements PlayersOut {

    private
    long id;
    private
    boolean isBankrupt;
    private
    int budget;

    public ConsumerOut(final Consumer consumer) {
        this.id = consumer.getId();
        this.isBankrupt = consumer.isBankrupt();
        this.budget = consumer.getInitialBudget();
    }

    public
    long getId() {
        return id; }

    public
    void setId(final long id) {
        this.id = id; }

    public
    boolean getIsBankrupt() {
        return isBankrupt; }

    public
    void setIsBankrupt(final boolean bankrupt) {
        isBankrupt = bankrupt; }

    public
    int getBudget() {
        return budget; }

    public
    void setBudget(final int budget) {
        this.budget = budget; }
}
