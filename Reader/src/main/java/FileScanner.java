import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileScanner {

    String body = "";
    String[] fieldNames;
    ArrayList<Boolean> used;
    String bodyContent = "";

    public String getHeader(String filePath) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(filePath));

        String header = in.readLine();

        String line;
        while ((line = in.readLine()) != null) {
            if (line.contains("Relates to measurement")){
                break;
            }
            line = line.replace("\t\t", "");
            line = line.replace("\t", ":");
            header  = header.concat("\n").concat(line);
        }

        in.close();

        return header;
    }

    public String getBody(String filePath) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(filePath));

        String line;
        boolean reachedContent = false;
        while ((line = in.readLine()) != null) {
            if (line.contains("Relates to measurement") || line.contains("Has Selection Panel")){
                line = line.replace("\t", "");
                body = body.concat(line).concat("\n");
            } else if (line.contains("Field Names:")) {
                //Based on instructions no need to show the fields names, and part numbers is never to be shown only used to determine which are
                line = line.replace("Field Names:", "");
                line = line.replace("Part Number", "");
                reachedContent = true;
                used = numberOfFields(line);

            } else if (reachedContent){
                bodyContent = bodyContent.concat("\n").concat(readFieldContents(line));
            }
        }

        body = body.concat(addFieldNames()).concat("\n");
        body = body.concat(bodyContent);
        in.close();

        return body;
    }

    public String addFieldNames(){
        String fields = "";
        for (int i = 0; i < fieldNames.length; i++){
            if(used.get(i)) {
                fields = fields.concat(fieldNames[i]).concat(":");
            }
        }
        return fields;
    }

    public ArrayList<Boolean> numberOfFields(String fieldsLine){
        fieldNames = fieldsLine.split("\t");
        ArrayList<Boolean> used = new ArrayList<>();
        for(int i = 0; i < fieldNames.length; i++){
            used.add(false);
        }
        return used;
    }

    public String readFieldContents(String fieldsContents) {
        //From the examples the Part number column is always a number. Included a check for a letter for thoroughness
        if(Character.isLetterOrDigit(fieldsContents.charAt(0) )){
            String[] fields = fieldsContents.split("\t");
            fieldsContents = "";
            for(int i = 2; i < fields.length; i++) {
                if (!fields[i].equals("")) {
                    fieldsContents = fieldsContents.concat(fields[i]).concat(":");
                    used.set(i-2, true);
                }
            }
            fieldsContents = fieldsContents.concat(fieldsContents);
        }
        return fieldsContents;
    }

}
