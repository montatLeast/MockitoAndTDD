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
    public void should_return_player_2_win_when_have_a_pair(){
        String cardGroup_1 = "2H 3H 4S 5H JH";
        String cardGroup_2 = "2C 5C 5D 6D TS";
        PokerCompare pokerCompare = new PokerCompare();
        String result = pokerCompare.compareCardGroup(cardGroup_1, cardGroup_2);
        Assert.assertEquals("Player 2 Win", result);
    }

    @Test
    public void should_return_player_1_win_when_have_pair_compare(){
        String cardGroup_1 = "2H 6H 6S 5H 9H";
        String cardGroup_2 = "2C 5C 5D 6D TS";
        PokerCompare pokerCompare = new PokerCompare();
        String result = pokerCompare.compareCardGroup(cardGroup_1, cardGroup_2);
        Assert.assertEquals("Player 1 Win", result);
    }

    @Test
    public void should_return_player_1_win_when_have_two_pairs(){
        String cardGroup_1 = "2H 2D 3H 3S 5H";
        String cardGroup_2 = "2C 5C 5D 6D TS";
        PokerCompare pokerCompare = new PokerCompare();
        String result = pokerCompare.compareCardGroup(cardGroup_1, cardGroup_2);
        Assert.assertEquals("Player 1 Win", result);
    }

    @Test
    public void should_return_player_2_win_when_have_two_pairs_compare(){
        String cardGroup_1 = "2H 2D 3H 3S TH";
        String cardGroup_2 = "2C 2S 5D 5S 9S";
        PokerCompare pokerCompare = new PokerCompare();
        String result = pokerCompare.compareCardGroup(cardGroup_1, cardGroup_2);
        Assert.assertEquals("Player 2 Win", result);
    }

    @Test
    public void should_return_player_2_win_when_have_three_of_a_kind(){
        String cardGroup_1 = "2H 2D 9H 9S JH";
        String cardGroup_2 = "2C 5C 5D 5S TS";
        PokerCompare pokerCompare = new PokerCompare();
        String result = pokerCompare.compareCardGroup(cardGroup_1, cardGroup_2);
        Assert.assertEquals("Player 2 Win", result);
    }

    @Test
    public void should_return_player_1_win_when_compare_three_of_a_kinds(){
        String cardGroup_1 = "2H 6D 6H 6S 9H";
        String cardGroup_2 = "2C 5C 5D 5S TS";
        PokerCompare pokerCompare = new PokerCompare();
        String result = pokerCompare.compareCardGroup(cardGroup_1, cardGroup_2);
        Assert.assertEquals("Player 1 Win", result);
    }

    @Test
    public void should_return_player_1_win_when_have_straight(){
        String cardGroup_1 = "4C 5H 6S 7H 8D";
        String cardGroup_2 = "2C 5C 5D 5S TS";
//        String cardGroup_2 = "5C 6H 7S 8H 9D";
        PokerCompare pokerCompare = new PokerCompare();
        String result = pokerCompare.compareCardGroup(cardGroup_1, cardGroup_2);
        Assert.assertEquals("Player 1 Win", result);
    }

    @Test
    public void should_return_player_2_win_when_have_fulsh(){
        String cardGroup_1 = "4C 5H 6S 7H 8D";
        String cardGroup_2 = "2D 3D 5D 6D 7D";
        PokerCompare pokerCompare = new PokerCompare();
        String result = pokerCompare.compareCardGroup(cardGroup_1, cardGroup_2);
        Assert.assertEquals("Player 2 Win", result);
    }


}
