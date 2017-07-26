public class JSONParser{

    int index=0;
    String json
        ArrayList<String> nameTokens;

    JSONValidate(String json){
        nameTokens=new ArrayList<String>();

        if(json.charAt(index)=='{'){
            parseObject(json);
        }    
    }

    ignoreWhiteSpace(){
       while(json.charAt(index)==' '){
           index+=1;
       }
    }

    parseObject(String json){
        int count=0;
        if(json.charAt(index)=='{' )
        {
            while(json.charAt(index)!='}'){
                //identify members
                //members->pair,members
                parseMembers();    
            }
        }
    //parse Members
    parseMembers(){
        ignoreWhiteSpace();
        if(json.charAt(index)=='}'){
            return;
        }
        parsePair();
        
        ignoreWhiteSpace();

        if(json.charAt(index)==','){
            
        }
        parseMembers();
    }
    //parse key value pair
    parsePair(){
        parseString();
        ignoreWhiteSpace();
        if(json.charAt(index)==':'){
            ignoreWhiteSpace();
            parseValue();
        }else{
            System.err.println(" : missing at position "+ index );
        }
    }

    //parse strings (name)
    parseString(){
        if(json.charAt(index)=='"'){
            while(json.charAt(index)!='"'){
                
                //validate use of escape character
                if(json.charAt(index)=='\'){
                    index++;
                    char c=json.charAt(index);
                    if(c!='"' && c!='\'&&c!='/'&&c!='b'&&c!='f'&&c!='n'&&c!='r'&&c!='t'){
                        if(c=='u'){
                            for(int i=0;i<4;i++){
                                //validate the hex chars
                                char c=json.charAt(index);
                                if(('a'<= c && c<='f')||('A'<=c && c<='F') || ('0'<=c && c<='9'){
                                    index++;
                                }
                                else{
                                    System.err.println("invalid basic multilingual plane character at position(4 hex digits required) at position "+index);
                                    //take proper action
                                    return;
                                 }
                            }
                        }else{ 
                            System.err.println("invalid use of escape character");
                        }
                    }
                }
                
                //parse string
                else if(!Character.isLetterOrDigit(json.charAt(index))){
                    System.err.println("only unicode letters allowed . \n error at position : "+index);
                    //take proper action
                    return;
                }
                index++;
            }
        }        
    else{
            System.err.println(" String(key) expected within quotes ( quote missing )");
            //take proper action
            return;
        }
    }

    //parse value
    parseValue(){

        //number , true,false,null,string, object,array 

         char ch=json.charAt(index);
        if(ch=='"'){
            //value is string type
        }
        else if(ch=='n'){
            //value is null
            //check though
        }
        else if(ch=='t'){
            //value is true
            //check though
        }
        else if(ch=='f'){
            //value is false
            //check though
        }
        else if(ch=='{'){
            //value is object
            //parse object
        }
        else if(ch=='['){
            //value is array
            //parse array
            parseArray();
        }
        else if(Character.isDigit(ch)||ch=='-'){
            //value is a number 
            //parse number
            parseNumber();
        }
        else{
            System.err.println("invalid char at position : "+index);
        }
    }

    //parse array
    parseArray(){
        ignoreWhiteSpace();
        if(json.charAt(index)==']'){
            //TODO take some proper action
            return;
        }
        parseElements();
    }
    
    //parse Elements
    parseElements(){
        if(json.charAt(index)==']'){
            //TODO take proper action
            return;
        }
        parseValue();
        
        ignoreWhiteSpace();
       
        //check element separator
        if(json.charAt(index)==','){
            parseElements();
        }else if(json.charAt(index)==']'){
            //TODO take action
            return;
        }
        else{
            System.err.println("] or , expected at character number "+index);
        }
    }

    //parse Numbers
    parseNumber(){
        parseInt();
        if(json.charAt(index)=='.'){
            //TODO fraction
            //can be followed by exponent.
        }
        else if(json.charAt(index)=='e'||json.charAt(index)=='E'){
            //TODO exponentiation
            //just check the next character n then parseDigits()
            
        }
        else{
            //TODO cud be the start of next key value pair

        }
    }

    parseInt(){
        //parseInt
        // can start with digit or - sign

        
    }
    

}



