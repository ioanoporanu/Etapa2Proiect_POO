package playersout;

import players.Consumer;
import players.Distributor;
import players.Players;
import players.Producer;

public final
class PlayersOutFactory {

    private
    static PlayersOutFactory singleInstance = null;

    private
    PlayersOutFactory() {

    }

    /**
     *
     * @return factory
     */
    public
    static PlayersOutFactory getPlayersOutFactory() {
        if (singleInstance == null) {
            singleInstance = new PlayersOutFactory();
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
     * @param playerType player
     * @param player player
     * @return playerout
     */
    public  static PlayersOut
    makePlayersOut(final Player playerType, final Players player) {
        return switch (playerType) {
            case consumer -> new ConsumerOut((Consumer) player);
            case distributor -> new DistributorOut((Distributor) player);
            case producer -> new ProducerOut((Producer) player);
        };
    }
}
