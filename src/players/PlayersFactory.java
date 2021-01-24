package players;

public final class PlayersFactory {

    private
    static PlayersFactory singleInstance = null;

    private
    PlayersFactory() {

    }

    /**
     *
     * @return factory
     */
    public
    static PlayersFactory getPlayersFactory() {
        if (singleInstance == null) {
            singleInstance = new PlayersFactory();
        }
        return singleInstance;
    }

    public
    enum Player {
        distributor,
        consumer,
        producer
    }

    /**
     *
     * @param playerType enum
     * @return player
     */
    public Players
    makePlayers(final Player playerType) {
        return switch (playerType) {
            case consumer -> new Consumer();
            case distributor -> new Distributor();
            case producer -> new Producer();
        };
    }

}
