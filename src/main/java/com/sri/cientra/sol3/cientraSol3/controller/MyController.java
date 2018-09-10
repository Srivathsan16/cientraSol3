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
    public  Map<String,Boolean> finalMap = new HashMap<>();


    @RequestMapping(value = "/trainMachine")
    public void trainMyMachine() throws FileNotFoundException {

        Scanner scan = new Scanner(new File("C:\\Users\\Prane\\Desktop\\sri\\enron_train.txt"));
int count=0;
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
        //System.out.println("Intent List is:::" + intent.size());
       // System.out.println("Message List Size is ::" + messages.size());
       // System.out.println("Message List Size is ::" + messages.get(9));
        intent1 = intent;
        messages1 = messages;
        scan.close();
        for(int x=0;x< intent.size();x++){
            splitMessages(intent.get(x),messages.get(x));
        }
        //callRealWorldEmail(trainedMap);
        testMyMachine(trainedMap);
        //System.out.println("Count :: " + count);
        /*for(String x: intent1) {

            splitMessages(messages1,x);
        }*/
       // System.out.println("Intent List 1 is:::" + intent1.size());
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
          //  String intentStr = splitted[0].trim();
           // String  messageT = mess[1].trim();
            //System.out.println(messageT);
          //  intentTest.add(intentStr);
            //messagesTest.add(messageT);
            for(String s: messagesTest) {
                System.out.println("MesseageTest :: is  ::" + s);
            }
        }
        for(String s: messagesTest){
            if(trainedMap.containsKey(s)){
                System.out.println("If 1");
                if(trainedMap.get(s)==true)
                    System.out.println(" if 2 Coming here:::" + trainedMap.get(s));
                    finalReal.add(true);
            }
            else if (trainedMap.containsKey(s)){
                System.out.println(" if 3 Coming here:::" + trainedMap.get(s));
                finalReal.add(false);
            }
        }
        System.out.println("Train How" + trainedMap.get("How"));
        System.out.println("Train are" + trainedMap.get("are"));
        System.out.println("Train things" + trainedMap.get("things"));
        System.out.println("final map" + finalReal);
        if(finalReal.contains(true)){
            System.out.println("Intent is Yes");
        }
        else{
            System.out.println("In Here:::Intent is No");
        }
        finalMap = null;

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

        //System.out.println("Map:::" + trainedMap.size());
        //System.out.println("Map:::" + trainedMap);
       // checkcount();
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
        for(String msg : checkMsg){

            if(trainedMap.containsKey(msg)){
                System.out.println("TrainMap::" + trainedMap.get(msg));
                System.out.println("TrainMap Intent::" + intentChec);
                if (trainedMap.get(msg) && intentChec.equals("Yes")){
                   finalMap.put(messageChec,true);
                    System.out.println("Intent is Yes::::::" + trainedMap.get(msg));

                }
                else{
                    System.out.printf("Intent is No");
                }
            }
        }

     // System.out.println("Final map ::" + finalMap);
        System.out.println("Final map ::" + finalMap.size());
        //System.out.println("::Count::" + count);
    }


}



