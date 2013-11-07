package org.skife.gctail.cli;

public class Main
{
    public static void main(String[] args)
    {
        while (!Thread.interrupted()) {
            new String("hello world");
        }
        System.out.println("hello world");
    }
}
