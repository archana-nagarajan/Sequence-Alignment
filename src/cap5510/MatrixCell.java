package cap5510;

public class MatrixCell {
	int direction;
	int score;
	int row;
	int column;
	
	public MatrixCell(int direction, int score, int row, int column) {
		super();
		this.direction = direction;
		this.score = score;
		this.row = row;
		this.column = column;
	}
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}

	@Override
	public String toString() {
		String dir = null;
		if(direction == 1 ){
			dir = "LEFT";
		}
		else if(direction == 2 ){
			dir = "UP";
		}
		else if(direction == 3 ){
			dir = "DIAGONAL";
		}
		else{
			dir = "NONE";
		}
		return "|" + dir + "|" + score + "|";
	}
}
