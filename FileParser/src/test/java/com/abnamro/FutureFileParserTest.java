package com.abnamro;


import com.abnamro.entity.Transaction;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class FutureFileParserTest {

    @Test
    public void testParseSingleLine() {
        String record = "315CL  432100030001FCC   FUCME N1    20100910JPY01S 0000000000 0000000003000000000000DUSD000000000015DUSD000000000000DJPY20100819059475      000308000093300000000             O";
        int lineNo = 1;
        FutureFileParser futureFileParser = new FutureFileParser();
        Transaction transaction = futureFileParser.parseSingleLine(record,lineNo);
        assertEquals(3, transaction.getQtyShort());
    }
}
