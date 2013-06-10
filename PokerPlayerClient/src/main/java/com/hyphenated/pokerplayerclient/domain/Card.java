package com.hyphenated.pokerplayerclient.domain;

import com.hyphenated.pokerplayerclient.R;

/**
 * Card Enum.  Used to enumerate the properties of the playing cards
 * <br /><br />
 * Created by jacobhyphenated on 6/7/13.
 */
public enum Card{

    TWO_OF_CLUBS("2c", R.drawable.two_clubs),
    THREE_OF_CLUBS("3c", R.drawable.three_clubs),
    FOUR_OF_CLUBS("4c", R.drawable.four_clubs),
    FIVE_OF_CLUBS("5c", R.drawable.five_clubs),
    SIX_OF_CLUBS("6c", R.drawable.six_clubs),
    SEVEN_OF_CLUBS("7c", R.drawable.seven_clubs),
    EIGHT_OF_CLUBS("8c", R.drawable.eight_clubs),
    NINE_OF_CLUBS("9c", R.drawable.nine_clubs),
    TEN_OF_CLUBS("Tc", R.drawable.ten_clubs),
    JACK_OF_CLUBS("Jc", R.drawable.jack_clubs),
    QUEEN_OF_CLUBS("Qc", R.drawable.queen_clubs),
    KING_OF_CLUBS("Kc", R.drawable.king_clubs),
    ACE_OF_CLUBS("Ac", R.drawable.ace_clubs),

    TWO_OF_DIAMONDS("2d", R.drawable.two_diamonds),
    THREE_OF_DIAMONDS("3d", R.drawable.three_diamonds),
    FOUR_OF_DIAMONDS("4d", R.drawable.four_diamonds),
    FIVE_OF_DIAMONDS("5d", R.drawable.five_diamonds),
    SIX_OF_DIAMONDS("6d", R.drawable.six_diamonds),
    SEVEN_OF_DIAMONDS("7d", R.drawable.seven_diamonds),
    EIGHT_OF_DIAMONDS("8d", R.drawable.eight_diamonds),
    NINE_OF_DIAMONDS("9d", R.drawable.nine_diamonds),
    TEN_OF_DIAMONDS("Td", R.drawable.ten_diamonds),
    JACK_OF_DIAMONDS("Jd", R.drawable.jack_diamonds),
    QUEEN_OF_DIAMONDS("Qd", R.drawable.queen_diamonds),
    KING_OF_DIAMONDS("Kd", R.drawable.king_diamonds),
    ACE_OF_DIAMONDS("Ad", R.drawable.ace_diamonds),

    TWO_OF_HEARTS("2h", R.drawable.two_hearts),
    THREE_OF_HEARTS("3h", R.drawable.three_hearts),
    FOUR_OF_HEARTS("4h", R.drawable.four_hearts),
    FIVE_OF_HEARTS("5h", R.drawable.five_hearts),
    SIX_OF_HEARTS("6h", R.drawable.six_hearts),
    SEVEN_OF_HEARTS("7h", R.drawable.seven_hearts),
    EIGHT_OF_HEARTS("8h", R.drawable.eight_hearts),
    NINE_OF_HEARTS("9h", R.drawable.nine_hearts),
    TEN_OF_HEARTS("Th", R.drawable.ten_hearts),
    JACK_OF_HEARTS("Jh", R.drawable.jack_hearts),
    QUEEN_OF_HEARTS("Qh", R.drawable.queen_hearts),
    KING_OF_HEARTS("Kh", R.drawable.king_hearts),
    ACE_OF_HEARTS("Ah", R.drawable.ace_hearts),

    TWO_OF_SPADES("2s", R.drawable.two_spades),
    THREE_OF_SPADES("3s", R.drawable.three_spades),
    FOUR_OF_SPADES("4s", R.drawable.four_spades),
    FIVE_OF_SPADES("5s", R.drawable.five_spades),
    SIX_OF_SPADES("6s", R.drawable.six_spades),
    SEVEN_OF_SPADES("7s",R.drawable.seven_spades),
    EIGHT_OF_SPADES("8s",R.drawable.eight_spades),
    NINE_OF_SPADES("9s", R.drawable.nine_spades),
    TEN_OF_SPADES("Ts", R.drawable.ten_spades),
    JACK_OF_SPADES("Js", R.drawable.jack_spades),
    QUEEN_OF_SPADES("Qs", R.drawable.queen_spades),
    KING_OF_SPADES("Ks", R.drawable.king_spades),
    ACE_OF_SPADES("As", R.drawable.ace_spades);


    private String identifier;
    private int drawable;

    private Card(String identifier, int drawable){
        this.identifier = identifier;
        this.drawable = drawable;
    }

    /**
     * Gets a {@link Card} enum based on the card identifier string.
     * <br /><br />
     * The identifier takes the form of[2-9TJQKA][cdhs]
     * <br />
     * The card value is the 2,3,T (for ten), A etc.
     * <br />
     * The card suit is c (for clubs) or d (for diamonds) etc.
     * <br />
     * Example: 'Ac' = Ace of clubs.  '5h' = Five of Hearts
     * @param identifier identifier for the card
     * @return Card
     */
    public static Card getCardByIdentifier(String identifier){
        for(Card c : Card.values()){
            if(c.getIdentifier().equals(identifier)){
                return c;
            }
        }
        return null;
    }

    public String getIdentifier(){
        return this.identifier;
    }

    public int getDrawable(){
        return this.drawable;
    }

}
