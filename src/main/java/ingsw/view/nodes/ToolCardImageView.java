package ingsw.view.nodes;

import ingsw.model.cards.toolcards.ToolCard;
import javafx.scene.image.ImageView;

public class ToolCardImageView extends ImageView {
    ToolCard toolCard;

    public ToolCardImageView(ToolCard toolCard) {
        super();
        this.toolCard = toolCard;
    }
}
