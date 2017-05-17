/**
 * Copyright 2006-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.objenesis.strategy;

import static org.objenesis.strategy.PlatformDescription.HOTSPOT;
import static org.objenesis.strategy.PlatformDescription.OPENJDK;

import java.io.Serializable;

import org.objenesis.instantiator.ObjectInstantiator;
import org.objenesis.instantiator.basic.AccessibleInstantiator;
import org.objenesis.instantiator.basic.ObjectInputStreamInstantiator;
import org.objenesis.instantiator.sun.SunReflectionFactoryInstantiator;

/**
 * Guess the best instantiator for a given class. The instantiator will instantiate the class
 * without calling any constructor. Currently, the selection doesn't depend on the class. It relies
 * on the
 * <ul>
 * <li>JVM version</li>
 * <li>JVM vendor</li>
 * <li>JVM vendor version</li>
 * </ul>
 * However, instantiators are stateful and so dedicated to their class.
 *
 * @author Henri Tremblay
 * @see ObjectInstantiator
 */
public class StdInstantiatorStrategy extends BaseInstantiatorStrategy {

   /**
    * Return an {@link ObjectInstantiator} allowing to create instance without any constructor being
    * called.
    *
    * @param type Class to instantiate
    * @return The ObjectInstantiator for the class
    */
   public <T> ObjectInstantiator<T> newInstantiatorOf(Class<T> type) {

      if(PlatformDescription.isThisJVM(HOTSPOT) || PlatformDescription.isThisJVM(OPENJDK)) {
         if(PlatformDescription.isGoogleAppEngine()) {
            if(Serializable.class.isAssignableFrom(type)) {
               return new ObjectInputStreamInstantiator<T>(type);
            }
            return new AccessibleInstantiator<T>(type);
         }
         // The UnsafeFactoryInstantiator would also work. But according to benchmarks, it is 2.5
         // times slower. So I prefer to use this one
         return new SunReflectionFactoryInstantiator<T>(type);
      }

      // Fallback instantiator, should work with most modern JVM
      return null;

   }
}
