/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.rhs.experiments;

import dk.rhs.distributedpasswordcracking.UserInfoClearText;
import java.util.HashSet;

/**
 *
 * @author Standard
 */
public class CrackedPartition {
    
    private String slaveContactDetails;
    private HashSet<UserInfoClearText> cracked;

    public CrackedPartition(String slaveContactDetails, HashSet<UserInfoClearText> cracked) {
        this.slaveContactDetails = slaveContactDetails;
        this.cracked = cracked;
    }

    public String getSlaveContactDetails() {
        return slaveContactDetails;
    }

    public HashSet<UserInfoClearText> getCracked() {
        return cracked;
    }
    
    
    
}
