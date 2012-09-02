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
package org.thingml.bglib.gui;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 *
 * @author franck
 */
public class BLEService {
    
    public static Hashtable<String, String> profiles = new Hashtable<String, String>();
    static {
        profiles.put("0x180A", "");
    }
    
    
    protected int start, end;
    protected byte[] uuid;
    
    protected ArrayList<BLEAttribute> attributes = new ArrayList<BLEAttribute>();
    
    public BLEService(byte[] uuid, int start, int end) {
        this.uuid = uuid;
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public byte[] getUuid() {
        return uuid;
    }

    public ArrayList<BLEAttribute> getAttributes() {
        return attributes;
    }
    
    public String getUuidString() {
        String result = "";
        for(int i = 0; i<uuid.length; i++) {
            result = String.format("%02X", uuid[i]) + result;
        }
        result = "0x" + result;
        return result;
    }
    
    public String toString() {
        return "BLEService " + getUuidString() + " (" + start + ".." + end + ")";
    }
    
    public String getDescription() {
        String result = toString();
        for (BLEAttribute a : attributes) {
            result += "\n\t" + a.toString();
        }
        return result;
    }
    
}
