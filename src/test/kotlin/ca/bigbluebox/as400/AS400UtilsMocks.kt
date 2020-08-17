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

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile

@Configuration
class AS400UtilsMocks {

    @Profile("os400")
    @Bean
    @Primary
    fun os400() = mock<AS400Utils> { on(it.isAS400()) doReturn true }

    @Profile("notOs400")
    @Bean
    @Primary
    fun notOs400() = mock<AS400Utils> { on(it.isAS400()) doReturn false }
}