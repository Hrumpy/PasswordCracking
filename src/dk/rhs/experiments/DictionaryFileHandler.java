/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.rhs.experiments;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author Marius
 */
public class DictionaryFileHandler {

    RandomAccessFile raf;
    private String[] DictionaryArray;
    private final int partitionSize;
    private int pointer;
    private final int lastPartitionStart;
    private final int lastPartitionSize;
    private boolean OutOfPartitions = false;

    /**
     * @param filename
     * @param partitionSize - number of words per partition.
     * @throws FileNotFoundException
     * @throws IOException
     */
    public DictionaryFileHandler(String filename, int numberOfPartitions) throws FileNotFoundException, IOException {
        raf = new RandomAccessFile(filename, "r");
        String wholeDictionary = new String(getFileAsBytes(), "UTF-8");
        DictionaryArray = wholeDictionary.split("\\n");
        if (DictionaryArray.length < numberOfPartitions) {
            throw new IllegalArgumentException("Number of partitions cannot be > number of dictionary entries");
        }
        this.partitionSize = DictionaryArray.length / numberOfPartitions;
        this.lastPartitionSize = partitionSize + (DictionaryArray.length - (partitionSize * numberOfPartitions));
        this.lastPartitionStart = DictionaryArray.length - lastPartitionSize;
    }

    private byte[] getFileAsBytes() throws IOException {
        byte[] fileAsBytes = new byte[(int) (long) (raf.length())];
        raf.read(fileAsBytes);
        return fileAsBytes;
    }

    public String[] getNextAvailablePartition() {

        if (pointer < lastPartitionStart) {
            String[] partitionArray = new String[this.partitionSize];
            System.arraycopy(DictionaryArray, pointer, partitionArray, 0, partitionSize);
            pointer = pointer + partitionSize;
            return partitionArray;
        } else {
            OutOfPartitions = true;
            String[] partitionArray = new String[this.lastPartitionSize];
            System.arraycopy(DictionaryArray, pointer, partitionArray, 0, lastPartitionSize);
            return partitionArray;
        }

    }
    public boolean getOutOfPartitions()
    {
        return OutOfPartitions;
    }
    public int getPartitionSize() {
        return partitionSize;
    }

    public int getLastPartitionStart() {
        return lastPartitionStart;
    }

    public int getLastPartitionSize() {
        return lastPartitionSize;
    }
 
    public int getPointer() {
        return pointer;
    }

    public int getDictionarySize() {
        return DictionaryArray.length;
    }
}
