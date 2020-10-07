import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class Ecobee_Task_1_Read_test_result_JSON
{
    /**
     * This code reads test_result.json file and print corresponding data of each test case
     * Test cases are further grouped into three categories: Pass, Fail, Blocked
     * Whole code is written in one Main function block for ease of running, simply press Run command
     * Using java version "1.8.0_251"
     * Using json-simple 1.1.1 through POM.XML
     * Code is written in IntelliJ IDEA IntelliJ IDEA 2020.1.2 (Community Edition)
     * Written By Anvesh Poluri, Email: anvesh.poluri@gmail.com, Date: July_13_2020
     */
    public static void main(String[] args) throws IOException, ParseException
    {
        // Parsing file "test_results.json"
        // Reading file name of test_results.json using FileReader
        Object test_results_obj_1 = new JSONParser().parse(new FileReader("test_results.json"));

        // Typecasting test_results_obj_1 into a JSON object
        //Layer_1
        JSONObject test_results_Jobj_1 = (JSONObject) test_results_obj_1;

        //Loading "test_suites" into test_suites_Jarray_1
        // Layer_1.1
        JSONArray test_suites_Jarray_1 = (JSONArray) test_results_Jobj_1.get("test_suites");
         /*
          getting Inside layer_1.1 of "test_suites"
          Once Inside we are finding the Test_suite name and reading the Results
          Results are stored in one more layer deep inside "test_suites" for every available suite
          So we are Iterating through the size of array to get all the available test suites
         */
        for (Object o : test_suites_Jarray_1)
        {
            // Typecasting test_suites_Jarray_1 into a JSON object
            //Iterating through every element in layer_1.1 of "test_suites"
            JSONObject temp_loop_Jobj_1 = (JSONObject) o;

            // Getting Suite Name and Printing it
            String suite_name = (String) temp_loop_Jobj_1.get("suite_name");
            System.out.println("/****************************************/");// For Format setting
            System.out.println("The Test Suite Name is  " + suite_name);
            System.out.println();// For Format setting

            // Loading the "results" of each "test_suites" into 'results_Jarray_2'
            // Layer_1.1.(1-2-.....)
            JSONArray results_Jarray_2 = (JSONArray) temp_loop_Jobj_1.get("results");
            /*
             getting Inside layer_1.1.1 & layer_1.1.2 of Results
             we are Iterating through the size of array it to get the following:
             1.We are finding total Number of Test Cases
             2.Once inside we are finding Each Test Case run time and Execution status
             3.Storing Test Case information inside a JAVA map and Printing it
            */
            // Creating Tree MAps for Each Execution Status
            // Because of TreeMap we can print our results in Ascending order according to test case number
            Map<Integer, String> pass_map = new TreeMap<>();
            Map<Integer, String> fail_map = new TreeMap<>();
            Map<Integer, String> block_map = new TreeMap<>();

            // Creating a Variable to store total number of test cases that took run time more than 10secs
            int testcases_longer_than_10sec = 0;

            /*
              Getting Test Case Number from each test case name
              In order to arrange the output results of each test case in Ascending order we are doing this
              Once we have the test case number we are using it as a 'Key' for a map
              We are creating a Tree map with Key & value pair so that our results are sorted automatically based on Key
              Here Key is Test case number
              and its corresponding value is the output result we want to print for each test case
             */
            for (Object value : results_Jarray_2) {

                // Typecasting results_Jarray_2 into a JSON object temp_loop_Jobj_2
                //Iterating through every element in layer_1.1.1 ... of "test_suites"
                JSONObject temp_loop_Jobj_2 = (JSONObject) value;

                // Getting test_name of each Test Case
                if (temp_loop_Jobj_2.containsKey("test_name"))
                {
                    String test_name = (String) temp_loop_Jobj_2.get("test_name");
                    // Getting Total Execution time of each Test Case
                    String test_time = (String) temp_loop_Jobj_2.get("time");
                    //Getting Execution Status of each Test Case
                    String test_status = (String) temp_loop_Jobj_2.get("status");
                      /*
                  Getting Test Case Number from each test case name
                  In order to arrange the output results of each test case in Ascending order we are doing this
                  Once we have the test case number we are using it as a 'Key' for a map
                  We are creating a Tree map with Key & value pair so that our results are sorted automatically based on Key
                  Here Key is Test case number
                  and its corresponding value is the output result we want to print for each test case
                 */
                    // Getting Test Case number from test case name
                    String numberOnly = test_name.replaceAll("[^0-9]", "");
                    //Casting the test case number into Integer
                    int test_case_number = Integer.parseInt(numberOnly);
                    // Based on the Execution Status the output of each test case is passed into corresponding Map
                    switch (test_status) {
                        case "pass": {
                            // Storing the output into a temp_string
                            String temp_string = ("The test called " + test_name + " ran for "
                                    + test_time + " secs and it " + test_status);
                            // Adding Pair of Key= 'test_case_number', Value='temp_string'
                            pass_map.put(test_case_number, temp_string);
                            break;
                        }
                        case "fail": {
                            // Storing the output into a temp_string
                            String temp_string = ("The test called " + test_name + " ran for "
                                    + test_time + " secs and it " + test_status);
                            // Adding Pair of Key= 'test_case_number', Value='temp_string'
                            fail_map.put(test_case_number, temp_string);
                            break;
                        }
                        case "blocked": {
                            // Storing the output into a temp_string
                            String temp_string = ("The test called " + test_name + " ran for "
                                    + test_time + " 0.00 secs and it " + test_status);
                            // Adding Pair of Key= 'test_case_number', Value='temp_string'
                            block_map.put(test_case_number, temp_string);
                            break;
                        }
                    }
                    // Code to organise the test cases that longer than 10 secs
                    if (test_time.isEmpty())
                    {
                        // In case of Null inside the test time this block exists
                        // Later we can use this to identify Blocked test Cases which don't have execution time
                    }
                    else
                    {
                        // Typecasting String of test_time into Double
                        double test_case_time = Double.parseDouble(test_time);
                        if (test_case_time > 10.0000)
                        {
                            // For every test case 10+ secs we are increasing the count
                            testcases_longer_than_10sec++;
                        }

                    }
                }




            }
            /*
              We captured all the Results of One Test Suite Now and Printing them in Output
             */
            if (pass_map.size() == 0) {
                //If Total Number of Test Cases that are passed 0 then this will Print
                System.out.println("The total number of test cases that passed are: 0 ");
            } else {
                // Out of total Number of test Cases in this Execution Status
                System.out.println("The total number of test cases that passed are: " + pass_map.size());
            }
            for (Map.Entry<Integer, String> e : pass_map.entrySet()) {
                // Printing Each TEst CAse Details that got Pass
                System.out.println(e.getValue());
            }
            System.out.println(); // For Format setting
            if (fail_map.size() == 0) {
                //If Total Number of Test Cases that are failed 0 then this will Print
                System.out.println("The total number of test cases that Failed are: 0 ");
            } else {
                // Out of total Number of test Cases in this Execution Status
                System.out.println("The total number of test cases that Failed are: " + fail_map.size());
            }
            for (Map.Entry<Integer, String> e : fail_map.entrySet()) {
                // Printing Each TEst CAse Details that got failed
                System.out.println(e.getValue());
            }
            System.out.println();// For Format setting
            if (block_map.size() == 0) {
                //If Total Number of Test Cases that are Blocked 0 then this will Print
                System.out.println("The total number of test cases that are Blocked : 0 ");
            } else {
                // Out of total Number of test Cases in this Execution Status
                System.out.println("The total number of test cases that are Blocked: " + block_map.size());
            }
            /*
              If you want details of blocked test cases please remove this comment and Run the code for this block
             for (Map.Entry<Integer, String> e : block_map.entrySet())
             {
             // Printing Each TEst CAse Details that got blocked
             System.out.println(e.getValue());
             }
             */
            System.out.println();// For Format setting
            System.out.println("The Total number of test cases took more " +
                    "than 10 seconds to execute are: " + testcases_longer_than_10sec);
            System.out.println();// For Format setting

        }
    }
}
