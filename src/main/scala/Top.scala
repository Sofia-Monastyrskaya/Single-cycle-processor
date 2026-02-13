package TOP

import spinal.core._
import spinal.core.sim._
import ALU.Alu
import ALU.AluOpcodes
import Memory.RegisterFile
import Memory.InstrMem

class Top() extends Component {
  val io = new Bundle {
    val clk_i = in  Bool()
    val rst_i = in  Bool()
    val sw_i  = in  Bits(16 bits)
    val out_o = out Bits(32 bits)
  }

  val RA        = Reg (Bits(32 bits)) init(0)
  val RD        = Bits(32 bits)
  val RD1       = Bits(32 bits)
  val RD2       = Bits(32 bits)
  val NextPc    = Bits(32 bits)
  val WE        = Bool()
  val se1       = RD  (5  downto 0)  
  val se2       = RD  (13 downto 0)  
  val se3       = RD  (21 downto 0)
  val SE1       = Bits(32 bits)
  val SE2       = Bits(32 bits)
  val SE3       = Bits(32 bits)
  val WA        = RD  (4  downto 0)
  val WD        = Bits(32 bits)
  val Result    = Bits(32 bits)
  val Flag      = Bool()

  val IMem      = InstrMem()
  val RegFile   = RegisterFile()
  val alu       = Alu()

  RA.simPublic() 
  RD.simPublic() 

  val four = U(4, 32 bits)
  val cond = (Flag && (RD(30))) || (RD(31))

  NextPc := (Mux(cond, RA.asUInt + four + SE3.asUInt, RA.asUInt + four)).asBits

  when(io.rst_i){
    RA := B(0, 32 bits)
  }otherwise{
    RA := NextPc
  }

  //Instr Mem
  IMem.io.read_addr_i := RA
  RD := IMem.io.read_data_o

  WE := !(RD(31)|RD(30))

  SE1 := (se1.asSInt.resize(32) << 2).asBits(31 downto 0)
  val sw_sign = B(16 bits, default -> io.sw_i(15)) 
  SE2 := ((sw_sign ## se2).asSInt.resize(32) << 2).asBits(31 downto 0)
  SE3 := (se3.asSInt.resize(32) << 2).asBits(31 downto 0)

  //Mux
  WD := RD(29 downto 28).mux(
    B"00" -> SE1,
    B"01" -> Result,
    B"10" -> SE2,
    B"11" -> B(0, 32 bits)
  )

  //Register file
  RegFile.io.write_enable_i := WE
  RegFile.io.read_addr1_i   := RD(22 downto 18)
  RegFile.io.read_addr2_i   := RD(17 downto 13)
  RegFile.io.write_addr_i   := WA
  RegFile.io.write_data_i   := WD
  RD1                       := RegFile.io.read_data1_o
  RD2                       := RegFile.io.read_data2_o

  //ALU
  alu.io.a_i                := RD1.asUInt
  alu.io.b_i                := RD2.asUInt
  alu.io.carry_i            := False 
  alu.io.alu_op_i.assignFromBits(RD(27 downto 23))
  Result                    := alu.io.result_o.asBits
  Flag                      := alu.io.flag_o

  //Out
  io.out_o := RD1
}

object TopVerilog extends App{
    SpinalConfig(
    targetDirectory = "rtl"
  ).generateSystemVerilog(new Top())
}