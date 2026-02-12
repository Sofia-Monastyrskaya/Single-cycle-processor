package ALU

import spinal.core._

object AluOpcodes extends SpinalEnum{
    val ADD, SUB, XOR, OR, AND, SRA, SRL, SLL, LTS, LTU, GES, GEU, EQ, NE, SLTS, SLTU = newElement()

    defaultEncoding = SpinalEnumEncoding("myEncoding")(
        ADD  -> BigInt("00000", 2),
        SUB  -> BigInt("01000", 2),
        XOR  -> BigInt("00100", 2),
        OR   -> BigInt("00110", 2),
        AND  -> BigInt("00111", 2),
        SRA  -> BigInt("01101", 2),
        SRL  -> BigInt("00101", 2),
        SLL  -> BigInt("00001", 2),
        LTS  -> BigInt("11100", 2),
        LTU  -> BigInt("11110", 2),
        GES  -> BigInt("11101", 2),
        GEU  -> BigInt("11111", 2),
        EQ   -> BigInt("11000", 2),
        NE   -> BigInt("11001", 2),
        SLTS -> BigInt("00010", 2),
        SLTU -> BigInt("00011", 2)
    )
}