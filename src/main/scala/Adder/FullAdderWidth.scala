package Adder

import spinal.core._
import spinal.sim._
import spinal.core.sim._

case class FullAdderWidth(width: Int) extends Component{
    val io = new Bundle{
        val a_i     =   in  Bits(width bits)
        val b_i     =   in  Bits(width bits)
        val carry_i =   in  Bool()
        val sum_o   =   out Bits(width bits)
        val carry_o =   out Bool()
    }

    val carries = Vec(Bool(), width + 1)
    carries(0) := io.carry_i

    for(i <- 0 until width){
        val adder = FullAdder().setName(s"fulladder_bit_$i")

        adder.io.a_i     := io.a_i(i)
        adder.io.b_i     := io.b_i(i)
        adder.io.carry_i := carries(i)
        io.sum_o(i)      := adder.io.sum_o 
        carries(i + 1)   := adder.io.carry_o 
    }
    io.carry_o := carries(width)
}

//you can substitute any number, depending on which bit depth you want to generate
//for example, 32-bit adder
object FullAdderWidthVerilog extends App {
  SpinalConfig(
    targetDirectory = "rtl"
  ).generateSystemVerilog(FullAdderWidth(32)) 
}