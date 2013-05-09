package se.forsum.kalaha;

import org.junit.Test;

public class MoveIterTest {
    @Test
    public void testFindAllPossibleMoves() throws Exception {
        Pos pos = new Pos();
        MoveIter iter = new MoveIter(pos);

        int i = 0;
        while(iter.hasNext()) {
            iter.next();
            i++;
        }

        assert(i == 10);
    }
}
