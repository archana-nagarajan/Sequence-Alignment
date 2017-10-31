package cap5510;

import java.util.Map;

public class LocalAlignment extends Alignment {
	int score=0;
	public LocalAlignment(int qSize, int dSize) {
		super(qSize, dSize);
	}

	@Override
	protected int[][] fillDpMatrix(ProteinSequence seq1, ProteinSequence seq2, Map<AlphabetPair,Integer> scoringMap, int penalty) {
		int vertical = 0, horizontal = 0, diagonal = 0;
		for(int i=0;i<dpMatrix.length;i++){
			dpMatrix[i][0] = 0;
		}
		for(int j=0;j<dpMatrix[0].length;j++){
			dpMatrix[0][j] = 0;
		}
		for(int i=1;i<dpMatrix.length;i++){
			for(int j=1;j<dpMatrix[0].length;j++){
				vertical = dpMatrix[i-1][j] + penalty;
				horizontal = dpMatrix[i][j-1] + penalty;
				diagonal = dpMatrix[i-1][j-1] + scoringMap.get(new AlphabetPair(seq1.getSequence().charAt(i), seq2.getSequence().charAt(j)));
				if(vertical >=0 || horizontal>=0 || diagonal>=0)
					dpMatrix[i][j]= Math.min(diagonal, Math.min(vertical, horizontal));
				else{
					dpMatrix[i][j]=0;
				}
			}
		}
		return dpMatrix;
	}

	@Override
	public int align(ProteinSequence seq1, ProteinSequence seq2, Map<AlphabetPair,Integer> scoringMap, int penalty) {
		int dpMatrix[][]=fillDpMatrix(seq1, seq2, scoringMap, penalty);
		int max = Integer.MIN_VALUE;
		for(int i=0;i<dpMatrix.length;i++){
			for(int j=0;j<dpMatrix[0].length;j++){
				if(max<dpMatrix[i][j]){
					max= dpMatrix[i][j];
				}
			}
		}
		
		return score;
	}

}
