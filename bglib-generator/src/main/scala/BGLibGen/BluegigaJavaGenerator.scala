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
object BluegigaJavaGenerator {

    val java_types : Hashtable[String,  String] = {
    val result = new Hashtable[String, String]()
    result.put("uint8", "int")
    result.put("uint16", "int")
    result.put("int16", "int")
    result.put("uint32", "int")
    result.put("int8", "int")
    result.put("bd_addr", "BDAddr")
    result.put("uint8array", "byte[]")
    result
  }

  def getJavaType(t : String) = {
    var result = java_types.get(t)
    if (result == null) result = "[" + t + "]"
    result
  }

def generateReceiveOperations (out : StringBuilder, root: scala.xml.Elem, before : String, after : String) =  {

  root \ "class" foreach { p =>
    val p_index =  (p \ "@index").text
    var p_name =  (p \ "@name").text
    out append "\n\t// Callbacks for class " + p_name + " (index = " + p_index + ")\n"

    p \ "command" foreach { c =>
      val c_index =  (c \ "@index").text
      val c_name =  (c \ "@name").text
      val c_script =  (c \ "@script").text

      out append "\t" + before + "void receive_" + p_name + "_" + c_name + "("

      var params = List[String]()
      c \ "returns" \ "param" foreach { a =>
        val a_datatype = (a \ "@datatype").text
        var a_name = (a \ "@name").text
        val a_type = (a \ "@type").text
        params ::= getJavaType(a_type) + " " + a_name
      }

      out append  params.reverse.mkString(", ")
       out append  ")"
      out append after
      out append "\n"

    }
    p \ "event" foreach { e =>
      val e_index =  (e \ "@index").text
      val e_name =  (e \ "@name").text
      val e_script =  (e \ "@script").text

      out append "\t" + before + "void receive_" + p_name + "_" + e_name + "("

      var params = List[String]()
      e \ "params" \ "param" foreach { a =>
        val a_datatype = (a \ "@datatype").text
        var a_name = (a \ "@name").text
        val a_type = (a \ "@type").text
        params ::= getJavaType(a_type) + " " + a_name
      }

      out append  params.reverse.mkString(", ")
      out append  ")"
      out append after
      out append "\n"

    }
  }
}


  def generateReceiveHandlers (out : StringBuilder, root: scala.xml.Elem) =  {

  root \ "class" foreach { p =>
    val p_index =  (p \ "@index").text
    var p_name =  (p \ "@name").text
    out append "\n\t// Callbacks for class " + p_name + " (index = " + p_index + ")\n"

    p \ "command" foreach { c =>
      val c_index =  (c \ "@index").text
      val c_name =  (c \ "@name").text
      val c_script =  (c \ "@script").text

      out append "\tprivate void receive_" + p_name + "_" + c_name + "(BGAPIPacket packet) {\n"
      out append "\t\tBGAPIPacketReader r = packet.getPayloadReader();\n"

      var params = List[String]()
      c \ "returns" \ "param" foreach { a =>
        val a_datatype = (a \ "@datatype").text
        var a_name = (a \ "@name").text
        val a_type = (a \ "@type").text
        params ::= "\t\t" + getJavaType(a_type) + " " + a_name + " = r.r_" + a_type + "();\n"
      }
      out append  params.reverse.mkString("")

      out append  "\t\tfor(BGAPIListener l : listeners) l.receive_" + p_name + "_" + c_name + "("
      params = List[String]()
      c \ "returns" \ "param" foreach { a =>
        val a_datatype = (a \ "@datatype").text
        var a_name = (a \ "@name").text
        val a_type = (a \ "@type").text
        params ::= a_name
      }
      out append  params.reverse.mkString(", ")
      out append ");\n"
      out append "\t}\n"

    }
    p \ "event" foreach { e =>
      val e_index =  (e \ "@index").text
      val e_name =  (e \ "@name").text
      val e_script =  (e \ "@script").text


      out append "\tprivate void receive_" + p_name + "_" + e_name + "(BGAPIPacket __packet) {\n"
      out append "\t\tBGAPIPacketReader r = __packet.getPayloadReader();\n"

      var params = List[String]()
     e \ "params" \ "param" foreach { a =>
        val a_datatype = (a \ "@datatype").text
        var a_name = (a \ "@name").text
        val a_type = (a \ "@type").text
        params ::= "\t\t" + getJavaType(a_type) + " " + a_name + " = r.r_" + a_type + "();\n"
      }
      out append  params.reverse.mkString("")

      out append  "\t\tfor(BGAPIListener l : listeners) l.receive_" + p_name + "_" + e_name + "("
      params = List[String]()
      e \ "params" \ "param" foreach { a =>
        val a_datatype = (a \ "@datatype").text
        var a_name = (a \ "@name").text
        val a_type = (a \ "@type").text
        params ::= a_name
      }
      out append  params.reverse.mkString(", ")
      out append ");\n"
      out append "\t}\n"

    }
  }
}

