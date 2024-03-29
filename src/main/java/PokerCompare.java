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
    private boolean sameColor_1 = false;
    private boolean sameColor_2 = false;

    private LevelEnum levelEnum_1 = LevelEnum.NORMAL;
    private LevelEnum levelEnum_2 = LevelEnum.NORMAL;

    public String compareCardGroup(String cardGroup_1, String cardGroup_2) {
        List<Card> deck_1 = initCardGroup(cardGroup_1);
        List<Card> deck_2 = initCardGroup(cardGroup_2);
        Set<String> colorType = deck_1.stream().map(Card::getColor).collect(Collectors.toSet());
        sameColor_1 = colorType.size() == 1;
        colorType = deck_2.stream().map(Card::getColor).collect(Collectors.toSet());
        sameColor_2 = colorType.size() == 1;
        int result = compareTwoDeck(deck_1, deck_2);
        return result==1 ?  PLAYER_1_WIN
                : (result==0 ? EQUAL : PLAYER_2_WIN);
    }

    private List<Card> initCardGroup(String cardGroup_Input) {
        List<Card> deck = new ArrayList<>();
        String[] cardGroup = cardGroup_Input.split(" ");
        for (String s : cardGroup){
            String color = s.substring(1);
            Card card = new Card(CARD_VALUE.indexOf(s.charAt(0)), color);
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
        levelEnum_1 = calculateLevel(deckValue_1, deckMap_1, sameColor_1);
        int level_1 = levelEnum_1.ordinal();
        int level_2 = calculateLevel(deckValue_2, deckMap_2, sameColor_2).ordinal();

        if (level_1 > level_2){
            return 1;
        }else if (level_2 > level_1){
            return -1;
        }
        return compareTwoDeckValue(deckValue_1, deckValue_2, levelEnum_1);
    }

    private int compareTwoDeckValue(Set<Integer> deckValue_1, Set<Integer> deckValue_2,LevelEnum levelEnum){
        if (levelEnum == LevelEnum.FOUR_OF_A_KIND){
            return compareByRate(deckValue_1, deckValue_2, 4);
        }

        if (levelEnum == LevelEnum.THREE_OF_A_KIND || levelEnum == LevelEnum.FULL_HOUSE){
            return compareByRate(deckValue_1, deckValue_2, 3);
        }
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

    private int compareByRate(Set<Integer> deckValue_1, Set<Integer> deckValue_2, int rate) {
        int base_1 = 0,base_2 = 0;
        for (Map.Entry<Integer, Integer> m : deckMap_1.entrySet()) {
            if (m.getValue() == rate) {
                base_1 = m.getKey();
            }
        }
        for (Map.Entry<Integer, Integer> m : deckMap_2.entrySet()) {
            if (m.getValue() == rate) {
                base_2 = m.getKey();
            }
        }
        if (base_1 == base_2){
            return compareHighCard(deckValue_1, deckValue_2);
        }
        return base_1 > base_2 ? 1 : -1;
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

    private LevelEnum calculateLevel(Set<Integer> deckValue, Map<Integer,Integer> deckMap, boolean sameColor){
        if (deckValue.size() == 2){
            for (Map.Entry<Integer, Integer> m : deckMap.entrySet()) {
                if (m.getValue() == 4) {
                    return LevelEnum.FOUR_OF_A_KIND;
                }
            }
            return LevelEnum.FULL_HOUSE;
        }
        if (sameColor && deckValue.size() == 5){
            if (checkStraight(deckValue)){
                return LevelEnum.Straight_Flush;
            }
            return LevelEnum.FLUSH;
        }
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
        }else if (deckValue.size() == 5){
            if (checkStraight(deckValue)){
                return LevelEnum.STRAIGHT;
            }
        }
        return LevelEnum.NORMAL;
    }

    private boolean checkStraight(Set<Integer> deckValue){
        //Set<Integer>  = new HashSet<>(deckValue);
        Integer [] check_Straight = deckValue.toArray(new Integer[] {});
        for (int i = 0; i < check_Straight.length; i++){
            check_Straight[i] += check_Straight.length - i;
        }
        Set<Integer> results = new HashSet<>(Arrays.asList(check_Straight));
        return results.size() == 1;
    }

}
