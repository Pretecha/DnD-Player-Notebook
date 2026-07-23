package persistence;

import java.io.*;

import model.Hub;

public class JsonWrite {
    private PrintWriter writer;
    private String data;

    // EFFECTS: constructs a JsonWrite object with the path to data as parameter.
    public JsonWrite(String data) {
        this.data = data;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file
    // cannot be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(data));
    }

    // EFFECTS: saves current application state to data
    public void save(Hub mainHub) {
        writer.print(mainHub.toJsonObject().toString(4));
    }

    // EFFECTS: closes the writer
    public void close() {
        writer.close();
    }
}
