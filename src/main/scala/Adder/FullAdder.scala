package Adder

import spinal.core._
import spinal.sim._
import spinal.core.sim._

case class FullAdder() extends Component {
  val io = new Bundle {
    val a_i     =   in  Bool()
    val b_i     =   in  Bool()
    val carry_i =   in  Bool()
    val sum_o   =   out Bool()
    val carry_o =   out Bool()
  }

  io.sum_o   := (io.a_i ^ io.b_i)     ^ io.carry_i
  io.carry_o := (io.a_i & io.b_i)     |
                (io.a_i & io.carry_i) |
                (io.b_i & io.carry_i)
}

object FullAdderVerilog extends App {
  SpinalConfig(
    targetDirectory = "rtl"
  ).generateSystemVerilog(FullAdder())
}