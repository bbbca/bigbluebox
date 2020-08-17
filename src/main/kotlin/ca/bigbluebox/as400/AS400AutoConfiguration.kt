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

import com.ibm.as400.access.AS400
import com.ibm.as400.access.AS400JDBCDataSource
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
@ConditionalOnClass(AS400::class)
@EnableConfigurationProperties(AS400Properties::class)
class AS400AutoConfiguration(val as400Utils: AS400Utils) {

    @Bean
    fun as400(as400Properties: AS400Properties): AS400 {
        if (!as400Utils.isAS400()) {
            if (as400Properties.address == "localhost") {
                throw IllegalStateException("as400.address can only be localhost when running on IBM i")
            }
            if (as400Properties.user == "*CURRENT") {
                throw IllegalStateException("as400.user can only be *CURRENT when running on IBM i")
            }
            if (as400Properties.password == "*CURRENT") {
                throw IllegalStateException("as400.password can only be *CURRENT when running on IBM i")
            }
        }

        with(as400Properties) {
            return AS400(address, user, password)
        }
    }

    @Bean
    fun dataSource(as400: AS400): DataSource {
        return AS400JDBCDataSource(as400)
    }
}