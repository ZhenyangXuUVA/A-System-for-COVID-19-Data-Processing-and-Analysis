package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;


class CSVLexer {
	
	enum STATES {START, DQUOTE, NOQUOTE, QTEXT }
	 
	
	protected static String[] readRow(BufferedReader br) throws IOException{
		
		boolean cr_flag = false;
		
		CSVLexer.STATES state = STATES.START;
		
    	
    	StringBuilder expected = new StringBuilder();
    	
    	ArrayList<String> toReturn = new ArrayList<>();
    	
    	int current;
    	
    	int countDoubleQuotes = 0; //This only for DQUOTE/QTEXT STATE
    	
    	while ( (current = br.read()) != -1) {
    		switch (state) {
    		case START:
    			switch(current) {
    			case 10: //Line Feed
    				//toReturn.add("\n");
    				toReturn.add("");
    				return toReturn.toArray(new String[0]);
    				
    			case 13: //CR can only  be followed by line feed
    				cr_flag = true;
    				break;
    				
    			case 34: //means encounter double quote
    				if (cr_flag) {throw new IOException();} //must end with LF
    				state = STATES.DQUOTE;
    				break;
    				
    			case 44: // This implies we start a new field
    				if (cr_flag) {throw new IOException();} //must end with LF
    				toReturn.add(expected.toString());
    				expected.setLength(0);
    				break;
    			default:
    				if (cr_flag) {throw new IOException();} //must end with LF
    				expected.append((char)current);
    				state = STATES.NOQUOTE;
    			}
    		break;
    		
    		// Regular field 
    		case NOQUOTE:
    			switch(current) {
    			case 10:
    				toReturn.add(expected.toString());
    				return toReturn.toArray(new String[0]);
    			
    			case 13:
    				cr_flag = true;
    				break;
    			case 34: //cannot have double in fields without starting with double quotes
    				throw new IOException();
    			case 44:
    				if (cr_flag) {throw new IOException();} //must end with LF
    				toReturn.add(expected.toString());
    				expected.setLength(0);
    				state = STATES.START;
    				break;
    			
    			default:
    				if (cr_flag) {throw new IOException();} //must end with LF
    				expected.append( (char) current);
    				
    			}
    		break;
    		
    		case DQUOTE:
    			switch (current) {
    			case 10: //Line Feed at double-quote
    				if (countDoubleQuotes == 0) {
    					//expected.append((char) 92); 
    					expected.append((char)10);
    					//cr_flag = false; //that means CRLF is done
    					
    				}

    				else if (countDoubleQuotes == 1) {
    					toReturn.add(expected.toString());
    					//toReturn.add("");
    					//toReturn.add("\n");
    					return toReturn.toArray(new String[0]);
    				}
    				break;
    			case 13: //Since it is always going to CRLF
    				if (countDoubleQuotes == 0) {
    					expected.append( (char) 13);
    				} else {
    					cr_flag = true;
    					}
    				break;
    			case 34: //So we end with a double with means our field is empty
    				if (cr_flag) {throw new IOException();} //must end with LF
    				countDoubleQuotes++;
    				if (countDoubleQuotes == 2) {
    					//expected.append((char)92);// This is '\"'
    					expected.append((char)34); 
    					countDoubleQuotes = 0;
    				}
    				break;
    			case 44: //Depending if even/odd quotes
    				if (cr_flag) {throw new IOException();} //must end with LF
    				if (countDoubleQuotes == 0) {
    				//throw new CSVFormatException();
    					//expected.append((char)92); 
    					expected.append((char)44);
    				}
    				if (countDoubleQuotes == 1) {
    					toReturn.add(expected.toString());
    					expected.setLength(0);
    					state = STATES.START;
    					countDoubleQuotes = 0;
    					
    				}
    				break;
    			default: //This means we put normal text within. 
    				if (cr_flag) {throw new IOException();} //must end with LF
    				if (countDoubleQuotes == 0) {
    					expected.append((char)current);
    					state = STATES.QTEXT; 
    				}
    				if (countDoubleQuotes == 1) {
    					throw new IOException();
    				}
    		}
    		break;
    		
    		case QTEXT:
    			switch(current) {
    			case 10:
    				if (countDoubleQuotes == 0) {
    					//expected.append((char)92); 
    					expected.append((char)10);
    					//cr_flag = false;
    				}
    				else if (countDoubleQuotes == 1) {
    					toReturn.add(expected.toString());
    					return toReturn.toArray(new String[0]);
    				}
    				
    				break;
    			case 13:
    				if (countDoubleQuotes == 0) {
    					expected.append((char) 13);
    				} else {
    					cr_flag = true;
    					}
    				break;
    			
    			case 34:
    				if (cr_flag) {throw new IOException();} //must end with LF
    				countDoubleQuotes++;
    				if (countDoubleQuotes == 2) {
    					//expected.append((char)92); 
    					expected.append((char)34);
    					countDoubleQuotes = 0;
    				}
    				break;
    				
    			case 44:
    				if (cr_flag) {throw new IOException();} //must end with LF
    				if (countDoubleQuotes == 0) {
    					//expected.append((char)92); 
    					expected.append((char)44);
    				}
    				if (countDoubleQuotes == 1) {
    					toReturn.add(expected.toString());
    					expected.setLength(0);
    					state = STATES.START;
    					countDoubleQuotes = 0;
    				}
    			
    				break;
    			default:
    				if (cr_flag) {throw new IOException();} //must end with LF
    				if (countDoubleQuotes == 0) {
    					expected.append( (char)current);
    				} 
    				if (countDoubleQuotes == 1 ) { //That means there is a close double quote,
    					throw new IOException();
    				}
    			}
    			
    		
    		}
    		
    	}
    	
    	if (cr_flag) {throw new IOException();} //must end with LF
    	
    	if ( (state == STATES.DQUOTE && countDoubleQuotes == 0) || (state == STATES.QTEXT && countDoubleQuotes == 0) ) {
    		throw new IOException();
    	}
    	
    	
    	//means it is a,b,c EOF
    	if (expected.length() != 0) {
    		toReturn.add(expected.toString());
    		return toReturn.toArray(new String[0]);
    	}
    	
    	//This means that this is abc, or a,b,c,""
    	if (!toReturn.isEmpty() || countDoubleQuotes == 1) {
    		toReturn.add("");
    		return toReturn.toArray(new String[0]);
    	}
    			
		
    	
    	
    	return null;
	}
}
