/*
 * Copyright (C) 2009-2016 Typesafe Inc. <http://www.typesafe.com>
 */
package play.api.libs.openid

trait RichUrl[A] {
  def hostAndPath: String
}
