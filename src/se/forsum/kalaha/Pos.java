package se.forsum.kalaha;

import java.io.Serializable;

class Pos implements Serializable{
    public Pos() {
        initializePosition();
    }

    public Pos(Pos other) {
        this.playersTurn = other.playersTurn;
        this.pits = other.pits.clone();
    }

    public boolean withinMovingSideHome(int nPit) {
        return ((playersTurn
		 && nPit >= 0
		 && nPit <= 5)
                || (!playersTurn
		    && nPit >= 7
		    && nPit <= 12));
    }

    public int movingSideKalaha(){
        if(playersTurn)
            return 6;
        else
            return 13;
    }

    public int oppositePit(int i){
        int f[] = {12, 11, 10, 9, 8, 7, 13, 5, 4, 3, 2, 1, 0, 6};
        return f[i];
    }

    public int noOfBallsInPit(int i){
        return pits[i];
    }

    int value(int nLevels, boolean bMaxLevel, int nAlfa, int nBeta){
        if(nLevels == 1)
            return value();

        int nBestSoFar = 0;
        MoveIter iter = possibleMoves();
        int nNewAlfa = nAlfa;
        int nNewBeta = nBeta;
        boolean bFirst = true;

        Move current;
        while(iter.hasNext())
        {
            current = iter.next();
            int nBranchValue = current.perform(this).value(nLevels - 1, !bMaxLevel, nNewAlfa, nNewBeta);

            if(bMaxLevel
	       && nBranchValue > nNewAlfa)
                nNewAlfa = nBranchValue;

            if(!bMaxLevel
	       && nBranchValue < nNewBeta)
                nNewBeta = nBranchValue;

            if(bMaxLevel
	       && nBranchValue > nBeta)
                return nBranchValue;

            if(!bMaxLevel
	       && nBranchValue < nAlfa)
                return nBranchValue;

            if(bFirst
                    || (bMaxLevel
			&& nBranchValue > nBestSoFar)
                    ||	(!bMaxLevel
			 && nBranchValue < nBestSoFar))
            {
                bFirst = false;
                nBestSoFar = nBranchValue;
            }
        }
        if(bFirst)
        {
            // win or loss
            return value();
        }

        return nBestSoFar;
    }

    // Calculate heuristic value of position
    int value(){
        int nSumPlayer = 0;
        for(int i=0 ; i<= 5 ; i++)
            nSumPlayer += pits[i];

        int nSumOpponent = 0;
        for(int i=7 ; i<= 12; i++)
            nSumOpponent += pits[i];

        if(nSumPlayer == 0
                || nSumOpponent == 0)
        {
            if(pits[6] == pits[13])
                return -90;  // it is a draw (need to invert with m_bPlayerTurn ??)

            if(pits[6] > pits[13])
                return 100;
            else
                return -100;
        }

        return pits[6] - pits[13];
    }

    // Make the move pick the nPit pit.
    void distributePit(int nPit){

        assert(pits[nPit] != 0); //cannot move empty pit

        assert((playersTurn
                && nPit <= 5
                && nPit >= 0)
                || (!playersTurn
                && nPit <= 12
                && nPit >= 7)); // wrong player!


        int nBalls = pits[nPit];
        int nOrignalBalls = nBalls;
        pits[nPit] = 0;
        int nCurrPit = nPit;
        do
        {
            nCurrPit++;
            if(playersTurn
                    && nCurrPit == 13)
                nCurrPit = 0;

            if(!playersTurn
                    && nCurrPit == 6)
                nCurrPit = 7;

            if(nCurrPit > 13)
                nCurrPit = 0;

            nBalls --;
            pits[nCurrPit]++;
        } while(nBalls>0);

        if(nOrignalBalls >= 8
                && pits[nCurrPit] == 1
                && pits[oppositePit(nCurrPit)] != 0
                && withinMovingSideHome(nCurrPit))
        {
            pits[movingSideKalaha()] += pits[nCurrPit];
            pits[movingSideKalaha()] += pits[oppositePit(nCurrPit)];
            pits[nCurrPit] = 0;
            pits[oppositePit(nCurrPit)] = 0;
        }

        ifAnySideEmptyMoveOpponentToKalaha();

        if(nCurrPit != movingSideKalaha())
            playersTurn = !playersTurn;
    }

