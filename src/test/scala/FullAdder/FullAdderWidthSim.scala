package Adder

import spinal.sim._
import spinal.core.sim._

object FullAdderWidthSim extends App {
    SimConfig
    .withVerilator
    .compile(FullAdderWidth(8))
    .doSim{ dut =>
        dut.io.a_i     #= 200
        dut.io.b_i     #= 100
        dut.io.carry_i #= false
        sleep(1)
        println(
            s"a=${dut.io.a_i.toBigInt} " +
            s"b=${dut.io.b_i.toBigInt} " +
            s"ccarry_i=${dut.io.carry_i.toBoolean} -> " +
            s"sum=${dut.io.sum_o.toBigInt} " +
            s"carry_o=${dut.io.carry_o.toBoolean}"
        )
    }
}