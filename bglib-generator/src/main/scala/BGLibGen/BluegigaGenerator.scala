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
package BGLibGen

import xml.XML
import java.util.Hashtable
import io.Source



object BluegigaGenerator {

  val kwds = List[String]("port", "end", "state", "function")


  val byte_sizes : Hashtable[String,  String] = {
    val result = new Hashtable[String, String]()
    result.put("uint8", "1")
    result.put("uint16", "2")
    result.put("int16", "2")
    result.put("uint32", "4")
    result.put("int8", "1")
    result
  }

  def getByteSize(c_type : String) = {
    var result = byte_sizes.get(c_type)
    if (result == null) result = "*"
    result
  }

def generateDataTypes(out : StringBuilder, root: scala.xml.Elem) =  {

  out append "/* Datatypes definitions */\n\n"

  root \ "datatypes" \ "datatype" foreach { dt =>
    val name =  (dt \ "@name").text
    var base =  (dt \ "@base").text
    if (base == "bd_addr") base += "*"
    out append "datatype " + name + "\n"
    out append "\t@c_type \""+ base +"\"\n"
    out append "\t@c_byte_size \"" + getByteSize(base) + "\"\n"
    out append "\t@java_type \"???\";\n\n"
  }

  out append "datatype byteArray\n"
	out append "@c_type \"uint8*\"\n"
	out append "@c_byte_size \"*\"\n"
	out append "@java_type \"???\";\n\n"

}

def generateMessages (out : StringBuilder, root: scala.xml.Elem) =  {

  out append "// Messages defintions fragment\n"
  out append "thing fragment BGLibMsgs\n"
  out append "@c_header \"#include \\\"apitypes.h\\\"\"\n"
  out append "{\n"

  root \ "class" foreach { p =>
    val p_index =  (p \ "@index").text
    var p_name =  (p \ "@name").text
    out append "\n\t// Messages for class " + p_name + " (index = " + p_index + ")\n"

    p \ "command" foreach { c =>
      val c_index =  (c \ "@index").text
      val c_name =  (c \ "@name").text
      val c_script =  (c \ "@script").text

      out append "\tmessage " + c_name + "("

      var params = List[String]()
      c \ "params" \ "param" foreach { a =>
        val a_datatype = (a \ "@datatype").text
        var a_name = (a \ "@name").text
        val a_type = (a \ "@type").text

        if (a_datatype == "uint8array") {
          // Generate 2 parameters array, followed by length
          params ::= (a_name  + "_len : uint8")
          params ::= (a_name  + "_data : byteArray")
        }
        else {
          if (kwds.contains(a_name)) a_name = "_" + a_name
          params ::= (a_name  + " : " + a_datatype)
        }
      }

      out append  params.reverse.mkString(", ")
      out append  ")\n"
      out append  "\t\t@bgapi_index \"" + c_index + "\" "
      out append  "@bgapi_script \"" + c_script + "\"\n"
      out append  "\t\t@response_message \"" + c_name + "_rsp\""
      out append  ";\n"

      out append "\tmessage " + c_name + "_rsp("

      params = List[String]()
      c \ "returns" \ "param" foreach { a =>
        val a_datatype = (a \ "@datatype").text
        var a_name = (a \ "@name").text
        val a_type = (a \ "@type").text

        if (a_datatype == "uint8array") {
          // Generate 2 parameters array, followed by length
          params ::= (a_name  + "_len : uint8")
          params ::= (a_name  + "_data : byteArray")
        }
        else {
          if (kwds.contains(a_name)) a_name = "_" + a_name
          params ::= (a_name  + " : " + a_datatype)
        }
      }

      out append  params.reverse.mkString(", ")
      out append  ")\n"
      out append  "\t\t@bgapi_index \"" + c_index + "\" "
      out append  "@bgapi_script \"" + c_script + "\"\n"
      out append  "\t\t@request_message \"" + c_name + "\""
      out append  ";\n"

    }
    p \ "event" foreach { e =>
      val e_index =  (e \ "@index").text
      val e_name =  (e \ "@name").text
      val e_script =  (e \ "@script").text

      out append "\tmessage " + e_name + "_evt("

     var params = List[String]()
     e \ "params" \ "param" foreach { a =>
        val a_datatype = (a \ "@datatype").text
        var a_name = (a \ "@name").text
        val a_type = (a \ "@type").text

        if (a_datatype == "uint8array") {
          // Generate 2 parameters array, followed by length
          params ::= (a_name  + "_len : uint8")
          params ::= (a_name  + "_data : byteArray")
        }
        else {
          if (kwds.contains(a_name)) a_name = "_" + a_name
          params ::= (a_name  + " : " + a_datatype)
        }
      }

      out append  params.reverse.mkString(", ")
      out append  ")\n"
      out append  "\t\t@bgapi_index \"" + e_index + "\" "
      out append  "@bgapi_script \"" + e_script + "\""
      out append  ";\n"

    }
  }
  out append  "}\n"
}


