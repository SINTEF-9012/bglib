BGAPI protocol in Java
======================

This JAVA API allow interfacing with Bluegiga Bluetooth Low Energy modules (BLE112) and the Blugiga Bluetooth Low Energy USB Dongle (BLED112). More information on these products can be found at at http://www.bluegiga.com/bluetooth-low-energy. BGLib is the Java equivalent to the C API provided by Bluegiga.

Usage
-----
The main use for this project is to make Java applications which can connect to Bluetooth Low Energy (Bluetooth Smart) sensors / devices. All you need is a BLED112 dongle on a PC. 

An example of such application can be foud at: https://github.com/SINTEF-9012/traale

Status
------
The current implementation supports protocol version 1.0.3.

The samples and GUI are very preliminary but the protocol is fully implemented and fully functional.

Sucessfully tested on Windows and Linux.

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