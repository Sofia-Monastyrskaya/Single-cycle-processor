package Memory

import spinal.sim._
import spinal.core.sim._

object RegisterFileSim extends App{
    SimConfig
    .withVerilator
    .compile(RegisterFile())
    .doSim{ dut =>
        dut.clockDomain.forkStimulus(10)
        dut.io.write_enable_i #= false
        dut.io.write_addr_i #= 0
        dut.io.write_data_i #= 0
        dut.io.read_addr1_i #= 0
        dut.io.read_addr2_i #= 1

        dut.clockDomain.waitSampling()

        dut.io.write_enable_i #= true
        dut.io.write_addr_i #= 0
        dut.io.write_data_i #= 123
        dut.clockDomain.waitSampling()
        assert(dut.io.read_data1_o.toInt == 0)

        dut.io.write_addr_i #= 1
        dut.io.write_data_i #= 42
        dut.clockDomain.waitSampling()

        dut.io.write_enable_i #= false
        dut.clockDomain.waitSampling()

        dut.io.read_addr1_i #= 1
        dut.clockDomain.waitSampling()
        println(s"Read reg1 = ${dut.io.read_data1_o.toInt}")

        dut.io.read_addr1_i #= 0
        dut.clockDomain.waitSampling()
        println(s"Read reg0 = ${dut.io.read_data1_o.toInt}")
    }
}