  def generateCallbacks (out : StringBuilder, root: scala.xml.Elem) =  {

    root \ "class" foreach { p =>
    val p_index =  (p \ "@index").text
    var p_name =  (p \ "@name").text
    out append "\n\t// Callbacks for class " + p_name + " (index = " + p_index + ")\n"

    p \ "command" foreach { c =>
      val c_index =  (c \ "@index").text
      val c_name =  (c \ "@name").text
      val c_script =  (c \ "@script").text

      var params = List[String]()
      c \ "returns" \ "param" foreach { a =>
        val a_datatype = (a \ "@datatype").text
        var a_name = (a \ "@name").text
        val a_type = (a \ "@type").text

        if (a_datatype == "uint8array") {
          // Generate 2 parameters array, followed by length
          params ::= ("'msg->" + a_name  + ".len'")
          params ::= ("'msg->" + a_name  + ".data'")
        }
        else if (a_datatype == "bd_addr") {
          //if (kwds.contains(a_name)) a_name = "_" + a_name
          params ::= ("'&msg->" + a_name + "'")
        }
        else {
          //if (kwds.contains(a_name)) a_name = "_" + a_name
          params ::= ("'msg->" + a_name + "'")
        }
      }

      out append "\tfunction " + c_name + "_rsp_callback()\n"
      //void ble_evt_gap_mode_changed(const struct ble_msg_gap_mode_changed_evt_t *msg)
      out append "\t@c_prototype \""

      if (params.size > 0) {
        out append "void ble_rsp_" + p_name + "_" + c_name +"(const struct ble_msg_"
        out append p_name + "_" + c_name + "_rsp_t *msg)\"\n"
      }
      else {
         out append "void ble_rsp_" + p_name + "_" + c_name +"(const void *nul)\"\n"
      }
      out append "\t@c_instance_var_name \"_bgapi_instance\"\n"
      //gap!mode_changed_evt('msg->discover', 'msg->connect')
      out append "\t" + p_name + "!" + c_name + "_rsp("

      out append  params.reverse.mkString(", ")
      out append  ")\n\n"

    }

    p \ "event" foreach { e =>
      val e_index =  (e \ "@index").text
      val e_name =  (e \ "@name").text
      val e_script =  (e \ "@script").text

     var params = List[String]()
     e \ "params" \ "param" foreach { a =>
        val a_datatype = (a \ "@datatype").text
        var a_name = (a \ "@name").text
        val a_type = (a \ "@type").text

        if (a_datatype == "uint8array") {
          // Generate 2 parameters array, followed by length
          params ::= ("'msg->" + a_name  + ".len'")
          params ::= ("'msg->" + a_name  + ".data'")
        }
        else if (a_datatype == "bd_addr") {
          //if (kwds.contains(a_name)) a_name = "_" + a_name
          params ::= ("'&msg->" + a_name + "'")
        }
        else {
          //if (kwds.contains(a_name)) a_name = "_" + a_name
          params ::= ("'msg->" + a_name + "'")
        }
      }

       out append "\tfunction " + e_name + "_evt_callback()\n"
      //void ble_evt_gap_mode_changed(const struct ble_msg_gap_mode_changed_evt_t *msg)
      out append "\t@c_prototype \""

      if (params.size > 0) {
        out append "void ble_evt_" + p_name + "_" + e_name +"(const struct ble_msg_"
        out append p_name + "_" + e_name + "_evt_t *msg)\"\n"
      }
      else {
         out append "void ble_rsp_" + p_name + "_" + e_name +"(const void *nul)\"\n"
      }


      out append "\t@c_instance_var_name \"_bgapi_instance\"\n"
      //gap!mode_changed_evt('msg->discover', 'msg->connect')
      out append "\t" + p_name + "!" + e_name + "_evt("

      out append  params.reverse.mkString(", ")
      out append  ")\n\n"

    }
  }

  }

