/*
 * Copyright 2013-2015 Tsukasa Kitachi
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

package com.github.kxbmap.configs.instance

import com.github.kxbmap.configs.ConfigProp
import com.typesafe.config.{Config, ConfigMemorySize}
import java.{util => ju}
import scalaprops.{Properties, Scalaprops}
import scalaz.std.list._
import scalaz.std.stream._
import scalaz.std.string._
import scalaz.std.vector._

object ConfigTypeConfigsTest extends Scalaprops with ConfigProp {

  val config = check[Config]
  val configList = check[ju.List[Config]]
  val configCollections = Properties.list(
    check[List[Config]].mapId("list " + _),
    check[Vector[Config]].mapId("vector " + _),
    check[Stream[Config]].mapId("stream " + _),
    check[Array[Config]].mapId("array " + _)
  )

  val configMemorySize = check[ConfigMemorySize]
  val configMemorySizeList = check[ju.List[ConfigMemorySize]]
  val configMemorySizeCollections = Properties.list(
    check[List[ConfigMemorySize]].mapId("list " + _),
    check[Vector[ConfigMemorySize]].mapId("vector " + _),
    check[Stream[ConfigMemorySize]].mapId("stream " + _),
    check[Array[ConfigMemorySize]].mapId("array " + _)
  )

}
