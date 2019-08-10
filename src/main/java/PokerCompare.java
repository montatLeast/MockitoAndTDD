import java.util.*;
import java.util.stream.Collectors;

public class PokerCompare {

    public static final String CARD_VALUE = "23456789TJQKA";
    public static final String CARD_COLOR = "HCDS";
    public static final String PLAYER_1_WIN = "Player 1 Win";
    public static final String EQUAL = "Equal";
    public static final String PLAYER_2_WIN = "Player 2 Win";



    private Set<Integer> deckValue_1;
    private Set<Integer> deckValue_2;
    private LevelEnum levelEnum_1 = LevelEnum.NORMAL;
    private LevelEnum levelEnum_2 = LevelEnum.NORMAL;

    public String compareCardGroup(String cardGroup_1, String cardGroup_2) {
        List<Card> deck_1 = initCardGroup(cardGroup_1);
        List<Card> deck_2 = initCardGroup(cardGroup_2);
        int result = compareTwoDeck(deck_1, deck_2);
        return result==1 ?  PLAYER_1_WIN
                : (result==0 ? EQUAL : PLAYER_2_WIN);
    }

    private List<Card> initCardGroup(String cardGroup_1) {
        List<Card> deck = new ArrayList<>();
        String[] cardGroup = cardGroup_1.split(" ");
        for (String s : cardGroup){
            Card card = new Card(CARD_VALUE.indexOf(s.charAt(0)),s.substring(1));
            deck.add(card);
        }
        deck = deck.stream().sorted(Comparator.comparing(Card::getValue).reversed()).collect(Collectors.toList());
        return deck;
    }

    private int compareTwoDeck(List<Card> deck_1, List<Card> deck_2){
        deckValue_1 = deck_1.stream().map(Card::getValue).collect(Collectors.toSet());
        deckValue_2 = deck_2.stream().map(Card::getValue).collect(Collectors.toSet());
        int level_1 = calculateLevel(deckValue_1).ordinal();
        int level_2 = calculateLevel(deckValue_2).ordinal();

        if (level_1 > level_2){
            return 1;
        }else if (level_2 > level_1){
            return -1;
        }
        return compareTwoDeckValue(deckValue_1, deckValue_2);
    }

    private int compareTwoDeckValue(Set<Integer> deckValue_1, Set<Integer> deckValue_2){
        Set<Integer> allValue = new HashSet<>(deckValue_1);
        Set<Integer> retainValue = new HashSet<>(deckValue_1);
        allValue.addAll(deckValue_2);
        retainValue.retainAll(deckValue_2);
        allValue.removeAll(retainValue);
        if (allValue.isEmpty()){
            return 0;
        }
        Integer max = allValue.stream().max(Integer::compareTo).get();
        return deckValue_1.contains(max) ? 1 : -1;
    }

    private LevelEnum calculateLevel(Set<Integer> deckValue){
        if (deckValue.size() == 4){
            return LevelEnum.PAIR;
        }
        return LevelEnum.NORMAL;
    }

}
