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
import org.thingml.bglib.BGAPIDefaultListener;
import org.thingml.bglib.BGAPI;

/**
 *
 * @author ffl
 */
public class BLEServiceInstance extends BGAPIDefaultListener {
    
    public ArrayList<BLEServiceValueListener> listeners = new ArrayList<BLEServiceValueListener>();
    
    public void addBLEServiceValueListener(BLEServiceValueListener l) {
        listeners.add(l);
    }
    
    public void removeBLEServiceValueListener(BLEServiceValueListener l) {
        listeners.remove(l);
    }
    
    protected BGAPI bgapi;
    protected int connection;
    protected int value_handle;
    protected int interval_handle;
    protected int config_handle;

    public BGAPI getBgapi() {
        return bgapi;
    }

    public int getConnection() {
        return connection;
    }

    public int getValue_handle() {
        return value_handle;
    }

    public int getInterval_handle() {
        return interval_handle;
    }

    public int getConfig_handle() {
        return config_handle;
    }

    public BLEServiceInstance(BGAPI bgapi, int connection, int value_handle, int interval_handle, int config_handle) {
        this.bgapi = bgapi;
        this.connection = connection;
        this.value_handle = value_handle;
        this.interval_handle = interval_handle;
        this.config_handle = config_handle;
        bgapi.addListener(this);
    }
    
    public void disconnect(){
        bgapi.removeListener(this);
    }
    
    public BLEServiceInstance(BGAPI bgapi, int connection, BLEService srv) {
        // TODO: Impelment
        throw new Error("Not Implemented");
    }
    
     public void subscribeIndications() {
        bgapi.send_attclient_write_command(connection, config_handle, new byte[]{0x02, 0x00});
    }
    
    public void subscribeNotifications() {
        bgapi.send_attclient_write_command(connection, config_handle, new byte[]{0x01, 0x00});
    }
    public void unsubscribe() {
        bgapi.send_attclient_write_command(connection, config_handle, new byte[]{0x00, 0x00});
    }
    
    public void writeInterval(int value) {
        byte[] i = new byte[2];
        i[0] = (byte)((value>>8) & 0xFF);
        i[1] = (byte)(value & 0xFF);
        bgapi.send_attclient_write_command(connection, interval_handle, i);
    }
    
    public void readInterval() {
        bgapi.send_attclient_read_by_handle(connection, interval_handle);
    }
    
    
    @Override
    public void receive_attclient_attribute_value(int conn, int atthandle, int type, byte[] value) {
        if (connection == conn) {
            if (atthandle == value_handle) {
                for (BLEServiceValueListener l : listeners) {
                    l.receivedValue(this, value);
                }
            }
            else if (atthandle == interval_handle) {
                for (BLEServiceValueListener l : listeners) {
                    l.receivedInterval(this, (value[0]<<8) + (value[1] & 0xFF));
                }
            }
        }
    }
    
}
