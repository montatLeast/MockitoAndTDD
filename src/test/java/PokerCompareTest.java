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
        String cardGroup_2 = "2C";
        PokerCompare pokerCompare = new PokerCompare();
        String result = pokerCompare.compareCardGroup(cardGroup_1, cardGroup_2);
        Assert.assertEquals("Equal", result);
    }

    @Test
    public void should_return_player_1_win_when_every_input_double_cards_and_high_card(){
        String cardGroup_1 = "3H 4H";
        String cardGroup_2 = "2C 4C";
        PokerCompare pokerCompare = new PokerCompare();
        String result = pokerCompare.compareCardGroup(cardGroup_1, cardGroup_2);
        Assert.assertEquals("Player 1 Win", result);
    }

    @Test
    public void should_return_player_1_win_when_a_card_group_and_high_card(){
        String cardGroup_1 = "2H 3H 5S 6H 7H";
        String cardGroup_2 = "2C 3C 4C 5D 7D";
        PokerCompare pokerCompare = new PokerCompare();
        String result = pokerCompare.compareCardGroup(cardGroup_1, cardGroup_2);
        Assert.assertEquals("Player 1 Win", result);
    }

    @Test
    public void should_return_player_1_win_when_have_a_pair(){
        String cardGroup_1 = "2H 3H 4S 5H JH";
        String cardGroup_2 = "2C 5C 5D 6D TS";
        PokerCompare pokerCompare = new PokerCompare();
        String result = pokerCompare.compareCardGroup(cardGroup_1, cardGroup_2);
        Assert.assertEquals("Player 2 Win", result);
    }

}
