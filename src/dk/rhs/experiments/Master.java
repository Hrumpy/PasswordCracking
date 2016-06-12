/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.rhs.experiments;

import dk.rhs.distributedpasswordcracking.UserInfoClearText;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author Marius
 */
public class Master {

    private static final String[] slaveContactDetails = {"localhost:65080", "localhost:5005"};
    private static final int numberOfDictionaryPartitions = 2;
    private static final int numberOfSlaves = slaveContactDetails.length;
    private static List<Future<CrackedPartition>> futureCrackedPartitions = new ArrayList<Future<CrackedPartition>>();
    private static ExecutorService pool = Executors.newFixedThreadPool(numberOfSlaves);
    private static int crackedPartitionCount = 0;

    public static void main(final String[] args) throws InterruptedException, ExecutionException, FileNotFoundException, IOException {
        final long startTime = System.currentTimeMillis();

        DictionaryFileHandler dfh = new DictionaryFileHandler("webster-dictionary.txt", numberOfDictionaryPartitions);
        final HashSet<UserInfoClearText> result = new HashSet<UserInfoClearText>();

// WHY U BREAK WHILE LOOP WHEN NOT DONE STUPID PROGRAM :PPP debug works fine. Might be due to the way it iterates.
        
        //runSlave(dfh);
        while (crackedPartitionCount < numberOfDictionaryPartitions) 
        {            
            /*int nSlaveFree = 0;
            runSlaveWithModifier(dfh,nSlaveFree);
            for (Future<CrackedPartition> fcp : futureCrackedPartitions)
            {
                if (fcp.isDone()) 
                {
                    result.addAll(fcp.get().getCracked());
                    crackedPartitionCount++;
                    nSlaveFree++;
                }
            }
            */
             runSlave(dfh);
            
            for (Iterator<Future<CrackedPartition>> it = futureCrackedPartitions.iterator(); it.hasNext();) 
            {
                Future<CrackedPartition> future = it.next();
                if (future.isDone()) {
                    CrackedPartition crackedPartition = future.get();
                    result.addAll(crackedPartition.getCracked());
                    crackedPartitionCount++;
                }
            }
            
        }

        

        final long endTime = System.currentTimeMillis();
        final long usedTime = endTime - startTime;
        System.out.println(result);
        System.out.println("Used time: " + usedTime / 1000 + " seconds = " + usedTime / 60000.0 + " minutes");
        pool.shutdown();
    }

    public static void runSlave(DictionaryFileHandler dfh) {
        for (int j = 0; j < numberOfSlaves; j++) {
            try{
            if (!dfh.getOutOfPartitions()) {
                String[] nextAvailablePartition = dfh.getNextAvailablePartition();
                // maybe add something that checks that a slave is done before opening a new one.
                Callable<CrackedPartition> slaveRunner = new SlaveRunner(nextAvailablePartition, slaveContactDetails[j]);
                Future<CrackedPartition> future = pool.submit(slaveRunner);
                futureCrackedPartitions.add(future);
            }
            }catch(Exception ex)
            {
                System.out.println(ex.getMessage());
            }
        }
    }
    public static void runSlaveWithModifier(DictionaryFileHandler dfh, int nOfSlaves) {
        for (int j = 0; j < nOfSlaves; j++) {
            try{
            if (!dfh.getOutOfPartitions()) {
                String[] nextAvailablePartition = dfh.getNextAvailablePartition();
                Callable<CrackedPartition> slaveRunner = new SlaveRunner(nextAvailablePartition, slaveContactDetails[j]);
                Future<CrackedPartition> future = pool.submit(slaveRunner);
                futureCrackedPartitions.add(future);
            }
            }catch(Exception ex)
            {
                System.out.println(ex.getMessage());
            }
        }
    }
}
