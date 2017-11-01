package cap5510;

import java.util.Map;

public abstract class Alignment {
	MatrixCell[][] dpMatrix;
	public Alignment(int qSize, int dSize){
		dpMatrix = new MatrixCell[qSize+1][dSize+1];
	}
	public void printDpMatrix(){
		for(int i=0;i<dpMatrix.length;i++){
			for(int j=0;j<dpMatrix[0].length;j++){
				System.out.print(dpMatrix[i][j]);
			}
			System.out.println();
		}
	}
	protected abstract void fillDpMatrix(ProteinSequence query, ProteinSequence data, Map<AlphabetPair,Integer> scoringMap, int penalty);
	public abstract OutputSequence align(ProteinSequence query, ProteinSequence data, Map<AlphabetPair,Integer> scoringMap, int penalty);
	protected abstract OutputSequence backTrace(MatrixCell matrixCell, ProteinSequence query, ProteinSequence data);
}
