/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.rhs.experiments;

import dk.rhs.distributedpasswordcracking.UserInfoClearText;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.concurrent.Callable;

/**
 *
 * @author Standard
 */
class SlaveRunner implements Callable<CrackedPartition> {

    private final Socket socket;
    private final String[] partition;
    private String slave_IP;
    private int slave_Port;
    

    public SlaveRunner(String[] partition, String slaveContactDetails) throws UnknownHostException, IOException {
        slave_IP = slaveContactDetails.split(":")[0];
        slave_Port = Integer.parseInt(slaveContactDetails.split(":")[1]);
        socket = new Socket(slave_IP, slave_Port);
        this.partition = partition;
    }

    @Override
    public CrackedPartition call() throws Exception {
        OutputStream os = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(partition);

        InputStream is = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(is);
        HashSet<UserInfoClearText> cracked = (HashSet<UserInfoClearText>) ois.readObject();
        socket.close();
        CrackedPartition crackedPartition = new CrackedPartition(slave_IP + ":" + slave_Port, cracked);
        return crackedPartition;
    }
}
