package Memory

import spinal.core._

case class RegisterFile() extends Component {
  val io = new Bundle {
    val write_enable_i = in Bool()
    val write_addr_i   = in Bits(5 bits)
    val read_addr1_i   = in Bits(5 bits)
    val read_addr2_i   = in Bits(5 bits)
    val write_data_i   = in Bits(32 bits)
    val read_data1_o   = out Bits(32 bits)
    val read_data2_o   = out Bits(32 bits)
  }

  val RAM = Vec(Reg(Bits(32 bits)) init (0), 32)

  io.read_data1_o := Mux(io.read_addr1_i.asUInt === 0, B(0, 32 bits), RAM(io.read_addr1_i.asUInt))
  io.read_data2_o := Mux(io.read_addr2_i.asUInt === 0, B(0, 32 bits), RAM(io.read_addr2_i.asUInt))

  when(io.write_enable_i && io.write_addr_i.asUInt =/= 0){
    RAM(io.write_addr_i.asUInt) := io.write_data_i
  }
}

object RegisterFileVerilog extends App {
  SpinalConfig(
    targetDirectory = "rtl"
  ).generateSystemVerilog(RegisterFile())
}