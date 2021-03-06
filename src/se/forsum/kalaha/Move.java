package se.forsum.kalaha;
import java.util.Vector;

class Move {

    public Move(){
        vecPartMoves = new Vector<Integer>();
    }

    public Move(Move other) {
        vecPartMoves = new Vector<Integer>(other.vecPartMoves);
    }

    void addPartMove(int pit){
        vecPartMoves.add(pit);
    }

    Pos perform(Pos p){
        Pos newPos = new Pos(p);
        for(int pit : vecPartMoves) {
            newPos.distributePit(pit);
        }

        return newPos;
    }

    public String toString(){
        String csStr = "Distribute the following pits: ";
        for(int value : vecPartMoves)
        {
            int pit = value+1;
            csStr += pit;
        }
        return csStr;
    }

    private Vector<Integer> vecPartMoves;

}
