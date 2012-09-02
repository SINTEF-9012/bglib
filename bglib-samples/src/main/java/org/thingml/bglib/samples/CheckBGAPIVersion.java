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
package org.thingml.bglib.samples;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.thingml.bglib.BGAPIPacket;
import org.thingml.bglib.BGAPIPacketReader;
import org.thingml.bglib.BGAPITransport;
import org.thingml.bglib.BGAPITransportListener;

public class CheckBGAPIVersion implements BGAPITransportListener
{
    
    
    public static void main( String[] args )
    {
        System.out.println( "Connecting BLED112 Dongle..." );
        BGAPITransport bgapi = BLED112.connectBLED112();
        bgapi.addListener(new CheckBGAPIVersion());
        //bgapi.addListener(new BGAPIPacketLogger());
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(CheckBGAPIVersion.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println( "Requesting Version Number..." );
        BGAPIPacket p = new BGAPIPacket(0, 0, 8); // Get Info Packet
        bgapi.sendPacket(p);
        
    }

    public void packetSent(BGAPIPacket packet) {}

    public void packetReceived(BGAPIPacket packet) {
        System.out.println("Received: " + packet.toString());
        if (packet.getClassID() == 0 && packet.getCommandID() == 8) {
            BGAPIPacketReader r = packet.getPayloadReader();
            Integer major = r.r_uint16();
            Integer minor = r.r_uint16();
            Integer patch = r.r_uint16();
            Integer build = r.r_uint16();
            Integer ll_version = r.r_uint16();
            Integer protocol_version = r.r_uint8();
            Integer hw = r.r_uint8();
            System.out.println("get_info_rsp :" + major + "." + minor + "." + patch + " (" + build + ") " + "ll=" + ll_version + " hw=" + hw);
        }
    }
}
