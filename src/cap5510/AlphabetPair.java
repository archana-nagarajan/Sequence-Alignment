package cap5510;

public class AlphabetPair {
	char alphabet1;
	@Override
	public String toString() {
		return "AlphabetPair [alphabet1=" + alphabet1 + ", alphabet2=" + alphabet2 + "]";
	}
	public char getAlphabet1() {
		return alphabet1;
	}
	public void setAlphabet1(char alphabet1) {
		this.alphabet1 = alphabet1;
	}
	public char getAlphabet2() {
		return alphabet2;
	}
	public void setAlphabet2(char alphabet2) {
		this.alphabet2 = alphabet2;
	}
	char alphabet2;
	public AlphabetPair(char alphabet1, char alphabet2) {
		super();
		this.alphabet1 = alphabet1;
		this.alphabet2 = alphabet2;
	}
	@Override
	public int hashCode() {
		//final int prime = 31;
		int result = 1;
		result = result + alphabet1;
		result = result + alphabet2;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AlphabetPair other = (AlphabetPair) obj;
		if (alphabet1 != other.alphabet1)
			return false;
		if (alphabet2 != other.alphabet2)
			return false;
		return true;
	}
}
