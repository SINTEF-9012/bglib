/**
 * Copyright (C) 2012 SINTEF <franck.fleurey@sintef.no>
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingml.bglib;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Franck FLEUREY (SINTEF)
 */
public class BGAPIPacketLogger implements BGAPITransportListener {

    private long start;
    
    public BGAPIPacketLogger() {
        start = System.currentTimeMillis();
    }

    private String time() {
        int millis_since_start = (int)(System.currentTimeMillis() - start);
        String ret = String.format("%d.%03d ", millis_since_start / 1000, millis_since_start % 1000);
        
        return ret;
    }
    
    public void packetSent(BGAPIPacket packet) {
         Logger.getLogger(BGAPIPacketLogger.class.getName()).log(Level.INFO, time() + "SND " + packet.toString());
    }

    public void packetReceived(BGAPIPacket packet) {
        Logger.getLogger(BGAPIPacketLogger.class.getName()).log(Level.INFO, time() + "RCV " + packet.toString());
    }
    
}
