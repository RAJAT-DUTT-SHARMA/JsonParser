
public class JSONParser {

	String json;
	int index=0;
	
	public JSONParser(String jsonString) {
		this.json=jsonString;
	}
	
	public void validateJSON(){
		if(json!=null) {
			ignoreWhiteSpace();
			parseJsonObject();
		}
	}
	
	void ignoreWhiteSpace() {
		while (json.charAt(index)==' '||json.charAt(index)=='\n'||json.charAt(index)=='\t'){
			index += 1;
		}
	}
	
	void parseJsonObject(){
		if(json.charAt(index)=='{') {
			index+=1;
			ignoreWhiteSpace();
			if(json.charAt(index)!='}') {
				parseMembers();
			}
		}
		if(json.charAt(index)=='}') {
			index+=1;
		}else {
			System.err.println("} expected at position "+index);
			System.err.println(json.substring(index));
			System.exit(1);
		}
		
	}

	private void parseMembers() {
		ignoreWhiteSpace();
		parsePair();
		ignoreWhiteSpace();
		if(json.charAt(index)==',') {
			index++;
			parseMembers();
		}
	}

	private void parsePair() {
		parseString();
		ignoreWhiteSpace();
		if (json.charAt(index) == ':') {
			index++;
			ignoreWhiteSpace();
			parseValue();
		} else {
			System.err.println(" : missing at position " + index);
			System.err.println(json.substring(index));
			System.exit(1);
		}
	}

	private void parseValue() {
		char c=json.charAt(index);
		if(c=='"') {
			parseString();
			}
		else if(c=='{') {
			parseJsonObject();
		}
		else if(c=='[') {
			index+=1;
			ignoreWhiteSpace();
			if(json.charAt(index)!=']') {
				parseArray();		
			}
		}
		else if(c=='n') {
			index += 1;
			if (json.charAt(index) == 'u' && json.charAt(index + 1) == 'l' && json.charAt(index + 2) == 'l') {
				// null value identified
				index += 3;
			}else {
				System.err.println("invalid value at position : " + index+"  null value expected");
				System.err.println(json.substring(index));
				System.exit(1);
			}
		}
		else if(c=='t') {
			index++;
			if (json.charAt(index) == 'r' && json.charAt(index + 1) == 'u' && json.charAt(index + 2) == 'e') {
				// true value identified
				index += 3;
			}else {
				System.err.println("invalid value at position : " + index+"  true value expected");
				System.err.println(json.substring(index));
				System.exit(1);
			}
		}
		else if(c=='f') {
			index++;
			if (json.charAt(index) == 'a' && json.charAt(index + 1) == 'l' && json.charAt(index + 2) == 's' && json.charAt(index+3)=='e') {
				// false value identified
				index += 4;
			}else {
				System.err.println("invalid value at position : " + index+"  false value expected");
				System.err.println(json.substring(index));
				System.exit(1);
			}
		}
		else if(c=='-'||Character.isDigit(json.charAt(index))) {
			parseNumber();
		}
		else{
			System.err.println("invalid value at position "+index);
			System.err.println(json.substring(index));
			System.exit(1);
		}
	}

	private void parseNumber() {
		parseInt();
		char c=json.charAt(index);
		if(c=='.') {
			index+=1;
			parseInt();
			if(json.charAt(index)=='e'||json.charAt(index)=='E') {
				index+=1;
				if(json.charAt(index)=='+'||json.charAt(index)=='-') {
					index++;
					parseInt();
					return;
				}else {
					if(Character.isDigit(json.charAt(index))) {
						parseInt();
						return;
					}else {
						System.err.println("digit expected at position "+index);
						System.err.println(json.substring(index));
						System.exit(1);		
					}
				}
			}
			else { 
					ignoreWhiteSpace();
					if(json.charAt(index)==','||json.charAt(index)=='}'||json.charAt(index)==']'){
						return;
					}else {
						System.err.println(",or } or ] expected at position "+index);
						System.err.println(json.substring(index));
						System.exit(1);
					}
			}
		}
		else if(c=='e'||c=='E') {
			index+=1;
			if(json.charAt(index)=='+'||json.charAt(index)=='-') {
				index++;
				parseInt();
				return;
			}else {
				if(Character.isDigit(json.charAt(index))) {
					parseInt();
					return;
				}else {
					System.err.println("digit expected at position "+index);
					System.err.println(json.substring(index));
					System.exit(1);		
				}
			}
		}
		else {
			
			ignoreWhiteSpace();
			if(json.charAt(index)==','||json.charAt(index)=='}'||json.charAt(index)==']'){
				return;
			}else {
				System.err.println(",or } or ] expected at position "+index);
				System.err.println(json.substring(index));
				System.exit(1);
			}
		}
	}

	private void parseInt() {
		if(Character.isDigit(json.charAt(index))|| json.charAt(index)=='-') {
			index+=1;
			while(Character.isDigit(json.charAt(index))) {
				index++;
			}
		}else {
			System.err.println("invalid number at position "+index);
			System.err.println(json.substring(index));
			System.exit(1);
		}
	}

	private void parseArray() {
		ignoreWhiteSpace();
		parseElements();
		ignoreWhiteSpace();
		if(json.charAt(index)!=']') {
			System.err.println(" ] expected at position "+index);
			System.err.println(json.substring(index));
			System.exit(1);
		}else {
			index++;
		}
	}

	private void parseElements() {
		parseValue();
		ignoreWhiteSpace();
		if(json.charAt(index)==',') {
			index++;
			ignoreWhiteSpace();
			parseElements();
		}
	}

	private void parseString() {
		if(json.charAt(index)=='"') {
			index+=1;
			while(json.charAt(index)!='"') {
				char c=json.charAt(index);
				if(c=='\\') {
					index+=1;
					c=json.charAt(index);
					if(c!='"' && c!='\\' && c!='b' && c!='f' && c!='n' && c!='r' && c!='t'&& c!='u') {
						System.err.println("invalid use of escape character at position "+index);
						System.err.println(json.substring(index));
						System.exit(1);
					}else if(c=='u') {
						index++;
						for(int i=0;i<4;i++){
							//validate the hex chars
							c=json.charAt(index);
							if(('a'<= c && c<='f')||('A'<=c && c<='F') || ('0'<=c && c<='9')){
								index++;
							}else {
								System.err.println("Invalid hex letters at position at "+index);
								System.err.println(json.substring(index));
								System.exit(1);
							}
						}
				
					}else {
						index++;
					}
				}else {index++;}
				
			}
			index++;
		}else {
			System.err.println("opening quote expected at position "+index);
			}
	}
	
}
