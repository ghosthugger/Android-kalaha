package se.forsum.kalaha;

import org.junit.Test;

public class PosTest {
    @Test
    public void testWithinMovingSideHome() throws Exception {
        Pos pos = new Pos();
        boolean withingMovingSideHome = pos.withinMovingSideHome(1);
        assert(withingMovingSideHome);
    }

    @Test
    public void testMovingSideKalaha() throws Exception {
        Pos pos = new Pos();
        int movingSideHome = pos.movingSideKalaha();
        assert(movingSideHome == 6);
    }

    @Test
    public void testOppositePit() throws Exception {
        Pos pos = new Pos();
        int oppositePit = pos.oppositePit(1);
        assert(oppositePit == 11);
    }

    @Test
    public void testNoOfBallsInPit() throws Exception {
        Pos pos = new Pos();
        assert(pos.noOfBallsInPit(1) == 6);
    }

    @Test
    public void testValue() throws Exception {
        Pos goodPos = getGoodPos();
        int goodPosValue = goodPos.value();

        Pos badPos = getBadPos(goodPos);
        int badPosValue = badPos.value();
        assert(goodPosValue > badPosValue);
    }

    private Pos getBadPos(Pos goodPos) {
        Pos badPos = new Pos();
        goodPos.pits[7] = 13;
        goodPos.pits[8] = 0;
        goodPos.pits[9] = 0;
        goodPos.pits[13]= 5;
        return badPos;
    }

    private Pos getGoodPos() {
        Pos goodPos = new Pos();
        goodPos.pits[1] = 13;
        goodPos.pits[2] = 0;
        goodPos.pits[3] = 0;
        goodPos.pits[6]= 5;
        return goodPos;
    }

    @Test
    public void testDistributePit() throws Exception {
        Pos pos = new Pos();
        pos.distributePit(1);

        assert(pos.noOfBallsInPit(1) == 0);

/*        int NumIterations = 50000;
        LinkedList<Integer> list = new LinkedList<Integer>();
        for(int i=0; i<NumIterations;i++)
            list.add(i);

        List<Object> listPositionsToRemove = new ArrayList<Object>();
        Random r = new Random();
        r.setSeed(17);
        for (int i=0;i<NumIterations;i++) {
            int random =(int) ((NumIterations-1) * r.nextDouble());

            listPositionsToRemove.add(list.get(random));
        }

        for(int i=0;i<NumIterations;i++) {
            list.remove(listPositionsToRemove.get(i));
        }*/
    }

    @Test
    public void testFindMove() throws Exception {
/*        int NumIterations = 500000;
        List<Integer> list = new ArrayList<Integer>();
        for(int i=0; i<NumIterations;i++)
            list.add(i);

        List<Integer> listPositionsToRemove = new ArrayList<Integer>();
        Random r = new Random();
        r.setSeed(17);
        for (int i=0;i<NumIterations;i++) {
            int random =(int) ((NumIterations-1) * r.nextDouble());

            listPositionsToRemove.add(random);
        }

        for(int i=0;i<NumIterations;i++) {
            list.remove(listPositionsToRemove.get(i));
        }*/
    }

    @Test
    public void testPossibleMoves() throws Exception {

    }

    @Test
    public void testToString() throws Exception {

    }

    @Test
    public void testPlayerWins() throws Exception {

    }

    @Test
    public void testOpponentWins() throws Exception {

    }

    @Test
    public void testItIsADraw() throws Exception {

    }

    @Test
    public void testIsGameOver() throws Exception {

    }

  @Test
  public void testIfPlayerSideEmptyMoveOpponentToKalaha() throws Exception {
    Pos pos = new Pos();
    pos.pits[0] = 0;
    pos.pits[1] = 0;
    pos.pits[2] = 0;
    pos.pits[3] = 0;
    pos.pits[4] = 0;
    pos.pits[5] = 0;

    pos.ifAnySideEmptyMoveOpponentToKalaha();

    assert(pos.pits[7] == 0);
    assert(pos.pits[8] == 0);
    assert(pos.pits[9] == 0);
    assert(pos.pits[10] == 0);
    assert(pos.pits[11] == 0);
    assert(pos.pits[12] == 0);
    assert(pos.pits[13] == 36);

  }

  @Test
  public void testIfOpponentSideEmptyMovePlayerToKalaha() throws Exception {
    Pos pos = new Pos();
    pos.pits[7] = 0;
    pos.pits[8] = 0;
    pos.pits[9] = 0;
    pos.pits[10] = 0;
    pos.pits[11] = 0;
    pos.pits[12] = 0;

    pos.ifAnySideEmptyMoveOpponentToKalaha();

    assert(pos.pits[0] == 0);
    assert(pos.pits[1] == 0);
    assert(pos.pits[2] == 0);
    assert(pos.pits[3] == 0);
    assert(pos.pits[4] == 0);
    assert(pos.pits[5] == 0);
    assert(pos.pits[6] == 36);

  }
}
