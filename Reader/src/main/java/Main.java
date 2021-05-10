import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        //For now uses manual file path input
        String filePath = "./Reader/src/main/resources/Metafiles/Elbow_MetaInfo.txt";

        FileScanner fileScanner = new FileScanner();

        String header = fileScanner.getHeader(filePath);

        String body = fileScanner.getBody(filePath);

        String full = header.concat("\n").concat(body);

        final JFrame parent = new JFrame();

        parent.pack();
        parent.setVisible(true);
        String name = JOptionPane.showInputDialog(parent,
                full, null);

    }


}