    Move findMove(int nLevels){
        int nBestSoFar = 0;
        Move BestSoFar = new Move();
        boolean bFirst = true;
        int nNewBeta = 10000;
        MoveIter iter = possibleMoves();
        Move current;
        while(iter.hasNext()) {
            current = iter.next();
            Pos branch = current.perform(this);
            int nBranchValue = branch.value(nLevels - 1, true, -10000, nNewBeta);
            if(nBranchValue < nNewBeta)  // this is always min level!
                nNewBeta = nBranchValue;

            if(bFirst
                    || nBestSoFar > nBranchValue)
            {
                bFirst = false;
                nBestSoFar = nBranchValue;
                BestSoFar = current;
            }
        }

        return BestSoFar; // might be the empty move
    }

    MoveIter possibleMoves(){
        return new MoveIter(this);
    }



//		10	7	78	8	8	7
//	12							9
//		89	8	99	12	12	12
//		this sides turn
    public String toString(){
        String csPos;
        String csRow1;
        csRow1 = "\t" + pits[12] + "\t" + pits[11] + "\t" + pits[10] + "\t" + pits[9]  + "\t" + pits[8] + "\t" + pits[7];

        String csRow2;
        csRow2 = pits[13] + "\t\t\t\t\t\t\t" + pits[6];

        String csRow3;
        csRow3 = "\t" + pits[0] + "\t" + pits[1] + "\t" + pits[2] + "\t" + pits[3]  + "\t" + pits[4] + "\t" + pits[5];

        String csFirstRow = "";
        String csSecondRow = "";
        if(playersTurn)
            csSecondRow = "\t\tthis sides turn";
        else
            csFirstRow = "\t\tthis sides turn";

        csPos = csFirstRow + "\n" + "\n" + csRow1 + "\n" + csRow2 + "\n" +csRow3 + "\n" + "\n" +csSecondRow +"\n";
        return csPos;
    }

    boolean playerWins(){
        return value() == 100;
    }

    boolean opponentWins(){
        return value() == -100;
    }

    boolean itIsADraw(){
        return value() == -90 || value() == 90;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean isGameOver(){
        return playerWins() || opponentWins() || itIsADraw();
    }

    void ifAnySideEmptyMoveOpponentToKalaha(){
        int nPSum = 0;
        for(int i=0 ; i <= 5 ; i++)
            nPSum += pits[i];

        int nOSum = 0;
        for(int i=7 ; i<= 12 ; i++)
            nOSum += pits[i];

        if(nOSum == 0
                || nPSum == 0)
        {
            if(nOSum == 0)
              pits[6] += nPSum;

            if(nPSum == 0)
              pits[13] += nOSum;

//            pits[movingSideKalaha()] += (nOSum + nPSum);
            for(int i=0 ; i<=5 ; i++)
                pits[i] = 0;

            for(int i=7 ; i<=12 ; i++)
                pits[i] = 0;
        }
    }

    int pits[] = new int[14];
    boolean playersTurn = true;

    public void initializePosition() {
        pits[0] = 6;
        pits[1] = 6;
        pits[2] = 6;
        pits[3] = 6;
        pits[4] = 6;
        pits[5] = 6;
        pits[6] = 0;

        pits[7] = 6;
        pits[8] = 6;
        pits[9] = 6;
        pits[10] = 6;
        pits[11] = 6;
        pits[12] = 6;
        pits[13] = 0;

        playersTurn = true;
    }

    boolean getPlayersTurn() {
        return playersTurn;
    }


}

