/*
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

import java.util.*;

public class GameManager {
    private Board board;
    private List<PrivateObjectiveCard> privateObjectiveCards;
    private List<PublicObjectiveCard> publicObjectiveCards;
    private List<ToolCard> toolCards;
    private List<PatternCard> patternCards;

    public GameManager(List<User> users) {
        setUpGameManager();
        this.board = new Board(choosePublicObjectiveCards(), chooseToolCards(), createPlayers(users));
    }

    public void run(){
        //The match starts

        //TODO: Choose Pattern card for each player

        //TODO: Set Favor Tokens for each player

        for( int i = 0; i < 10; i++){
            //playRound();
            //TODO: Create Round class or implement it in the GameManager??
        }

        //TODO: count the points

        //TODO: choose the winner

        //TODO: end of the match

    }

    private List<Player> createPlayers(List<User> users) {
        List<Player> playerList = new ArrayList<>();
        Collections.shuffle(privateObjectiveCards);
        for (User user : users) {
            playerList.add(new Player(user, privateObjectiveCards.get(0)));
            privateObjectiveCards.remove(0);
        }

        return playerList;
    }

    private synchronized void setUpGameManager() {
        setUpPrivateObjectiveCards();
        setUpPublicObjectiveCards();
        setUpToolCards();
        setUpPatternCards();
    }

    private void setUpPatternCards() {
        this.patternCards = new LinkedList<>();
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
        toolCards = new LinkedList<>();
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
        publicObjectiveCards = new LinkedList<>();
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
        privateObjectiveCards = new LinkedList<>();
        this.privateObjectiveCards.add(new BluePrivateObjectiveCard());
        this.privateObjectiveCards.add(new GreenPrivateObjectiveCard());
        this.privateObjectiveCards.add(new RedPrivateObjectiveCard());
        this.privateObjectiveCards.add(new PurplePrivateObjectiveCard());
        this.privateObjectiveCards.add(new YellowPrivateObjectiveCard());

    }

    private Set<ToolCard> chooseToolCards() {
        Collections.shuffle(toolCards);
        return new HashSet<>(toolCards.subList(0, 3));
    }

    private Set<PublicObjectiveCard> choosePublicObjectiveCards() {
        Collections.shuffle(publicObjectiveCards);
        return new HashSet<>(publicObjectiveCards.subList(0, 3));
    }

    private Set<PatternCard> choosePatternCards() {
        Collections.shuffle(patternCards);
        return new HashSet<>(patternCards.subList(0,4));
    }

    public synchronized void loginUser() {
        /* Single login per user, or wait for all user and the pass the array? */
    }

}
