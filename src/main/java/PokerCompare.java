import java.util.*;
import java.util.stream.Collectors;

public class PokerCompare {

    public static final String CARD_VALUE = "23456789TJQKA";
    public static final String CARD_COLOR = "HCDS";
    public static final String PLAYER_1_WIN = "Player 1 Win";
    public static final String EQUAL = "Equal";
    public static final String PLAYER_2_WIN = "Player 2 Win";



    private Set<Integer> deskValue_1;
    private Set<Integer> deskValue_2;


    public String compareCardGroup(String cardGroup_1, String cardGroup_2) {
        List<Card> desk_1 = initCardGroup(cardGroup_1);
        List<Card> desk_2 = initCardGroup(cardGroup_2);
        int result = compareTwoDesk(desk_1, desk_2);
        return result==1 ?  PLAYER_1_WIN
                : (result==0 ? EQUAL : PLAYER_2_WIN);
    }

    private List<Card> initCardGroup(String cardGroup_1) {
        List<Card> desk = new ArrayList<>();
        String[] cardGroup = cardGroup_1.split(" ");
        for (String s : cardGroup){
            Card card = new Card(CARD_VALUE.indexOf(s.charAt(0)),s.substring(1));
            desk.add(card);
        }
        desk = desk.stream().sorted(Comparator.comparing(Card::getValue).reversed()).collect(Collectors.toList());
        return desk;
    }

    private int compareTwoDesk(List<Card> desk_1, List<Card> desk_2){
        deskValue_1 = desk_1.stream().map(Card::getValue).collect(Collectors.toSet());
        deskValue_2 = desk_2.stream().map(Card::getValue).collect(Collectors.toSet());
        return compareTwoDeskValue(deskValue_1, deskValue_2);
    }

    private int compareTwoDeskValue(Set<Integer> deskValue_1, Set<Integer> deskValue_2){
        Set<Integer> allValue = new HashSet<>(deskValue_1);
        Set<Integer> removeValue = new HashSet<>(deskValue_1);
        allValue.addAll(deskValue_2);
        removeValue.removeAll(deskValue_2);
        allValue.removeAll(removeValue);
        if (allValue.isEmpty()){
            return 0;
        }
        Integer max = allValue.stream().max(Integer::compareTo).get();
        return deskValue_1.contains(max) ? 1 : -1;
    }
}
