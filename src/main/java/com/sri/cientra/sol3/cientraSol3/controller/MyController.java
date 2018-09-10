package com.sri.cientra.sol3.cientraSol3.controller;


import com.sri.cientra.sol3.cientraSol3.domain.WeightOfString;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@RestController
public class MyController {
    WeightOfString ws = new WeightOfString();
    List<String> intent1 = null;
    List<String> messages1 = null;
public  Map<String,Boolean> trainedMap = new HashMap<>();
    public  List<String> finalMap = new LinkedList<>();


    @RequestMapping(value = "/trainMachine")
    public void trainMyMachine() throws FileNotFoundException {

        Scanner scan = new Scanner(new File("C:\\Users\\Prane\\Desktop\\sri\\enron_train.txt"));
        List<String> intent = new ArrayList<>();
        List<String> messages = new ArrayList<>();

        while(scan.hasNext()){
            String curLine = scan.nextLine();
            String[] splitted = curLine.split("\t");
            String intentStr = splitted[0].trim();
            String  message = splitted[1].trim();
            intent.add(intentStr);
            messages.add(message);
         }
        //intent1 = intent;
       // messages1 = messages;
        scan.close();
        for(int x=0;x< intent.size();x++){
            splitMessages(intent.get(x),messages.get(x));
        }
        callRealWorldEmail(trainedMap);
        testMyMachine(trainedMap);
    }
    private void splitMessages(String intent, String message) {
        String[] messageContent = message.split(" ");
        for(String sp: messageContent){
            if(!trainedMap.containsKey(sp)) {
                trainedMap.put(sp, intent.equals("Yes") ? true : false);
            }
            else if (trainedMap.containsKey(sp) && (trainedMap.get(sp)))
                trainedMap.put(sp, intent.equals("Yes") ? true : false);
        }
    }

    private void callRealWorldEmail(Map<String, Boolean> trainedMap) throws FileNotFoundException {
         Map<String,Boolean> finalMap = new HashMap<>();
        Scanner scan = new Scanner(new File("C:\\Users\\Prane\\Desktop\\sri\\enron_train_real.txt"));
       List<Boolean> finalReal = new ArrayList<>();
        List<String> messagesTest = new ArrayList<>();
        while(scan.hasNext()){
            String curLine = scan.nextLine();
            String[] mess = curLine.split(" ");
            for(String s2: mess){
             messagesTest.add(s2);
            }
           for(String s: messagesTest) {
                System.out.println("MesseageTest :: is  ::" + s);
            }
        }
        for(int s=0;s<messagesTest.size();s++){
            if(trainedMap.containsKey(messagesTest.get(s))){
                if(trainedMap.get(messagesTest.get(s))) {
                    System.out.println(" if 2 Coming here:::" + trainedMap.get(messagesTest.get(s)));
                    finalReal.add(true);
                }
                else{
                    System.out.println(" if 3 Coming here:::" + trainedMap.get(messagesTest.get(s)));
                    finalReal.add(false);
                }
            }
        }

        /*for(String s: messagesTest){
            if(trainedMap.containsKey(s)){
                System.out.println("If 1 ::::::" + trainedMap.get(s) + " S value >>>>>" + s);
                if(trainedMap.get(s)) {
                    System.out.println(" if 2 Coming here:::" + trainedMap.get(s));
                    finalReal.add(true);
                }
                else{
                    System.out.println(" if 3 Coming here:::" + trainedMap.get(s));
                    finalReal.add(false);
                }
            }

        }*/
        if(finalReal.contains(true)){
            System.out.println("Intent is Yes");
        }
        else{
            System.out.println("In Here:::Intent is No");
        }

        System.out.println("Could value ::::" + trainedMap.get("How"));
    }



public void testMyMachine(Map<String, Boolean> trainedMap) throws FileNotFoundException {
    Scanner scan = new Scanner(new File("C:\\Users\\Prane\\Desktop\\sri\\enron_test.txt"));
    List<String> intentTest = new ArrayList<>();
    List<String> messagesTest = new ArrayList<>();
    while(scan.hasNext()){
        String curLine = scan.nextLine();
        String[] splitted = curLine.split("\t");
        String intentStr = splitted[0].trim();
        String  messageT = splitted[1].trim();
        intentTest.add(intentStr);
        messagesTest.add(messageT);

    }
    for(int i =0;i<intentTest.size();i++){
        splitMessagestoCheck(intentTest.get(i),messagesTest.get(i));

        }

    }

    private void splitMessagestoCheck(String intentChec, String messageChec) {
        String[] checkMsg = messageChec.split(" ");
        for (String msg : checkMsg) {

            if (trainedMap.containsKey(msg)) {
               if (trainedMap.get(msg) && intentChec.equals("Yes")) {
                    finalMap.add(messageChec);
                   // System.out.println("Intent is Yes::::::" + trainedMap.get(msg));

                } else {
                   // System.out.printf("Intent is No");
                }
            }
        }

        System.out.println("Final map ::" + finalMap.size());
    }



}



