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
public class BDAddr {
   
    public static BDAddr fromString(String addr) {
        String[] bytes = addr.split(":");
        if (bytes.length != 6) {
            throw new Error ("Invalid Bluetooth address format.");
        }
        byte[] byte_addr = new byte[6];
        for (int i=0; i<6; i++) {
            byte_addr[5-i] = (byte)Integer.parseInt(bytes[i], 16);
        }
        return new BDAddr(byte_addr);
    }
    
    protected byte[] byte_addr;
    
    public byte[] getByteAddr() {
        return byte_addr;
    }
    
    public BDAddr(byte[] addr) {
        byte_addr = addr;
    }
    
    public String toString() {
        StringBuffer result = new StringBuffer();
        for (int i=0; i<byte_addr.length; i++) {
            result.append(Integer.toHexString((byte_addr[5-i] & 0xFF)));
            if (i<byte_addr.length-1) result.append(":");
        }
        return result.toString();
    }
    
}
