# Lab1

## Description

The project focuses on the computational logic behind two key mathematical functions: `f(x)` and `g(x)`. Its features include:

- **User Input**: Allows the calculation of these expressions based on user-provided values.
- **Cancellation Handling**: Employs mechanisms to handle cancellations and exceptions gracefully.
- **Result Display**: Outputs the results of the computations in a clear and concise format.
- **Intuitive Interface**: Provides an easy-to-use interface for inputting values and displaying results.

### Technical Details

- **Language**: Java
- **Communication**: Unix-sockets
- **Synchronization**: Futures and Executors for async operations, non-blocking I/O
- **Computation Medium**: Processes and threads to ensure isolated and concurrent computations
- **Binary Operation**: Addition, which is applied when both computations yield valid results.


#Lab2

## Description

Lab2 enhances the process scheduling simulation with notable improvements and optimizations.

### Features

- **Algorithm Optimization with SJN:**
  The implementation of the Shortest Job Next (SJN) algorithm leverages a `PriorityQueue`, which automatically orders processes based on their CPU time. This ensures that the shortest jobs receive priority, streamlining the scheduling process and reducing wait times.
