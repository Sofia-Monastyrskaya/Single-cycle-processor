package ALU

import spinal.sim._
import spinal.core.sim._

object AluSim extends App{
  SimConfig
  .withVerilator
  .compile(Alu())
  .doSim{ dut =>

    dut.io.a_i #= 15
    dut.io.b_i #= 10
    dut.io.alu_op_i #= AluOpcodes.ADD
    sleep(1)
    println(s"ADD: ${dut.io.result_o.toBigInt}, carry: ${dut.io.carry_o.toBoolean}")

    dut.io.a_i #= 20
    dut.io.b_i #= 5
    dut.io.alu_op_i #= AluOpcodes.SUB
    sleep(1)
    println(s"SUB: ${dut.io.result_o.toBigInt}, carry: ${dut.io.carry_o.toBoolean}")

    dut.io.a_i #= 0xF0F0
    dut.io.b_i #= 0x0FF0
    dut.io.alu_op_i #= AluOpcodes.XOR
    sleep(1)
    println(f"XOR: 0x${dut.io.result_o.toBigInt}%X")

    dut.io.a_i #= 0x0F0F
    dut.io.b_i #= 0xF0F0
    dut.io.alu_op_i #= AluOpcodes.OR
    sleep(1)
    println(f"OR: 0x${dut.io.result_o.toBigInt}%X")

    dut.io.a_i #= 0x0F0F
    dut.io.b_i #= 0xF0F0
    dut.io.alu_op_i #= AluOpcodes.AND
    sleep(1)
    println(f"AND: 0x${dut.io.result_o.toBigInt}%X")

    dut.io.a_i #= BigInt("F0000000", 16)
    dut.io.b_i #= 4
    dut.io.alu_op_i #= AluOpcodes.SRA
    sleep(1)
    println(f"SRA: 0x${dut.io.result_o.toBigInt}%X")

    dut.io.a_i #= BigInt("F0000000", 16)
    dut.io.b_i #= 4
    dut.io.alu_op_i #= AluOpcodes.SRL
    sleep(1)
    println(f"SRL: 0x${dut.io.result_o.toBigInt}%X")

    dut.io.a_i #= 0x0000000FL
    dut.io.b_i #= 4
    dut.io.alu_op_i #= AluOpcodes.SLL
    sleep(1)
    println(f"SLL: 0x${dut.io.result_o.toBigInt}%X")

    dut.io.a_i #= 10
    dut.io.b_i #= 20
    dut.io.alu_op_i #= AluOpcodes.LTS
    sleep(1)
    println(s"LTS: flag=${dut.io.flag_o.toBoolean}")

    dut.io.a_i #= 10
    dut.io.b_i #= 5
    dut.io.alu_op_i #= AluOpcodes.LTU
    sleep(1)
    println(s"LTU: flag=${dut.io.flag_o.toBoolean}")

    dut.io.a_i #= 20
    dut.io.b_i #= 10
    dut.io.alu_op_i #= AluOpcodes.GES
    sleep(1)
    println(s"GES: flag=${dut.io.flag_o.toBoolean}")

    dut.io.a_i #= 5
    dut.io.b_i #= 10
    dut.io.alu_op_i #= AluOpcodes.GEU
    sleep(1)
    println(s"GEU: flag=${dut.io.flag_o.toBoolean}")

    dut.io.a_i #= 15
    dut.io.b_i #= 15
    dut.io.alu_op_i #= AluOpcodes.EQ
    sleep(1)
    println(s"EQ: flag=${dut.io.flag_o.toBoolean}")

    dut.io.a_i #= 15
    dut.io.b_i #= 20
    dut.io.alu_op_i #= AluOpcodes.NE
    sleep(1)
    println(s"NE: flag=${dut.io.flag_o.toBoolean}")

    dut.io.a_i #= 5
    dut.io.b_i #= 10
    dut.io.alu_op_i #= AluOpcodes.SLTS
    sleep(1)
    println(s"SLTS: result=${dut.io.result_o.toBigInt}")

    dut.io.a_i #= 5
    dut.io.b_i #= 10
    dut.io.alu_op_i #= AluOpcodes.SLTU
    sleep(1)
    println(s"SLTU: result=${dut.io.result_o.toBigInt}")

    simSuccess()
  }
}