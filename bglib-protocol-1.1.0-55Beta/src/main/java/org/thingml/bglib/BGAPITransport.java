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
package org.thingml.bglib;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Franck FLEUREY (SINTEF)
 */
public class BGAPITransport implements Runnable {
    
    protected ArrayList<BGAPITransportListener> listeners = new ArrayList<BGAPITransportListener>();
    public void addListener(BGAPITransportListener l) {
        listeners.add(l);
    }
    public void removeListener(BGAPITransportListener l) {
        listeners.remove(l);
    }
    
    protected InputStream in;
    protected OutputStream out;
    
    private Thread rxthread = null;
    
    private long receivedBytes = 0;

    public long getReceivedBytes() {
        return receivedBytes;
    }

    public BGAPITransport(InputStream in, OutputStream out) {
        this.in = in;
        this.out = out;
        rxthread = new Thread(this);
        rxthread.start();
    }
    
    public void sendPacket(BGAPIPacket p) {
        try {
            out.write(p.getPacketBytes());
        } catch (IOException ex) {
            Logger.getLogger(BGAPITransport.class.getName()).log(Level.SEVERE, null, ex);
            throw new IOError(ex);
        }
        for(BGAPITransportListener l : listeners) l.packetSent(p);
    }
    
    
    
    /**************************************************************************
     * CODE OF THE RECEIVER THREAD
     *************************************************************************/
    
    public void stop() {
        terminate = true;
    }
    
    public void run() {

        byte[] buffer = new byte[1024];
        byte[] hdr = new byte[HEADER_SIZE];
        //byte[] data = new byte[2048];
        int len = -1;
        int idx = 0;
        int state = WAITING;
        BGAPIPacket p = null;
        

        try {
            //System.out.println("Receiver Thread Started.");
            while (!terminate && ((len = this.in.read(buffer)) > -1)) {
                receivedBytes += len;
                
                for (int i = 0; i < len; i++) {
                    byte c = buffer[i];
                    if (state == WAITING) {
                            idx = 0;
                            state = HEADER;
                            hdr[idx++] = c;
                    }
                    else if (state == HEADER) {
                            hdr[idx++] = c;
                            if (idx == HEADER_SIZE) { // We got the whole header
                                p = new BGAPIPacket(hdr);
                                //System.out.println("Got Header" + p.toString());

                                if (p.getPayloadLength() > 0) { // there is a payload
                                            state = PAYLOAD;
                                            idx = 0;	
                                    }
                                    else { // There is no payload
                                            state = WAITING;
                                            for (BGAPITransportListener l : listeners) l.packetReceived(p);
                                            p = null;
                                    }
                            }
                    }
                    else if (state == PAYLOAD) {
                            p.getPayloadData().write(c);
                            idx++;
                            if (idx == p.getPayloadLength()) { // We got a complete message
                                    state = WAITING;
                                    for (BGAPITransportListener l : listeners) l.packetReceived(p);
                                    p = null;
                            }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        System.err.println("BLED112: Receiver thread stopped.");
    }
    
    private boolean terminate = false;

    private static final int WAITING = 0;
    private static final int HEADER = 1;
    private static final int PAYLOAD = 2;
    
    private static final int HEADER_SIZE = 4;
    
}
