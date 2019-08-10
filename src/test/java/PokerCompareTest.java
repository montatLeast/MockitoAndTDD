import org.junit.Assert;
import org.junit.Test;

public class PokerCompareTest {

    @Test
    public void should_return_player_2_win_when_input_two_card_and_high_card(){
        String cardGroup_1 = "2H";
        String cardGroup_2 = "3H";
        PokerCompare pokerCompare = new PokerCompare();
        String result = pokerCompare.compareCardGroup(cardGroup_1, cardGroup_2);
        Assert.assertEquals("Player 2 Win", result);
    }

    @Test
    public void should_return_equal_when_input_two_card_value_same(){
        String cardGroup_1 = "2H";
        String cardGroup_2 = "2D";
        PokerCompare pokerCompare = new PokerCompare();
        String result = pokerCompare.compareCardGroup(cardGroup_1, cardGroup_2);
        Assert.assertEquals("Equal", result);
    }

}
