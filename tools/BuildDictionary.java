import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.DataInputStream;
import java.io.ObjectOutputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class BuildDictionary {

    private boolean isLibrary = false;
    private String filename;

    private LinkedHashMap<String, LinkedHashMap> wordDict;

    public BuildDictionary(String filename) {
        this.filename = filename;
        wordDict = new LinkedHashMap<String, LinkedHashMap>();
    }

    private void insert(String w) {
        String key = w.substring(0,1);
        String word = w.substring(1,w.length());
        LinkedHashMap<String, LinkedHashMap> set = wordDict.get(key);
        if (set == null) {
            set = new LinkedHashMap<String, LinkedHashMap>();
            wordDict.put(key,set);
        }
        //set.put(word, null);
        insert(set, word);
    }

    private void insert(LinkedHashMap<String, LinkedHashMap> hash, String w) {
        if (w.length() > 0) {
            String key = w.substring(0,1);
            String word = w.substring(1,w.length());
            LinkedHashMap<String, LinkedHashMap> set = hash.get(key);
            if (set == null) {
                set = new LinkedHashMap<String, LinkedHashMap>();
                hash.put(key,set);
            }
            //set.put(word, null);
            insert(set, word);
        }
    }

    public LinkedHashMap<String, LinkedHashMap> getWordDict() {
        return wordDict;
    }

    public void exportWordDict(String outfilename) throws Exception {
        FileOutputStream f_out = new FileOutputStream(outfilename);
        ObjectOutputStream obj_out = new ObjectOutputStream (f_out);
        obj_out.writeObject(wordDict);
    }

    public void importFile() throws Exception {
        try {
            FileInputStream fstream = new FileInputStream(this.filename);
            DataInputStream in = new DataInputStream(fstream);
            InputStreamReader isr = new InputStreamReader(in, "UTF8");
            BufferedReader reader = new BufferedReader(isr);

            String line = reader.readLine();

            while (line != null) {
                if (! line.substring(0, 3).equals("###")) {
                    if (line.equals("%dictdef begin")) {
                        isLibrary = true;
                        line = reader.readLine();
                        continue;
                    }
                    if (line.equals("%dictdef end")) {
                        isLibrary = false;
                        line = reader.readLine();
                        continue;
                    }
                    if (isLibrary) {
                        String[] split = line.split(" ");
                        //System.out.println("####" + split[0] + "####" + split[1] + "####");
                        //String.format("%X", split[1].charAt(0)));
                        this.insert(split[0]);
                        line = reader.readLine();
                        continue;
                    }
                }
                line = reader.readLine();
            }
            System.out.println("END");
            reader.close();
            isr.close();
            in.close();
            fstream.close();
        } catch (Exception e) {

        }
    }

    public static void main(String [] args) throws Exception {
        if (args.length == 2) {
            BuildDictionary obj = new BuildDictionary(args[0]);
            obj.importFile();
            System.out.println(obj.getWordDict());//.get("\u5b8b"));
            obj.exportWordDict(args[1]);
        } else {
            System.out.println("No File Name");
        }
    }

}

