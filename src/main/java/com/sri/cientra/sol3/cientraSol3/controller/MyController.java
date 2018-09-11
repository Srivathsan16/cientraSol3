package com.sri.cientra.sol3.cientraSol3.controller;



import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@RestController
public class MyController {
    List<String> intent1 = null;
    List<String> messages1 = null;
public  Map<String,Boolean> trainedMap = new HashMap<>();
    public  Map<String,Integer> weightMap = new HashMap<>();
    public  List<String> finalMap = new LinkedList<>();
    Map<String,Integer> trueMap = new HashMap<>();
    Map<String,Integer> falseMap = new HashMap<>();


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
            for(Map.Entry<String,Integer> en : trueMap.entrySet()){
                if(falseMap.containsKey(en.getKey())){
                    if(en.getValue()>falseMap.get(en.getKey())){
                        trainedMap.put(en.getKey(),true);
                    }
                    else{
                        trainedMap.put(en.getKey(),false);
                    }
                }
            }
            //TODO:Remove if confused These are Just for Understanding and debugging
        System.out.println("Trained map Size ::" + trainedMap.size());
        System.out.println("Trained map Size ::" + trainedMap);
        System.out.println("true map Size ::" + trueMap.size());
        System.out.println("true map  ::" + trueMap);
        System.out.println("false map Size ::" + falseMap.size());
        System.out.println("false map  ::" + falseMap);


        testMyMachine(trainedMap);
        System.out.println("Test trained data :::  testDataset ::::::" + finalMap.size());
        callRealWorldEmail(trainedMap);
    }
    private void splitMessages(String intent, String message) {

        String[] messageContent = message.split(" ");
        for(String sp: messageContent){
               if (intent.equals("Yes")) {
                    if (trueMap.containsKey(sp))
                        trueMap.put(sp, trueMap.get(sp) + 1);
                    else{
                        trueMap.put(sp,1);
                    }
                }
                if (intent.equals("No")) {
                    if (falseMap.containsKey(sp))
                        falseMap.put(sp, falseMap.get(sp) + 1);
                    else{
                        falseMap.put(sp,1);
                    }

                }
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
        }
        for(int s=0;s<messagesTest.size();s++){
            if(trainedMap.containsKey(messagesTest.get(s))){
                if(trainedMap.get(messagesTest.get(s))) {
                    finalReal.add(true);
                }
                else{
                    finalReal.add(false);
                }
            }
        }

        if(finalReal.contains(false)){
            System.out.println("FindReal:::" + finalReal);
            System.out.println("Intent is No");

        }
        else{
            System.out.println("Intent is Yes");
        }

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
               if (trainedMap.get(msg)) {
                    if (intentChec.equals("Yes")) {
                        if (!finalMap.contains(messageChec))
                        finalMap.add(messageChec);
                    }
                }
            }
        }


    }



}



