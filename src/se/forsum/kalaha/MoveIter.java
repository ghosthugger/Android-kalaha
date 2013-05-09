package se.forsum.kalaha;
import java.util.Iterator;
import java.util.Vector;

class MoveIter {
    MoveIter(Pos pos) {
        Move move = new Move();
        findAllPossibleMoves(move, pos);
        iter = vecAllPossibleMoves.iterator();
    }

    private MoveIter() {}

    void findAllPossibleMoves(Move move, Pos pos) {
        int nDelta = 0;
        if(!pos.playersTurn)
            nDelta = 7;  // generate opponent moves

        for(int i=nDelta ; i<=5+nDelta ; i++)
        {
            if(move.perform(pos).pits[i] != 0)
            {
                Move newMove = new Move(move);
                newMove.addPartMove(i);
                Pos newPos = newMove.perform(pos);
                if(newPos.playersTurn == pos.playersTurn
                        && !newPos.isGameOver())
                {
                    findAllPossibleMoves(new Move(newMove), pos);
                }
                else
                {
                    vecAllPossibleMoves.add(newMove);
                }
            }
        }
    }

    boolean hasNext(){
        return iter.hasNext();
    }

    Move next() {
        return iter.next();
    }

    private MoveIter(MoveIter other) {}  // do not copy me!
    private Vector<Move> vecAllPossibleMoves = new Vector<Move>();
    private Iterator<Move> iter;
}

