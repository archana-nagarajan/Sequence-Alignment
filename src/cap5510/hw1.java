package cap5510;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class hw1 {
	static int alignmentType;
	static String queryFile;
	static String databaseFile;
	static String alphabetFile;
	static String scoringMatrix;
	static int outputCount;
	static int gapPenalty;
	static Map<AlphabetPair, Integer> scoringMap = new HashMap<>();
	public static void main(String args[]){
		if(args.length!=7){
			System.out.println("Wrong number of arguments");
		}
		else{
			initializeArgs(args);
			 try {
	               int[][] scoreMatrix2D = buildMatrix();
	               char[] alphabets=parseAlphabet(alphabetFile);
	               constructScoringMap(scoreMatrix2D,alphabets);
	               List<ProteinSequence> querySequences = parseQueryFile();
	               List<ProteinSequence> dataSequences = parseDataFile();
	               if(alignmentType==1){
	            	    new GlobalAlignment();
	               }
	               else if(alignmentType==2){
	            	   //System.out.println(new LocalAlignment(4,4).align("ACTG", "ACTT", scoringMap, gapPenalty));
	               }
	               else if(alignmentType==3){
	            	   new DovetailAlignment();
	               }
	               else{
	            	   System.out.println("Wrong Input");
	               }
	            } catch (FileNotFoundException e) {
	                e.printStackTrace();
	            }
		}
	}
	
	private static char[] parseAlphabet(String fileName) throws FileNotFoundException {
		BufferedReader buffer = new BufferedReader(new FileReader(fileName +".txt"));
		String line;
		char[] alphabets = null;
		try{
		while ((line = buffer.readLine()) != null) {
			alphabets = line.toCharArray();
		}
		}catch (IOException ex) {
			ex.printStackTrace();
	    }
		return alphabets;
	}

	static void initializeArgs(String[] args) {
    	alignmentType = Integer.valueOf(args[0]);
        queryFile = args[1];
        databaseFile = args[2];
        alphabetFile = args[3];
        scoringMatrix = args[4];
        outputCount = Integer.valueOf(args[5]);
        gapPenalty = Integer.valueOf(args[6]);
    }
	
    private static void constructScoringMap(int[][] scoreMatrix2D, char[] alphabets) {
		for(int i=0;i<scoreMatrix2D.length;i++){
			for(int j=0;j<scoreMatrix2D[0].length;j++){
				scoringMap.put(new AlphabetPair(alphabets[i],alphabets[j]), scoreMatrix2D[i][j]);
			}
		}
		//System.out.println(scoringMap);
	}

	private static List<ProteinSequence> parseDataFile() throws FileNotFoundException {
    	return parseSequencesFromFile(databaseFile);
	}

	private static List<ProteinSequence> parseQueryFile() throws FileNotFoundException {
		return parseSequencesFromFile(queryFile);
	}

	private static List<ProteinSequence> parseSequencesFromFile(String fileName) throws FileNotFoundException {
        List<ProteinSequence> result = new ArrayList<>();
        BufferedReader buffer = new BufferedReader(new FileReader(fileName +".txt"));
        Pattern p = Pattern.compile("(?<=LOC)(.*)(?=\\s)");
        String line;
        int counter = 0;
        ProteinSequence seq = null;
        StringBuilder builder = new StringBuilder();
        try {
            while ((line = buffer.readLine()) != null) {
                Matcher m = p.matcher(line);

                if (m.find()) {
                    if (counter > 1) {
                        if (seq != null)
                            result.add(seq);

                        seq = new ProteinSequence();

                        if (builder.length() > 0)
                            seq.setSequence(builder.toString());

                        seq.setUid(Integer.valueOf(m.group()));
                        builder.setLength(0);
                    }
                } else {
                    builder.append(line);
                }
                counter ++;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
	}
	
	private static int[][] buildMatrix() throws FileNotFoundException {
        int[][] matrix = null;
        BufferedReader buffer = new BufferedReader(new FileReader(scoringMatrix +".txt"));

        String line;
        int row = 0;
        int size =0;

        try {
            while ((line = buffer.readLine()) != null) {
                String[] vals = line.trim().split("\\s+");

                // Lazy instantiation.
                if (matrix == null) {
                    size = vals.length;
                    matrix = new int[size][size];
                }

                for (int col = 0; col < size; col++) {
                    matrix[row][col] = Integer.parseInt(vals[col]);
                }

                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matrix;
	}
}
