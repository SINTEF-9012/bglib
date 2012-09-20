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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Franck FLEUREY (SINTEF)
 */
public class BGAPIPacket {
    
    protected int msgType;
    protected int classID;
    protected int commandID;
    protected int payloadLength = -1;

    public int getClassID() {
        return classID;
    }

    public int getCommandID() {
        return commandID;
    }

    public int getMsgType() {
        return msgType;
    }

    public int getPayloadLength() {
        return payloadLength;
    }
    
    protected ByteArrayOutputStream data = new ByteArrayOutputStream();

    public ByteArrayOutputStream getPayloadData() {
        return data;
    }
    
    public BGAPIPacket(byte[] header) {
        msgType = (header[0] & 0xFF) >> 7;
        payloadLength = ((header[0] & 0x07) << 8) + header[1];
        classID = header[2];
        commandID = header[3];
    }
    
    public BGAPIPacket(int msg_type, int classID, int commandID) {
        this.msgType = msg_type;
        this.classID = classID;
        this.commandID = commandID;
    }
    
    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append("< typ=" + msgType + " cla=" + classID + " cmd=" + commandID + " len=" + payloadLength + " ");
        if (data.size() > 0) {
            byte[] bytes = data.toByteArray();
            result.append( "[ ");
            for (byte b : bytes) result.append( Integer.toHexString((int) (b & 0xFF)) + " ");   
            result.append( "] ");
        }
        result.append(">");
        return result.toString();
    }
    
    public BGAPIPacketReader getPayloadReader() {
        return new BGAPIPacketReader(data.toByteArray());
    }
    
    public byte[] getPacketBytes() {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        payloadLength = data.size();
        result.write( (msgType << 7) + (payloadLength >> 8) );
        result.write( payloadLength & 0xFF );
        result.write( classID & 0xFF );
        result.write( commandID & 0xFF );
        try {
            result.write( data.toByteArray() );
        } catch (IOException ex) {
            Logger.getLogger(BGAPIPacket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result.toByteArray();
    }
    
    public void w_uint8(int v) {
        data.write(v & 0xFF);
    }
    public void w_int8(int v) {
        data.write(v);
    }
    
    
    public void w_uint16(int v) {
        data.write(v & 0xFF);
        data.write((v >> 8) & 0xFF);
    }
    
    public void w_int16(int v) {
        data.write(v & 0xFF);
        data.write((v >> 8) & 0xFF);
    }
    
    public void w_uint32(int v) {
        data.write(v & 0xFF);
        data.write((v >> 8) & 0xFF);
        data.write((v >> 16) & 0xFF);
        data.write((v >> 24) & 0xFF);
    }
    
    public void w_uint8array(byte[] bytes) {
        data.write(bytes.length);
        for (int i=0; i<bytes.length; i++) {
            data.write(bytes[i]);
        }
    }
    
    public void w_bd_addr(BDAddr addr) {
       byte[] bytes = addr.getByteAddr();
       for (int i=0; i<bytes.length; i++) {
            data.write(bytes[i]);
        }
    }
    
}
