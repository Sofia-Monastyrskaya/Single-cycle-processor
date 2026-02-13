package Adder

import spinal.sim._
import spinal.core.sim._

object FullAdderSim extends App {
  SimConfig
    .withVerilator
    .compile(FullAdder())
    .doSim{ dut =>

      dut.io.a_i     #= false
      dut.io.b_i     #= false
      dut.io.carry_i #= false
      sleep(1)
      println(s"a=0 b=0 cin=0 -> sum=${dut.io.sum_o.toBoolean} carry=${dut.io.carry_o.toBoolean}")

      dut.io.a_i     #= false
      dut.io.b_i     #= true
      dut.io.carry_i #= false
      sleep(1)
      println(s"a=0 b=1 cin=0 -> sum=${dut.io.sum_o.toBoolean} carry=${dut.io.carry_o.toBoolean}")

      dut.io.a_i     #= true
      dut.io.b_i     #= false
      dut.io.carry_i #= false
      sleep(1)
      println(s"a=1 b=0 cin=0 -> sum=${dut.io.sum_o.toBoolean} carry=${dut.io.carry_o.toBoolean}")

      dut.io.a_i     #= true
      dut.io.b_i     #= true
      dut.io.carry_i #= false
      sleep(1)
      println(s"a=1 b=1 cin=0 -> sum=${dut.io.sum_o.toBoolean} carry=${dut.io.carry_o.toBoolean}")

      simSuccess()
    }
}