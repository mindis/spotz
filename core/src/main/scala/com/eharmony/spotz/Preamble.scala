package com.eharmony.spotz

import scala.math.Ordering
import scala.language.implicitConversions

/**
  * Implicit definitions that define the default implementation of a point of hyper parameter values.
  *
  * @author vsuthichai
  */
object Preamble {
  implicit object PointLossOrdering extends Ordering[(Point, Double)] {
    override def compare(x: (Point, Double), y: (Point, Double)): Int = {
      if (x._2 > y._2) 1
      else if (x._2 < y._2) -1
      else 0
    }
  }

  class Point(val hyperParamMap: Map[String, _]) extends Serializable {
    def get[T](label: String): T = hyperParamMap(label).asInstanceOf[T]
    def getHyperParameterLabels: Set[String] = hyperParamMap.keySet

    override def toString: String = {
      val paramStrings = hyperParamMap.foldLeft(new StringBuilder()) {
        case (sb, (label, value)) => sb ++= s"$label -> $value, "
      }
      s"Point($paramStrings)"
    }

    override def equals(that: Any): Boolean = {
      that match {
        case that: Point => hyperParamMap.equals(that.hyperParamMap)
        case _ => false
      }
    }
  }

  implicit def pointFactory(params: Map[String, _]): Point = new Point(params)
}
