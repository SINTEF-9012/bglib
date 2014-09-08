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

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Franck FLEUREY (SINTEF)
 */
public class BGAPI implements BGAPITransportListener {
    
    protected BGAPITransport bgapi;
    
    public BGAPI(BGAPITransport bgapi) {
        this.bgapi = bgapi;
        bgapi.addListener(this);
    }
    
    public BGAPITransport getLowLevelDriver() {
        return bgapi;
    }
    
    public void disconnect() {
        bgapi.stop();
    }
    
    public List<BGAPIListener> listeners = new CopyOnWriteArrayList<BGAPIListener>();
    public void addListener(BGAPIListener l) {
        listeners.add(l);
    }
    public void removeListener(BGAPIListener l) {
        listeners.remove(l);
    }

    public void packetSent(BGAPIPacket packet) {}

    // General packet handler
    public void packetReceived(BGAPIPacket packet) {
		if (packet.getMsgType() == 0) {
			switch(packet.classID) {
				case 0: receive_system_cmd(packet);break;
				case 1: receive_flash_cmd(packet);break;
				case 2: receive_attributes_cmd(packet);break;
				case 3: receive_connection_cmd(packet);break;
				case 4: receive_attclient_cmd(packet);break;
				case 5: receive_sm_cmd(packet);break;
				case 6: receive_gap_cmd(packet);break;
				case 7: receive_hardware_cmd(packet);break;
				case 8: receive_test_cmd(packet);break;
				default: break;
			}
		}
		else {
			switch(packet.classID) {
				case 0: receive_system_evt(packet);break;
				case 1: receive_flash_evt(packet);break;
				case 2: receive_attributes_evt(packet);break;
				case 3: receive_connection_evt(packet);break;
				case 4: receive_attclient_evt(packet);break;
				case 5: receive_sm_evt(packet);break;
				case 6: receive_gap_evt(packet);break;
				case 7: receive_hardware_evt(packet);break;
				case 8: receive_test_evt(packet);break;
				default: break;
			}
		}

    }
    
	private void receive_system_cmd(BGAPIPacket packet) {
		switch(packet.commandID) {
			case 0: receive_system_reset(packet); break;
			case 1: receive_system_hello(packet); break;
			case 2: receive_system_address_get(packet); break;
			case 3: receive_system_reg_write(packet); break;
			case 4: receive_system_reg_read(packet); break;
			case 5: receive_system_get_counters(packet); break;
			case 6: receive_system_get_connections(packet); break;
			case 7: receive_system_read_memory(packet); break;
			case 8: receive_system_get_info(packet); break;
			case 9: receive_system_endpoint_tx(packet); break;
			case 10: receive_system_whitelist_append(packet); break;
			case 11: receive_system_whitelist_remove(packet); break;
			case 12: receive_system_whitelist_clear(packet); break;
			default: break;
		}
	}
	private void receive_system_evt(BGAPIPacket packet) {
		switch(packet.commandID) {
			case 0: receive_system_boot(packet); break;
			case 1: receive_system_debug(packet); break;
			case 2: receive_system_endpoint_rx(packet); break;
			default: break;
		}
	}
	private void receive_flash_cmd(BGAPIPacket packet) {
		switch(packet.commandID) {
			case 0: receive_flash_ps_defrag(packet); break;
			case 1: receive_flash_ps_dump(packet); break;
			case 2: receive_flash_ps_erase_all(packet); break;
			case 3: receive_flash_ps_save(packet); break;
			case 4: receive_flash_ps_load(packet); break;
			case 5: receive_flash_ps_erase(packet); break;
			case 6: receive_flash_erase_page(packet); break;
			case 7: receive_flash_write_words(packet); break;
			default: break;
		}
	}
	private void receive_flash_evt(BGAPIPacket packet) {
		switch(packet.commandID) {
			case 0: receive_flash_ps_key(packet); break;
			default: break;
		}
	}
	private void receive_attributes_cmd(BGAPIPacket packet) {
		switch(packet.commandID) {
			case 0: receive_attributes_write(packet); break;
			case 1: receive_attributes_read(packet); break;
			case 2: receive_attributes_read_type(packet); break;
			case 3: receive_attributes_user_response(packet); break;
			default: break;
		}
	}
	private void receive_attributes_evt(BGAPIPacket packet) {
		switch(packet.commandID) {
			case 0: receive_attributes_value(packet); break;
			case 1: receive_attributes_user_request(packet); break;
			default: break;
		}
	}
	private void receive_connection_cmd(BGAPIPacket packet) {
		switch(packet.commandID) {
			case 0: receive_connection_disconnect(packet); break;
			case 1: receive_connection_get_rssi(packet); break;
			case 2: receive_connection_update(packet); break;
			case 3: receive_connection_version_update(packet); break;
			case 4: receive_connection_channel_map_get(packet); break;
			case 5: receive_connection_channel_map_set(packet); break;
			case 6: receive_connection_features_get(packet); break;
			case 7: receive_connection_get_status(packet); break;
			case 8: receive_connection_raw_tx(packet); break;
			default: break;
		}
	}
	private void receive_connection_evt(BGAPIPacket packet) {
		switch(packet.commandID) {
			case 0: receive_connection_status(packet); break;
			case 1: receive_connection_version_ind(packet); break;
			case 2: receive_connection_feature_ind(packet); break;
			case 3: receive_connection_raw_rx(packet); break;
			case 4: receive_connection_disconnected(packet); break;
			default: break;
		}
	}
	private void receive_attclient_cmd(BGAPIPacket packet) {
		switch(packet.commandID) {
			case 0: receive_attclient_find_by_type_value(packet); break;
			case 1: receive_attclient_read_by_group_type(packet); break;
			case 2: receive_attclient_read_by_type(packet); break;
			case 3: receive_attclient_find_information(packet); break;
			case 4: receive_attclient_read_by_handle(packet); break;
			case 5: receive_attclient_attribute_write(packet); break;
			case 6: receive_attclient_write_command(packet); break;
			case 7: receive_attclient_reserved(packet); break;
			case 8: receive_attclient_read_long(packet); break;
			case 9: receive_attclient_prepare_write(packet); break;
			case 10: receive_attclient_execute_write(packet); break;
			case 11: receive_attclient_read_multiple(packet); break;
			default: break;
		}
	}
	private void receive_attclient_evt(BGAPIPacket packet) {
		switch(packet.commandID) {
			case 0: receive_attclient_indicated(packet); break;
			case 1: receive_attclient_procedure_completed(packet); break;
			case 2: receive_attclient_group_found(packet); break;
			case 3: receive_attclient_attribute_found(packet); break;
			case 4: receive_attclient_find_information_found(packet); break;
			case 5: receive_attclient_attribute_value(packet); break;
			case 6: receive_attclient_read_multiple_response(packet); break;
			default: break;
		}
	}
	private void receive_sm_cmd(BGAPIPacket packet) {
		switch(packet.commandID) {
			case 0: receive_sm_encrypt_start(packet); break;
			case 1: receive_sm_set_bondable_mode(packet); break;
			case 2: receive_sm_delete_bonding(packet); break;
			case 3: receive_sm_set_parameters(packet); break;
			case 4: receive_sm_passkey_entry(packet); break;
			case 5: receive_sm_get_bonds(packet); break;
			case 6: receive_sm_set_oob_data(packet); break;
			default: break;
		}
	}
	private void receive_sm_evt(BGAPIPacket packet) {
		switch(packet.commandID) {
			case 0: receive_sm_smp_data(packet); break;
			case 1: receive_sm_bonding_fail(packet); break;
			case 2: receive_sm_passkey_display(packet); break;
			case 3: receive_sm_passkey_request(packet); break;
			case 4: receive_sm_bond_status(packet); break;
			default: break;
		}
	}
	private void receive_gap_cmd(BGAPIPacket packet) {
		switch(packet.commandID) {
			case 0: receive_gap_set_privacy_flags(packet); break;
			case 1: receive_gap_set_mode(packet); break;
			case 2: receive_gap_discover(packet); break;
			case 3: receive_gap_connect_direct(packet); break;
			case 4: receive_gap_end_procedure(packet); break;
			case 5: receive_gap_connect_selective(packet); break;
			case 6: receive_gap_set_filtering(packet); break;
			case 7: receive_gap_set_scan_parameters(packet); break;
			case 8: receive_gap_set_adv_parameters(packet); break;
			case 9: receive_gap_set_adv_data(packet); break;
			case 10: receive_gap_set_directed_connectable_mode(packet); break;
			default: break;
		}
	}
	private void receive_gap_evt(BGAPIPacket packet) {
		switch(packet.commandID) {
			case 0: receive_gap_scan_response(packet); break;
			case 1: receive_gap_mode_changed(packet); break;
			default: break;
		}
	}
	private void receive_hardware_cmd(BGAPIPacket packet) {
		switch(packet.commandID) {
			case 0: receive_hardware_io_port_config_irq(packet); break;
			case 1: receive_hardware_set_soft_timer(packet); break;
			case 2: receive_hardware_adc_read(packet); break;
			case 3: receive_hardware_io_port_config_direction(packet); break;
			case 4: receive_hardware_io_port_config_function(packet); break;
			case 5: receive_hardware_io_port_config_pull(packet); break;
			case 6: receive_hardware_io_port_write(packet); break;
			case 7: receive_hardware_io_port_read(packet); break;
			case 8: receive_hardware_spi_config(packet); break;
			case 9: receive_hardware_spi_transfer(packet); break;
			case 10: receive_hardware_i2c_read(packet); break;
			case 11: receive_hardware_i2c_write(packet); break;
			case 12: receive_hardware_set_txpower(packet); break;
			default: break;
		}
	}
	private void receive_hardware_evt(BGAPIPacket packet) {
		switch(packet.commandID) {
			case 0: receive_hardware_io_port_status(packet); break;
			case 1: receive_hardware_soft_timer(packet); break;
			case 2: receive_hardware_adc_result(packet); break;
			default: break;
		}
	}
	private void receive_test_cmd(BGAPIPacket packet) {
		switch(packet.commandID) {
			case 0: receive_test_phy_tx(packet); break;
			case 1: receive_test_phy_rx(packet); break;
			case 2: receive_test_phy_end(packet); break;
			case 3: receive_test_phy_reset(packet); break;
			case 4: receive_test_get_channel_map(packet); break;
			default: break;
		}
	}
	private void receive_test_evt(BGAPIPacket packet) {
		switch(packet.commandID) {
			default: break;
		}
	}



