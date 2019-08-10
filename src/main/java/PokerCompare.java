public class PokerCompare {

    public String compareCardGroup(String cardGroup_1, String cardGroup_2) {
        int num1 = cardGroup_1.charAt(0);
        int num2 = cardGroup_2.charAt(0);
        return num1 > num2 ?  "Player 1 Win"
                : ((num1 == num2) ? "Equal" : "Player 2 Win");
    }
}
