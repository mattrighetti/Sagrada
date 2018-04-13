/**
 *
 * Un GameManager per ogni partita
 * Board passata da SagradaGame al momento della scelta o creazione della nuova partita o preesistente
 *
 * Contiene tutti gli oggetti del gioco perché si occupa del setup della Board e della creazione della stessa
 *
 * SINGLETON
 *
 */

package ingsw.model;

import ingsw.model.cards.patterncard.AuroraeMagnificus;
import ingsw.model.cards.patterncard.PatternCard;
import ingsw.model.cards.privateoc.*;
import ingsw.model.cards.publicoc.ColorDiagonals;
import ingsw.model.cards.publicoc.PublicObjectiveCard;
import ingsw.model.cards.toolcards.CopperFoilBurnisher;
import ingsw.model.cards.toolcards.ToolCard;

import java.util.Set;

public class GameManager {
    Board board;
    Set<PrivateObjectiveCard> privateObjectiveCards;
    Set<PublicObjectiveCard> publicObjectiveCards;
    Set<ToolCard> toolCards;
    Set<PatternCard> patternCards;
    //TODO decidere come gestire i FavorToken, se con un semplice int o con una nuova classe, in tal caso aggiungere un nuovo set di tokens

    private static GameManager instance;

    private GameManager() {
        this.board = new Board();
        /* Si potrebbe prima scegliere le carte
           effettive della Board e inizializzare la Board con il Set delle sue carte */
        setUpGameManager();
    }

    public static synchronized GameManager get() {
        if (instance == null) {
            instance = new GameManager();
        }

        return instance;
    }

    private synchronized void setUpGameManager() {
        this.privateObjectiveCards.add(new BluePrivateObjectiveCard());
        this.privateObjectiveCards.add(new GreenPrivateObjectiveCard());
        this.privateObjectiveCards.add(new RedPrivateObjectiveCard());
        this.privateObjectiveCards.add(new PurplePrivateObjectiveCard());
        this.privateObjectiveCards.add(new YellowPrivateObjectiveCard());

        this.publicObjectiveCards.add(new ColorDiagonals());


        this.toolCards.add(new CopperFoilBurnisher());


        this.patternCards.add(new AuroraeMagnificus(4)); //<----Probabilmente è meglio inserire la difficoltà già nella carta

        //TODO add everySingle card to its corresponding Set like above
        //TODO (check if there's a quicker way to do this)
    }


}
