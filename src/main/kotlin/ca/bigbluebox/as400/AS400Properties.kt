/*
 * Copyright 2020 Damien Ferrand
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
 *
 */


package ca.bigbluebox.as400

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.util.Assert

@ConfigurationProperties(prefix = "as400")
class AS400Properties() {
    /**
     * IBM i IP address or hostname.
     */
    var address = "localhost"
        set(value) {
            Assert.isTrue(value.isNotEmpty(), "Address must not be empty")
            field = value
        }

    /**
     * IBM i user
     */
    var user = "*CURRENT"
        set(value) {
            Assert.isTrue(value.isNotEmpty(), "User must not be empty")
            Assert.isTrue(value.length < 11, "User must have a length smaller than 11")
            field = value
        }

    /**
     * IBM i password
     */
    var password = "*CURRENT"
        set(value) {
            Assert.isTrue(value.isNotEmpty(), "Password must not be empty")
            Assert.isTrue(value.length < 129, "Password must have a length smaller than 129")
            field = value
        }
}