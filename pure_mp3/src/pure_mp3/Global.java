/*
 *  This file is part of pure.mp3.
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

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Some Global Vars and Constants
 * @author Martin Braun
*/
public class Global 
{
	public static float volume = 100;
	public static final double linearscalar = Math.log(10.0)/20;
	public static final Injector injector = Guice.createInjector();
	
	public static void setVolume(float xVolume)
	{
		volume = xVolume;
	}
}
