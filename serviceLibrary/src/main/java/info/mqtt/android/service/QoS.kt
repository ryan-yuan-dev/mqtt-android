package info.mqtt.android.service

enum class QoS(val value: Int) {

    AtMostOnce(0),
    AtLeastOnce(1),
    ExactlyOnce(2);

    companion object {
        @JvmStatic
        fun valueOf(qos: Int): QoS {
            return when (qos) {
                0 -> AtMostOnce
                1 -> AtLeastOnce
                2 -> ExactlyOnce
                else -> throw IllegalArgumentException("qos must be 0, 1 or 2")
            }
//            return entries[qos]
        }
    }

}
