package DTF98.MaxInXLSX.service;

import DTF98.MaxInXLSX.util.ExcelReader;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class ExcelService {

    public int getMaxNthFromExcel(String filePath, int n) {
        List<Integer> numbers = ExcelReader.readNumbersFromExcel(filePath);
        if (numbers.size() >= 100000) {
            try {
                return findNthMaxMultiThread(numbers, n);
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException("Ошибка при поиске максимального значения в многопоточном методе");
            }
        } else {
            return findNthMaxSingleThread(numbers, n);
        }
    }

    private int findNthMaxSingleThread(List<Integer> numbers, int n) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(n);

        for (int num : numbers) {
            if (minHeap.size() < n) {
                minHeap.offer(num);
            } else if (minHeap.peek() < num) {
                minHeap.poll();
                minHeap.offer(num);
            }
        }

        return minHeap.peek();
    }

    private int findNthMaxMultiThread(List<Integer> numbers, int n) throws ExecutionException, InterruptedException {
        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<PriorityQueue<Integer>>> futures = new ArrayList<>();

        int chunkSize = numbers.size() / numThreads;
        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = (i == numThreads - 1) ? numbers.size() : start + chunkSize;
            List<Integer> subList = numbers.subList(start, end);
            futures.add(executor.submit(() -> findTopN(subList, n)));
        }

        PriorityQueue<Integer> finalHeap = new PriorityQueue<>(n);
        for (Future<PriorityQueue<Integer>> future : futures) {
            PriorityQueue<Integer> heap = future.get();
            for (int num : heap) {
                if (finalHeap.size() < n) {
                    finalHeap.offer(num);
                } else if (num > finalHeap.peek()) {
                    finalHeap.poll();
                    finalHeap.offer(num);
                }
            }
        }

        executor.shutdown();
        return finalHeap.peek();
    }

    private PriorityQueue<Integer> findTopN(List<Integer> numbers, int n) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(n);

        for (int num : numbers) {
            if (minHeap.size() < n) {
                minHeap.offer(num);
            } else if (num > minHeap.peek()) {
                minHeap.poll();
                minHeap.offer(num);
            }
        }

        return minHeap;
    }
}
