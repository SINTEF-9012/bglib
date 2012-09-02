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

/**
 *
 * @author franck
 */
public class BLEAttribute {
    protected byte[] uuid;
    protected int handle;
    
    public BLEAttribute(byte[] uuid, int handle) {
        this.uuid = uuid;
        this.handle = handle;
    }
    
    public byte[] getUuid() {
        return uuid;
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
        return "ATT " + getUuidString() + " => 0x" + Integer.toHexString(handle).toUpperCase();
    }
    
}
