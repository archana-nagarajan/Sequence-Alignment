package cap5510;

import java.util.Map;

public class GlobalAlignment extends Alignment {

	public GlobalAlignment(int qSize, int dSize) {
		super(qSize, dSize);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void fillDpMatrix(ProteinSequence query, ProteinSequence data, Map<AlphabetPair, Integer> scoringMap,
			int penalty) {
		int vertical = 0, horizontal = 0, diagonal = 0;
		for(int i=0;i<dpMatrix.length;i++){
			for(int j=0;j<dpMatrix[0].length;j++){
				vertical = dpMatrix[i-1][j].getScore() + penalty;
				horizontal = dpMatrix[i][j-1].getScore() + penalty;
				diagonal = dpMatrix[i-1][j-1].getScore() + scoringMap.get(new AlphabetPair(query.getSequence().charAt(i-1), data.getSequence().charAt(j-1)));
//				System.out.println(vertical + ":" + horizontal + ":" + diagonal);
				if(vertical >=0 || horizontal>=0 || diagonal>=0){
					int max = Math.max(diagonal, Math.max(vertical, horizontal));
					int direction = 0;
					if(max == vertical){
						direction = Constants.VERTICAL;
					}
					else if(max == horizontal){
						direction = Constants.HORIZONTAL;
					}
					else if(max == diagonal){
						direction = Constants.DIAGONAL;
					}
					dpMatrix[i][j] = new MatrixCell(direction, Math.max(diagonal, Math.max(vertical, horizontal)), i, j);
				}else{
					dpMatrix[i][j] = new MatrixCell(Constants.NONE, 0, i, j);
				}
			}
		}
	}

	@Override
	public OutputSequence align(ProteinSequence seq1, ProteinSequence seq2, Map<AlphabetPair, Integer> scoringMap, int penalty) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected OutputSequence backTrace(MatrixCell matrixCell, ProteinSequence query, ProteinSequence data) {
		// TODO Auto-generated method stub
		return null;
	}

}