	// Callbacks for class system (index = 0)
	private void receive_system_reset(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		for(BGAPIListener l : listeners) l.receive_system_reset();
	}
	private void receive_system_hello(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		for(BGAPIListener l : listeners) l.receive_system_hello();
	}
	private void receive_system_address_get(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		BDAddr address = r.r_bd_addr();
		for(BGAPIListener l : listeners) l.receive_system_address_get(address);
	}
	private void receive_system_reg_write(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_system_reg_write(result);
	}
	private void receive_system_reg_read(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int address = r.r_uint16();
		int value = r.r_uint8();
		for(BGAPIListener l : listeners) l.receive_system_reg_read(address, value);
	}
	private void receive_system_get_counters(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int txok = r.r_uint8();
		int txretry = r.r_uint8();
		int rxok = r.r_uint8();
		int rxfail = r.r_uint8();
		for(BGAPIListener l : listeners) l.receive_system_get_counters(txok, txretry, rxok, rxfail);
	}
	private void receive_system_get_connections(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int maxconn = r.r_uint8();
		for(BGAPIListener l : listeners) l.receive_system_get_connections(maxconn);
	}
	private void receive_system_read_memory(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int address = r.r_uint32();
		byte[] data = r.r_uint8array();
		for(BGAPIListener l : listeners) l.receive_system_read_memory(address, data);
	}
	private void receive_system_get_info(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int major = r.r_uint16();
		int minor = r.r_uint16();
		int patch = r.r_uint16();
		int build = r.r_uint16();
		int ll_version = r.r_uint16();
		int protocol_version = r.r_uint8();
		int hw = r.r_uint8();
		for(BGAPIListener l : listeners) l.receive_system_get_info(major, minor, patch, build, ll_version, protocol_version, hw);
	}
	private void receive_system_endpoint_tx(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		for(BGAPIListener l : listeners) l.receive_system_endpoint_tx();
	}
	private void receive_system_whitelist_append(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_system_whitelist_append(result);
	}
	private void receive_system_whitelist_remove(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_system_whitelist_remove(result);
	}
	private void receive_system_whitelist_clear(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		for(BGAPIListener l : listeners) l.receive_system_whitelist_clear();
	}
	private void receive_system_boot(BGAPIPacket __packet) {
		BGAPIPacketReader r = __packet.getPayloadReader();
		int major = r.r_uint16();
		int minor = r.r_uint16();
		int patch = r.r_uint16();
		int build = r.r_uint16();
		int ll_version = r.r_uint16();
		int protocol_version = r.r_uint8();
		int hw = r.r_uint8();
		for(BGAPIListener l : listeners) l.receive_system_boot(major, minor, patch, build, ll_version, protocol_version, hw);
	}
	private void receive_system_debug(BGAPIPacket __packet) {
		BGAPIPacketReader r = __packet.getPayloadReader();
		byte[] data = r.r_uint8array();
		for(BGAPIListener l : listeners) l.receive_system_debug(data);
	}
	private void receive_system_endpoint_rx(BGAPIPacket __packet) {
		BGAPIPacketReader r = __packet.getPayloadReader();
		int endpoint = r.r_uint8();
		byte[] data = r.r_uint8array();
		for(BGAPIListener l : listeners) l.receive_system_endpoint_rx(endpoint, data);
	}

