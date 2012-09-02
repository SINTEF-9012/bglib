package BGLibGen

import xml.XML
import java.util.Hashtable
import io.Source

object SimpleCopyTemplate {

    def copyFromClassPath(path : String) : String = {
      Source.fromInputStream(this.getClass.getClassLoader.getResourceAsStream(path),"utf-8").getLines().mkString("\n")
    }
}


