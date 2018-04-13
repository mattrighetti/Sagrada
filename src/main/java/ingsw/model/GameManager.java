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

import ingsw.model.cards.patterncard.*;
import ingsw.model.cards.privateoc.*;
import ingsw.model.cards.publicoc.*;
import ingsw.model.cards.toolcards.*;

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
        setUpGameManager(); //Choose the cards of the match to insert in the board.
        Set<PublicObjectiveCard> choosenPublicObjectiveCards = choosePublicObjectiveCards();
//      Set<PrivateObjectiveCard> choosenPrivateObjectiveCards = choosePrivateObjectiveCards(); //TODO:Check if this kind of cards has to be choosen now.
        Set<ToolCard> choosenToolCards = chooseToolCards();
        this.board = new Board(); //TODO:Find a way to pass the players and the selected cards to the board;
                                  //TODO Re-edit:Find a way to pass the players. The choosen cards will be selected with the methods called above.The Board constructor must be reimplemented once the implementation of the set is choosen.
    }

    private Set<ToolCard> chooseToolCards() {
        /**
         * Pick 3 ToolCards in a random way and store them in a set to return;
         */
        return null;
    }

    private Set<PrivateObjectiveCard> choosePrivateObjectiveCards() {
        /**
         * ??????
         */
        return null;
    }

    private Set<PublicObjectiveCard> choosePublicObjectiveCards() {
        /**
         * Pick 3 Public Objective Cards in a random way and store them in a set to return;
         */
        return null;
    }

    public static synchronized GameManager get() {
        if (instance == null) {
            instance = new GameManager();
        }

        return instance;
    }

    private synchronized void setUpGameManager() {
        setUpPrivateObjectiveCards();
        setUpPublicObjectiveCards();
        setUpToolCards();
        setUpPatternCards();
        //TODO (check if there's a quicker way to do this)
    }

    private void setUpPatternCards() {
        this.patternCards.add(new AuroraeMagnificus());
        this.patternCards.add(new AuroraSagradis());
        this.patternCards.add(new Batllo());
        this.patternCards.add(new Bellesguard());
        this.patternCards.add(new ChromaticSplendor());
        this.patternCards.add(new Comitas());
        this.patternCards.add(new Firelight());
        this.patternCards.add(new Firmitas());
        this.patternCards.add(new FractalDrops());
        this.patternCards.add(new FulgorDelCielo());
        this.patternCards.add(new Gravitas());
        this.patternCards.add(new Industria());
        this.patternCards.add(new KaleidoscopicDream());
        this.patternCards.add(new LuxAstram());
        this.patternCards.add(new LuxMundi());
        this.patternCards.add(new LuzCelestial());
        this.patternCards.add(new RipplesOfLight());
        this.patternCards.add(new ShadowThief());
        this.patternCards.add(new SunCatcher());
        this.patternCards.add(new SunsGlory());
        this.patternCards.add(new SymphonyOfLight());
        this.patternCards.add(new ViaLux());
        this.patternCards.add(new Virtus());
        this.patternCards.add(new WaterOfLife());

    }

    private void setUpToolCards() {
        this.toolCards.add(new CopperFoilBurnisher());
        this.toolCards.add(new CorkBarckedStraightEdge());
        this.toolCards.add(new EglomiseBrush());
        this.toolCards.add(new FluxBrush());
        this.toolCards.add(new FluxRemover());
        this.toolCards.add(new GlazingHammer());
        this.toolCards.add(new GrindingStone());
        this.toolCards.add(new GrozingPliers());
        this.toolCards.add(new Lathekin());
        this.toolCards.add(new LensCutter());
        this.toolCards.add(new RunningPliers());
        this.toolCards.add(new TapWheel());

    }

    private void setUpPublicObjectiveCards() {
        this.publicObjectiveCards.add(new ColorDiagonals());
        this.publicObjectiveCards.add(new ColorVariety());
        this.publicObjectiveCards.add(new ColumnShadeVariety());
        this.publicObjectiveCards.add(new DeepShades());
        this.publicObjectiveCards.add(new Diagonals());
        this.publicObjectiveCards.add(new LightShades());
        this.publicObjectiveCards.add(new MediumShades());
        this.publicObjectiveCards.add(new RowColorVariety());
        this.publicObjectiveCards.add(new RowShadeVariety());
        this.publicObjectiveCards.add(new ShadeVariety());

    }

    private void setUpPrivateObjectiveCards() {
        this.privateObjectiveCards.add(new BluePrivateObjectiveCard());
        this.privateObjectiveCards.add(new GreenPrivateObjectiveCard());
        this.privateObjectiveCards.add(new RedPrivateObjectiveCard());
        this.privateObjectiveCards.add(new PurplePrivateObjectiveCard());
        this.privateObjectiveCards.add(new YellowPrivateObjectiveCard());

    }


}
