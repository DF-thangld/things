package algorithms_class._03_stack_and_queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) throws FileNotFoundException {
        int size = Integer.parseInt(args[0]);

        RandomizedQueue<String> queue = new RandomizedQueue<String>(size);

        String stringItem;
        int readItem = 0;
        do {
            stringItem = "";
            try {
                stringItem = StdIn.readString();
                readItem++;

                // if stringItem can't be read => end of input
                // take string samples according to Reservoir sampling using Algorithm R
                // https://en.wikipedia.org/wiki/Reservoir_sampling

                if (queue.size() < size) {
                    queue.enqueue(stringItem);
                }
                else {

                    int randomNumber = StdRandom.uniform(readItem);
                    if (randomNumber < size) {
                        queue.dequeue();
                        queue.enqueue(stringItem);
                    }
                }
            }
            catch (java.util.NoSuchElementException exc) {
                //StdOut.println(exc.toString());
            }
        } while (stringItem != "");

        Iterator<String> iterator = queue.iterator();
        while (iterator.hasNext()) {
            StdOut.println(iterator.next());
        }

    }
}
