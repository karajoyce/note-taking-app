package flashcard;

public class Card {

    private String CardFront;
    private String CardBack;

    private int CardID;

    public Card() {
        // CardID will be generated here from the list of Cards latest one.
        this.CardID = 0;
    }

    public String getCardFront() {
        return this.CardFront;
    }

    public void setCardFront(String cardFront) {
        CardFront = cardFront;
    }

    public String getCardBack() {
        return this.CardBack;
    }

    public void setCardBack(String cardBack) {
        CardBack = cardBack;
    }

    public int getCardID() {
        return this.CardID;
    }
}
