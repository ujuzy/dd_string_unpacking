package com.dd.stringunpacking;

import java.util.Scanner;
import java.util.Stack;

public class Unpacker {
    public static void main(String[] args) {
        var in = new Scanner(System.in);

        System.out.print("Packed: ");

        var packedString = in.nextLine();

        System.out.println("Unpacked: " + unpack(packedString));
    }

    public static String unpack(String _packed) {
        var packed = new StringBuilder(_packed);
        var unpacked = new StringBuilder();

        do {
            unpacked.setLength(0);

            var idx = 0;
            while (idx < packed.length()) {
                if (Character.isAlphabetic(packed.charAt(idx))) {
                    unpacked.append(packed.charAt(idx));

                    idx++;
                    continue;
                }

                if (Character.isDigit(packed.charAt(idx))) {
                    var sequence = packed.substring(packed.indexOf("[", idx) + 1, packed.indexOf("[", idx) +
                            getSequenceBetweenBracketsLength(packed.substring(packed.indexOf("[", idx))));

                    unpacked.append(sequence.repeat(Math.max(0, Character.getNumericValue(packed.charAt(idx)))));
                    idx += sequence.length() + 3;
                }
            }

            packed = new StringBuilder(unpacked);
        } while (unpacked.toString().contains("[") && unpacked.toString().contains("]"));

        return unpacked.toString();
    }

    private static int getSequenceBetweenBracketsLength(String sequence) {
        var bracketsStack = new Stack<Character>();

        for (var idx = 0; idx < sequence.length(); ++idx) {
            if (sequence.charAt(idx) == '[') {
                bracketsStack.push(sequence.charAt(idx));
            }

            if (sequence.charAt(idx) == ']') {
                bracketsStack.pop();

                if (bracketsStack.isEmpty()) {
                    return idx;
                }
            }
        }

        return 0;
    }
}
