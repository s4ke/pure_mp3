 /**
 * @author Martin Braun
 *   
 * This file is part of pure.mp3.
 *
 *  pure.mp3 is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  pure.mp3 is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with pure.mp3.  If not, see <http://www.gnu.org/licenses/>.
 */

package pure_mp3;

import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;
//import javax.swing.*;
//import javax.swing.border.*;
//import java.awt.*;

public class TestTheme extends DefaultMetalTheme {

    public String getName() { return "Toms"; }

    private final ColorUIResource primary1 = new ColorUIResource(255, 255, 0);
    private final ColorUIResource primary2 = new ColorUIResource(0, 255, 255);
    private final ColorUIResource primary3 = new ColorUIResource(255, 0, 255);

    protected ColorUIResource getPrimary1() { return primary1; }
    protected ColorUIResource getPrimary2() { return primary2; }
    protected ColorUIResource getPrimary3() { return primary3; }

}