	// Callbacks for class flash (index = 1)
	private void receive_flash_ps_defrag(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		for(BGAPIListener l : listeners) l.receive_flash_ps_defrag();
	}
	private void receive_flash_ps_dump(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		for(BGAPIListener l : listeners) l.receive_flash_ps_dump();
	}
	private void receive_flash_ps_erase_all(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		for(BGAPIListener l : listeners) l.receive_flash_ps_erase_all();
	}
	private void receive_flash_ps_save(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_flash_ps_save(result);
	}
	private void receive_flash_ps_load(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int result = r.r_uint16();
		byte[] value = r.r_uint8array();
		for(BGAPIListener l : listeners) l.receive_flash_ps_load(result, value);
	}
	private void receive_flash_ps_erase(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		for(BGAPIListener l : listeners) l.receive_flash_ps_erase();
	}
	private void receive_flash_erase_page(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_flash_erase_page(result);
	}
	private void receive_flash_write_words(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		for(BGAPIListener l : listeners) l.receive_flash_write_words();
	}
	private void receive_flash_ps_key(BGAPIPacket __packet) {
		BGAPIPacketReader r = __packet.getPayloadReader();
		int key = r.r_uint16();
		byte[] value = r.r_uint8array();
		for(BGAPIListener l : listeners) l.receive_flash_ps_key(key, value);
	}

	// Callbacks for class attributes (index = 2)
	private void receive_attributes_write(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_attributes_write(result);
	}
	private void receive_attributes_read(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int handle = r.r_uint16();
		int offset = r.r_uint16();
		int result = r.r_uint16();
		byte[] value = r.r_uint8array();
		for(BGAPIListener l : listeners) l.receive_attributes_read(handle, offset, result, value);
	}
	private void receive_attributes_read_type(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int handle = r.r_uint16();
		int result = r.r_uint16();
		byte[] value = r.r_uint8array();
		for(BGAPIListener l : listeners) l.receive_attributes_read_type(handle, result, value);
	}
	private void receive_attributes_user_response(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		for(BGAPIListener l : listeners) l.receive_attributes_user_response();
	}
	private void receive_attributes_value(BGAPIPacket __packet) {
		BGAPIPacketReader r = __packet.getPayloadReader();
		int connection = r.r_uint8();
		int reason = r.r_uint8();
		int handle = r.r_uint16();
		int offset = r.r_uint16();
		byte[] value = r.r_uint8array();
		for(BGAPIListener l : listeners) l.receive_attributes_value(connection, reason, handle, offset, value);
	}
	private void receive_attributes_user_request(BGAPIPacket __packet) {
		BGAPIPacketReader r = __packet.getPayloadReader();
		int connection = r.r_uint8();
		int handle = r.r_uint16();
		int offset = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_attributes_user_request(connection, handle, offset);
	}

	// Callbacks for class connection (index = 3)
	private void receive_connection_disconnect(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int connection = r.r_uint8();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_connection_disconnect(connection, result);
	}
	private void receive_connection_get_rssi(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int connection = r.r_uint8();
		int rssi = r.r_int8();
		for(BGAPIListener l : listeners) l.receive_connection_get_rssi(connection, rssi);
	}
	private void receive_connection_update(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int connection = r.r_uint8();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_connection_update(connection, result);
	}
	private void receive_connection_version_update(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int connection = r.r_uint8();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_connection_version_update(connection, result);
	}
	private void receive_connection_channel_map_get(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int connection = r.r_uint8();
		byte[] map = r.r_uint8array();
		for(BGAPIListener l : listeners) l.receive_connection_channel_map_get(connection, map);
	}
	private void receive_connection_channel_map_set(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int connection = r.r_uint8();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_connection_channel_map_set(connection, result);
	}
	private void receive_connection_features_get(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int connection = r.r_uint8();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_connection_features_get(connection, result);
	}
	private void receive_connection_get_status(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int connection = r.r_uint8();
		for(BGAPIListener l : listeners) l.receive_connection_get_status(connection);
	}
	private void receive_connection_raw_tx(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int connection = r.r_uint8();
		for(BGAPIListener l : listeners) l.receive_connection_raw_tx(connection);
	}
	private void receive_connection_status(BGAPIPacket __packet) {
		BGAPIPacketReader r = __packet.getPayloadReader();
		int connection = r.r_uint8();
		int flags = r.r_uint8();
		BDAddr address = r.r_bd_addr();
		int address_type = r.r_uint8();
		int conn_interval = r.r_uint16();
		int timeout = r.r_uint16();
		int latency = r.r_uint16();
		int bonding = r.r_uint8();
		for(BGAPIListener l : listeners) l.receive_connection_status(connection, flags, address, address_type, conn_interval, timeout, latency, bonding);
	}
	private void receive_connection_version_ind(BGAPIPacket __packet) {
		BGAPIPacketReader r = __packet.getPayloadReader();
		int connection = r.r_uint8();
		int vers_nr = r.r_uint8();
		int comp_id = r.r_uint16();
		int sub_vers_nr = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_connection_version_ind(connection, vers_nr, comp_id, sub_vers_nr);
	}
	private void receive_connection_feature_ind(BGAPIPacket __packet) {
		BGAPIPacketReader r = __packet.getPayloadReader();
		int connection = r.r_uint8();
		byte[] features = r.r_uint8array();
		for(BGAPIListener l : listeners) l.receive_connection_feature_ind(connection, features);
	}
	private void receive_connection_raw_rx(BGAPIPacket __packet) {
		BGAPIPacketReader r = __packet.getPayloadReader();
		int connection = r.r_uint8();
		byte[] data = r.r_uint8array();
		for(BGAPIListener l : listeners) l.receive_connection_raw_rx(connection, data);
	}
	private void receive_connection_disconnected(BGAPIPacket __packet) {
		BGAPIPacketReader r = __packet.getPayloadReader();
		int connection = r.r_uint8();
		int reason = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_connection_disconnected(connection, reason);
	}

