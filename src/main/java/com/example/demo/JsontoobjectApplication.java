package com.example.demo;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.google.gson.Gson;
import com.google.gson.stream.JsonToken;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
@SpringBootApplication
public class JsontoobjectApplication {
	static Gson gson =new Gson(); 
    static BufferedWriter bw;   

    static {      
        try {
            bw = new BufferedWriter(new FileWriter("output.xml") );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static Function getBody(String g, Function f ){   // tzidhom fel function tchouf est ce que aaana request wala response 
        String res , req;
        if(g.charAt(4)=='q'){
            Test2 reqBody;
            req = g.split("schema\":")[1].split("}")[0]+"}"; // takra baaed schema: w tekef fe accolade puis on ajoute accolade khater heya tfasakahha behch on ocnserve une forme json  l
            if(req.split(",").length==2){       //nchoufou chaine mtaa request body fehaa type walee 
                req+="}";
                reqBody=gson.fromJson(req,Test2.class);  // bch nakra type puis ref 
                if(req.contains("items")){      // request inajem ikoun fiha type array w ref objet kif response  
                    reqBody.set$ref(gson.fromJson(req.split("items\":")[1].split("}")[0]+"}",Test2.class).get$ref()); //from json taatini class houni l ajout mtaa ref 
                }
            }else {
                reqBody=gson.fromJson(req,Test2.class); // aana un seul type 
            }
            f.setRequest(reqBody);            // hatina req fel fontion
            res = g.split("schema\":")[2].split("}")[0]+"}"; // hahart e chainee li bch nekhou menha e  resultat nekhou eschema 2

        }else {
             res = g.split("schema\":")[1].split("}")[0]+"}"; //maandich request 
        }
        Test2 resBody;
        if(res.split(",").length==2){
            res+="}";   // pour la forme de json khater split tfaskhouu 
             resBody=gson.fromJson(res,Test2.class);
            if(res.contains("items")){
                resBody.set$ref(gson.fromJson(res.split("items\":")[1].split("}")[0]+"}",Test2.class).get$ref());
            }
        }
        else {
            resBody=gson.fromJson(res,Test2.class);
        }
        f.setResponse(resBody);
        return f;
    }
    static String getFunctions(FileReader fr){   
        BufferedReader br = new BufferedReader(fr);
        String s = br.lines().collect(Collectors.joining(System.lineSeparator())); 
        return s.split("paths\":")[1].split("\"components\":")[0];
    }
    static void generateXML( String controller,Set<Function> functions) throws IOException {    

        bw.write("<packagedElement xmi:type=\"uml:Class\"  xmi:id=\"\"  name=\""+controller+"\">");
        for (Function f:functions) {       // parcouritt l functions l andi l kol   
            bw.write("<ownedOperation xmi:id=\"\" name=\""+ f.getOpId()+"\">");
            if(f.getResponse()!= null){
                bw.write("<ownedParameter xmi:id=\"\" name=\"return\" direction=\"return\"> <type xmi:type=\"");// a modifier fazet e type also ref 
                if (f.getResponse().type!=null){
                    bw.write(f.getResponse().type);      
                }else {
                    bw.write("uml:Class");
                }
                bw.write("\"href=\""+f.getResponse().$ref+"\"/></ownedParameter>");
            }
            if(f.getParam()!=null){
                bw.write("<ownedParameter xmi:id=\"\" name=\""+f.getParam().name+"\"><type xmi:type=\""+f.getParam().schema.type+"\" href=\"\"/> </ownedParameter>");
            }
            if(f.getRequest()!=null){
                bw.write("<ownedParameter xmi:id=\"\" name=\"requestBody\"><type xmi:type=\"");
                if (f.getRequest().type!=null){
                    bw.write(f.getResponse().type);
                }else {
                    bw.write("uml:Class");
                }
                bw.write("\" href=\""+f.getRequest().$ref+"\"/></ownedParameter>");
            }
            bw.write("</ownedOperation>");
        }
        bw.write("</packagedElement>");       
    }
    public static void main(String[] args) throws IOException {
        FileReader fileReader = new FileReader("api_docs_1.json");
        String s= getFunctions(fileReader);  // "{\"openapi\":\"3.0.1\",\"info\":{\"title\":\"OpenAPI definition\",\"version\":\"v0\"},\"servers\":[{\"url\":\"http://localhost:8089\",\"description\":\"Generated server url\"}],\"paths\":{\"/user/{id}\":{\"get\":{\"tags\":[\"user-controller\"],\"operationId\":\"getUserById\",\"parameters\":[{\"name\":\"id\",\"in\":\"path\",\"required\":true,\"schema\":{\"type\":\"integer\",\"format\":\"int64\"}}],\"responses\":{\"200\":{\"description\":\"OK\",\"content\":{\"*/*\":{\"schema\":{\"$ref\":\"#/components/schemas/User\"}}}}}},\"put\":{\"tags\":[\"user-controller\"],\"operationId\":\"updateUser\",\"parameters\":[{\"name\":\"id\",\"in\":\"path\",\"required\":true,\"schema\":{\"type\":\"integer\",\"format\":\"int64\"}}],\"requestBody\":{\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/User\"}}},\"required\":true},\"responses\":{\"200\":{\"description\":\"OK\",\"content\":{\"*/*\":{\"schema\":{\"$ref\":\"#/components/schemas/User\"}}}}}},\"delete\":{\"tags\":[\"user-controller\"],\"operationId\":\"deleteUser\",\"parameters\":[{\"name\":\"id\",\"in\":\"path\",\"required\":true,\"schema\":{\"type\":\"integer\",\"format\":\"int64\"}}],\"responses\":{\"200\":{\"description\":\"OK\",\"content\":{\"*/*\":{\"schema\":{\"type\":\"object\",\"additionalProperties\":{\"type\":\"boolean\"}}}}}}}},\"/user\":{\"post\":{\"tags\":[\"user-controller\"],\"operationId\":\"createUser\",\"requestBody\":{\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/User\"}}},\"required\":true},\"responses\":{\"200\":{\"description\":\"OK\",\"content\":{\"*/*\":{\"schema\":{\"$ref\":\"#/components/schemas/User\"}}}}}}},\"/posts\":{\"get\":{\"tags\":[\"post-controller\"],\"operationId\":\"getAllPost\",\"responses\":{\"200\":{\"description\":\"OK\",\"content\":{\"*/*\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/Post\"}}}}}}},\"post\":{\"tags\":[\"post-controller\"],\"operationId\":\"createPost\",\"requestBody\":{\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/Post\"}}},\"required\":true},\"responses\":{\"200\":{\"description\":\"OK\",\"content\":{\"*/*\":{\"schema\":{\"$ref\":\"#/components/schemas/Post\"}}}}}}},\"/locations\":{\"get\":{\"tags\":[\"location-controller\"],\"operationId\":\"getAllLocation\",\"responses\":{\"200\":{\"description\":\"OK\",\"content\":{\"*/*\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/Location\"}}}}}}},\"post\":{\"tags\":[\"location-controller\"],\"operationId\":\"createLocation\",\"requestBody\":{\"content\":{\"application/json\":{\"schema\":{\"$ref\":\"#/components/schemas/Location\"}}},\"required\":true},\"responses\":{\"200\":{\"description\":\"OK\",\"content\":{\"*/*\":{\"schema\":{\"$ref\":\"#/components/schemas/Location\"}}}}}}},\"/users\":{\"get\":{\"tags\":[\"user-controller\"],\"operationId\":\"getAllUsers\",\"responses\":{\"200\":{\"description\":\"OK\",\"content\":{\"*/*\":{\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/components/schemas/User\"}}}}}}}},\"/hello\":{\"get\":{\"tags\":[\"location-controller\"],\"operationId\":\"Test\",\"responses\":{\"200\":{\"description\":\"OK\",\"content\":{\"*/*\":{\"schema\":{\"type\":\"string\"}}}}}}},\"/Posts/{id}\":{\"get\":{\"tags\":[\"post-controller\"],\"operationId\":\"getLocationById\",\"parameters\":[{\"name\":\"id\",\"in\":\"path\",\"required\":true,\"schema\":{\"type\":\"integer\",\"format\":\"int64\"}}],\"responses\":{\"200\":{\"description\":\"OK\",\"content\":{\"*/*\":{\"schema\":{\"$ref\":\"#/components/schemas/Post\"}}}}}}},\"/Locations/{id}\":{\"get\":{\"tags\":[\"location-controller\"],\"operationId\":\"getLocationById_1\",\"parameters\":[{\"name\":\"id\",\"in\":\"path\",\"required\":true,\"schema\":{\"type\":\"integer\",\"format\":\"int64\"}}],\"responses\":{\"200\":{\"description\":\"OK\",\"content\":{\"*/*\":{\"schema\":{\"$ref\":\"#/components/schemas/Location\"}}}}}}}},\"components\":{\"schemas\":{\"Location\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"name\":{\"type\":\"string\"}}},\"User\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"firstname\":{\"type\":\"string\"},\"lastname\":{\"type\":\"string\"},\"location\":{\"$ref\":\"#/components/schemas/Location\"},\"email\":{\"type\":\"string\"}}},\"Post\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"postDate\":{\"type\":\"string\",\"format\":\"date-time\"},\"user\":{\"$ref\":\"#/components/schemas/User\"},\"details\":{\"type\":\"string\"}}}}}}".split("paths\":")[1].split("\"components\":")[0];
        List<String> ss = new ArrayList<>(Arrays.stream(s.split("tags\":")).toList()); //t9asmou b tags takra men tags tkasmhom des array list mtaa chaine 
        Map<String, Set<Function>> functionMap= new HashMap<>(); // Set kima list ensemble mtaa functions 
        ss.remove(0); // fasakhna l partie loula 
        for (String c: ss) {
            StringTokenizer st = new StringTokenizer(c,"[]"); //
            int n = st.countTokens();
            Function f =new Function();;
            String g;
            g =st.nextToken();
            f.setController(g.substring(1,g.length()-1)); //token lowelfih esml l controller -1 to remove l quotes 
            switch(n){
                case 2 : 
                    g=st.nextToken();
                    String[] h = g.split(",");
                    String v = g;
                    g = h[1].split(":")[1];   // nekhdou l opID 
                    f.setOpId(g.substring(1,g.length()-1)); // nahi l quote
                    g = v.substring(h[1].length()+1); //bch nzidou virgule  bch tokeed fard forme meaa l cas li ana fih param (4 tokens)
                    f=getBody(g,f);
                    break;
                case 4 :   
                    g=st.nextToken();
                    g = g.split(",")[1].split(":")[1];
                    f.setOpId(g.substring(1,g.length()-1));
                    g =st.nextToken();
                    f.setParam(gson.fromJson(g,Test.class));
                    g =st.nextToken();
                    f=getBody(g,f);
                    break;
            }
            if(functionMap.containsKey(f.getController())){       
                functionMap.get(f.getController()).add(f);
            }
            else{
                HashSet<Function> functions = new HashSet<>();  
                functions.add(f);
                functionMap.put(f.getController(),functions);
            }

        }
        for (Map.Entry<String,Set<Function>> entry : functionMap.entrySet()) {
            generateXML(entry.getKey(),entry.getValue()); //parcours mtaa les controller , entry tlem l clee meaa l valeur mteeha bch tparmori map lezem aana entry 
            
        }
        fileReader.close();
        bw.close();
    }
}


