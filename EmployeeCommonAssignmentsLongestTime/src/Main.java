import java.io.File;
import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

        File file = new File("D:\\EmployeeCommonAssignmentsLongestTime\\src\\empDB.txt");
        Scanner scan = new Scanner(file);
        List<Employee> employees = new ArrayList<>();

        generateListOfEmployees(scan, employees);

        Map<List<Integer>,Long> empPairs = new HashMap<>();

        generateMapOfParedEmployees(employees, empPairs);

        long maxTime = Long.MIN_VALUE;

        maxTime = getMaxTime(empPairs, maxTime);

        EmpPareOutput(empPairs, maxTime);
    }

    public static void generateMapOfParedEmployees(List<Employee> employees, Map<List<Integer>, Long> empPairs) {
        for (int i = 0; i < employees.size(); i++) {
            for (int j = 1; j < employees.size(); j++) {
               if(employees.get(i).getProjectId() == employees.get(j).getProjectId()) {

                    if (employees.get(i).getDateFrom().compareTo(employees.get(j).getDateFrom())<=0  &&
                        employees.get(i).getDateTo().compareTo(employees.get(j).getDateFrom())<=0 ||
                        employees.get(i).getDateFrom().compareTo(employees.get(j).getDateFrom())>=0  &&
                        employees.get(i).getDateTo().compareTo(employees.get(j).getDateFrom())>=0) {

                        long timeTogether = 0;

                        List<Integer> pair = getEmpPare(employees, i, j);

                        empPairs.putIfAbsent(pair,timeTogether);

                    } else if(employees.get(i).getDateFrom().compareTo(employees.get(j).getDateFrom())<=0  &&
                            employees.get(i).getDateTo().compareTo(employees.get(j).getDateFrom())>=0) {

                        long timeTogether = (employees.get(i).getDateTo().getTime()- employees.get(j).getDateFrom().getTime())/86400000;

                        List<Integer> pair = getEmpPare(employees, i, j);

                        if(empPairs.containsKey(pair)) {
                            empPairs.put(pair,timeTogether+ empPairs.get(pair));
                        }

                        empPairs.put(pair,timeTogether);

                    } else if(employees.get(j).getDateFrom().compareTo(employees.get(i).getDateFrom())<=0 &&
                            employees.get(i).getDateTo().compareTo(employees.get(j).getDateFrom())>=0) {

                        long timeTogether = (employees.get(j).getDateTo().getTime()- employees.get(i).getDateFrom().getTime())/86400000;

                        List<Integer> pair = getEmpPare(employees, i, j);

                        if(empPairs.containsKey(pair)) {
                            empPairs.put(pair,timeTogether+ empPairs.get(pair));
                        }


                        empPairs.put(pair,timeTogether);


                    } else if (employees.get(j).getDateFrom().compareTo(employees.get(i).getDateFrom())>=0 &&
                            employees.get(j).getDateTo().compareTo(employees.get(i).getDateTo())<=0) {

                        long timeTogether = (employees.get(j).getDateTo().getTime()- employees.get(j).getDateFrom().getTime())/86400000;

                        List<Integer> pair = getEmpPare(employees, i, j);

                        if(empPairs.containsKey(pair)) {
                            empPairs.put(pair,timeTogether+ empPairs.get(pair));
                        }

                        empPairs.put(pair,timeTogether);

                    }else if(employees.get(i).getDateFrom().compareTo(employees.get(j).getDateFrom())>=0 &&
                            employees.get(i).getDateTo().compareTo(employees.get(j).getDateTo())<=0) {

                        long timeTogether = (employees.get(i).getDateTo().getTime()- employees.get(i).getDateFrom().getTime())/86400000;

                        List<Integer> pair = getEmpPare(employees, i, j);

                        if(empPairs.containsKey(pair)) {
                            empPairs.put(pair,timeTogether+ empPairs.get(pair));
                        }


                        empPairs.put(pair,timeTogether);

                    }
                }
            }
            employees.remove(i--);
        }
    }

    public static void generateListOfEmployees(Scanner scan, List<Employee> employees) {

        while (scan.hasNextLine()) {

            String[] empTokens = scan.nextLine().split(", ");

            int empId = Integer.parseInt(empTokens[0]);
            int projectId = Integer.parseInt(empTokens[1]);
            Date dateFrom;
            Date dateTo;

            try {
                dateFrom = new SimpleDateFormat("yyyy-MM-dd").parse(empTokens[2]);

                if(!empTokens[3].equals("NULL")) {
                     dateTo = new SimpleDateFormat("yyyy-MM-dd").parse(empTokens[3]);
                } else {
                    LocalDateTime today = LocalDateTime.now();
                    String date = today.toString();
                    dateTo = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                }

                Employee employee = new Employee(empId,projectId,dateFrom,dateTo);

                employees.add(employee);

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }

    public static long getMaxTime(Map<List<Integer>, Long> empPairs, long maxTime) {
        for (var employeePair : empPairs.entrySet()) {
            if(employeePair.getValue() > maxTime) {
                maxTime = employeePair.getValue();
            }
        }
        return maxTime;
    }


    public static void EmpPareOutput(Map<List<Integer>, Long> empPairs, long maxTime) {
        if(maxTime <=0){
            System.out.println("There are no employees that worked together on common projects!");
        } else {
            for (var employeePair : empPairs.entrySet()) {
                if (employeePair.getValue() == maxTime) {
                    System.out.println("Employees " + employeePair.getKey() + " worked together the longest.");
                    break;
                }
            }
        }
    }

    public static List<Integer> getEmpPare(List<Employee> employees, int i, int j) {
        List<Integer> pair = new ArrayList<>();
        Integer emp1 = employees.get(i).getEmpId();
        Integer emp2 = employees.get(j).getEmpId();
        pair.add(emp1);
        pair.add(emp2);
        return pair;
    }
}