	// Callbacks for class attclient (index = 4)
	private void receive_attclient_find_by_type_value(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int connection = r.r_uint8();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_attclient_find_by_type_value(connection, result);
	}
	private void receive_attclient_read_by_group_type(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int connection = r.r_uint8();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_attclient_read_by_group_type(connection, result);
	}
	private void receive_attclient_read_by_type(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int connection = r.r_uint8();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_attclient_read_by_type(connection, result);
	}
	private void receive_attclient_find_information(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int connection = r.r_uint8();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_attclient_find_information(connection, result);
	}
	private void receive_attclient_read_by_handle(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int connection = r.r_uint8();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_attclient_read_by_handle(connection, result);
	}
	private void receive_attclient_attribute_write(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int connection = r.r_uint8();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_attclient_attribute_write(connection, result);
	}
	private void receive_attclient_write_command(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int connection = r.r_uint8();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_attclient_write_command(connection, result);
	}
	private void receive_attclient_reserved(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		for(BGAPIListener l : listeners) l.receive_attclient_reserved();
	}
	private void receive_attclient_read_long(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int connection = r.r_uint8();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_attclient_read_long(connection, result);
	}
	private void receive_attclient_prepare_write(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int connection = r.r_uint8();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_attclient_prepare_write(connection, result);
	}
	private void receive_attclient_execute_write(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int connection = r.r_uint8();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_attclient_execute_write(connection, result);
	}
	private void receive_attclient_read_multiple(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int connection = r.r_uint8();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_attclient_read_multiple(connection, result);
	}
	private void receive_attclient_indicated(BGAPIPacket __packet) {
		BGAPIPacketReader r = __packet.getPayloadReader();
		int connection = r.r_uint8();
		int attrhandle = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_attclient_indicated(connection, attrhandle);
	}
	private void receive_attclient_procedure_completed(BGAPIPacket __packet) {
		BGAPIPacketReader r = __packet.getPayloadReader();
		int connection = r.r_uint8();
		int result = r.r_uint16();
		int chrhandle = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_attclient_procedure_completed(connection, result, chrhandle);
	}
	private void receive_attclient_group_found(BGAPIPacket __packet) {
		BGAPIPacketReader r = __packet.getPayloadReader();
		int connection = r.r_uint8();
		int start = r.r_uint16();
		int end = r.r_uint16();
		byte[] uuid = r.r_uint8array();
		for(BGAPIListener l : listeners) l.receive_attclient_group_found(connection, start, end, uuid);
	}
	private void receive_attclient_attribute_found(BGAPIPacket __packet) {
		BGAPIPacketReader r = __packet.getPayloadReader();
		int connection = r.r_uint8();
		int chrdecl = r.r_uint16();
		int value = r.r_uint16();
		int properties = r.r_uint8();
		byte[] uuid = r.r_uint8array();
		for(BGAPIListener l : listeners) l.receive_attclient_attribute_found(connection, chrdecl, value, properties, uuid);
	}
	private void receive_attclient_find_information_found(BGAPIPacket __packet) {
		BGAPIPacketReader r = __packet.getPayloadReader();
		int connection = r.r_uint8();
		int chrhandle = r.r_uint16();
		byte[] uuid = r.r_uint8array();
		for(BGAPIListener l : listeners) l.receive_attclient_find_information_found(connection, chrhandle, uuid);
	}
	private void receive_attclient_attribute_value(BGAPIPacket __packet) {
		BGAPIPacketReader r = __packet.getPayloadReader();
		int connection = r.r_uint8();
		int atthandle = r.r_uint16();
		int type = r.r_uint8();
		byte[] value = r.r_uint8array();
		for(BGAPIListener l : listeners) l.receive_attclient_attribute_value(connection, atthandle, type, value);
	}
	private void receive_attclient_read_multiple_response(BGAPIPacket __packet) {
		BGAPIPacketReader r = __packet.getPayloadReader();
		int connection = r.r_uint8();
		byte[] handles = r.r_uint8array();
		for(BGAPIListener l : listeners) l.receive_attclient_read_multiple_response(connection, handles);
	}

	// Callbacks for class sm (index = 5)
	private void receive_sm_encrypt_start(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int handle = r.r_uint8();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_sm_encrypt_start(handle, result);
	}
	private void receive_sm_set_bondable_mode(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		for(BGAPIListener l : listeners) l.receive_sm_set_bondable_mode();
	}
	private void receive_sm_delete_bonding(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_sm_delete_bonding(result);
	}
	private void receive_sm_set_parameters(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		for(BGAPIListener l : listeners) l.receive_sm_set_parameters();
	}
	private void receive_sm_passkey_entry(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_sm_passkey_entry(result);
	}
	private void receive_sm_get_bonds(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int bonds = r.r_uint8();
		for(BGAPIListener l : listeners) l.receive_sm_get_bonds(bonds);
	}
	private void receive_sm_set_oob_data(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		for(BGAPIListener l : listeners) l.receive_sm_set_oob_data();
	}
	private void receive_sm_smp_data(BGAPIPacket __packet) {
		BGAPIPacketReader r = __packet.getPayloadReader();
		int handle = r.r_uint8();
		int packet = r.r_uint8();
		byte[] data = r.r_uint8array();
		for(BGAPIListener l : listeners) l.receive_sm_smp_data(handle, packet, data);
	}
	private void receive_sm_bonding_fail(BGAPIPacket __packet) {
		BGAPIPacketReader r = __packet.getPayloadReader();
		int handle = r.r_uint8();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_sm_bonding_fail(handle, result);
	}
	private void receive_sm_passkey_display(BGAPIPacket __packet) {
		BGAPIPacketReader r = __packet.getPayloadReader();
		int handle = r.r_uint8();
		int passkey = r.r_uint32();
		for(BGAPIListener l : listeners) l.receive_sm_passkey_display(handle, passkey);
	}
	private void receive_sm_passkey_request(BGAPIPacket __packet) {
		BGAPIPacketReader r = __packet.getPayloadReader();
		int handle = r.r_uint8();
		for(BGAPIListener l : listeners) l.receive_sm_passkey_request(handle);
	}
	private void receive_sm_bond_status(BGAPIPacket __packet) {
		BGAPIPacketReader r = __packet.getPayloadReader();
		int bond = r.r_uint8();
		int keysize = r.r_uint8();
		int mitm = r.r_uint8();
		int keys = r.r_uint8();
		for(BGAPIListener l : listeners) l.receive_sm_bond_status(bond, keysize, mitm, keys);
	}

