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
package org.thingml.bglib.gui;

/**
 *
 * @author ffl
 */
public class ByteUtils {
    
    public static byte[] bytesFromString(String bytes) {
        String[] bs = bytes.split(" ");
        byte[] result = new byte[bs.length];
        for (int i = 0; i<bs.length; i++) {
            int b = Integer.parseInt(bs[i], 16);
            result[i] = (byte)b;
        }
        return result;
    }
    
    public static String bytesToString(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for(byte b : bytes) result.append( Integer.toHexString(b & 0xFF) + " ");
        return result.toString();        
    }
    
    
}
