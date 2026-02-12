package ALU

import spinal.core._
import Adder.FullAdderWidth

case class Alu() extends Component {
  val io         = new Bundle {
    val a_i      = in UInt(32 bits)
    val b_i      = in UInt(32 bits)
    val alu_op_i = in(AluOpcodes())
    val carry_i  = in Bool()
    val carry_o  = out Bool() 
    val flag_o   = out Bool()
    val result_o = out UInt(32 bits)
  }

  val adder = FullAdderWidth(32).setName("adder")
  adder.io.a_i     := io.a_i.asBits
  adder.io.b_i     := Mux(io.alu_op_i === AluOpcodes.SUB, (~io.b_i).asBits, io.b_i.asBits)
  adder.io.carry_i := Mux(io.alu_op_i === AluOpcodes.SUB, True, io.carry_i)

  //default
  io.result_o := 0
  io.flag_o   := False
  io.carry_o  := False

  switch(io.alu_op_i){
    is(AluOpcodes.ADD, AluOpcodes.SUB){
      io.result_o := adder.io.sum_o.asUInt
      io.carry_o  := adder.io.carry_o
    }
    is(AluOpcodes.XOR){
      io.result_o := io.a_i ^ io.b_i
    }
    is(AluOpcodes.OR){
      io.result_o := io.a_i | io.b_i
    }
    is(AluOpcodes.AND){
      io.result_o := io.a_i & io.b_i
    }
    is(AluOpcodes.SRA){
      io.result_o := ((io.a_i.asSInt >> io.b_i(4 downto 0)).asUInt).resized
    }
    is(AluOpcodes.SRL){
      io.result_o := (io.a_i >> io.b_i(4 downto 0)).resized
    }
    is(AluOpcodes.SLL){
      io.result_o := (io.a_i << io.b_i(4 downto 0)).resized
    }
    is(AluOpcodes.LTS){
      io.flag_o   := io.a_i.asSInt < io.b_i.asSInt
    }
    is(AluOpcodes.LTU){
      io.flag_o   := io.a_i < io.b_i
    }
    is(AluOpcodes.GES){
      io.flag_o   := io.a_i.asSInt >= io.b_i.asSInt
    }
    is(AluOpcodes.GEU){
      io.flag_o   := io.a_i >= io.b_i
    }
    is(AluOpcodes.EQ){
      io.flag_o   := io.a_i === io.b_i
    }
    is(AluOpcodes.NE){
      io.flag_o   := io.a_i =/= io.b_i
    }
    is(AluOpcodes.SLTS){
      io.result_o := (io.a_i.asSInt < io.b_i.asSInt).asUInt.resized
    }
    is(AluOpcodes.SLTU){
      io.result_o := (io.a_i < io.b_i).asUInt.resized
    }
  }
}

object AluVerilog extends App {
  SpinalConfig(
    targetDirectory = "rtl"
  ).generateSystemVerilog(Alu())
}