package input;

import common.Constants;
import data.DataBase;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class FileIterator {
    private final File dir, outDir;

    public FileIterator(final String inPath, final String outPath) throws IOException {
        this.dir = new File(inPath);
        Path path = Paths.get(outPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        this.outDir = new File(outPath);
        deleteFiles(outDir.listFiles());
    }

    private void deleteFiles(final File[] directory) {
        if (directory != null) {
            for (File file : directory) {
                if (!file.delete()) {
                    System.out.println("nu s-a sters");
                }
            }
        }

    }

    public void run() throws IOException, ParseException {
        if (dir.listFiles() != null) {
            int i = 0;
            for (File file : Objects.requireNonNull(dir.listFiles())) {
                i++;
                String filepath = Constants.OUTPUT_PATH + i + Constants.FILE_EXTENSION;
                File out = new File(filepath);
                boolean isCreated = out.createNewFile();
                if (isCreated) {
                    runTest(file.getAbsolutePath(), filepath);
                }
            }
        }
    }

    private void runTest(final String absolutePath,
                         final String filepath)
                         throws ParseException,
                         IOException {
        InputLoader inputLoader = new InputLoader(absolutePath);
        //System.out.println(inputLoader.getDataBase().run().toString());
        try{
            FileWriter fw=new FileWriter(filepath);
            fw.write(inputLoader.getDataBase().run().toJSONString());
            fw.close();
        }catch(Exception e){System.out.println(e);}
    }

}
