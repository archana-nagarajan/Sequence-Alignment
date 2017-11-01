package cap5510;

public class OutputSequence {
	int dataStartPos;
	int queryStartPos;
	ProteinSequence query;
	ProteinSequence data;
	int score;
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getDataStartPos() {
		return dataStartPos;
	}

	public void setDataStartPos(int dataStartPos) {
		this.dataStartPos = dataStartPos;
	}

	public int getQueryStartPos() {
		return queryStartPos;
	}

	public void setQueryStartPos(int queryStartPos) {
		this.queryStartPos = queryStartPos;
	}

	public ProteinSequence getQuery() {
		return query;
	}

	public void setQuery(ProteinSequence query) {
		this.query = query;
	}

	public ProteinSequence getData() {
		return data;
	}

	public void setData(ProteinSequence data) {
		this.data = data;
	}

	public OutputSequence(int dataStartPos, int queryStartPos, ProteinSequence query, ProteinSequence data, int score) {
		super();
		this.dataStartPos = dataStartPos;
		this.queryStartPos = queryStartPos;
		this.query = query;
		this.data = data;
		this.score = score;
	}

	@Override
	public String toString() {
		return "OutputSequence [dataStartPos=" + dataStartPos + ", queryStartPos=" + queryStartPos + ", query=" + query
				+ ", data=" + data + ", score=" + score + "]";
	}
}
