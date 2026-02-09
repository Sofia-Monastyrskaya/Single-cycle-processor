package Adder

import spinal.core._
import spinal.sim._
import spinal.core.sim._

//import scala.language.postfixOps

case class FullAdder() extends Component {
  val io = new Bundle {
    val a_i     =   in Bool()
    val b_i     =   in Bool()
    val carry_i =   in Bool()
    val sum_o   =   out Bool()
    val carry_o =   out Bool()
  }

  io.sum_o   := (io.a_i ^ io.b_i)     ^ io.carry_i
  io.carry_o := (io.a_i & io.b_i)     |
                (io.a_i & io.carry_i) |
                (io.b_i & io.carry_i)

}

object FullAdderSim extends App {
  SimConfig
    .withVerilator
    .compile(FullAdder())
    .doSim{ dut =>

      dut.io.a_i     #= false
      dut.io.b_i     #= false
      dut.io.carry_i #= false
      //dut.clockDomain.waitSampling()
      sleep(1)
      println(s"a=0 b=0 cin=0 -> sum=${dut.io.sum_o.toBoolean} carry=${dut.io.carry_o.toBoolean}")

      dut.io.a_i     #= false
      dut.io.b_i     #= true
      dut.io.carry_i #= false
     // dut.clockDomain.waitSampling()
      sleep(1)
      println(s"a=0 b=1 cin=0 -> sum=${dut.io.sum_o.toBoolean} carry=${dut.io.carry_o.toBoolean}")

      dut.io.a_i     #= true
      dut.io.b_i     #= false
      dut.io.carry_i #= false
     // dut.clockDomain.waitSampling()
      sleep(1)
      println(s"a=1 b=0 cin=0 -> sum=${dut.io.sum_o.toBoolean} carry=${dut.io.carry_o.toBoolean}")

      dut.io.a_i     #= true
      dut.io.b_i     #= true
      dut.io.carry_i #= false
     // dut.clockDomain.waitSampling()
      sleep(1)
      println(s"a=1 b=1 cin=0 -> sum=${dut.io.sum_o.toBoolean} carry=${dut.io.carry_o.toBoolean}")

    }
}

object FullAdderVerilog extends App {
  SpinalConfig(
    targetDirectory = "rtl"
  ).generateVerilog(FullAdder())
}