	// Callbacks for class gap (index = 6)
	private void receive_gap_set_privacy_flags(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		for(BGAPIListener l : listeners) l.receive_gap_set_privacy_flags();
	}
	private void receive_gap_set_mode(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_gap_set_mode(result);
	}
	private void receive_gap_discover(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_gap_discover(result);
	}
	private void receive_gap_connect_direct(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int result = r.r_uint16();
		int connection_handle = r.r_uint8();
		for(BGAPIListener l : listeners) l.receive_gap_connect_direct(result, connection_handle);
	}
	private void receive_gap_end_procedure(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_gap_end_procedure(result);
	}
	private void receive_gap_connect_selective(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int result = r.r_uint16();
		int connection_handle = r.r_uint8();
		for(BGAPIListener l : listeners) l.receive_gap_connect_selective(result, connection_handle);
	}
	private void receive_gap_set_filtering(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_gap_set_filtering(result);
	}
	private void receive_gap_set_scan_parameters(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_gap_set_scan_parameters(result);
	}
	private void receive_gap_set_adv_parameters(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_gap_set_adv_parameters(result);
	}
	private void receive_gap_set_adv_data(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_gap_set_adv_data(result);
	}
	private void receive_gap_set_directed_connectable_mode(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_gap_set_directed_connectable_mode(result);
	}
	private void receive_gap_scan_response(BGAPIPacket __packet) {
		BGAPIPacketReader r = __packet.getPayloadReader();
		int rssi = r.r_int8();
		int packet_type = r.r_uint8();
		BDAddr sender = r.r_bd_addr();
		int address_type = r.r_uint8();
		int bond = r.r_uint8();
		byte[] data = r.r_uint8array();
		for(BGAPIListener l : listeners) l.receive_gap_scan_response(rssi, packet_type, sender, address_type, bond, data);
	}
	private void receive_gap_mode_changed(BGAPIPacket __packet) {
		BGAPIPacketReader r = __packet.getPayloadReader();
		int discover = r.r_uint8();
		int connect = r.r_uint8();
		for(BGAPIListener l : listeners) l.receive_gap_mode_changed(discover, connect);
	}

	// Callbacks for class hardware (index = 7)
	private void receive_hardware_io_port_config_irq(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_hardware_io_port_config_irq(result);
	}
	private void receive_hardware_set_soft_timer(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_hardware_set_soft_timer(result);
	}
	private void receive_hardware_adc_read(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_hardware_adc_read(result);
	}
	private void receive_hardware_io_port_config_direction(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_hardware_io_port_config_direction(result);
	}
	private void receive_hardware_io_port_config_function(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_hardware_io_port_config_function(result);
	}
	private void receive_hardware_io_port_config_pull(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_hardware_io_port_config_pull(result);
	}
	private void receive_hardware_io_port_write(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_hardware_io_port_write(result);
	}
	private void receive_hardware_io_port_read(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int result = r.r_uint16();
		int port = r.r_uint8();
		int data = r.r_uint8();
		for(BGAPIListener l : listeners) l.receive_hardware_io_port_read(result, port, data);
	}
	private void receive_hardware_spi_config(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int result = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_hardware_spi_config(result);
	}
	private void receive_hardware_spi_transfer(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int result = r.r_uint16();
		int channel = r.r_uint8();
		byte[] data = r.r_uint8array();
		for(BGAPIListener l : listeners) l.receive_hardware_spi_transfer(result, channel, data);
	}
	private void receive_hardware_i2c_read(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int result = r.r_uint16();
		byte[] data = r.r_uint8array();
		for(BGAPIListener l : listeners) l.receive_hardware_i2c_read(result, data);
	}
	private void receive_hardware_i2c_write(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int written = r.r_uint8();
		for(BGAPIListener l : listeners) l.receive_hardware_i2c_write(written);
	}
	private void receive_hardware_set_txpower(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		for(BGAPIListener l : listeners) l.receive_hardware_set_txpower();
	}
	private void receive_hardware_io_port_status(BGAPIPacket __packet) {
		BGAPIPacketReader r = __packet.getPayloadReader();
		int timestamp = r.r_uint32();
		int port = r.r_uint8();
		int irq = r.r_uint8();
		int state = r.r_uint8();
		for(BGAPIListener l : listeners) l.receive_hardware_io_port_status(timestamp, port, irq, state);
	}
	private void receive_hardware_soft_timer(BGAPIPacket __packet) {
		BGAPIPacketReader r = __packet.getPayloadReader();
		int handle = r.r_uint8();
		for(BGAPIListener l : listeners) l.receive_hardware_soft_timer(handle);
	}
	private void receive_hardware_adc_result(BGAPIPacket __packet) {
		BGAPIPacketReader r = __packet.getPayloadReader();
		int input = r.r_uint8();
		int value = r.r_int16();
		for(BGAPIListener l : listeners) l.receive_hardware_adc_result(input, value);
	}

	// Callbacks for class test (index = 8)
	private void receive_test_phy_tx(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		for(BGAPIListener l : listeners) l.receive_test_phy_tx();
	}
	private void receive_test_phy_rx(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		for(BGAPIListener l : listeners) l.receive_test_phy_rx();
	}
	private void receive_test_phy_end(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		int counter = r.r_uint16();
		for(BGAPIListener l : listeners) l.receive_test_phy_end(counter);
	}
	private void receive_test_phy_reset(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		for(BGAPIListener l : listeners) l.receive_test_phy_reset();
	}
	private void receive_test_get_channel_map(BGAPIPacket packet) {
		BGAPIPacketReader r = packet.getPayloadReader();
		byte[] channel_map = r.r_uint8array();
		for(BGAPIListener l : listeners) l.receive_test_get_channel_map(channel_map);
	}



