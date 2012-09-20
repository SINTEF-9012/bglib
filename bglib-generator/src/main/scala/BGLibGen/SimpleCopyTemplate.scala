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

object SimpleCopyTemplate {

    def copyFromClassPath(path : String) : String = {
      Source.fromInputStream(this.getClass.getClassLoader.getResourceAsStream(path),"utf-8").getLines().mkString("\n")
    }
}


