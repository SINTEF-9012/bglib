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
import org.thingml.bglib.BDAddr;
import org.thingml.bglib.BGAPI;
import org.thingml.bglib.BGAPIDefaultListener;
import org.thingml.bglib.BGAPIPacketLogger;
import org.thingml.bglib.BGAPITransport;

public class SimpleDiscovery extends BGAPIDefaultListener
{
    
    
    public static void main( String[] args )
    {
        System.out.println( "Connecting BLED112 Dongle..." );
        BGAPITransport bgapi = BLED112.connectBLED112();
        bgapi.addListener(new BGAPIPacketLogger());
        BGAPI impl = new BGAPI(bgapi);
        impl.addListener(new SimpleDiscovery());
        
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(SimpleDiscovery.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println( "Requesting Version Number..." );
        impl.send_system_get_info();
        impl.send_system_hello();
        impl.send_gap_set_scan_parameters(10, 250, 1);
        impl.send_gap_discover(1);
        
    }

    public void receive_system_get_info(Integer major, Integer minor, Integer patch, Integer build, Integer ll_version, Integer protocol_version, Integer hw) {
        System.out.println("get_info_rsp :" + major + "." + minor + "." + patch + " (" + build + ") " + "ll=" + ll_version + " hw=" + hw);
    }

    public void receive_gap_scan_response(Integer rssi, Integer packet_type, BDAddr sender, Integer address_type, Integer bond, byte[] data) {
        System.out.println("FOUND: " + sender.toString() + "["+ new String(data).trim() + "] (rssi = " + rssi + ", packet type= " + packet_type + ")");
    }

    @Override
    public void receive_system_hello() {
        System.out.println("GOT HELLO!");
    }
    
    
}