	// Callbacks for class system (index = 0)
	public void send_system_reset(int boot_in_dfu) {
		BGAPIPacket p = new BGAPIPacket(0, 0, 0);
		p.w_uint8(boot_in_dfu);
		bgapi.sendPacket(p);
	}
	public void send_system_hello() {
		BGAPIPacket p = new BGAPIPacket(0, 0, 1);
		bgapi.sendPacket(p);
	}
	public void send_system_address_get() {
		BGAPIPacket p = new BGAPIPacket(0, 0, 2);
		bgapi.sendPacket(p);
	}
	public void send_system_reg_write(int address, int value) {
		BGAPIPacket p = new BGAPIPacket(0, 0, 3);
		p.w_uint16(address);
		p.w_uint8(value);
		bgapi.sendPacket(p);
	}
	public void send_system_reg_read(int address) {
		BGAPIPacket p = new BGAPIPacket(0, 0, 4);
		p.w_uint16(address);
		bgapi.sendPacket(p);
	}
	public void send_system_get_counters() {
		BGAPIPacket p = new BGAPIPacket(0, 0, 5);
		bgapi.sendPacket(p);
	}
	public void send_system_get_connections() {
		BGAPIPacket p = new BGAPIPacket(0, 0, 6);
		bgapi.sendPacket(p);
	}
	public void send_system_read_memory(int address, int length) {
		BGAPIPacket p = new BGAPIPacket(0, 0, 7);
		p.w_uint32(address);
		p.w_uint8(length);
		bgapi.sendPacket(p);
	}
	public void send_system_get_info() {
		BGAPIPacket p = new BGAPIPacket(0, 0, 8);
		bgapi.sendPacket(p);
	}
	public void send_system_endpoint_tx(int endpoint, byte[] data) {
		BGAPIPacket p = new BGAPIPacket(0, 0, 9);
		p.w_uint8(endpoint);
		p.w_uint8array(data);
		bgapi.sendPacket(p);
	}
	public void send_system_whitelist_append(BDAddr address, int address_type) {
		BGAPIPacket p = new BGAPIPacket(0, 0, 10);
		p.w_bd_addr(address);
		p.w_uint8(address_type);
		bgapi.sendPacket(p);
	}
	public void send_system_whitelist_remove(BDAddr address, int address_type) {
		BGAPIPacket p = new BGAPIPacket(0, 0, 11);
		p.w_bd_addr(address);
		p.w_uint8(address_type);
		bgapi.sendPacket(p);
	}
	public void send_system_whitelist_clear() {
		BGAPIPacket p = new BGAPIPacket(0, 0, 12);
		bgapi.sendPacket(p);
	}

	// Callbacks for class flash (index = 1)
	public void send_flash_ps_defrag() {
		BGAPIPacket p = new BGAPIPacket(0, 1, 0);
		bgapi.sendPacket(p);
	}
	public void send_flash_ps_dump() {
		BGAPIPacket p = new BGAPIPacket(0, 1, 1);
		bgapi.sendPacket(p);
	}
	public void send_flash_ps_erase_all() {
		BGAPIPacket p = new BGAPIPacket(0, 1, 2);
		bgapi.sendPacket(p);
	}
	public void send_flash_ps_save(int key, byte[] value) {
		BGAPIPacket p = new BGAPIPacket(0, 1, 3);
		p.w_uint16(key);
		p.w_uint8array(value);
		bgapi.sendPacket(p);
	}
	public void send_flash_ps_load(int key) {
		BGAPIPacket p = new BGAPIPacket(0, 1, 4);
		p.w_uint16(key);
		bgapi.sendPacket(p);
	}
	public void send_flash_ps_erase(int key) {
		BGAPIPacket p = new BGAPIPacket(0, 1, 5);
		p.w_uint16(key);
		bgapi.sendPacket(p);
	}
	public void send_flash_erase_page(int page) {
		BGAPIPacket p = new BGAPIPacket(0, 1, 6);
		p.w_uint8(page);
		bgapi.sendPacket(p);
	}
	public void send_flash_write_words(int address, byte[] words) {
		BGAPIPacket p = new BGAPIPacket(0, 1, 7);
		p.w_uint16(address);
		p.w_uint8array(words);
		bgapi.sendPacket(p);
	}

	// Callbacks for class attributes (index = 2)
	public void send_attributes_write(int handle, int offset, byte[] value) {
		BGAPIPacket p = new BGAPIPacket(0, 2, 0);
		p.w_uint16(handle);
		p.w_uint8(offset);
		p.w_uint8array(value);
		bgapi.sendPacket(p);
	}
	public void send_attributes_read(int handle, int offset) {
		BGAPIPacket p = new BGAPIPacket(0, 2, 1);
		p.w_uint16(handle);
		p.w_uint16(offset);
		bgapi.sendPacket(p);
	}
	public void send_attributes_read_type(int handle) {
		BGAPIPacket p = new BGAPIPacket(0, 2, 2);
		p.w_uint16(handle);
		bgapi.sendPacket(p);
	}
	public void send_attributes_user_response(int connection, int att_error, byte[] value) {
		BGAPIPacket p = new BGAPIPacket(0, 2, 3);
		p.w_uint8(connection);
		p.w_uint8(att_error);
		p.w_uint8array(value);
		bgapi.sendPacket(p);
	}

	// Callbacks for class connection (index = 3)
	public void send_connection_disconnect(int connection) {
		BGAPIPacket p = new BGAPIPacket(0, 3, 0);
		p.w_uint8(connection);
		bgapi.sendPacket(p);
	}
	public void send_connection_get_rssi(int connection) {
		BGAPIPacket p = new BGAPIPacket(0, 3, 1);
		p.w_uint8(connection);
		bgapi.sendPacket(p);
	}
	public void send_connection_update(int connection, int interval_min, int interval_max, int latency, int timeout) {
		BGAPIPacket p = new BGAPIPacket(0, 3, 2);
		p.w_uint8(connection);
		p.w_uint16(interval_min);
		p.w_uint16(interval_max);
		p.w_uint16(latency);
		p.w_uint16(timeout);
		bgapi.sendPacket(p);
	}
	public void send_connection_version_update(int connection) {
		BGAPIPacket p = new BGAPIPacket(0, 3, 3);
		p.w_uint8(connection);
		bgapi.sendPacket(p);
	}
	public void send_connection_channel_map_get(int connection) {
		BGAPIPacket p = new BGAPIPacket(0, 3, 4);
		p.w_uint8(connection);
		bgapi.sendPacket(p);
	}
	public void send_connection_channel_map_set(int connection, byte[] map) {
		BGAPIPacket p = new BGAPIPacket(0, 3, 5);
		p.w_uint8(connection);
		p.w_uint8array(map);
		bgapi.sendPacket(p);
	}
	public void send_connection_features_get(int connection) {
		BGAPIPacket p = new BGAPIPacket(0, 3, 6);
		p.w_uint8(connection);
		bgapi.sendPacket(p);
	}
	public void send_connection_get_status(int connection) {
		BGAPIPacket p = new BGAPIPacket(0, 3, 7);
		p.w_uint8(connection);
		bgapi.sendPacket(p);
	}
	public void send_connection_raw_tx(int connection, byte[] data) {
		BGAPIPacket p = new BGAPIPacket(0, 3, 8);
		p.w_uint8(connection);
		p.w_uint8array(data);
		bgapi.sendPacket(p);
	}

