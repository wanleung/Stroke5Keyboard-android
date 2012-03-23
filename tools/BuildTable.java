import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.DataInputStream;
import java.io.ObjectOutputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class BuildTable {

    private boolean isLibrary = false;
    private String filename;

    private ConcurrentSkipListMap<String, CopyOnWriteArrayList<String>> wordDict;

    public BuildTable(String filename) {
        this.filename = filename;
        wordDict = new ConcurrentSkipListMap<String, CopyOnWriteArrayList<String>>();
    }

    private void insert(String k, String w) {
        for (int i = 0; i < k.length(); i++) {
            String key = k.substring(0,i+1);
            System.out.println(key + " " + w);
            CopyOnWriteArrayList<String> set = wordDict.get(key);
            if (set == null) {
                set = new CopyOnWriteArrayList<String>();
                wordDict.put(key, set);
            }
            if (! set.contains(w)) {
                set.add(w);
            }
        }
    }

    public ConcurrentSkipListMap<String, CopyOnWriteArrayList<String>> getWordDict() {
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
                    if (line.equals("%chardef begin")) {
                        isLibrary = true;
                        line = reader.readLine();
                        continue;
                    }
                    if (line.equals("%chardef end")) {
                        isLibrary = false;
                        line = reader.readLine();
                        continue;
                    }
                    if (isLibrary) {
                        String[] split = line.split(" ");
                        //System.out.println("####" + split[0] + "####" + split[1] + "####");
                        //String.format("%X", split[1].charAt(0)));
                        this.insert(split[0], split[1]);
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
            BuildTable obj = new BuildTable(args[0]);
            obj.importFile();
            System.out.println(obj.getWordDict().get("."));
            System.out.println(obj.getWordDict().get(",.mm/"));
            obj.exportWordDict(args[1]);
        } else {
            System.out.println("No File Name");
        }
    }

}

