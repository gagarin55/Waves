package com.wavesplatform.metrics

import com.wavesplatform.database.Key
import kamon.Kamon
import kamon.metric.{HistogramMetric, MeasurementUnit}

object LevelDBStats {
  implicit class DbHistogramExt(val h: HistogramMetric) {
    def recordTagged(key: Key[_], value: Array[Byte]): Unit = recordTagged(key.name, value)

    def recordTagged(tag: String, value: Array[Byte]): Unit =
      h.refine("key", tag).record(Option(value).map(_.length.toLong).getOrElse(0))
  }

  val read  = Kamon.histogram("db.read", MeasurementUnit.information.kilobytes)
  val write = Kamon.histogram("db.write", MeasurementUnit.information.kilobytes)
}
