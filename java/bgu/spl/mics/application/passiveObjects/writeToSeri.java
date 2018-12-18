package bgu.spl.mics.application.passiveObjects;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class writeToSeri {

    //Saving of object in a file

    public void writeToSeri(Object object,String filename) {
        try {
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);
        // Method for serialization of object
            out.writeObject(object);
            out.close();
            file.close();
        } catch (IOException ignored) { }
    }
}
