package Memory

import spinal.core._
import scala.io.Source

case class InstrMem() extends Component{
    val io = new Bundle{
        val read_addr_i = in Bits(32 bits)
        val read_data_o = out Bits(32 bits)
    }

    val ROM = Mem(Bits(32 bits), MemPkg.INSTR_MEM_SIZE_WORDS)
    
    val RomInitFile = Source.fromFile("src/main/scala/Memory/program.mem")
                 .getLines()
                 .map(line => BigInt(line.trim, 16))
                 .toArray
    
    val RomInit = Array.fill(MemPkg.INSTR_MEM_SIZE_WORDS)(BigInt(0))
    for(i <- RomInitFile.indices) RomInit(i) = RomInitFile(i)
    ROM.initBigInt(RomInit)

    io.read_data_o := ROM.readAsync(io.read_addr_i(log2Up(MemPkg.INSTR_MEM_SIZE_WORDS) - 1 downto 0).asUInt)
}

object InstrMemVerilog extends App {
  SpinalConfig(
    targetDirectory = "rtl"
  ).generateSystemVerilog(InstrMem())
}