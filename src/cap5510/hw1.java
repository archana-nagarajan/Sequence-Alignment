package cap5510;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//	               System.out.println(querySequences);
	               List<ProteinSequence> dataSequences = parseDataFile();
//	               System.out.println(dataSequences);
	               List<OutputSequence>outputSequences = new ArrayList<>();
	               if(alignmentType == 1){
//	            	   ProteinSequence d = new ProteinSequence("actgttga",1);
//	            	   ProteinSequence q = new ProteinSequence("acctgttg",2);
//	            	   outputSequences.add(new GlobalAlignment(q.getSequence().length(),d.getSequence().length()).align(q, d, scoringMap, gapPenalty));
//	            	   System.out.println(outputSequences);
	            	   for(ProteinSequence q : querySequences){
	            		   for(ProteinSequence d : dataSequences){
	            			   outputSequences.add(new LocalAlignment(q.getSequence().length(),d.getSequence().length()).align(q, d, scoringMap, gapPenalty));
	            		   }
	            	   }
	               }
	               else if(alignmentType == 2){
//	            	   ProteinSequence d = new ProteinSequence("caagac",1);
//	            	   ProteinSequence q = new ProteinSequence("gaac",2);
	            	   ProteinSequence d = new ProteinSequence("gctggaaggcatta",1);
	            	   ProteinSequence q = new ProteinSequence("tacaagcagagcacg",2);
	            	   outputSequences.add(new LocalAlignment(q.getSequence().length(),d.getSequence().length()).align(q, d, scoringMap, gapPenalty));
	            	   System.out.println(outputSequences);
//	            	   for(ProteinSequence q : querySequences){
//	            		   for(ProteinSequence d : dataSequences){
//	            			   outputSequences.add(new LocalAlignment(q.getSequence().length(),d.getSequence().length()).align(q, d, scoringMap, gapPenalty));
//	            		   }
//	            	   }
	               }
	               else if(alignmentType == 3){
	            	   ProteinSequence d = new ProteinSequence("ccatgac",1);
	            	   ProteinSequence q = new ProteinSequence("ttccagtg",2);
	            	   outputSequences.add(new DovetailAlignment(q.getSequence().length(),d.getSequence().length()).align(q, d, scoringMap, gapPenalty));
	            	   System.out.println(outputSequences);
//	            	   for(ProteinSequence q : querySequences){
//	            		   for(ProteinSequence d : dataSequences){
//	            			   outputSequences.add(new DovetailAlignment(q.getSequence().length(),d.getSequence().length()).align(q, d, scoringMap, gapPenalty));
//	            		   }
//	            	   }
	               }
	               else{
	            	   System.out.println("Wrong Input");
	               }
//	               Collections.sort(outputSequences, new SortByScore());
//	               int count = 0;
//	               for(OutputSequence op :  outputSequences){
//	            	   if(count<=outputCount){
//	            		   System.out.println(op);
//	            		   count++;
//	            	   }
//	            	   else{
//	            		   break;
//	            	   }
//	               }
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
				scoringMap.put(new AlphabetPair(Character.toLowerCase(alphabets[i]),Character.toLowerCase(alphabets[j])), scoreMatrix2D[i][j]);
			}
		}
//		System.out.println(scoringMap);
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
      //  Pattern p = Pattern.compile("(?<=LOC)(.*)(?=\\s)");
        String line;
        ProteinSequence seq = null;
        StringBuilder builder = new StringBuilder();
        StringBuilder seqId = null;
        boolean first = true;
        try {
            while ((line = buffer.readLine()) != null) {
                if(line.startsWith(">")){
                	int i = 5;
                	seqId = new StringBuilder();
                	while(line.charAt(i)!=' '){
                		seqId.append(line.charAt(i));
                		i++;
                	}
                	int id = Integer.parseInt(seqId.toString());
                	if(first || builder.length() > 0){
                	//	seq = new ProteinSequence(builder.toString(),id);
                		while(first){
                			line = buffer.readLine();
                			if(line.startsWith(">")){
                				first = false;
                			}
                			else{
                				builder.append(line.toLowerCase());
                			}
                		}
                		seq = new ProteinSequence(builder.toString(),id);
                		builder = new StringBuilder();
                		
                	}
                	result.add(seq);
                }
                else{
                	builder.append(line.toLowerCase());
                }
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