	// Callbacks for class attclient (index = 4)
	public void send_attclient_find_by_type_value(int connection, int start, int end, int uuid, byte[] value) {
		BGAPIPacket p = new BGAPIPacket(0, 4, 0);
		p.w_uint8(connection);
		p.w_uint16(start);
		p.w_uint16(end);
		p.w_uint16(uuid);
		p.w_uint8array(value);
		bgapi.sendPacket(p);
	}
	public void send_attclient_read_by_group_type(int connection, int start, int end, byte[] uuid) {
		BGAPIPacket p = new BGAPIPacket(0, 4, 1);
		p.w_uint8(connection);
		p.w_uint16(start);
		p.w_uint16(end);
		p.w_uint8array(uuid);
		bgapi.sendPacket(p);
	}
	public void send_attclient_read_by_type(int connection, int start, int end, byte[] uuid) {
		BGAPIPacket p = new BGAPIPacket(0, 4, 2);
		p.w_uint8(connection);
		p.w_uint16(start);
		p.w_uint16(end);
		p.w_uint8array(uuid);
		bgapi.sendPacket(p);
	}
	public void send_attclient_find_information(int connection, int start, int end) {
		BGAPIPacket p = new BGAPIPacket(0, 4, 3);
		p.w_uint8(connection);
		p.w_uint16(start);
		p.w_uint16(end);
		bgapi.sendPacket(p);
	}
	public void send_attclient_read_by_handle(int connection, int chrhandle) {
		BGAPIPacket p = new BGAPIPacket(0, 4, 4);
		p.w_uint8(connection);
		p.w_uint16(chrhandle);
		bgapi.sendPacket(p);
	}
	public void send_attclient_attribute_write(int connection, int atthandle, byte[] data) {
		BGAPIPacket p = new BGAPIPacket(0, 4, 5);
		p.w_uint8(connection);
		p.w_uint16(atthandle);
		p.w_uint8array(data);
		bgapi.sendPacket(p);
	}
	public void send_attclient_write_command(int connection, int atthandle, byte[] data) {
		BGAPIPacket p = new BGAPIPacket(0, 4, 6);
		p.w_uint8(connection);
		p.w_uint16(atthandle);
		p.w_uint8array(data);
		bgapi.sendPacket(p);
	}
	public void send_attclient_reserved() {
		BGAPIPacket p = new BGAPIPacket(0, 4, 7);
		bgapi.sendPacket(p);
	}
	public void send_attclient_read_long(int connection, int chrhandle) {
		BGAPIPacket p = new BGAPIPacket(0, 4, 8);
		p.w_uint8(connection);
		p.w_uint16(chrhandle);
		bgapi.sendPacket(p);
	}
	public void send_attclient_prepare_write(int connection, int atthandle, int offset, byte[] data) {
		BGAPIPacket p = new BGAPIPacket(0, 4, 9);
		p.w_uint8(connection);
		p.w_uint16(atthandle);
		p.w_uint16(offset);
		p.w_uint8array(data);
		bgapi.sendPacket(p);
	}
	public void send_attclient_execute_write(int connection, int commit) {
		BGAPIPacket p = new BGAPIPacket(0, 4, 10);
		p.w_uint8(connection);
		p.w_uint8(commit);
		bgapi.sendPacket(p);
	}
	public void send_attclient_read_multiple(int connection, byte[] handles) {
		BGAPIPacket p = new BGAPIPacket(0, 4, 11);
		p.w_uint8(connection);
		p.w_uint8array(handles);
		bgapi.sendPacket(p);
	}

	// Callbacks for class sm (index = 5)
	public void send_sm_encrypt_start(int handle, int bonding) {
		BGAPIPacket p = new BGAPIPacket(0, 5, 0);
		p.w_uint8(handle);
		p.w_uint8(bonding);
		bgapi.sendPacket(p);
	}
	public void send_sm_set_bondable_mode(int bondable) {
		BGAPIPacket p = new BGAPIPacket(0, 5, 1);
		p.w_uint8(bondable);
		bgapi.sendPacket(p);
	}
	public void send_sm_delete_bonding(int handle) {
		BGAPIPacket p = new BGAPIPacket(0, 5, 2);
		p.w_uint8(handle);
		bgapi.sendPacket(p);
	}
	public void send_sm_set_parameters(int mitm, int min_key_size, int io_capabilities) {
		BGAPIPacket p = new BGAPIPacket(0, 5, 3);
		p.w_uint8(mitm);
		p.w_uint8(min_key_size);
		p.w_uint8(io_capabilities);
		bgapi.sendPacket(p);
	}
	public void send_sm_passkey_entry(int handle, int passkey) {
		BGAPIPacket p = new BGAPIPacket(0, 5, 4);
		p.w_uint8(handle);
		p.w_uint32(passkey);
		bgapi.sendPacket(p);
	}
	public void send_sm_get_bonds() {
		BGAPIPacket p = new BGAPIPacket(0, 5, 5);
		bgapi.sendPacket(p);
	}
	public void send_sm_set_oob_data(byte[] oob) {
		BGAPIPacket p = new BGAPIPacket(0, 5, 6);
		p.w_uint8array(oob);
		bgapi.sendPacket(p);
	}

