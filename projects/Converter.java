/**
 * Created by Raymond Ho on 12/19/13.
 * This program implements a GUI which
 * accepts text/decimals and converts
 * them to decimals/ASCII respectively.
 * Converts using a generic queue class.
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.applet.*;
import java.awt.*;

class Word2Num extends Applet{
    public static void main (String[] args) {
        gui window = new gui();
        window.setVisible(true);
    }
}

class gui extends JFrame{
    //Initialize stuff.
    private JLabel lab1;
    private JLabel lab2;
    private JTextArea input;
    private JTextArea output;
    private JButton toDec;
    private JButton toAsc;

    public gui() {
        //Create the gui specs.
        setTitle("Word2Num");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new FlowLayout());

        //Label Stuff
        lab1 = new JLabel("Enter ASCII / Decimal input here:");
        lab2 = new JLabel("Output:");

        //Text stuff
        input = new JTextArea(5,23);
        input.setLineWrap(true);
        input.setWrapStyleWord(true);
        JScrollPane scrollin = new JScrollPane(input);

        output = new JTextArea(5,23);
        output.setEditable(false);
        output.setLineWrap(true);
        output.setWrapStyleWord(true);
        JScrollPane scrollout = new JScrollPane(output);

        //Button Stuff
        toDec = new JButton("I want Decimal!");
        toDec.addActionListener(new Action());
        toAsc = new JButton("I want ASCII!");
        toAsc.addActionListener(new Action());

        //Add to the actual gui.
        add(lab1);
        add(scrollin);
        add(lab2);
        add(scrollout);
        add(toDec);
        add(toAsc);

    }
    public class Action implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            //Makes sure text is blank before writing to it.
            output.setText(null);

            //If toDec button was clicked.
            if (e.getSource() == toDec){

                //Create a Queue of integers.
                Queue<Integer> intStack = new Queue<Integer>();
                //Reads input and store into 'in'
                String in = input.getText();

                //Iterate string; push each item in queue.
                for (char x : in.toCharArray())
                    intStack.push((int)x);

                //Iterate through the stack and prints each item.
                while (!intStack.isEmpty())
                    output.append(intStack.pop() + " ");
            }
            //If toAsc button was clicked.
            if (e.getSource() == toAsc){
                //Create a Queue of Characters.
                Queue<Character> intStack = new Queue<Character>();

                //Reads input and store into 'in'
                String in = input.getText();

                //Separate by whitespace.
                String[] inn = in.split(" ");

                //Create a new array of int
                int[] result = new int[inn.length];

                //Convert the string array to int array
                for (int i = 0; i < inn.length; i++)
                    try {
                        result[i] = Integer.parseInt(inn[i]);
                    }
                    catch(NumberFormatException err) {
                        output.setText("Number Format Exception");
                }

                //Iterate string; push each item in queue.
                for (int x : result)
                    intStack.push((char)x);

                //Iterate through the stack and prints each item.
                while (!intStack.isEmpty())
                    output.append(intStack.pop() + " ");

            }
        }
    }
}

//A generic Queue class.
class Queue<Item> {

    private Node first = null;
    private Node last = null;

    private class Node {
        Item item;
        Node link;
    }

    public boolean isEmpty() {
        //Returns true/false if top is empty.
        return first == null;
    }

    //Adds an 'item' to the end of the queue.
    public void push(Item item) {
        //Create a new node.
        Node newNode = new Node();
        //Node's item is the item.
        newNode.item = item;
        //Node points at null;
        newNode.link = null;
        //Assigns the first item to be the newNode
        if (first == null) first = newNode;
        //If first item isn't empty, last link points to newNode
        else last.link = newNode;
        //The newNode is now the last item.
        last = newNode;
    }

    //Returns an 'item' off the queue.
    public Item pop() {
        //Create a copy of the top node's item.
        Item tmp = first.item;
        //Set the first item to null for GC
        first.item = null;
        //First is it's link.
        first = first.link;
        //Return the copy of the item.
        return tmp;
    }
}
