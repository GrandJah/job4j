package ru.job4j.orderbook;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 14.11.2017
 */
public class OrderBookTest {
    /**
     * Test method.
     */
    @Test
    public void whenCreateOrderBookThenCorrect() {
        OrderBook orderBook = new OrderBook();
        IMap bid = new IMap(); IMap ask = new IMap(true);
        bid.put(12, 15);     ask.put(52, 5);
        bid.put(15, 152);    ask.put(50, 25);
        bid.put(19, 151);   ask.put(47, 125);
        bid.put(22, 15568);  ask.put(32, 1254);
        bid.put(33, 1548);   ask.put(23, 143);
        bid.put(34, 154);    ask.put(17, 12);
        bid.put(50, 5);      ask.put(15, 2);
        orderBook.toOrderBook(bid, ask);
        assertThat(bid.toString() + ask.toString(), is("IMap{12:15; 15:152; 19:151; 22:1091}IMap{52:5; 50:25; 47:125; 32:1254}"));
    }

    /**
     * Test method.
     * @throws IOException IOException
     */
//    @Test
    public void whenFileOriginalThenAnswerEquals() throws IOException {
        assertThat(new OrderBook().createOrderBook(new ParserOperation().parse("C:\\Users\\Atlant\\IdeaProjects\\junior\\orders.xml")),
                is("order book : \"book-1\"" + System.lineSeparator()
                        + "       BID       |       ASK" + System.lineSeparator()
                        + " 97,50 @     92  |  102,30 @     67" + System.lineSeparator()
                        + " 97,70 @     42  |  102,10 @    105" + System.lineSeparator()
                        + " 97,90 @     81  |  102,00 @    327" + System.lineSeparator()
                        + " 98,00 @    248  |  101,90 @    652" + System.lineSeparator()
                        + " 98,10 @    487  |  101,80 @    746" + System.lineSeparator()
                        + " 98,20 @    866  |  101,70 @   1965" + System.lineSeparator()
                        + " 98,30 @   2446  |  101,60 @   2958" + System.lineSeparator()
                        + " 98,40 @   4152  |  101,50 @   7192" + System.lineSeparator()
                        + " 98,50 @   6527  |  101,40 @  12863" + System.lineSeparator()
                        + " 98,60 @  12335  |  101,30 @  21322" + System.lineSeparator()
                        + " 98,70 @  21732  |  101,20 @  34831" + System.lineSeparator()
                        + " 98,80 @  31207  |  101,10 @  52708" + System.lineSeparator()
                        + " 98,90 @  54296  |  101,00 @  76636" + System.lineSeparator()
                        + " 99,00 @  81511  |  100,90 @ 110504" + System.lineSeparator()
                        + " 99,10 @ 119845  |  100,80 @ 150411" + System.lineSeparator()
                        + " 99,20 @ 150290  |  100,70 @ 187652" + System.lineSeparator()
                        + " 99,30 @ 201050  |  100,60 @ 256305" + System.lineSeparator()
                        + " 99,40 @ 257296  |  100,50 @ 307691" + System.lineSeparator()
                        + " 99,50 @ 304076  |  100,40 @ 370360" + System.lineSeparator()
                        + " 99,60 @ 366586  |  100,30 @ 417976" + System.lineSeparator()
                        + " 99,70 @ 409494  |  100,20 @ 452104" + System.lineSeparator()
                        + " 99,80 @ 449939  |  100,10 @ 464039" + System.lineSeparator()
                        + " 99,90 @ 464876  |  100,00 @  10060" + System.lineSeparator()
                        + "order book : \"book-2\"" + System.lineSeparator()
                        + "       BID       |       ASK" + System.lineSeparator()
                        + " 97,60 @     44  |  102,10 @    143" + System.lineSeparator()
                        + " 97,80 @      8  |  102,00 @    159" + System.lineSeparator()
                        + " 97,90 @    193  |  101,90 @    454" + System.lineSeparator()
                        + " 98,00 @    339  |  101,80 @    862" + System.lineSeparator()
                        + " 98,10 @    778  |  101,70 @   1980" + System.lineSeparator()
                        + " 98,20 @   1109  |  101,60 @   4175" + System.lineSeparator()
                        + " 98,30 @   2155  |  101,50 @   5603" + System.lineSeparator()
                        + " 98,40 @   3682  |  101,40 @  12335" + System.lineSeparator()
                        + " 98,50 @   6389  |  101,30 @  20180" + System.lineSeparator()
                        + " 98,60 @  13665  |  101,20 @  37026" + System.lineSeparator()
                        + " 98,70 @  20559  |  101,10 @  51008" + System.lineSeparator()
                        + " 98,80 @  31844  |  101,00 @  79794" + System.lineSeparator()
                        + " 98,90 @  50235  |  100,90 @ 108765" + System.lineSeparator()
                        + " 99,00 @  76596  |  100,80 @ 154344" + System.lineSeparator()
                        + " 99,10 @ 112117  |  100,70 @ 199429" + System.lineSeparator()
                        + " 99,20 @ 151606  |  100,60 @ 255797" + System.lineSeparator()
                        + " 99,30 @ 205994  |  100,50 @ 320736" + System.lineSeparator()
                        + " 99,40 @ 255846  |  100,40 @ 367049" + System.lineSeparator()
                        + " 99,50 @ 310715  |  100,30 @ 413034" + System.lineSeparator()
                        + " 99,60 @ 365271  |  100,20 @ 439919" + System.lineSeparator()
                        + " 99,70 @ 404565  |  100,10 @ 465343" + System.lineSeparator()
                        + " 99,80 @ 445490  |   ------------- " + System.lineSeparator()
                        + " 99,90 @ 469632  |   ------------- " + System.lineSeparator()
                        + "100,00 @   9303  |   ------------- " + System.lineSeparator()
                        + "order book : \"book-3\"" + System.lineSeparator()
                        + "       BID       |       ASK" +  System.lineSeparator()
                        + " 97,70 @     88  |  102,20 @     45" + System.lineSeparator()
                        + " 97,90 @     57  |  102,00 @    586" + System.lineSeparator()
                        + " 98,00 @    166  |  101,90 @    549" + System.lineSeparator()
                        + " 98,10 @    491  |  101,80 @    588" + System.lineSeparator()
                        + " 98,20 @   1214  |  101,70 @   1981" + System.lineSeparator()
                        + " 98,30 @   1748  |  101,60 @   4086" + System.lineSeparator()
                        + " 98,40 @   4669  |  101,50 @   6575" + System.lineSeparator()
                        + " 98,50 @   5956  |  101,40 @  13421" + System.lineSeparator()
                        + " 98,60 @  11529  |  101,30 @  21386" + System.lineSeparator()
                        + " 98,70 @  20869  |  101,20 @  34997" + System.lineSeparator()
                        + " 98,80 @  34635  |  101,10 @  50956" + System.lineSeparator()
                        + " 98,90 @  53026  |  101,00 @  76090" + System.lineSeparator()
                        + " 99,00 @  79696  |  100,90 @ 112496" + System.lineSeparator()
                        + " 99,10 @ 112782  |  100,80 @ 156824" + System.lineSeparator()
                        + " 99,20 @ 148623  |  100,70 @ 205435" + System.lineSeparator()
                        + " 99,30 @ 209486  |  100,60 @ 261123" + System.lineSeparator()
                        + " 99,40 @ 259198  |  100,50 @ 316100" + System.lineSeparator()
                        + " 99,50 @ 308460  |  100,40 @ 371112" + System.lineSeparator()
                        + " 99,60 @ 366860  |  100,30 @ 408532" + System.lineSeparator()
                        + " 99,70 @ 411798  |  100,20 @ 448526" + System.lineSeparator()
                        + " 99,80 @ 447799  |  100,10 @ 466915" + System.lineSeparator()
                        + " 99,90 @ 470833  |   ------------- " + System.lineSeparator()
                        + "100,00 @   8340  |   ------------- "  + System.lineSeparator()
                )
        );
    }
}