package cap5510;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Alignment {
	int[][] dpMatrix;
	public Alignment(int qSize, int dSize){
		dpMatrix = new int[qSize+1][dSize+1];
	}
	public void printDpMatrix(){
		for(int i=0;i<dpMatrix.length;i++){
			for(int j=0;j<dpMatrix[0].length;j++){
				System.out.print(dpMatrix[i][j]);
			}
			System.out.println();
		}
	}
	protected abstract int[][] fillDpMatrix(ProteinSequence seq1, ProteinSequence seq2, Map<AlphabetPair,Integer> scoringMap, int penalty);
	public abstract int align(ProteinSequence seq1, ProteinSequence seq2, Map<AlphabetPair,Integer> scoringMap, int penalty);
}
