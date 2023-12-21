// Run() is called from Scheduling.main() and is where
// the scheduling algorithm written by the user resides.
// User modification should occur within the Run() function.
package org.example;

import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Vector;
import java.io.*;

public class SchedulingAlgorithm {

  public static Results Run(int runtime, Vector<sProcess> processVector, Results result) {
    int comptime = 0;
    sProcess currentProcess;

    PriorityQueue<sProcess> queue = new PriorityQueue<>(Comparator.comparingInt(sProcess::getCputime));
    queue.addAll(processVector);

    String resultsFile = "Summary-Processes";

    try {
      PrintStream out = new PrintStream(new FileOutputStream(resultsFile));

      while (comptime < runtime && !queue.isEmpty()) {
        currentProcess = queue.poll(); // Вибір процесу з найкоротшим часом виконання

        // Записуємо, що процес зареєстровано
        out.println("Process: " + currentProcess.hashCode() + " registered... (" + currentProcess.getCputime() + " " + currentProcess.getIoblocking() + " " + currentProcess.getCpudone() + " " + currentProcess.getNumblocked() + ")");

        // Виконання поточного процесу
        while (currentProcess.getCpudone() < currentProcess.getCputime() && comptime < runtime) {
          currentProcess.setCpudone(currentProcess.getCpudone() + 1);
          comptime++;
        }

        // Записуємо, що процес завершився
        out.println("Process: " + currentProcess.hashCode() + " completed. CPU Time: " + currentProcess.getCputime() + " ms, CPU Done: " + currentProcess.getCpudone() + " ms");
      }

      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    result.compuTime = comptime;
    return result;
  }
}

  /* private static Vector<sProcess> setPriority(Vector<sProcess> processes) {
    int priority= 0;
    int prevTime = 0;
    processes.sort(Comparator.comparing(sProcess::getCputime).thenComparing(sProcess::getIoblocking));

    for (sProcess process : processes) {
      if(prevTime < process.getCputime()) {
        priority += 1;
      }
      prevTime = process.getCputime();
      process.setPriority(priority);
    }
    return processes;
  }
}*/
