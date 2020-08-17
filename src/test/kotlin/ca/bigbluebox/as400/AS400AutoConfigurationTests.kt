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
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.autoconfigure.AutoConfigurations
import org.springframework.boot.test.context.runner.WebApplicationContextRunner
import java.lang.IllegalStateException
import javax.sql.DataSource

class AS400AutoConfigurationTests {
    private val webApplicationContextRunner: WebApplicationContextRunner = WebApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(AS400AutoConfiguration::class.java, AS400UtilsMocks::class.java))

    @Test
    fun localhostWhenNotOs400Fails() {
        webApplicationContextRunner
                .withPropertyValues("spring.profiles.active=notOs400",
                        "as400.address=localhost",
                        "as400.user=user",
                        "as400.password=password")
                .run { context ->
                    Assertions.assertThatIllegalStateException()
                            .isThrownBy { context.getBean(AS400::class.java) }
                            .havingRootCause()
                            .isInstanceOf(IllegalStateException::class.java)
                            .withMessage("as400.address can only be localhost when running on IBM i")
                }
    }

    @Test
    fun currentUserWhenNotOs400Fails() {
        webApplicationContextRunner
                .withPropertyValues("spring.profiles.active=notOs400",
                        "as400.address=as400",
                        "as400.user=*CURRENT",
                        "as400.password=password")
                .run { context ->
                    Assertions.assertThatIllegalStateException()
                            .isThrownBy { context.getBean(AS400::class.java) }
                            .havingRootCause()
                            .isInstanceOf(IllegalStateException::class.java)
                            .withMessage("as400.user can only be *CURRENT when running on IBM i")
                }
    }

    @Test
    fun currentPasswordWhenNotOs400Fails() {
        webApplicationContextRunner
                .withPropertyValues("spring.profiles.active=notOs400",
                        "as400.address=as400",
                        "as400.user=user",
                        "as400.password=*CURRENT")
                .run { context ->
                    Assertions.assertThatIllegalStateException()
                            .isThrownBy { context.getBean(AS400::class.java) }
                            .havingRootCause()
                            .isInstanceOf(IllegalStateException::class.java)
                            .withMessage("as400.password can only be *CURRENT when running on IBM i")
                }
    }

    @Test
    fun localhostAndCurrentWhenOs400Succeeds() {
        webApplicationContextRunner
                .withPropertyValues("spring.profiles.active=os400",
                        "as400.address=localhost",
                        "as400.user=*CURRENT",
                        "as400.password=*CURRENT")
                .run { context ->
                    assertThat(context).hasSingleBean(AS400::class.java)
                    val as400 = context.getBean(AS400::class.java)
                    assertThat(as400.systemName).isEqualTo("localhost")
                    assertThat(as400.userId).isEqualTo("*CURRENT")

                    assertThat(context).hasSingleBean(DataSource::class.java)
                    val ds = context.getBean(DataSource::class.java)
                    assertThat(ds).isInstanceOf(AS400JDBCDataSource::class.java)
                }
    }

    @Test
    fun notLocalhostAndNotCurrentWhenNotOs400Succeeds() {
        webApplicationContextRunner
                .withPropertyValues("spring.profiles.active=notOs400",
                        "as400.address=as400",
                        "as400.user=user",
                        "as400.password=password")
                .run { context ->
                    assertThat(context).hasSingleBean(AS400::class.java)
                    val as400 = context.getBean(AS400::class.java)
                    assertThat(as400.systemName).isEqualTo("as400")
                    assertThat(as400.userId).isEqualTo("USER")

                    assertThat(context).hasSingleBean(DataSource::class.java)
                    val ds = context.getBean(DataSource::class.java)
                    assertThat(ds).isInstanceOf(AS400JDBCDataSource::class.java)
                }
    }
}