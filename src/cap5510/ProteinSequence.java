package cap5510;

public class ProteinSequence {
	private String sequence;
    private int uid;

    public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    @Override
    public String toString() {
        return "ProteinSequence{" +
                "sequence='" + sequence + '\'' +
                ", Unique ID=" + uid +
                '}';
    }
}