	// Callbacks for class gap (index = 6)
	public void send_gap_set_privacy_flags(int peripheral_privacy, int central_privacy) {
		BGAPIPacket p = new BGAPIPacket(0, 6, 0);
		p.w_uint8(peripheral_privacy);
		p.w_uint8(central_privacy);
		bgapi.sendPacket(p);
	}
	public void send_gap_set_mode(int discover, int connect) {
		BGAPIPacket p = new BGAPIPacket(0, 6, 1);
		p.w_uint8(discover);
		p.w_uint8(connect);
		bgapi.sendPacket(p);
	}
	public void send_gap_discover(int mode) {
		BGAPIPacket p = new BGAPIPacket(0, 6, 2);
		p.w_uint8(mode);
		bgapi.sendPacket(p);
	}
	public void send_gap_connect_direct(BDAddr address, int addr_type, int conn_interval_min, int conn_interval_max, int timeout, int latency) {
		BGAPIPacket p = new BGAPIPacket(0, 6, 3);
		p.w_bd_addr(address);
		p.w_uint8(addr_type);
		p.w_uint16(conn_interval_min);
		p.w_uint16(conn_interval_max);
		p.w_uint16(timeout);
		p.w_uint16(latency);
		bgapi.sendPacket(p);
	}
	public void send_gap_end_procedure() {
		BGAPIPacket p = new BGAPIPacket(0, 6, 4);
		bgapi.sendPacket(p);
	}
	public void send_gap_connect_selective(int conn_interval_min, int conn_interval_max, int timeout, int latency) {
		BGAPIPacket p = new BGAPIPacket(0, 6, 5);
		p.w_uint16(conn_interval_min);
		p.w_uint16(conn_interval_max);
		p.w_uint16(timeout);
		p.w_uint16(latency);
		bgapi.sendPacket(p);
	}
	public void send_gap_set_filtering(int scan_policy, int adv_policy, int scan_duplicate_filtering) {
		BGAPIPacket p = new BGAPIPacket(0, 6, 6);
		p.w_uint8(scan_policy);
		p.w_uint8(adv_policy);
		p.w_uint8(scan_duplicate_filtering);
		bgapi.sendPacket(p);
	}
	public void send_gap_set_scan_parameters(int scan_interval, int scan_window, int active) {
		BGAPIPacket p = new BGAPIPacket(0, 6, 7);
		p.w_uint16(scan_interval);
		p.w_uint16(scan_window);
		p.w_uint8(active);
		bgapi.sendPacket(p);
	}
	public void send_gap_set_adv_parameters(int adv_interval_min, int adv_interval_max, int adv_channels) {
		BGAPIPacket p = new BGAPIPacket(0, 6, 8);
		p.w_uint16(adv_interval_min);
		p.w_uint16(adv_interval_max);
		p.w_uint8(adv_channels);
		bgapi.sendPacket(p);
	}
	public void send_gap_set_adv_data(int set_scanrsp, byte[] adv_data) {
		BGAPIPacket p = new BGAPIPacket(0, 6, 9);
		p.w_uint8(set_scanrsp);
		p.w_uint8array(adv_data);
		bgapi.sendPacket(p);
	}
	public void send_gap_set_directed_connectable_mode(BDAddr address, int addr_type) {
		BGAPIPacket p = new BGAPIPacket(0, 6, 10);
		p.w_bd_addr(address);
		p.w_uint8(addr_type);
		bgapi.sendPacket(p);
	}

	// Callbacks for class hardware (index = 7)
	public void send_hardware_io_port_config_irq(int port, int enable_bits, int falling_edge) {
		BGAPIPacket p = new BGAPIPacket(0, 7, 0);
		p.w_uint8(port);
		p.w_uint8(enable_bits);
		p.w_uint8(falling_edge);
		bgapi.sendPacket(p);
	}
	public void send_hardware_set_soft_timer(int time, int handle, int single_shot) {
		BGAPIPacket p = new BGAPIPacket(0, 7, 1);
		p.w_uint32(time);
		p.w_uint8(handle);
		p.w_uint8(single_shot);
		bgapi.sendPacket(p);
	}
	public void send_hardware_adc_read(int input, int decimation, int reference_selection) {
		BGAPIPacket p = new BGAPIPacket(0, 7, 2);
		p.w_uint8(input);
		p.w_uint8(decimation);
		p.w_uint8(reference_selection);
		bgapi.sendPacket(p);
	}
	public void send_hardware_io_port_config_direction(int port, int direction) {
		BGAPIPacket p = new BGAPIPacket(0, 7, 3);
		p.w_uint8(port);
		p.w_uint8(direction);
		bgapi.sendPacket(p);
	}
	public void send_hardware_io_port_config_function(int port, int function) {
		BGAPIPacket p = new BGAPIPacket(0, 7, 4);
		p.w_uint8(port);
		p.w_uint8(function);
		bgapi.sendPacket(p);
	}
	public void send_hardware_io_port_config_pull(int port, int tristate_mask, int pull_up) {
		BGAPIPacket p = new BGAPIPacket(0, 7, 5);
		p.w_uint8(port);
		p.w_uint8(tristate_mask);
		p.w_uint8(pull_up);
		bgapi.sendPacket(p);
	}
	public void send_hardware_io_port_write(int port, int mask, int data) {
		BGAPIPacket p = new BGAPIPacket(0, 7, 6);
		p.w_uint8(port);
		p.w_uint8(mask);
		p.w_uint8(data);
		bgapi.sendPacket(p);
	}
	public void send_hardware_io_port_read(int port, int mask) {
		BGAPIPacket p = new BGAPIPacket(0, 7, 7);
		p.w_uint8(port);
		p.w_uint8(mask);
		bgapi.sendPacket(p);
	}
	public void send_hardware_spi_config(int channel, int polarity, int phase, int bit_order, int baud_e, int baud_m) {
		BGAPIPacket p = new BGAPIPacket(0, 7, 8);
		p.w_uint8(channel);
		p.w_uint8(polarity);
		p.w_uint8(phase);
		p.w_uint8(bit_order);
		p.w_uint8(baud_e);
		p.w_uint8(baud_m);
		bgapi.sendPacket(p);
	}
	public void send_hardware_spi_transfer(int channel, byte[] data) {
		BGAPIPacket p = new BGAPIPacket(0, 7, 9);
		p.w_uint8(channel);
		p.w_uint8array(data);
		bgapi.sendPacket(p);
	}
	public void send_hardware_i2c_read(int address, int length) {
		BGAPIPacket p = new BGAPIPacket(0, 7, 10);
		p.w_uint8(address);
		p.w_uint8(length);
		bgapi.sendPacket(p);
	}
	public void send_hardware_i2c_write(int address, byte[] data) {
		BGAPIPacket p = new BGAPIPacket(0, 7, 11);
		p.w_uint8(address);
		p.w_uint8array(data);
		bgapi.sendPacket(p);
	}
	public void send_hardware_set_txpower(int power) {
		BGAPIPacket p = new BGAPIPacket(0, 7, 12);
		p.w_uint8(power);
		bgapi.sendPacket(p);
	}

	// Callbacks for class test (index = 8)
	public void send_test_phy_tx(int channel, int length, int type) {
		BGAPIPacket p = new BGAPIPacket(0, 8, 0);
		p.w_uint8(channel);
		p.w_uint8(length);
		p.w_uint8(type);
		bgapi.sendPacket(p);
	}
	public void send_test_phy_rx(int channel) {
		BGAPIPacket p = new BGAPIPacket(0, 8, 1);
		p.w_uint8(channel);
		bgapi.sendPacket(p);
	}
	public void send_test_phy_end() {
		BGAPIPacket p = new BGAPIPacket(0, 8, 2);
		bgapi.sendPacket(p);
	}
	public void send_test_phy_reset() {
		BGAPIPacket p = new BGAPIPacket(0, 8, 3);
		bgapi.sendPacket(p);
	}
	public void send_test_get_channel_map() {
		BGAPIPacket p = new BGAPIPacket(0, 8, 4);
		bgapi.sendPacket(p);
	}

    
}
