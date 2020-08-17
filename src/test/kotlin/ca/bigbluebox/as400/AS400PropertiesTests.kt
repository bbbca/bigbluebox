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

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class AS400PropertiesTests {

    @Test
    fun addressMustNotBeEmpty() {
        val as400Properties = AS400Properties()
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy { as400Properties.address = "" }
                .withMessage("Address must not be empty")
    }

    @Test
    fun userMustNotBeEmpty() {
        val as400Properties = AS400Properties()
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy { as400Properties.user = "" }
                .withMessage("User must not be empty")
    }

    @Test
    fun userMustBeLessThan11Long() {
        val as400Properties = AS400Properties()
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy { as400Properties.user = "x".repeat(11) }
                .withMessage("User must have a length smaller than 11")
    }

    @Test
    fun passwordMustNotBeEmpty() {
        val as400Properties = AS400Properties()
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy { as400Properties.password = "" }
                .withMessage("Password must not be empty")
    }

    @Test
    fun paswordMustBeLessThan129Long() {
        val as400Properties = AS400Properties()
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy { as400Properties.password = "x".repeat(129) }
                .withMessage("Password must have a length smaller than 129")
    }
}