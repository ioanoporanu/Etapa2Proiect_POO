package strategies;

public final
class StrategyFactory {

    private
    static StrategyFactory singleInstance = null;

    private
    StrategyFactory() {

    }

    /**
     * singleton
     * @return instance
     */
    public
    static StrategyFactory getStrategyFactory() {
        if (singleInstance == null) {
            singleInstance = new StrategyFactory();
        }
        return singleInstance;
    }

    public enum StrategyType {
        GREEN,
        PRICE,
        QUANTITY
    }

    /**
     * makes strategy
     * @param strategyType strategy
     * @return strategy
     */
    public static Strategy makeStrategy(StrategyType strategyType) {
        return switch (strategyType) {
            case GREEN -> new Green();
            case PRICE -> new Price();
            case QUANTITY -> new Quantity();
        };
    }
}
