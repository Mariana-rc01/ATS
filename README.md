# ATS

UMinho's 2024/25 Software Testing and Analysis class assignment. This assignment consisted of
testing a previous year's [Object Oriented Programming Project](https://github.com/pedrofp4444/POO)
using unit testing and automatically generated tests, with branch and mutant coverage analysis. See
[Assignment.pdf](Assignment.pdf) for more details.

## Grade: 18.5 / 20 :star:

### Authors

 - Humberto Gomes (A104348)
 - José Lopes (A104541)
 - Mariana Rocha (A90817)

### Running

To get the code in this repository, clone it:

```
$ git clone https://github.com/voidbert/ATS.git
$ cd ATS
```

Then, you can build run the application:

```
$ gradle run
```

There are multiple test suites. To execute the tests written by the previous authors, run:

```
$ gradle test -PtestDir=oldunittests
```

To execute the new unit tests, run:

```
$ gradle test
```
