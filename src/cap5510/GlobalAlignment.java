package cap5510;

import java.util.Map;

public class GlobalAlignment extends Alignment {

	public GlobalAlignment(int qSize, int dSize) {
		super(qSize, dSize);
	}

	@Override
	protected void fillDpMatrix(ProteinSequence query, ProteinSequence data, Map<AlphabetPair, Integer> scoringMap,
			int penalty) {
		int vertical = 0, horizontal = 0, diagonal = 0;
		dpMatrix[0][0] = new MatrixCell(Constants.NONE, 0, 0, 0);
		for(int i=1;i<dpMatrix.length;i++){
			int score = dpMatrix[i-1][0].getScore()+1;
			dpMatrix[i][0] = new MatrixCell(Constants.VERTICAL, score, i, 0);
		}
		for(int j=1;j<dpMatrix[0].length;j++){
			int score = dpMatrix[0][j-1].getScore()+1;
			dpMatrix[0][j] = new MatrixCell(Constants.HORIZONTAL, score, 0, j);
		}
		for(int i=1;i<dpMatrix.length;i++){
			for(int j=1;j<dpMatrix[0].length;j++){
				vertical = dpMatrix[i-1][j].getScore() + penalty;
				horizontal = dpMatrix[i][j-1].getScore() + penalty;
				diagonal = dpMatrix[i-1][j-1].getScore() + scoringMap.get(new AlphabetPair(query.getSequence().charAt(i-1), data.getSequence().charAt(j-1)));
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
			}
		}
	}

	@Override
	public OutputSequence align(ProteinSequence query, ProteinSequence data, Map<AlphabetPair, Integer> scoringMap, int penalty) {
		fillDpMatrix(query, data, scoringMap, penalty);
//		printDpMatrix();
		OutputSequence sequence = backTrace(dpMatrix[dpMatrix.length-1][dpMatrix[0].length-1], query, data);
		return sequence;
	}

	@Override
	protected OutputSequence backTrace(MatrixCell matrixCell, ProteinSequence query, ProteinSequence data) {
		ProteinSequence querySequence = new ProteinSequence(null,query.getUid());
		ProteinSequence dataSequence = new ProteinSequence(null,data.getUid());;
		StringBuilder querysb = new StringBuilder();
		StringBuilder datasb = new StringBuilder();
		OutputSequence sequence = new OutputSequence(0, 0, null, null, -1);
		if(sequence.getScore() == -1){
			sequence.setScore(matrixCell.getScore());
		}
		int queryStart =  0, dataStart = 0;
		int i = 0, j = 0;
		while(i <= dpMatrix.length && j <= dpMatrix[0].length ){
			int direction = matrixCell.getDirection();
			queryStart =  matrixCell.getRow();
			dataStart = matrixCell.getColumn();
			if(direction == 1){ // horizontal - insertion
				matrixCell = dpMatrix[queryStart][dataStart-1];
				querysb.append("-");
				datasb.append(data.getSequence().charAt(dataStart-1));
			}
			else if(direction == 2){ // vertical - deletion
				matrixCell = dpMatrix[queryStart-1][dataStart];
				querysb.append(query.getSequence().charAt(queryStart-1));
				datasb.append("-");
			}
			else if(direction == 3){ // diagonal - match/mismatch
				matrixCell = dpMatrix[queryStart-1][dataStart-1];
				querysb.append(query.getSequence().charAt(queryStart-1));
				datasb.append(data.getSequence().charAt(dataStart-1));
			}
			i++;
			j++;
		}
		querySequence.setSequence(querysb.reverse().toString());
		dataSequence.setSequence(datasb.reverse().toString());
		sequence.setData(dataSequence);
		sequence.setQuery(querySequence);
		sequence.setDataStartPos(0);
		sequence.setQueryStartPos(0);
		return sequence;
	}

}