  def generateReceiveSwitch (out : StringBuilder, root: scala.xml.Elem) =  {

    out append "\t\tif (packet.getMsgType() == 0) {\n"
    out append "\t\t\tswitch(packet.classID) {\n"
    root \ "class" foreach { p =>
      val p_index =  (p \ "@index").text
      var p_name =  (p \ "@name").text
      out append "\t\t\t\tcase " + p_index + ": receive_" + p_name + "_cmd(packet);break;\n"
    }
    out append "\t\t\t\tdefault: break;\n"
    out append "\t\t\t}\n"
    out append "\t\t}\n"

    out append "\t\telse {\n"
    out append "\t\t\tswitch(packet.classID) {\n"
    root \ "class" foreach { p =>
      val p_index =  (p \ "@index").text
      var p_name =  (p \ "@name").text
      out append "\t\t\t\tcase " + p_index + ": receive_" + p_name + "_evt(packet);break;\n"
    }
    out append "\t\t\t\tdefault: break;\n"
    out append "\t\t\t}\n"
    out append "\t\t}\n"
  }


  def generateReceiveSwitchOps (out : StringBuilder, root: scala.xml.Elem) =  {

  root \ "class" foreach { p =>
    val p_index =  (p \ "@index").text
    var p_name =  (p \ "@name").text
    //out append "\n\t// Callbacks for class " + p_name + " (index = " + p_index + ")\n"

    out append "\tprivate void receive_" + p_name + "_cmd(BGAPIPacket packet) {\n"
    out append "\t\tswitch(packet.commandID) {\n"

     p \ "command" foreach { c =>
      val c_index =  (c \ "@index").text
      val c_name =  (c \ "@name").text
      val c_script =  (c \ "@script").text
      out append "\t\t\tcase " + c_index + ": receive_" + p_name + "_" + c_name + "(packet); break;\n"
     }

    out append "\t\t\tdefault: break;\n"
    out append "\t\t}\n"
    out append "\t}\n"

    out append "\tprivate void receive_" + p_name + "_evt(BGAPIPacket packet) {\n"
    out append "\t\tswitch(packet.commandID) {\n"

    p \ "event" foreach { e =>
      val e_index =  (e \ "@index").text
      val e_name =  (e \ "@name").text
      val e_script =  (e \ "@script").text
      out append "\t\t\tcase " + e_index + ": receive_" + p_name + "_" + e_name + "(packet); break;\n"
     }

    out append "\t\t\tdefault: break;\n"
    out append "\t\t}\n"
    out append "\t}\n"
  }
}


  def generateSendOperations (out : StringBuilder, root: scala.xml.Elem) =  {

  root \ "class" foreach { p =>
    val p_index =  (p \ "@index").text
    var p_name =  (p \ "@name").text
    out append "\n\t// Callbacks for class " + p_name + " (index = " + p_index + ")\n"

    p \ "command" foreach { c =>
      val c_index =  (c \ "@index").text
      val c_name =  (c \ "@name").text
      val c_script =  (c \ "@script").text

      out append "\tpublic void send_" + p_name + "_" + c_name + "("

      var params = List[String]()
      c \ "params" \ "param" foreach { a =>
        val a_datatype = (a \ "@datatype").text
        var a_name = (a \ "@name").text
        val a_type = (a \ "@type").text
        params ::= getJavaType(a_type) + " " + a_name
      }

      out append  params.reverse.mkString(", ")
      out append  ") {\n"
      out append "\t\tBGAPIPacket p = new BGAPIPacket(0, "+ p_index +", "+c_index+");\n"

      params = List[String]()
      c \ "params" \ "param" foreach { a =>
        val a_datatype = (a \ "@datatype").text
        var a_name = (a \ "@name").text
        val a_type = (a \ "@type").text
        params ::= "\t\tp.w_" + a_type + "(" + a_name +");\n"
      }
      out append  params.reverse.mkString("")
      out append "\t\tbgapi.sendPacket(p);\n"

      out append "\t}\n"
    }
  }
}

  def main(args : Array[String]) {
    println( "Loading XML..." );
    val url = getClass.getResource("/bleapi.xml")
    println( "url = " + url );
    val bleapi = XML.loadFile(url.getFile)

    {
      var result =  SimpleCopyTemplate.copyFromClassPath("BGAPIListener.javat")

      var builder = new StringBuilder()
      generateReceiveOperations(builder, bleapi, "", ";")
      result = result.replace("/*METHODS*/", builder.toString())

      println("************************************************************************************")
      println("* BGAPIListener.java")
      println("************************************************************************************")
      println(result.toString)
    }

     {
      var result =  SimpleCopyTemplate.copyFromClassPath("BGAPIDefaultListener.javat")

      var builder = new StringBuilder()
      generateReceiveOperations(builder, bleapi, "public ", " {}")
      result = result.replace("/*METHODS*/", builder.toString())

      println("************************************************************************************")
      println("* BGAPIDefaultListener.java")
      println("************************************************************************************")
      println(result.toString)
    }

     {
      var result =  SimpleCopyTemplate.copyFromClassPath("BGAPIImpl.javat")

      var builder = new StringBuilder()
      generateSendOperations(builder, bleapi)
      result = result.replace("/*SENDS*/", builder.toString())

      builder = new StringBuilder()
      generateReceiveHandlers(builder, bleapi)
      result = result.replace("/*RECEIVES*/", builder.toString())

       builder = new StringBuilder()
      generateReceiveSwitch(builder, bleapi)
      result = result.replace("/*SWITCH*/", builder.toString())

       builder = new StringBuilder()
      generateReceiveSwitchOps(builder, bleapi)
      result = result.replace("/*SWITCHOPS*/", builder.toString())

      println("************************************************************************************")
      println("* BGAPIImpl.java")
      println("************************************************************************************")
      println(result.toString)
    }


           /*
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

    println(result.toString)         */
  }





}
