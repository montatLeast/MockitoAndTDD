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
    private Map<Integer,Integer> deckMap_1 = new HashMap<>();
    private Map<Integer,Integer> deckMap_2 = new HashMap<>();

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

    private void initCardSetAndMap(List<Card> deck_1,List<Card> deck_2){
        List cardList_1 = deck_1.stream().map(Card::getValue).collect(Collectors.toList());
        List cardList_2 = deck_2.stream().map(Card::getValue).collect(Collectors.toList());
        deckValue_1 = new HashSet<>(cardList_1);
        deckValue_2 = new HashSet<>(cardList_2);
        if (deckValue_1.size() < 5){
            for (int i : deckValue_1){
                deckMap_1.put(i, Collections.frequency(cardList_1, i));
            }
        }
        if (deckValue_2.size() < 5){
            for (int j : deckValue_2){
                deckMap_2.put(j, Collections.frequency(cardList_2 , j));
            }
        }
    }

    private int compareTwoDeck(List<Card> deck_1, List<Card> deck_2){
        initCardSetAndMap(deck_1, deck_2);
        levelEnum_1 = calculateLevel(deckValue_1, deckMap_1);
        int level_1 = levelEnum_1.ordinal();
        int level_2 = calculateLevel(deckValue_2, deckMap_2).ordinal();

        if (level_1 > level_2){
            return 1;
        }else if (level_2 > level_1){
            return -1;
        }
        return compareTwoDeckValue(deckValue_1, deckValue_2, levelEnum_1);
    }

    private int compareTwoDeckValue(Set<Integer> deckValue_1, Set<Integer> deckValue_2,LevelEnum levelEnum){
        if (levelEnum == LevelEnum.TWO_PAIR){
            int single_1 = 0,single_2 = 0;
            for (Map.Entry<Integer, Integer> m : deckMap_1.entrySet()) {
                if (m.getValue() == 1) {
                    single_1 = m.getKey();
                }
            }
            for (Map.Entry<Integer, Integer> m : deckMap_2.entrySet()) {
                if (m.getValue() == 1) {
                    single_2 = m.getKey();
                }
            }
            deckValue_1.remove(single_1);
            deckValue_2.remove(single_2);
            int result = compareHighCard(deckValue_1, deckValue_2);
            if (result == 0){
                if (single_1 == single_2){
                    return 0;
                }
                return single_1 > single_2 ? 1 : -1;
            }
            return result;
        }
        if (levelEnum == LevelEnum.PAIR){
            int num_1 = 0,num_2 = 0;
            for (Map.Entry<Integer, Integer> m : deckMap_1.entrySet()) {
                if (m.getValue() == 2) {
                    num_1 = m.getKey();
                }
            }

            for (Map.Entry<Integer, Integer> m : deckMap_2.entrySet()) {
                if (m.getValue() == 2) {
                    num_2 = m.getKey();
                }
            }

            if (num_1 > num_2){
                return 1;
            }else if (num_2 > num_1){
                return -1;
            }
        }
        return compareHighCard(deckValue_1, deckValue_2);
    }

    private int compareHighCard(Set<Integer> deckValue_1, Set<Integer> deckValue_2) {
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

    private LevelEnum calculateLevel(Set<Integer> deckValue, Map<Integer,Integer> deckMap){
        if (deckValue.size() == 3){
            for (Map.Entry<Integer, Integer> m : deckMap.entrySet()) {
                if (m.getValue() == 3) {
                    return LevelEnum.THREE_OF_A_KIND;
                }
            }
            return LevelEnum.TWO_PAIR;
        }
        else if (deckValue.size() == 4){
            return LevelEnum.PAIR;
        }
        return LevelEnum.NORMAL;
    }

}
