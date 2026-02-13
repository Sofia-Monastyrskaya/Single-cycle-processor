
### Реализация однотактного процессора RISC-V с использованием [SpinalHDL](https://spinalhdl.github.io/SpinalDoc-RTD/master/index.html) (Scala).

Процессор выполняет инструкции за один такт и включает основные компоненты:

- **Регистровый файл (RegisterFile)**
    
- **Инструкционную память (InstrMem)**
    
- **АЛУ (Арифметико-логическое устройство) (ALU)**
    
- **Счётчик команд (Program Counter, PC)**
    

*В составе АЛУ есть модуль полного 32-х битного сумматора, который в свою очередь реализован на базе полного 1-битного сумматора.

### Симуляция

Симуляция выполнена с использованием [Verilator](https://github.com/verilator/verilator) и [Scala/SBT](https://www.scala-sbt.org/download/).
Пример запуска:
```
sbt "Test / runMain TOP.TopSim"
```

Пример генерации Verilog/SystemVerilog:
```
sbt "runMain TOP.TopVerilog"
```

В моем коде сейчас генерируется SystemVerilog, однако, если вы хотите получить именно Verilog, можно сделать так:
```
object TopVerilog extends App{
    SpinalConfig(
    targetDirectory = "rtl"
  ).generateVerilog(new Top())

}
```


### Развитие

Репозиторий будет пополняться новыми модулями по мере наличия свободного времени у автора (его не всегда много), также будут исправляться ошибки, поэтому с удовольствием почитаю ваши вопросы/отзывы.
