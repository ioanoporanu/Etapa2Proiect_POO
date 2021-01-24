package strategies;

import input.InputLoader;
import players.Distributor;

public interface Strategy {

     /**
      * choose producers for distributor
      * @param input input
      * @param distributor player
      */
     void choose(InputLoader input, Distributor distributor);

}
