package TOP

import spinal.core._
import spinal.sim._
import spinal.core.sim._
import ALU.Alu
import Memory.RegisterFile
import Memory.InstrMem 

object TopSim extends App{
  SimConfig
  .withVerilator
  .compile(new Top())
  .doSim{ dut =>

    dut.clockDomain.forkStimulus(10)

    println(s"Test has been started")
    dut.io.rst_i #= true
    dut.io.sw_i #= 0

    sleep(10)
    dut.io.rst_i #= false

    dut.io.sw_i #= 0x042 // 16'b100001000

    println("Test has been started")

    for(_ <- 0 until 40) {
      dut.clockDomain.waitSampling()
      val pc = dut.RA.toLong.toHexString
      val instr = dut.RD.toLong.toHexString
      val out = dut.io.out_o.toLong
      println(s"PC: 0x$pc | Instr: 0x$instr | Out: $out")
    }

    println("\nThe test is over\n")
    simSuccess()
  }
}