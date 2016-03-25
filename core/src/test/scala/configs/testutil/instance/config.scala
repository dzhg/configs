/*
 * Copyright 2013-2016 Tsukasa Kitachi
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

package configs.testutil.instance

import com.typesafe.config.{Config, ConfigList, ConfigMemorySize, ConfigObject, ConfigValue, ConfigValueFactory}
import configs.testutil.gen._
import configs.testutil.instance.string._
import java.{util => ju}
import scala.collection.convert.decorateAsJava._
import scalaprops.Gen
import scalaz.{Equal, Need}

object config {


  implicit lazy val configEqual: Equal[Config] =
    Equal.equalA[Config]

  implicit lazy val configGen: Gen[Config] =
    configObjectGen.map(_.toConfig)

  implicit lazy val configValueEqual: Equal[ConfigValue] =
    Equal.equalA[ConfigValue]

  implicit lazy val configListEqual: Equal[ConfigList] =
    Equal.equalA[ConfigList]

  implicit lazy val configObjectEqual: Equal[ConfigObject] =
    Equal.equalA[ConfigObject]

  private[this] def configValue[A: Gen]: Gen[ConfigValue] =
    Gen[A].map(ConfigValueFactory.fromAnyRef)

  private[this] val configNumberGen: Gen[ConfigValue] =
    Gen.oneOf(
      configValue[Byte],
      configValue[Short],
      configValue[Int],
      configValue[Long],
      configValue[Float],
      configValue[Double]
    )

  implicit lazy val configListGen: Gen[ConfigList] =
    Gen.list(configValueGen).map(_.asJava).map(ConfigValueFactory.fromIterable)

  implicit lazy val configValueJListGen: Gen[ju.List[ConfigValue]] =
    Gen[ConfigList].as[ju.List[ConfigValue]]

  implicit lazy val configObjectGen: Gen[ConfigObject] =
    Gen.mapGen(Gen[String], configValueGen).map(_.asJava).map(ConfigValueFactory.fromMap)

  implicit lazy val configValueJavaMapGen: Gen[ju.Map[String, ConfigValue]] =
    Gen[ConfigObject].as[ju.Map[String, ConfigValue]]

  implicit lazy val configValueGen: Gen[ConfigValue] =
    Gen.lazyFrequency(
      40 -> Need(configValue[String]),
      40 -> Need(configNumberGen),
      10 -> Need(configValue[Boolean]),
      5 -> Need(configListGen.as[ConfigValue]),
      5 -> Need(configObjectGen.as[ConfigValue])
    ).mapSize(_ / 2)


  implicit lazy val configMemorySizeEqual: Equal[ConfigMemorySize] =
    Equal.equalA[ConfigMemorySize]

  implicit lazy val configMemorySizeGen: Gen[ConfigMemorySize] =
    Gen.nonNegativeLong.map(ConfigMemorySize.ofBytes)

}