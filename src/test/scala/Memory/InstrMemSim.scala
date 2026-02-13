package Memory

import spinal.sim._
import spinal.core.sim._

object InstrMemSim extends App{
    SimConfig
    .withVerilator
    .compile(InstrMem())
    .doSim{ dut =>
        
        dut.clockDomain.forkStimulus(10)

        for(addr <- 0 until 6){ 
            dut.io.read_addr_i #= addr
            dut.clockDomain.waitRisingEdge()
            println(f"Read addr $addr: 0x${dut.io.read_data_o.toLong}%08X")
        }
        
        simSuccess()
    }
}