  def generatePortsFragment (out : StringBuilder, root: scala.xml.Elem) =  {

    out append "\n// Fragment defining the ports for the BGLib\n"
    out append "thing fragment BGLibPorts includes BGLibMsgs, BLED112Ports {\n"

    root \ "class" foreach { p =>
      val p_index =  (p \ "@index").text
      val p_name =  (p \ "@name").text

      out append "\n\tprovided port " + p_name + " {\n"

      var sends = List[String]()
      var receives = List[String]()
      var events = List[String]()

      p \ "command" foreach { c =>
        val c_name =  (c \ "@name").text
        sends ::= c_name
        receives ::= c_name + "_rsp"
      }
      p \ "event" foreach { e =>
        val e_name =  (e \ "@name").text
        events ::= e_name + "_evt"
      }
      if (sends.length > 0)
        out append "\t\t receives " + sends.mkString(", ") + "\n"
      if (receives.length > 0)
        out append "\t\t sends " + receives.mkString(", ") + "\n"
      if (events.length > 0)
        out append "\t\t sends " + events.mkString(", ")  + "\n"
      out append  "\t}\n"
    }
    out append  "}\n"
  }





def generateClientPortsFragment (out : StringBuilder, root: scala.xml.Elem) =  {

  out append "\n// Fragment defining the ports for a BGLib client\n"
  out append "thing fragment BGLibClientPorts includes BGLibMsgs, BLED112ClientPorts {\n"

  root \ "class" foreach { p =>
    val p_index =  (p \ "@index").text
    val p_name =  (p \ "@name").text

    out append "\n\trequired port " + p_name + " {\n"

    var sends = List[String]()
    var receives = List[String]()
    var events = List[String]()

    p \ "command" foreach { c =>
      val c_name =  (c \ "@name").text
      sends ::= c_name
      receives ::= c_name + "_rsp"
    }
    p \ "event" foreach { e =>
      val e_name =  (e \ "@name").text
      events ::= e_name + "_evt"
    }
    if (sends.length > 0)
      out append "\t\t sends " + sends.mkString(", ") + "\n"
    if (receives.length > 0)
      out append "\t\t receives " + receives.mkString(", ") + "\n"
    if (events.length > 0)
      out append "\t\t receives " + events.mkString(", ")  + "\n"
    out append  "\t}\n"
  }
  out append  "}\n"
}





  def generateImpl (out : StringBuilder, root: scala.xml.Elem) =  {

    //out append "// Implemnatation of the API\n"
    //out append "thing BGLibImpl includes BGLibPorts {\n\n"


    out append "\tstatechart BGLibImpl init Running {\n"
    //out append "\t\ton entry start_receiver_process()\n"
    out append "\t\tstate Running {}\n\n"

    out append "\t\tinternal event bled112?bled112_connect action start_receiver_process()\n\n"

   // out append "\t\t// Forwarding all incoming messages to the API\n"

    root \ "class" foreach { p =>
      val p_index =  (p \ "@index").text
      var p_name =  (p \ "@name").text
      out append "\n\t\t// Forward messages for port " + p_name + "\n"

      p \ "command" foreach { c =>
        val c_index =  (c \ "@index").text
        val c_name =  (c \ "@name").text
        val c_script =  (c \ "@script").text

        out append "\t\tinternal event m : " + p_name + "?" + c_name + " "
        out append "action 'ble_cmd_"+p_name+"_"+c_name+"("

        var params = List[String]()
        c \ "params" \ "param" foreach { a =>
          var a_name = (a \ "@name").text
          val a_datatype = (a \ "@datatype").text

          if (a_datatype == "uint8array") {
            // Generate 2 parameters array, followed by length
            params ::= ( "'& m." + a_name  + "_len &'")
            params ::= ( "'& m." + a_name  + "_data &'")
          }
          else {

            if (kwds.contains(a_name)) a_name = "_" + a_name
            params ::= ( "'& m." + a_name  + " &'")
          }
        }

        out append  params.reverse.mkString(", ")
        out append ");'\n"
      }
    }
    out append  "\t}\n"
    //out append  "}\n"
  }


  def main(args : Array[String]) {
    println( "Loading XML..." );
    val url = getClass.getResource("/bleapi.xml")
    println( "url = " + url );
    val bleapi = XML.loadFile(url.getFile)

    var result =  SimpleCopyTemplate.copyFromClassPath("output.thingml")

    var builder = new StringBuilder()
    generateDataTypes(builder, bleapi)
    result = result.replace("/*TYPES*/", builder.toString())

    builder = new StringBuilder()
    generateMessages(builder, bleapi)
    result = result.replace("/*MESSAGES*/", builder.toString())

    builder = new StringBuilder()
    generatePortsFragment(builder, bleapi)
    result = result.replace("/*LIB_PORTS*/", builder.toString())

    builder = new StringBuilder()
    generateClientPortsFragment(builder, bleapi)
    result = result.replace("/*CLIENT_PORTS*/", builder.toString())

    builder = new StringBuilder()
    generateCallbacks(builder, bleapi)
    result = result.replace("/*CALLBACKS*/", builder.toString())

    builder = new StringBuilder()
    generateImpl(builder, bleapi)
    result = result.replace("/*IMPL_SM*/", builder.toString())

    println(result.toString)
  }





}
