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

/**
 *
 * @author Franck FLEUREY (SINTEF)
 */
public class BGAPIPacketReader {
    
    private byte[] data;
    private int index;
    
    public BGAPIPacketReader(byte[] data) {
        this.data = data;
    }
    
    public void reset() {
        index = 0;
    }
    
    public int length() {
        return data.length;
    }
    
    public int bytesLeft() {
        return data.length - index;
    }
    
    private Integer next_uint() {
        return data[index++] & 0xFF;
    }
    
    public int r_int8() {
        return (int)data[index++];
    }
    
    public int r_uint8() {
        return (int)next_uint();
    }
    
    public int r_uint16() {
        int result = (int)next_uint();
        result += (int)(next_uint()<<8);
        return result;
    }
     public int r_int16() {
        int result = (int)next_uint();
        result += (int)(next_uint()<<8);
        return result;
    }
    
    
    public int r_uint32() {
        int result = (int)next_uint();
        result += (int)(next_uint()<<8);
        result += (int)(next_uint()<<16);
        result += (int)(next_uint()<<24);
        return result;
    }
    
    public byte[] r_uint8array() {
        byte[] result = new byte[next_uint()];
        for (int i=0; i<result.length; i++) {
            result[i] = data[index++];
        }
        return result;
    }
    
    public BDAddr r_bd_addr() {
        byte[] addr = new byte[6];
        for (int i=0; i<6; i++) {
            addr[i] = data[index++];
        }
        return new BDAddr(addr);
    }
}
