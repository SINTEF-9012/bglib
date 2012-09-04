BGLib is a Java implementation of the BGAPI binary protocol for Bluegiga BLE112 Bluetooth low energy modules.

Summary
-------
This JAVA API allow interfacing with Bluegiga Bluetooth Low Energy modules (BLE112) and the Blugiga Bluetooth Low Energy USB Dongle (BLED112). More information on these products can be found at at http://www.bluegiga.com/bluetooth-low-energy. BGLib is the Java equivalent to the C API provided by Blugiga.

Status
------
The current implementation supports protocol version 1.0.3.
The samples and GUI are very preliminary but the protocol is fully implemented and fully functional.

Bugs and Feature Requests
-------------------------
Please use the issue tracker at: https://github.com/SINTEF-9012/bglib/issues

Distribution
------------

Binary distribution of the latest build are available through a Maven repository.

    <dependency>
      <groupId>org.thingml</groupId>
      <artifactId>bglib-protocol</artifactId>
      <version>1.0.3-SNAPSHOT</version>
    </dependency>