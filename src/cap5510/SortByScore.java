package cap5510;

import java.util.Comparator;

public class SortByScore implements Comparator<OutputSequence>{

	@Override
	public int compare(OutputSequence o1, OutputSequence o2) {
		return o2.getScore() - o1.getScore();
	}

}
