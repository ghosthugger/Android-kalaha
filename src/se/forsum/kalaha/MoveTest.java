package se.forsum.kalaha;

import org.junit.Test;

public class MoveTest {
    @Test
    public void testAddPartMove() throws Exception {
        Move move = new Move();
        move.addPartMove(1);

        Pos pos = new Pos();
        Pos Result = move.perform(pos);
        assert(Result.noOfBallsInPit(1) == 0);
    }

    @Test
    public void testPerform() throws Exception {
        Move move = new Move();
        move.addPartMove(1);
        Pos pos = new Pos();
        Pos Result = move.perform(pos);

        assert(Result.noOfBallsInPit(2) == 7);
    }

    @Test
    public void testToString() throws Exception {
        Move move = new Move();
        move.addPartMove(1);
        assert(move.toString().compareTo("Distribute the following pits: 2") == 0);
    }
}
